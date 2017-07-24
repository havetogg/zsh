package org.jumutang.zsh.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jumutang.zsh.model.PCUserModel;
import org.jumutang.zsh.model.Rob;
import org.jumutang.zsh.services.RobServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 秒杀控制层
 */
@Controller
@RequestMapping("/rob")
public class RobController {

	Logger logger= Logger.getLogger(RobController.class);

	@Autowired
	private RobServiceI robServiceI;

	/**
	 * 添加秒杀 微信端
	 * @param request
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/add")
	public ResResult addRob(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		String activityId=request.getParameter("activityId");
		logger.info("activityId:"+activityId);
		//判断是否有activityId
		if(!StringUtils.isNotBlank(activityId)){
			return new ResResult(3,"请选择秒杀活动");
		}
		Rob rob=new Rob();
		rob.setActivityId(activityId);
		int result = robServiceI.addRob(rob,userId);
		logger.info("result:"+result);
		if(result==0){
			return new ResResult(0,"秒杀成功");
		}else{
			return new ResResult(1,"秒杀失败");
		}
	}

	/**
	 * 查询秒杀 微信端
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/query")
	public ResResult queryRob(HttpServletRequest request,Rob rob){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		List<Rob> list = robServiceI.queryRob(rob);

		if(list!=null||list.size()>0){
			return ResponseModel.returnData(list);
		}else{
			return new ResResult(1,"查询秒杀失败");
		}

	}

	/**
	 * 查询秒杀组合 PC端
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryCombination")
	public ResResult queryRobCombination(HttpServletRequest request,Rob rob){
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		if(pcUserObj == null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("用户未登录");
		}
		PCUserModel currentUser = (PCUserModel)pcUserObj;
		int userType = (int)currentUser.getRole();
		if(userType == 0) {
			List<Map> list = robServiceI.queryRobCombination(rob);
			if(list!=null||list.size()>0){
				return ResponseModel.returnData(list);
			}else{
				return new ResResult(1,"查询秒杀失败");
			}
		}else{
			return ResponseModel.errorActive("用户类型错误");
		}
	}

	/**
	 * 更新秒杀 微信端 PC端
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public ResResult updateRob(HttpServletRequest request,Rob rob){
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		Object wxUserTypeObject = session.getAttribute("wxUserType");
		if(pcUserObj == null&&wxUserTypeObject == null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("用户类型获取失败");
		}
		int result = 0;
		if(StringUtils.isNotBlank(rob.getRobId())){
			result = robServiceI.updateRob(rob);
		}else{
			List<Rob> robList = robServiceI.queryRob(rob);
			if(robList.size()>0){
				rob = robList.get(0);
				short type = Short.parseShort(request.getParameter("type"));
				String rId = request.getParameter("rId");
				String address = request.getParameter("address");
				if(type==1){
					rob.setReceiveStatus((short)1);
					rob.setPickupAddress(address);
				}else if(type==2){
					rob.setReceiveStatus((short)2);
					rob.setAddressId(rId);
				}
				result = robServiceI.updateRob(rob);
			}
		}
		if(result==0){
			return new ResResult(1,"修改失败");
		}else{
			return new ResResult(0,"修改成功");
		}
	}

	/**
	 * 查询秒杀 PC端 微信端
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryAll")
	public ResResult queryAll(HttpServletRequest request, @RequestParam(value = "type",required = false)Integer type){
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		Object userIdObject = session.getAttribute("userId");
		if(pcUserObj == null&&userIdObject == null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("用户类型获取失败");
		}
		Rob rob = new Rob();
		if(type==null) {
			rob.setOpenId((String)userIdObject);
		}
		List<Map> returnList = robServiceI.queryAll(rob);
		return ResponseModel.returnData(returnList);
	}
}
