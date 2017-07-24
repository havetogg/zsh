package org.jumutang.zsh.services;

import java.util.List;

import org.jumutang.zsh.model.OilModel;

/**
 * 油价服务层
 * 
 * @author ³��
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public interface OilServiceI {
	
	/**
	 * 查询所有油价
	 * 
	 * @return list
	 */
	public List<OilModel> queryOil(OilModel oilModel);

	//查询所有油价
	public List<OilModel> queryOilAll();

	public int updateOilAll(OilModel oilModel);

	/**
	 * 批量插入
	 * 
	 * @param list
	 * 
	 * @return int
	 */
	public int batchInsertOil(List<OilModel> list);
	
	/**
	 * 更新油价状态
	 * 
	 * @return int
	 */
	public int updateOil();
}
