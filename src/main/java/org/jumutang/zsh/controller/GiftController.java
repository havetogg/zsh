package org.jumutang.zsh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jumutang.zsh.model.GiftModel;
import org.jumutang.zsh.services.GiftServiceI;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 礼物控制层
 *
 */
@Controller
@RequestMapping("/gift")
public class GiftController {

	@Autowired
	private GiftServiceI giftServiceI;
	/**
	 * 礼物添加
	 * @param giftModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public ResResult addGift(GiftModel giftModel,HttpServletRequest request){
		HttpSession session = request.getSession();
		String openId = (String)session.getAttribute("userId");
		if(openId==null){
			return new ResResult(2,"未登录");
		}
		Integer department = (Integer)session.getAttribute("department");
		if(department == 2){
			return new ResResult(3,"请选择正确的模块");
		}
		giftModel.setOpenId(openId);
		List<GiftModel> list = giftServiceI.queryGift(giftModel);
		if(list.size()!=0){
			return ResponseModel.errorActive("已添加过礼物");
		}
		int result = giftServiceI.addGift(giftModel);
		if(result>0){
			return ResponseModel.successActive();
		}
		return ResponseModel.errorActive("添加失败");
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
		if(department == 2){
			return new ResResult(3,"非该模块下的用户");
		}
		return new ResResult(0);
	}
}
