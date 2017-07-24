package org.jumutang.zsh.services;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.model.Rob;
/**
 * 秒杀服务层
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public interface RobServiceI {

	/**
	 * 添加秒杀
	 * @param rob
	 * @return
	 */
	public int addRob(Rob rob,String userId);
	
	/**
	 * 统计秒杀
	 * @return 
	 */
	public int countRob(Rob rob);
	
	/**
	 * 查询
	 * @return 
	 */
	public List<Rob> queryRob(Rob rob);

	//组合查询
	public List<Map> queryRobCombination(Rob rob);

	//更新
	public int updateRob(Rob rob);

	//按收货地址查询
	public List<Map> queryAll(Rob queryRob);
}
