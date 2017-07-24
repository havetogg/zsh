package org.jumutang.zsh.dao;

import java.util.List;

import org.jumutang.zsh.model.GiftModel;
import org.springframework.stereotype.Repository;

/**
 * 礼物dao层
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
@Repository
public interface GiftDaoI {

	/**
	 * 添加礼物信息
	 * @param giftModel
	 * @return
	 */
	public int addGift(GiftModel giftModel);
	
	/**
	 * 查询礼物信息
	 * @param giftModel
	 * @return
	 */
	public List<GiftModel> queryGift(GiftModel giftModel);
}
