package org.jumutang.zsh.dao;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.model.TopModel;
import org.springframework.stereotype.Repository;

/**
 * 今日排名
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
@Repository
public interface TopDaoI {
	
	/**
	 * 查询今日油价
	 * @return list
	 */
	public List<TopModel> queryAll(Short status);
	
	/**
	 * 查询油价
	 * @param param
	 * @return list
	 */
	public List<TopModel> queryTopByType(Map<String,Object> param);
	
	/**
	 * 批量插入
	 * 
	 * @param list
	 * @return int
	 */
	public int batchInsertTop(List<TopModel> list);
	
	/**
	 * 更新排名信息
	 * 
	 * @param param
	 * 
	 * @return int
	 */
	public int updateTop(Map<String,Object> param);
}
