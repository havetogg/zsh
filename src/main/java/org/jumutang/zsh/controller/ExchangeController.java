package org.jumutang.zsh.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jumutang.zsh.dao.GoodDaoI;
import org.jumutang.zsh.model.*;
import org.jumutang.zsh.services.CustomerServiceI;
import org.jumutang.zsh.services.ExchangeServiceI;
import org.jumutang.zsh.services.GoodServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 兑换控制层
 *
 */
@Controller
@Transactional
@RequestMapping("/exchange")
public class ExchangeController {

	@Autowired
	private ExchangeServiceI exchangeServiceI;

	
	private Logger logger = Logger.getLogger(ExchangeController.class);

	/**
	 * 添加兑换信息 微信端
	 * @param exchange
	 * @param request
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/add")
	public ResResult addExchange(Exchange exchange, HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		String userName = (String)session.getAttribute("name");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		// 添加到exchange表
		exchange.setOpenId(userId);

		int result = exchangeServiceI.addExchange(exchange,userId,userName);

		if(result==0){
			return ResponseModel.errorActive("插入失败");
		}else{
			return ResponseModel.successActive();
		}
	}
	
	/**
	 * 分页获取礼物兑换信息 PC端
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryByPage")
	public ResResult queryByPage(HttpServletRequest request,PageMode pageMode){
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
		//预留散列用户条件查询
		Map<String,Object> param = new HashMap<String, Object>();
		int count = exchangeServiceI.countExchange(param);  
		logger.info("获取信息条数-----------------------"+count);
		pageMode.setTotal(count);
		PageMode result = exchangeServiceI.queryByPage(param, pageMode);
		logger.info("------获取兑换信息------------"+pageMode);

		return ResponseModel.returnData(result);
		
	}

	/**
	 * 根据用户查询兑换信息 微信端
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryByUser")
	public ResResult queryByUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		String userName = (String)session.getAttribute("name");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		if(userId == null){
			return new ResResult(2,"未登录");
		}
		List<Map> result = exchangeServiceI.queryByUser(userId);

		return ResponseModel.returnData(result);

	}

	/**
	 * 根据id查询兑换信息 微信端
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryByExchangeId")
	public ResResult queryByExchangeId(Exchange exchange,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		String userName = (String)session.getAttribute("name");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		if(userId == null){
			return new ResResult(2,"未登录");
		}
		Exchange result = exchangeServiceI.queryByExchangeId(exchange.getExchangeId());
		return ResponseModel.returnData(result);

	}

	/**
	 * 更新兑换状态 PC端
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateExchange")
	public ResResult updateExchange(Exchange exchange,HttpServletRequest request){
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
		int result = exchangeServiceI.updateExchange(exchange);
		if(result==0){
			return ResponseModel.errorActive("更新失败");
		}else{
			return ResponseModel.successActive();
		}
	}

	/**
	 * 查询所有订单 PC端
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryAll")
	public ResResult queryAll(HttpServletRequest request){
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
		List<Map> returnList = exchangeServiceI.queryAll();
		return ResponseModel.returnData(returnList);
	}
}
