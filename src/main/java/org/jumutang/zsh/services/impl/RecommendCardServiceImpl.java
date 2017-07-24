package org.jumutang.zsh.services.impl;

import org.apache.commons.lang.StringUtils;
import org.jumutang.zsh.model.RecommendCard;
import org.jumutang.zsh.services.RecommendCardServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.jumutang.zsh.dao.RecommendCardDaoI;

@Service("recommendCardServiceI")
public class RecommendCardServiceImpl implements RecommendCardServiceI {

	@Autowired
	private RecommendCardDaoI  cardDaoI;
	
	/**
	 * 推荐办卡默认状态为1
	 */
	private final static Short NORMAL_STATUS=1;
	
	/**
	 * 添加推荐办卡
	 * @param card
	 * @return
	 */
	public int addRecommendCard(RecommendCard card){
		card.setRecommendId(ZshUtil.createUuid());
		card.setRecommendStatus(NORMAL_STATUS);
		card.setRecommenDate(DateUtil.formatDate());
		return cardDaoI.addRecommendCard(card);
	}
	
	/**
	 * 更新推荐办卡信息
	 * @param card
	 * @return int
	 */
	public int updateRecommendCard(RecommendCard card){
		
		return cardDaoI.updateRecommendCard(card);
	}
	
	/**
	 * 查询推荐看卡信息
	 * @param card
	 * @return
	 */
	public List<RecommendCard> queryRecommendCard(RecommendCard card){
		if(StringUtils.isBlank(card.getRecommenDate())){
			card.setRecommenDate(null);
		}
		return cardDaoI.queryRecommendCard(card);
	}
}
