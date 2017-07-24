package org.jumutang.zsh.dao;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.model.CustomerModel;
import org.springframework.stereotype.Repository;

/**
 * 用户dao接口
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
@Repository
public interface CustomerDaoI {
	/**
	 * 查询
	 * @param customerModel
	 * @return custormerModel
	 */
	public List<CustomerModel> queryCustomer(CustomerModel customerModel);
	
	/**
	 * 添加
	 * @param customerModel
	 * @return int
	 */
	public int addCustomer(CustomerModel customerModel);
	
	/**
	 * 更新
	 * @param customerModel
	 * @return
	 */
	public int updateCustomer(CustomerModel customerModel);
	public int updateCustomerInfo(CustomerModel customerModel);
	public int updateStatus(CustomerModel customerModel);
	/**
	 * 查询所有的客户信息
	 * @param param
	 * @return
	 */
	public List<CustomerModel> quertByType(Map<String,Object> param);
	
	/**
	 * 统计数据条数
	 * @param param
	 * @return
	 */
	public int countCustomer(Map<String,Object> param);
}
