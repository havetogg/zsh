package org.jumutang.zsh.controller;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jumutang.zsh.model.CustomerModel;
import org.jumutang.zsh.model.DailyModel;
import org.jumutang.zsh.model.PCUserModel;
import org.jumutang.zsh.services.CustomerServiceI;
import org.jumutang.zsh.services.DailyServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ExcelUtil;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Tinny.liang
 * @Description:日报controller
 * @Date: Create in 14:30 2017/4/20
 * @Modified By:
 */
@Controller
@RequestMapping("/daily")
public class DailyController {

    private Logger logger = Logger.getLogger(DailyController.class);

    @Autowired
    private DailyServiceI dailyService;

    /**
     * 上传excel PC端
     * @param request
     */
    @RequestMapping(value="/uploadExcel.do" ,produces="text/html;charset=utf-8" )
    @ResponseBody
    public String uploadExcel(HttpServletRequest request){
        Map<String,Object> map= new HashMap<String,Object>();
        HttpSession session = request.getSession();
        Object pcUserObj = session.getAttribute("PCUser");
        if(pcUserObj == null){
            logger.info("用户未登录");
            map.put("result","error");
            return JSON.toJSONString(map);
        }
        PCUserModel currentUser = (PCUserModel)pcUserObj;
        int userType = (int)currentUser.getRole();
        if(userType != 0) {
            map.put( "result", "error");
            return String.valueOf(JSONObject.fromObject (map));
        }
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("file0");
        if(file==null){
            logger.info("未选择文件");
            map.put("result","error");
        }
        int result = ExcelUtil.readDailyExcel(file,dailyService);
        logger.info("查看文件是否更新成功---------------------------"+result);

        if(result == 1){
            map.put("result","success");
        }else{
            map.put("result","error");

        }
        String resultString=String. valueOf(JSONObject.fromObject (map));
        return resultString;
    }

    /**
     * 查询当前用户信息 微信员工端  PC端
     *
     * @param request
     * @return resResult
     */
    @ResponseBody
    @RequestMapping("/query")
    public ResResult query(DailyModel dailyModel , HttpServletRequest request){
        HttpSession session = request.getSession();
        Object pcUserObj = session.getAttribute("PCUser");
        Object wxUserTypeObj = session.getAttribute("wxUserType");
        if(pcUserObj == null&&wxUserTypeObj == null){
            logger.info("用户未登录");
            return ResponseModel.errorActive("用户未登录");
        }
        if(pcUserObj != null){
            if((int)((PCUserModel)pcUserObj).getRole() != 0){
                return ResponseModel.errorActive("没有权限登陆");
            }
        }
        if(wxUserTypeObj != null){
            if((int)wxUserTypeObj == 1){
                return ResponseModel.errorActive("只有员工才能登陆");
            }
        }
        Object nameObj = session.getAttribute("name");
        String name = "";
        if(nameObj != null){
            name = (String)nameObj;
        }
        if(StringUtils.isBlank(dailyModel.getCreateDate())){
            dailyModel.setCreateDate(DateUtil.formatDate());
        }
        List returnList = new ArrayList();
        List<DailyModel> dailyModelList = dailyService.listDaily(dailyModel);
        if(dailyModelList.size()==0){
            return ResponseModel.errorActive("暂无信息");
        }
        returnList.add(dailyModelList);
        DailyModel dailyModel2 = new DailyModel();
        dailyModel2.setCustomerName(name);
        List<DailyModel> dailyModelList2 = dailyService.listDaily(dailyModel2);
        if(dailyModelList2.size()>0){
            returnList.add(dailyModelList2.get(0));
        }else{
            returnList.add(null);
        }
        return ResponseModel.returnData(returnList);
    }
}
