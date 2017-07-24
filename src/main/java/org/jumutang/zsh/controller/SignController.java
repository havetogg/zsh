package org.jumutang.zsh.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jumutang.zsh.model.CustomerModel;
import org.jumutang.zsh.model.PageMode;
import org.jumutang.zsh.model.SignModel;
import org.jumutang.zsh.services.CustomerServiceI;
import org.jumutang.zsh.services.SignServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 签到控制层
 *
 */
@Controller
@Transactional
@RequestMapping("/sign")
public class SignController {

	@Autowired
	private SignServiceI signServiceI;
	@Autowired
	private CustomerServiceI customerServiceI;
	
	/**
	 * 添加签到信息
	 * @param signModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public ResResult addSign(SignModel signModel,HttpServletRequest request,CustomerModel customerModel){
		HttpSession session = request.getSession(true);
		String openId = (String)session.getAttribute("userId");
		if(openId==null){
			return new ResResult(2,"未登录");
		}
		signModel.setOpenId(openId);
		if(customerModel.getCustomerCode()!=null && customerModel.getCustomerName() !=null && customerModel.getCustomerAddress()!=null &&
				customerModel.getPhonePerson() !=null && customerModel.getPhone() !=null
			){
		List<CustomerModel> list = customerServiceI.queryCustomer(customerModel);
		if(list.size()!=0){
			return ResponseModel.errorActive("已签到，但未签退");
		}
		customerModel.setEmployeeId(openId);
		int result = customerServiceI.addCustomer(customerModel);
		if(result<0){
			return ResponseModel.errorActive("签到失败");
		}
	}
		SignModel param = new SignModel();
		param.setOpenId(openId);
		param.setSignDate(DateUtil.formatDate());
		param.setSignStatus(new Short("1"));
		List<SignModel> resultList = signServiceI.querySign(param);
		if(resultList.size()!=0){
			int result =  signServiceI.updateSignStatus(param);
			if(result<0){
				return ResponseModel.errorActive("签到失败");
			}
		}
		signModel.setSignDate(DateUtil.formatDate());
		
		signModel.setUserName((String)session.getAttribute("name"));
		if(signModel.getSignType()!=1){
			signModel.setSignStatus(new Short("2"));
		}
		
		int result  =signServiceI.addSign(signModel);
		if(result>0){
			return ResponseModel.successActive();
		}else{
			return ResponseModel.errorActive("签到失败");
		}
	}
	
	/**
	 * 查询签到信息
	 * @param session
	 * @return resResult
	 */
	@ResponseBody
	@RequestMapping("/query")
	public ResResult querySign(HttpServletRequest request){
		HttpSession session = request.getSession();
		String openId = (String)session.getAttribute("userId");
		Integer department = (Integer)session.getAttribute("department");
		if(openId==null){
			return new ResResult(2,"未登录");
		}
		if(department == 4 || department==5){
			return new ResResult(3,"非本模块下的用户");
		}
		SignModel signModel = new SignModel();
		signModel.setOpenId(openId);
		signModel.setSignDate(DateUtil.formatDate());
		List<SignModel> list = signServiceI.querySign(signModel);
		if(list.size()==0){
			return ResponseModel.errorActive("暂无数据");
		}
			return ResponseModel.returnData(list.get(0));	
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public ResResult uploadImg(HttpServletRequest request){
		try {
		HttpSession session = request.getSession();
		String openId = (String)session.getAttribute("userId");
		if(openId==null){
			return new ResResult(2,"未登录");
		}
		Integer department = (Integer)session.getAttribute("department");
		if(department == 4 || department==5){
			return new ResResult(3,"非本模块下的用户");
		}
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		MultipartFile file = multipartHttpServletRequest.getFile("file");
		String fileName =DateUtil.getTime()+"_"+file.getOriginalFilename();
		String path = multipartHttpServletRequest.getRealPath("/")+ "image" + File.separator+fileName;
		File saveFile = new File(path);
		if(saveFile.exists()){
				saveFile.createNewFile();
		}
			file.transferTo(saveFile);
		
		SignModel signModel = new SignModel();
		signModel.setSignDate(DateUtil.formatDate());
		signModel.setOpenId(openId);
		String imgUrl = request.getContextPath()+"/image/"+ fileName;
		signModel.setSignStatus(new Short("1"));
		List<SignModel> list = signServiceI.querySign(signModel);
		if(list.size()==0){
			signModel.setSignType(new Short("1"));
			signModel.setScenePhotos(imgUrl);
			int result = signServiceI.addSign(signModel);
			if(result>0){
				return ResponseModel.returnData(signModel);
			}else{
				return ResponseModel.errorActive("上传失败");
			}
		}else if(list.size()!=0){
			int result =  signServiceI.updateSignStatus(signModel);
			if(result == 0){
				return ResponseModel.errorActive("更新失败");
			}
		
			signModel.setSignStatus(new Short("2"));
			signModel.setSignType(new Short("2"));
			signModel.setScenePhotos(imgUrl);
			int result1= signServiceI.addSign(signModel);
			if(result1>0){
				return ResponseModel.returnData(signModel);
			}else{
				return ResponseModel.errorActive("添加失败");
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseModel.errorActive("更新失败");
	}
	
	/**
	 * 更新信息
	 * @param signModel
	 * @param customerModel
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/update")
	public ResResult updateSign(SignModel signModel , CustomerModel customerModel,HttpServletRequest request){
		HttpSession session = request.getSession();
		String openId = (String)session.getAttribute("userId");
		if(openId==null){
			return new ResResult(2,"未登录");
		}
		Integer department = (Integer)session.getAttribute("department");
		if(department == 4 || department==5){
			return new ResResult(3,"非本模块下的用户");
		}
		if(customerModel.getCustomerCode()!=null && customerModel.getCustomerName() !=null && customerModel.getCustomerAddress()!=null &&
					customerModel.getPhonePerson() !=null && customerModel.getPhone() !=null
				){
			List<CustomerModel> list = customerServiceI.queryCustomer(customerModel);
			if(list.size()!=0){
				return ResponseModel.errorActive("数据已存在");
			}
			customerModel.setEmployeeId(openId);
			int result = customerServiceI.addCustomer(customerModel);
			if(result<0){
				return ResponseModel.errorActive("更新失败");
			}
		}
		if(signModel.getSignType()==2){
			SignModel param = new SignModel();
			param.setOpenId(openId);
			param.setSignDate(DateUtil.formatDate());
			param.setSignStatus(new Short("1"));
			List<SignModel> resultList = signServiceI.querySign(param);
			if(resultList.size()!=0){
				int result =  signServiceI.updateSignStatus(param);
				if(result<0){
					return ResponseModel.errorActive("更新失败");
				}
			}
		}
		int  result = signServiceI.updateSigne(signModel);
		if(result<0){
			return ResponseModel.errorActive("更新失败");
		}
		return ResponseModel.successActive();
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
	
	/**
	 * 分页查询分页信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryByPage")
	public ResResult queryByPage(PageMode pageMode){
		Map<String, Object> param = new HashMap<String, Object>();
		int count = signServiceI.countSign(param);
		pageMode.setTotal(count);
		PageMode result = signServiceI.queryByPage(param, pageMode);
		return ResponseModel.returnData(result);
	}
}
