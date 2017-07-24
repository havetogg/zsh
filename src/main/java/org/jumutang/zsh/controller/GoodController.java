package org.jumutang.zsh.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.jumutang.zsh.model.GoodModel;
import org.jumutang.zsh.model.PCUserModel;
import org.jumutang.zsh.model.PageMode;
import org.jumutang.zsh.services.GoodServiceI;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 兑换礼物控制层
 *
 */
@Controller
@RequestMapping("/good")
public class GoodController {

	private Logger logger = Logger.getLogger(GoodController.class);
	@Autowired
	private GoodServiceI goodServiceI;
	
	/**
	 * 添加兑换奖品信息 PC端
	 * @param goodModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addGood")
	public ResResult addGood(GoodModel goodModel,HttpServletRequest request){
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
		List<GoodModel> list = goodServiceI.queryGoodInfo(goodModel);
		if(list.size()!=0){
			return ResponseModel.errorActive("该信息已存在");
		}
		int result = goodServiceI.addGood(goodModel);
		if(result == 0){
			return ResponseModel.errorActive("更新失败");
		}else{
			return ResponseModel.successActive();
		}
	}
	
	/**
	 * 更新兑换奖品信息 PC端
	 * @param goodModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateGood")
	public ResResult updateGood(GoodModel goodModel,HttpServletRequest request){
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
		int result = goodServiceI.updateGood(goodModel);
		if(result == 0){
			return ResponseModel.errorActive("更新失败");
		}else{
			return ResponseModel.successActive();
		}
	}
	
	/**
	 * 分页查询兑换礼物信息 PC端 微信端
	 * @param goodModel
	 * @param pageMode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryGood")
	public ResResult queryGood(GoodModel goodModel, PageMode pageMode,HttpServletRequest request){
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		Object wxUserTypeObject = session.getAttribute("wxUserType");
		if(pcUserObj == null&&wxUserTypeObject == null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("用户类型获取失败");
		}
		Map<String,Object> param = new HashMap<String,Object>();
		if(goodModel.getGoodId()!=null){
			param.put("goodId", goodModel.getGoodId());
		}
		if(goodModel.getGoodState() !=null){
			param.put("goodState", goodModel.getGoodState());
		}
		String mark = request.getParameter("mark");
		if(mark !=null){
			param.put("mark", mark);
		}
		int count = goodServiceI.countGood(param);
		pageMode.setTotal(count);
		PageMode result = goodServiceI.queryGood(param, pageMode);
		return ResponseModel.returnData(result);
	}

	/**
	 * 上传商品图片 PC端
	 * @param file0
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/importPicFile" ,produces="text/html;charset=utf-8" )
	public @ResponseBody String importPicFile1(@RequestParam MultipartFile file0, HttpServletRequest request){
		Map<String,Object> map= new HashMap<String,Object>();
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
			String floderName="good/pic/"+fileBaseName;
			try{
				String genePicPath=request.getSession().getServletContext().getRealPath("/upload/" +floderName);
				//把上传的图片放到服务器的文件夹下
				FileUtils. copyInputStreamToFile(file0.getInputStream(), new File(genePicPath,originalFilename));
				//coding
				map.put( "result", "success");
				map.put("path","/upload/good/pic/"+fileBaseName+"/"+originalFilename);
			} catch (Exception e) {
				map.put( "result", "error");
				map.put( "msg",e.getMessage());

			}
		}
		String result=String. valueOf(JSONObject.fromObject (map));
		return result;
	}

	/**
	 * 上传图片详细信息 PC端
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/importPicFile2" ,produces="text/html;charset=utf-8" )
	public @ResponseBody String importPicFile2(@RequestParam(value="file",required=false) MultipartFile file, HttpServletRequest request){
		Map<String,Object> map= new HashMap<String,Object>();
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
		String path = request.getParameter("path");
		if(file.isEmpty()){
			map.put( "result", "error");
			map.put( "msg", "上传文件不能为空" );
		} else{
			String originalFilename=file.getOriginalFilename();
			//String fileBaseName= FilenameUtils.getBaseName(originalFilename);
			String floderName=path.substring(0,path.lastIndexOf("/"));
			try{
				String genePicPath=request.getSession().getServletContext().getRealPath(floderName);
				//把上传的图片放到服务器的文件夹下
				FileUtils. copyInputStreamToFile(file.getInputStream(), new File(genePicPath,originalFilename));
				//coding
				map.put( "result", "success");
				map.put("path",floderName+"/"+originalFilename);
			} catch (Exception e) {
				map.put( "result", "error");
				map.put( "msg",e.getMessage());

			}
		}
		String result=String. valueOf(JSONObject.fromObject (map));
		return result;
	}
}
