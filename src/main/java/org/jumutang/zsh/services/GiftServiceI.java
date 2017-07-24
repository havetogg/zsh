package org.jumutang.zsh.services;
import java.util.List;

/**
 * ����ķ����
 * 
 * @author ³��
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
import org.jumutang.zsh.model.GiftModel;

/**
 * 礼物服务接口
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public interface GiftServiceI {

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
