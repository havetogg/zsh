package org.jumutang.zsh.controller;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.jumutang.zsh.model.ActivityModel;
import org.jumutang.zsh.model.PCUserModel;
import org.jumutang.zsh.model.ReceiveAddress;
import org.jumutang.zsh.services.ActivityServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Tinny.liang
 * @Description: 活动Controller
 * @Date: 2017/4/14
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {

    private Logger logger = Logger.getLogger(ActivityController.class);

    @Autowired
    private ActivityServiceI activityServiceI;

    /**
     * 查询活动信息 微信端
     * @param activityModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryActivity")
    public ResResult queryAcitivity(ActivityModel activityModel,HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        Integer wxUserType = (Integer)session.getAttribute("wxUserType");
        if(wxUserType == 2){
            return ResponseModel.errorActive("只有客户才能登陆");
        }
        logger.info("查询秒杀活动------------");
        activityModel.setActivityDate(DateUtil.formatDate());
        List<ActivityModel> list = activityServiceI.queryActivity(activityModel);
        return ResponseModel.returnData(list);
    }

    /**
     * 查询活动信息 PC端
     * @param activityModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryWebActivity")
    public ResResult queryWebActivity(ActivityModel activityModel,HttpServletRequest request){
        HttpSession session = request.getSession();
        Object pcUserObj = session.getAttribute("PCUser");
        if(pcUserObj == null){
            logger.info("用户未登录");
            return ResponseModel.errorActive("用户未登录");
        }
        PCUserModel currentUser = (PCUserModel)pcUserObj;
        int userType = (int)currentUser.getRole();
        if(userType != 0) {
            return ResponseModel.errorActive("用户类型错误");
        }
        logger.info("查询秒杀活动------------");
        Map returnMap = activityServiceI.queryWebActivity(activityModel);
        return ResponseModel.returnData(returnMap);
    }
    /**
     * 上传活动图片 PC端
     * @Auther: Tinny.liang
     * @Description:
     * @Date: 17:12 2017/4/14
     */
    @RequestMapping(value="/importPicFile.do" ,produces="text/html;charset=utf-8" )
    public @ResponseBody String importPicFile1(@RequestParam MultipartFile file0, HttpServletRequest request){
        Map<String,Object> map= new HashMap<>();
        HttpSession session = request.getSession();
        Object pcUserObj = session.getAttribute("PCUser");
        if(pcUserObj == null){
            logger.info("用户未登录");
            map.put( "result", "error");
            return String.valueOf(JSONObject.fromObject (map));
        }
        PCUserModel currentUser = (PCUserModel)pcUserObj;
        int userType = (int)currentUser.getRole();
        if(userType != 0) {
            map.put( "result", "error");
            return String.valueOf(JSONObject.fromObject (map));
        }
        if(file0.isEmpty()){
            map.put( "result", "error");
            map.put( "msg", "上传文件不能为空" );
        } else{
            String originalFilename=file0.getOriginalFilename();
            String fileBaseName= FilenameUtils.getBaseName(originalFilename);
            String floderName="activity/pic/"+fileBaseName;
            try{

                String genePicPath=request.getSession().getServletContext().getRealPath("/upload/" +floderName);
                //把上传的图片放到服务器的文件夹下
                FileUtils. copyInputStreamToFile(file0.getInputStream(), new File(genePicPath,originalFilename));
                //coding
                map.put( "result", "success");
                map.put("path","/upload/activity/pic/"+fileBaseName+"/"+originalFilename);
            } catch (Exception e) {
                map.put( "result", "error");
                map.put( "msg",e.getMessage());

            }
        }
        String result=String. valueOf(JSONObject.fromObject (map));
        return result;
    }

    /**
     * 增加活动 PC端
     * @param activityModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/addActivity")
    public ResResult addAcitivity(ActivityModel activityModel,HttpServletRequest request){
        HttpSession session = request.getSession();
        Object pcUserObj = session.getAttribute("PCUser");
        if(pcUserObj == null){
            logger.info("用户未登录");
            return ResponseModel.errorActive("用户未登录");
        }
        PCUserModel currentUser = (PCUserModel)pcUserObj;
        int userType = (int)currentUser.getRole();
        if(userType != 0) {
            return ResponseModel.errorActive("用户类型错误");
        }
        logger.info("增加秒杀活动------------");
        int result = activityServiceI.addActivity(activityModel);
        if(result>0){
            return ResponseModel.successActive();
        }else{
            return ResponseModel.errorActive("添加失败");
        }
    }
}
