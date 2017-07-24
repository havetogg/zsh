package org.jumutang.zsh.services;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.model.GoodModel;
import org.jumutang.zsh.model.PageMode;

/**
 * 兑换礼物服务层接口
 * 
 * @author 鲁雨
 * @since 20170222
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public interface GoodServiceI {

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
	public PageMode queryGood(Map<String,Object> param,PageMode pageMode);
	
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
