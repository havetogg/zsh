package org.jumutang.zsh.dao;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.model.GoodModel;
import org.springframework.stereotype.Repository;

/**
 * 兑换礼物dao接口
 * 
 * @author 鲁雨
 * @since 20170222
 * @version v1.0
 *
 *	copyright Luyu(18994139782@163.com)
 */
@Repository
public interface GoodDaoI {

	/**
	 * 添加礼物兑换信息
	 * @param goodModel
	 * @return
	 */
	public int addGood(GoodModel goodModel);
	
	/**
	 * 修改礼物信息
	 * @param goodModel
	 * @return
	 */
	public int updateGood(GoodModel goodModel);
	
	/**
	 * 查询礼物信息
	 * @param goodModel
	 * @return
	 */
	public List<GoodModel> queryGood(Map<String,Object> param);
	
	/**
	 * 统计条数
	 * @param param
	 * @return
	 */
	public int countGood(Map<String,Object> param);
	
	/**
	 * 查信息
	 * @param goodModel
	 * @return
	 */
	public List<GoodModel> queryGoodInfo(GoodModel goodModel);
}
