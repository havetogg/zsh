package org.jumutang.zsh.services;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.model.CustomerModel;
import org.jumutang.zsh.model.PageMode;

/**
 * 客户服务层
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public interface CustomerServiceI {

	/**
	 * 查询客户信息
	 * 
	 * @param customerModel
	 * @return CustomerModel
	 */
	public List<CustomerModel> queryCustomer(CustomerModel customerModel);
	
	/**
	 * 添加客户信息
	 * @param customerModel
	 * @return int
	 */
	public int addCustomer(CustomerModel customerModel);
	
	/**
	 * 更新客户信息
	 * @param customerModel
	 * @return int
	 */
	public int updateCustomer(CustomerModel customerModel);

	/**
	 * 更新客户生日，爱好
	 * @param customerModel
	 * @return
	 */
	public int updateCustomerInfo(CustomerModel customerModel);

	/**
	 * 更新客户状态
	 * @param customerModel
	 * @return
	 */
	public int updateStatus(CustomerModel customerModel);
	/**
	 * 查询所有的客户信息
	 * @param param
	 * @return
	 */
	public PageMode quertByType(Map<String,Object> param,PageMode pageMode);
	
	/**
	 * 统计数据条数
	 * @param param
	 * @return
	 */
	public int countCustomer(Map<String,Object> param);
}
