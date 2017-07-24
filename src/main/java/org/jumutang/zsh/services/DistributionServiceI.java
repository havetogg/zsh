package org.jumutang.zsh.services;

import java.util.List;

import org.jumutang.zsh.model.DistributionModel;

/**
 * 任务分配计划
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public interface DistributionServiceI {
	
	/**
	 * 添加配送计划
	 * @param distributionModel
	 * @param int 
	 */
	public int addDistribution(DistributionModel distributionModel);
	
	/**
	 * 查询分配计划
	 * @param distributionModel
	 * @return
	 */
	public List<DistributionModel> queryDistribution(DistributionModel distributionModel);
}
