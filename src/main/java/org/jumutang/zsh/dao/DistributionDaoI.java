package org.jumutang.zsh.dao;

import java.util.List;

import org.jumutang.zsh.model.DistributionModel;
import org.springframework.stereotype.Repository;

/**
 * 分配任务dao层
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
@Repository
public interface DistributionDaoI {
	
	/**
	 * 添加任务分配计划
	 * 
	 * @param distributionModel
	 * @param int 
	 */
	public int addDistribution(DistributionModel distributionModel);
	
	/**
	 * 查询任务分配计划
	 * @param distributionModel
	 * @return
	 */
	public List<DistributionModel> queryDistribution(DistributionModel distributionModel);
}
