package org.jumutang.zsh.services;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.model.Exchange;
import org.jumutang.zsh.model.PageMode;

/**
 * 积分兑换服务接口
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public interface ExchangeServiceI {

	/**
	 * 添加积分兑换信息
	 * @param exchange
	 * @return 
	 */
	public int addExchange(Exchange exchange,String userId,String userName);
	
	/**
	 * 查询兑换礼物
	 * @param param
	 * @return 
	 */
	public PageMode queryByPage(Map<String,Object> param,PageMode pageMode );
	
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
	public List<Map> queryByUser(String userId);

	/**
	 * 按id查询兑换信息
	 * @param exchangeId
	 * @return
	 */
	public Exchange queryByExchangeId(String exchangeId);

	//更新订单表
	public int updateExchange(Exchange exchange);

	//按收货地址查询
	public List<Map> queryAll();
}
