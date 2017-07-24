package org.jumutang.zsh.dao;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.model.OilModel;
import org.springframework.stereotype.Repository;

/**
 * 油价dao层
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
@Repository
public interface OilDaoI {

	/**
	 * 查询油价信息
	 * 
	 * @param status
	 * 
	 * @return string
	 */
	public List<OilModel> queryOil(OilModel oilModel);


	//查询所有油价信息
	public List<OilModel> queryOilAll();

	//修改油价信息
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
	 * 更新
	 * 
	 * @param param
	 * 
	 * @return int
	 */
	public int updateOil(Map<String,Object> param);
}
