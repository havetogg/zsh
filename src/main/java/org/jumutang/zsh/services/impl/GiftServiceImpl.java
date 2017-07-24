package org.jumutang.zsh.services.impl;

import java.util.List;

import org.jumutang.zsh.dao.GiftDaoI;
import org.jumutang.zsh.model.GiftModel;
import org.jumutang.zsh.services.GiftServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("giftServiceI")
public class GiftServiceImpl implements GiftServiceI {

	@Autowired
	private GiftDaoI giftDaoI;
	/**
	 * 定制礼物添加
	 * @param giftModel
	 * @return  
	 */
	public int addGift(GiftModel giftModel) {
		giftModel.setGiftId(ZshUtil.createUuid());
		giftModel.setGiftDate(DateUtil.formatDate());
		giftModel.setGiftStatus(new Short("1"));
		return giftDaoI.addGift(giftModel);
	}
	
	/**
	 * 定制礼物查询
	 * @param giftModel
	 * @return
	 */
	public List<GiftModel> queryGift(GiftModel giftModel) {
		return giftDaoI.queryGift(giftModel);
	}

	
}
