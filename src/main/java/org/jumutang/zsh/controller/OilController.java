package org.jumutang.zsh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jumutang.zsh.model.OilModel;
import org.jumutang.zsh.model.PCUserModel;
import org.jumutang.zsh.services.OilServiceI;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 油价控制层
 */
@Controller
@Transactional
@RequestMapping("/oil")
public class OilController {
	private Logger logger = Logger.getLogger(OilController.class);
	@Autowired
	private OilServiceI oilService;

	/**
	 * 当日油价信息 微信端
	 * @return resResult
	 */
	@ResponseBody
	@RequestMapping("/query")
	public ResResult queryOil(HttpServletRequest request){
		HttpSession session = request.getSession();
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		Integer customerLevel = (Integer)session.getAttribute("customerLevel");
		logger.info("我的部门号---------------"+customerLevel);
		Map<String,List> returnMap = new HashMap<>();
		OilModel oilModel = new OilModel();
		oilModel.setOilType((short)2);
		if(customerLevel == 0 ){
			logger.info("我是普通用户--------------------------");
			oilModel.setCustomerType((short)(0));
		}else if(customerLevel == 1){
			logger.info("我是vip------------");
			oilModel.setCustomerType((short)(1));
		}else if(customerLevel == 2){
			logger.info("我是白银------------");
			oilModel.setCustomerType((short)(2));
		}else if(customerLevel == 3){
			logger.info("我是黄金------------");
			oilModel.setCustomerType((short)(3));
		}else if(customerLevel == 4){
			logger.info("我是钻石------------");
			oilModel.setCustomerType((short)(4));
		}
		List<OilModel> list = oilService.queryOil(oilModel);
		returnMap.put("customerPrice",list);
		oilModel = new OilModel();
		oilModel.setOilType((short)0);
		List<OilModel> benchmarkList = oilService.queryOil(oilModel);
		returnMap.put("benchmarkList",benchmarkList);
		oilModel = new OilModel();
		oilModel.setOilType((short)1);
		List<OilModel> internationalList = oilService.queryOil(oilModel);
		returnMap.put("internationalPrice",internationalList);
		if(list.size()==0){
			return ResponseModel.errorActive("暂无数据");
		}
			return ResponseModel.returnData(returnMap);
	}

	/**
	 * 查询所有油价信息 PC端
	 * @return resResult
	 */
	@ResponseBody
	@RequestMapping("/queryAll")
	public ResResult queryOilAll(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		if(pcUserObj == null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("用户未登录");
		}
		PCUserModel currentUser = (PCUserModel)pcUserObj;
		int userType = (int)currentUser.getRole();
		if(userType == 0) {
			List<OilModel> oilModelList = oilService.queryOilAll();
			if (oilModelList.size() == 0) {
				return ResponseModel.errorActive("暂无数据");
			}
			return ResponseModel.returnData(oilModelList);
		}else{
			return ResponseModel.errorActive("用户类型错误");
		}
	}

	/**
	 * 修改所有油价信息 PC端
	 * @return resResult
	 */
	@ResponseBody
	@RequestMapping("/updateOilAll")
	public ResResult updateOilAll(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		if(pcUserObj == null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("用户未登录");
		}
		PCUserModel currentUser = (PCUserModel)pcUserObj;
		int userType = (int)currentUser.getRole();
		if(userType == 0) {
			String oilModelsStr=request.getParameter("oilModels");
			JSONArray array=JSONArray.fromObject(oilModelsStr);
			List<OilModel> oilModelList = (List<OilModel>)JSONArray.toCollection(array,OilModel.class);
			boolean flag = true;
			for(OilModel oilModel:oilModelList){
				int result = oilService.updateOilAll(oilModel);
				if(result == 0){
					flag = false;
				}
			}
			if(flag){
				return ResponseModel.successActive();
			}else{
				return ResponseModel.errorActive("修改失败");
			}
		}else{
			return ResponseModel.errorActive("用户类型错误");
		}
	}
}
