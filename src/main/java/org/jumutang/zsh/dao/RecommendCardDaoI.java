package org.jumutang.zsh.dao;

import java.util.List;

import org.jumutang.zsh.model.RecommendCard;
import org.springframework.stereotype.Repository;

/**
 * 推荐办卡dao层
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
@Repository
public interface RecommendCardDaoI {
	
	/**
	 * 添加办卡信息
	 * @param card
	 * @return
	 */
	public int addRecommendCard(RecommendCard card);
	
	/**
	 * 更新推荐办卡信息
	 * @param card
	 * @return int
	 */
	public int updateRecommendCard(RecommendCard card);
	
	/**
	 * 查询推荐办卡信息
	 * @param card
	 * @return
	 */
	public List<RecommendCard> queryRecommendCard(RecommendCard card);
}
