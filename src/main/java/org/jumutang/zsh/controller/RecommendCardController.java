package org.jumutang.zsh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jumutang.zsh.model.CustomerModel;
import org.jumutang.zsh.model.PCUserModel;
import org.jumutang.zsh.model.RecommendCard;
import org.jumutang.zsh.services.CustomerServiceI;
import org.jumutang.zsh.services.RecommendCardServiceI;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 推荐办卡
 *
 */
@Controller
@RequestMapping("/recommend")
public class RecommendCardController {
	@Autowired
	private RecommendCardServiceI cardServiceI;
	@Autowired
	private CustomerServiceI customerServiceI;
	
	/**
	 * 默认成功办卡添加积分
	 */
	private final static int SCORCE = 500;
	
	/**
	 * 添加推荐办卡 微信端
	 * @param card
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public ResResult addRecommendCard(RecommendCard card,HttpServletRequest request){
		HttpSession session = request.getSession();
		String openId = (String)session.getAttribute("userId");
		if(openId==null){
			return ResponseModel.errorActive("未登录");
		}
		card.setOpenId(openId);
		List<RecommendCard> list = cardServiceI.queryRecommendCard(card);
		if(list.size()!=0){
			return ResponseModel.errorActive("添加推荐卡");
		}
		int result = cardServiceI.addRecommendCard(card);
		if(result>0){
			return ResponseModel.successActive();
		}else{
			return ResponseModel.errorActive("添加失败");
		}
	}
	
	/**
	 * 更新信息
	 * @param card
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public ResResult updateRecommendCard(RecommendCard card){
		if(card.getRecommendStatus()==3){
			card.setRecommendStatus(null);
			List<RecommendCard> list = cardServiceI.queryRecommendCard(card);
			String openId = list.get(0).getOpenId();
			CustomerModel customerModel = new CustomerModel();
			customerModel.setCustomerOpenId(openId);
			List<CustomerModel> list1 = customerServiceI.queryCustomer(customerModel);
			int integer = list1.get(0).getCustomerIntegral()+500;
			customerModel.setCustomerIntegral(integer);
			int result = customerServiceI.updateCustomer(customerModel);
			if(result<0){
				return ResponseModel.errorActive("更新失败");
			}
			card.setRecommendStatus(new Short("3"));
		}
		int result = cardServiceI.updateRecommendCard(card);
		if(result>0){
			return ResponseModel.successActive();
		}else{
			return ResponseModel.errorActive("更新失败");
		}
	}
	/**
	 * 查询推荐信息 PC端
	 * @param card
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/query")
	public ResResult queryRecommendCard(RecommendCard card,HttpServletRequest request){
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		if(pcUserObj == null){
			return ResponseModel.errorActive("用户未登录");
		}
		PCUserModel currentUser = (PCUserModel)pcUserObj;
		int userType = (int)currentUser.getRole();
		if(userType == 0){
			List<RecommendCard> resultList = cardServiceI.queryRecommendCard(card);
			if(resultList.size()==0){
				return ResponseModel.errorActive("暂无数据");
			}
			return ResponseModel.returnData(resultList);
		}else{
			return ResponseModel.errorActive("用户类型错误");
		}
	}
}
