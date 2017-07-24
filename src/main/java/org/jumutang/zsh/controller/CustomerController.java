package org.jumutang.zsh.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jumutang.zsh.model.*;
import org.jumutang.zsh.services.CustomerServiceI;
import org.jumutang.zsh.services.ReceiveAddressServiceI;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.jumutang.zsh.tools.UserManagerUtil;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 客户控制层
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	private Logger logger = Logger.getLogger(CustomerController.class);
	@Autowired
	private CustomerServiceI customerService;
	@Autowired
	private ReceiveAddressServiceI receiveAddressServiceI;

	/**
	 * 查询当前客户信息 微信端
	 * 
	 * @param request
	 * @return resResult
	 */
	@ResponseBody
	@RequestMapping("/query")
	public ResResult queryByOpenId(CustomerModel param,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		if(StringUtils.isBlank(param.getCustomerOpenId())){
			param.setCustomerOpenId(userId);
		}
		List<CustomerModel> customerModel = customerService.queryCustomer(param);
		if(customerModel.size()==0){
			return ResponseModel.errorActive("暂无信息");
		}
		return ResponseModel.returnData(customerModel);
	}

	/**
	 * 查询所有用户信息 微信端 PC端 不做校验
	 * @param customerModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/find")
	public ResResult queryCustomer(CustomerModel customerModel,HttpServletRequest request){
		HttpSession session = request.getSession();
		Object userIdObj = session.getAttribute("userId");
		Object nameObj = session.getAttribute("name");
		Object pcUserObj = session.getAttribute("PCUser");
		if(userIdObj == null&&pcUserObj == null||nameObj == null&&pcUserObj == null) {
			return ResponseModel.errorActive("用户未登陆");
		}
		if(session.getAttribute("wxUserType") != null&&(int)session.getAttribute("wxUserType") == 2){
			String name = (String)nameObj;
			customerModel.setManager(name);
		}
		List<CustomerModel> list = customerService.queryCustomer(customerModel);
		if(list.size()==0){
			return ResponseModel.errorActive("暂无数据");
		}
		return ResponseModel.returnData(list);
	}

	/**
	 * 查询员工信息 PC端
	 * @param pageMode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryByEmp")
	public ResResult quertByEmp(PageMode pageMode,HttpServletRequest request,@RequestParam("keyWord") String keyWord){
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		if(pcUserObj == null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("用户未登录");
		}
		PCUserModel currentUser = (PCUserModel)pcUserObj;
		int userType = (int)currentUser.getRole();
		if(userType == 0){
			List<String> list = new ArrayList<String>();
			list.add("2");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			if(keyWord!=null&&!"".equals(keyWord)){
				param.put("customerName",keyWord);
			}else{
				param.put("customerName",null);
			}
			int count = customerService.countCustomer(param);
			pageMode.setPageNow(pageMode.getPageIndex());
			pageMode.setTotal(count);
			PageMode result = customerService.quertByType(param,pageMode);
			return ResponseModel.returnData(result);
		}else{
			return ResponseModel.errorActive("用户类型错误");
		}
	}

	/**
	 * 查询客户信息
	 * @param pageMode PC端
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryByCustomer")
	public ResResult quertByType(HttpServletRequest request,PageMode pageMode, @RequestParam("keyWord") String keyWord, @RequestParam("hobby") String hobby){
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		if(pcUserObj == null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("用户未登录");
		}
		PCUserModel currentUser = (PCUserModel)pcUserObj;
		int userType = (int)currentUser.getRole();
		if(userType == 0) {
			List<String> list = new ArrayList<String>();
			list.add("1");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("list", list);
			if (keyWord != null && !"".equals(keyWord)) {
				param.put("phonePerson", keyWord);
			} else {
				param.put("phonePerson", null);
			}
			if (hobby != null && !"".equals(hobby)) {
				param.put("hobby", hobby);
			} else {
				param.put("hobby", null);
			}
			int count = customerService.countCustomer(param);
			pageMode.setPageNow(pageMode.getPageIndex());
			pageMode.setTotal(count);
			PageMode result = customerService.quertByType(param, pageMode);
			return ResponseModel.returnData(result);
		}else{
			return ResponseModel.errorActive("用户类型错误");
		}
	}

	/**
	 * 查询用户收获地址列表 微信端
	 *
	 * @param request
	 * @return resResult
	 */
	@ResponseBody
	@RequestMapping("/queryReceiveAddress")
	public ResResult queryReceiveAddress(ReceiveAddress param, HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		param.setUserId(userId);
		param.setIsDelete("0");//查询未删除的地址
		List<ReceiveAddress> customerModel = receiveAddressServiceI.queryReceiveAddressListByUserId(param);
		return ResponseModel.returnData(customerModel);
	}

	/**
	 * 添加用户 PC端口
	 * @param customerModel
	 * @return  ResResult
	 */
	@ResponseBody
	@RequestMapping("/add")
	public ResResult addCustomer(HttpServletRequest request,CustomerModel customerModel) throws Exception{
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		if(pcUserObj == null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("用户未登录");
		}
		PCUserModel currentUser = (PCUserModel)pcUserObj;
		int userType = (int)currentUser.getRole();
		if(userType == 0){
			if(StringUtils.isNotBlank(customerModel.getPhone())){
				CustomerModel param = new CustomerModel();
				param.setPhone(customerModel.getPhone());
				param.setCustomerStatus((short)1);
				List<CustomerModel> list = customerService.queryCustomer(param);
				if(list.size()!=0){
					return ResponseModel.errorActive("已存在该用户");
				}
				WCUserModel wcUserModel = new WCUserModel();
				wcUserModel.setUserid(customerModel.getPhone());
				wcUserModel.setName(customerModel.getCustomerName());
				if(customerModel.getCustomerType() == (short)1){
					wcUserModel.setDepartment(new String[]{"3"});
				}else if(customerModel.getCustomerType() == (short)2){
					wcUserModel.setDepartment(new String[]{"2"});
				}
				if(StringUtils.isNotBlank(customerModel.getPosition())){
					wcUserModel.setPosition(customerModel.getPosition());
				}
				wcUserModel.setMobile(customerModel.getPhone());
				if("男".equals(customerModel.getGender())){
					wcUserModel.setGender("1");
				}else if("女".equals(customerModel.getGender())){
					wcUserModel.setGender("2");
				}
				if(StringUtils.isNotBlank(customerModel.getEmail())){
					wcUserModel.setEmail(customerModel.getEmail());
				}
				if(StringUtils.isNotBlank(customerModel.getWeiXinId())){
					wcUserModel.setWeixinid(customerModel.getWeiXinId());
				}
				String result = UserManagerUtil.createUser(wcUserModel);
				JSONObject jsonObject = JSONObject.fromObject(result);
				if((Integer)jsonObject.get("errcode") == 0){
					int result2 = customerService.addCustomer(customerModel);
					if(result2>0){
						return ResponseModel.successActive();
					}else{
						return ResponseModel.errorActive("数据库插入失败");
					}
				}else{
					logger.info("添加通讯录失败");
					return ResponseModel.errorActive(jsonObject.getString("errmsg"));
				}
			}else{
				return ResponseModel.errorActive("请输入手机号");
			}
		}else{
			return ResponseModel.errorActive("用户类型错误");
		}
	}

	/**
	 * 增加收货地址 微信端
	 *
	 * @param request
	 * @return resResult
	 */
	@ResponseBody
	@RequestMapping("/addReceiveAddress")
	public ResResult addReceiveAddress(ReceiveAddress param, HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		param.setUserId(userId);
		int res=0;
		param.setrId(ZshUtil.createUuid());
		param.setIsDelete("0");
		if(param.getIsDefault().equals("1")){
			//如果设为默认 先执行查询 其他的都修改成0
			List<ReceiveAddress> list=this.receiveAddressServiceI.queryReceiveAddressListByUserId(param);
			if(list.size()>0){
				for(ReceiveAddress receiveAddress:list){
					receiveAddress.setIsDefault("0");
					this.receiveAddressServiceI.updateReceiveAddressModel(receiveAddress);
				}
			}
			res=this.receiveAddressServiceI.addReceiveAddressModel(param);
		}else{
			param.setIsDelete("0");
			res=this.receiveAddressServiceI.addReceiveAddressModel(param);
		}
		return ResponseModel.returnData(res);
	}

	/**
	 * 修改信息 PC端 微信端 更新 删除
	 * @param customerModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateCustClient")
	public ResResult updateCustClient(CustomerModel customerModel,HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		Object wxUserTypeObject = session.getAttribute("wxUserType");
		if(pcUserObj == null&&wxUserTypeObject == null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("用户类型获取失败");
		}
		if(pcUserObj != null&&((PCUserModel)pcUserObj).getRole() == 0||wxUserTypeObject != null&&(int)wxUserTypeObject == 2||wxUserTypeObject != null&&(int)wxUserTypeObject == 3){
			int res = 0;
			//更新
			if(customerModel.getCustomerId()!=null){
				CustomerModel queryCustomerModel = new CustomerModel();
				queryCustomerModel.setCustomerId(customerModel.getCustomerId());
				List<CustomerModel> customerModelList = customerService.queryCustomer(customerModel);
				if(customerModelList.size()>0){
					WCUserModel wcUserModel = new WCUserModel();
					wcUserModel.setUserid(customerModel.getPhone());
					wcUserModel.setName(customerModel.getCustomerName());
					if(customerModel.getCustomerType() == (short)1){
						wcUserModel.setDepartment(new String[]{"3"});
					}else if(customerModel.getCustomerType() == (short)2){
						wcUserModel.setDepartment(new String[]{"2"});
					}
					if(StringUtils.isNotBlank(customerModel.getPosition())){
						wcUserModel.setPosition(customerModel.getPosition());
					}
					wcUserModel.setMobile(customerModel.getPhone());
					if("男".equals(customerModel.getGender())){
						wcUserModel.setGender("1");
					}else if("女".equals(customerModel.getGender())){
						wcUserModel.setGender("2");
					}
					if(StringUtils.isNotBlank(customerModel.getEmail())){
						wcUserModel.setEmail(customerModel.getEmail());
					}
					if(StringUtils.isNotBlank(customerModel.getWeiXinId())){
						wcUserModel.setWeixinid(customerModel.getWeiXinId());
					}
					String result = UserManagerUtil.updateUser(wcUserModel);
					JSONObject jsonObject = JSONObject.fromObject(result);
					if((Integer)jsonObject.get("errcode") == 0){
						this.customerService.updateCustomer(customerModel);
						return ResponseModel.successActive();
					}else{
						return ResponseModel.errorActive(jsonObject.getString("errmsg"));
					}
				}else{
					return ResponseModel.errorActive("查无数据");
				}
			//删除
			}else if(customerModel.getPhone()!=null){
				CustomerModel updateCustomerModel = new CustomerModel();
				updateCustomerModel.setPhone(customerModel.getPhone());
				updateCustomerModel.setCustomerStatus((short)1);
				List<CustomerModel> customerModelList = customerService.queryCustomer(customerModel);
				if(customerModelList.size()>0){
					CustomerModel queryCustomerModel = customerModelList.get(0);
					queryCustomerModel.setCustomerType((short)3);
					res=this.customerService.updateStatus(customerModel);
					if(res==0){
						return ResponseModel.errorActive("更新用户信息失败");
					}
					String result = UserManagerUtil.delUser(queryCustomerModel.getCustomerOpenId());
					JSONObject jsonObject = JSONObject.fromObject(result);
					if((Integer)jsonObject.get("errcode") == 0){
						return ResponseModel.returnData("删除成功");
					}else{
						return ResponseModel.errorActive(jsonObject.getString("errmsg"));
					}
				}else{
					return ResponseModel.errorActive("查无该用户信息");
				}
			}
			return ResponseModel.errorActive("传入参数无效");
		}else{
			return ResponseModel.errorActive("用户类型错误");
		}
	}
	/**
	 * 修改生日 爱好 微信端
	 * @param param
	 * @param request
     * @return
     */
	@ResponseBody
	@RequestMapping("/updateCust")
	public ResResult updateCust(CustomerModel param,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		param.setCustomerOpenId(userId);
		int res=this.customerService.updateCustomerInfo(param);
		if(res==0){
			return ResponseModel.errorActive("更新用户信息失败");
		}
		return ResponseModel.returnData(res);
	}


	/**
	 * 修改用户收获地址列表 微信端
	 *
	 * @param request
	 * @return resResult
	 */
	@ResponseBody
	@RequestMapping("/updateReceiveAddress")
	public ResResult updateReceiveAddress(ReceiveAddress param, HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		Integer wxUserType = (Integer)session.getAttribute("wxUserType");
		if(wxUserType == 2){
			return ResponseModel.errorActive("只有客户才能登陆");
		}
		if(param.getIsDefault()!=null&&param.getIsDefault().equals("1")){
			ReceiveAddress re=new ReceiveAddress();
			re.setUserId(userId);
			List<ReceiveAddress> list=this.receiveAddressServiceI.queryReceiveAddressListByUserId(re);
			if(list.size()>0){
				for(ReceiveAddress receiveAddress:list){
					receiveAddress.setIsDefault("0");
					this.receiveAddressServiceI.updateReceiveAddressModel(receiveAddress);
				}
			}
		}
		int res=0;
		if(param.getrId()!=null&&StringUtils.isEmpty(param.getrId())){
			ReceiveAddress re=new ReceiveAddress();
			re.setUserId(userId);
			List<ReceiveAddress> list=this.receiveAddressServiceI.queryReceiveAddressListByUserId(re);
			if(list.size()>0){
				for(ReceiveAddress receiveAddress:list){
					receiveAddress.setIsDefault("0");
					this.receiveAddressServiceI.updateReceiveAddressModel(receiveAddress);
				}
			}
		}else{
			res=this.receiveAddressServiceI.updateReceiveAddressModel(param);
		}
		return ResponseModel.returnData(res);
	}


}
