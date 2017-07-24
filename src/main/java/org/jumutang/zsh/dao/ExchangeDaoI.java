package org.jumutang.zsh.dao;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.model.Exchange;
import org.springframework.stereotype.Repository;

/**
 * 积分兑换dao层
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
@Repository
public interface ExchangeDaoI {

	/**
	 * 添加礼物对话
	 * @param exchange
	 * @return
	 */
	public int addExchange(Exchange exchange);



	//编辑礼物信息
	public int updateExchange(Exchange exchange);

	/**
	 * 查询兑换礼物
	 * @param param
	 * @return 
	 */
	public List<Exchange> queryByPage(Map<String,Object> param);
	
	/**
	 * 统计信息条数
	 * @param param
	 * @return
	 */
	public int countExchange(Map<String,Object> param);

	/**
	 * 按用户查询兑换信息
	 * @param userId
	 * @return
	 */
	public List<Exchange> queryByUser(String userId);

	/**
	 * 按id查询兑换信息
	 * @param exchangeId
	 * @return
	 */
	public Exchange queryByExchangeId(String exchangeId);

	//查询所有的兑换信息
	public List<Exchange> queryAll();
}
