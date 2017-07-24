package org.jumutang.zsh.services;

import java.util.List;

import org.jumutang.zsh.model.RecommendCard;

/**
 * 推荐办卡服务接口
 * 
 * @author 鲁雨
 * @since20170104
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public interface RecommendCardServiceI {

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
	public List<RecommendCard>queryRecommendCard(RecommendCard card);
}
