package org.jumutang.zsh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jumutang.zsh.model.DistributionModel;
import org.jumutang.zsh.services.DistributionServiceI;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 分配任务控制层
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
@Controller
@RequestMapping("/distribution")
public class DistributionController {

	@Autowired
	private DistributionServiceI distributionServiceI;
	
	/**
	 * 添加分配
	 * @param distributionModel
	 * @param resResult
	 */
	@ResponseBody
	@RequestMapping("/add")
	public ResResult addDistribution(DistributionModel distributionModel,HttpServletRequest request){
		HttpSession session = request.getSession();
		Integer department = (Integer)session.getAttribute("department");
		String userId = (String)session.getAttribute("userId");
		if(userId==null){
			return new ResResult(2,"未登录");
		}
		if(department == 4 || department == 5){
			return new ResResult(3,"请选择用户模块");
		}
		distributionModel.setEmployeeId(userId);
		List<DistributionModel> list = distributionServiceI.queryDistribution(distributionModel);
		if(list.size()!=0){
			return ResponseModel.errorActive("任务已存在");
		}
		int result = distributionServiceI.addDistribution(distributionModel);
		if(result>0){
			return ResponseModel.successActive();
		}else{
			return ResponseModel.errorActive("添加失败");
		}
	}
	/**
	 * 查询任务
	 * @param distributionModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/query")
	public ResResult queryDistribution(DistributionModel distributionModel,HttpServletRequest request){
		HttpSession session = request.getSession();
		Integer department = (Integer)session.getAttribute("department");
		String userId = (String)session.getAttribute("userId");
		if(userId == null ){
			return new ResResult(2,"未登录");
		}
		if(department==4 || department == 5){
			return new ResResult(3,"请选择用户模块");
		}
		distributionModel.setEmployeeId(userId);
		List<DistributionModel> list = distributionServiceI.queryDistribution(distributionModel);
		return ResponseModel.returnData(list);
	}
	
	/**
	 * 身份验证
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserInfo")
	public ResResult getUserInfo(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer department = (Integer)session.getAttribute("department");
		if(userId == null){
			return new ResResult(2,"未登录");
		}
		if(department == 4 || department==5){
			return new ResResult(3,"非该模块下的用户");
		}
		return new ResResult(0);
	}
}
