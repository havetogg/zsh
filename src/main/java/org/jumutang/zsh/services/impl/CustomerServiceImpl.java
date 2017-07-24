package org.jumutang.zsh.services.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jumutang.zsh.controller.CustomerController;
import org.jumutang.zsh.dao.CustomerDaoI;
import org.jumutang.zsh.model.CustomerModel;
import org.jumutang.zsh.model.PageMode;
import org.jumutang.zsh.model.WCUserModel;
import org.jumutang.zsh.services.CustomerServiceI;
import org.jumutang.zsh.tools.UserManagerUtil;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerServiceImpl implements CustomerServiceI {

	private Logger logger = Logger.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerDaoI customerDaoI;

	/**
	 * 默认状态未1
	 */
	private final static Short NURMOL_STATUS= 1;
	
	/**
	 * 默认每页分页10条
	 */
	private final static Integer NURMOL_PAGESIZE=10;
	
	/**
	 * 查询用户信息
	 * 
	 * @param customerModel
	 * @return CustomerModel
	 */
	public List<CustomerModel> queryCustomer(CustomerModel customerModel) {
		return customerDaoI.queryCustomer(customerModel);
	}

	/**
	 * 添加客户信息
	 * @param customerModel
	 * @return int
	 */
	public int addCustomer(CustomerModel customerModel) {
		customerModel.setCustomerId(ZshUtil.createUuid());
		customerModel.setCustomerStatus(NURMOL_STATUS);
		customerModel.setCustomerLevel((short)0);
		if(customerModel.getCustomerIntegral()==null||customerModel.getCustomerIntegral()==0){
			customerModel.setCustomerIntegral(0);
		}
		/*//微信模型
		WCUserModel wcUserModel = new WCUserModel();
		wcUserModel.setUserid(ZshUtil.createUuid());
		if(wcUserModel.getUserid()==null||"".equals(wcUserModel.getUserid())){
			logger.info(customerModel.getCustomerName()+"没有账号请重新填写");
		}
		wcUserModel.setName(customerModel.getCustomerName());
		if(wcUserModel.getName()==null||"".equals(wcUserModel.getName())){
			logger.info(customerModel.getCustomerName()+"没有姓名请重新填写");
		}
		if(customerModel.getCustomerType() == 1){
			//客户
			wcUserModel.setDepartment(new String[]{"5"});
		}else{
			//员工
			wcUserModel.setDepartment(new String[]{"6"});
		}
		wcUserModel.setMobile(customerModel.getPhone());
		wcUserModel.setWeixinid(customerModel.getWeiXinId());
		if(wcUserModel.getMobile()==null&&wcUserModel.getWeixinid()==null){
			logger.info(customerModel.getCustomerName()+"缺少三选一条件");
			return 0;
		}
		String result = UserManagerUtil.createUser(wcUserModel,token);
		JSONObject jsonObject = JSONObject.fromObject(result);
		if(jsonObject.getInt("errcode")!=0){
		}
		customerModel.setCustomerOpenId(wcUserModel.getUserid());*/
		return customerDaoI.addCustomer(customerModel);
	}

	/**
	 * 更新客户信息
	 * @param customerModel
	 * @return int
	 */
	public int updateCustomer(CustomerModel customerModel) {
		
		return customerDaoI.updateCustomer(customerModel);
	}

	/**
	 * 更新客户生日，爱好
	 * @param customerModel
	 * @return
	 */
	public int updateCustomerInfo(CustomerModel customerModel) {
		return customerDaoI.updateCustomerInfo(customerModel);
	}

	/**
	 * 更新客户状态
	 * @param customerModel
	 * @return
	 */
	public int updateStatus(CustomerModel customerModel) {
		return customerDaoI.updateStatus(customerModel);
	}

	/**
	 * 查询所有的客户信息
	 * @param param
	 * @return
	 */
	public PageMode quertByType(Map<String,Object> param,PageMode pageMode){
		param.put("pageSize", NURMOL_PAGESIZE);
		Integer  index = (pageMode.getPageIndex()-1)*NURMOL_PAGESIZE;
		param.put("pageIndex", index);
		pageMode.setList(customerDaoI.quertByType(param));
		return pageMode;
	}

	/**
	 * 统计数据条数
	 * @param param
	 * @return
	 */
	public int countCustomer(Map<String,Object> param) {
		return customerDaoI.countCustomer(param);
	}

}
