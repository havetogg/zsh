package org.jumutang.zsh.dao;

import java.util.List;

import org.jumutang.zsh.model.Exchange;
import org.jumutang.zsh.model.Rob;
import org.springframework.stereotype.Repository;

/**
 * 秒杀
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
@Repository
public interface RobDaoI {

	/**
	 * 新增秒杀
	 * @param rob
	 * @return
	 */
	public int addRob(Rob rob);
	
	/**
	 * 统计今日秒杀数
	 * @return 
	 */
	public int countRob(Rob rob);
	
	/**
	 * 查询
	 * @return 
	 */
	public List<Rob> queryRob(Rob rob);

	public int updateRob(Rob rob);

	//查询所有
	public List<Rob> queryAll();
}
