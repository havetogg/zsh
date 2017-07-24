package org.jumutang.zsh.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jumutang.zsh.dao.DistributionDaoI;
import org.jumutang.zsh.model.DistributionModel;
import org.jumutang.zsh.services.DistributionServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("distributionServiceI")
public class DistributionServiceImpl implements DistributionServiceI {

	@Autowired
	private DistributionDaoI distributionDaoI;
	
	/**
	 * 默认进行的状态未1
	 */
	private final static String STATUS_ING="1";
	
	/**
	 * 默认审核的状态未1
	 */
	private final static String CHECK_PENDING="1";
	
	/**
	 * 添加配送计划
	 * @param distributionModel
	 * @param int 
	 */
	public int addDistribution(DistributionModel distributionModel) {
		distributionModel.setId(ZshUtil.createUuid());
		distributionModel.setState(STATUS_ING);
		distributionModel.setAuditstate(CHECK_PENDING);
		distributionModel.setPlandate(DateUtil.formatDate());
		return distributionDaoI.addDistribution(distributionModel);
	}
	
	/**
	 * 查询配送计划
	 * @param distributionModel
	 * @return
	 */
	public List<DistributionModel> queryDistribution(DistributionModel distributionModel) {
		return distributionDaoI.queryDistribution(distributionModel);
	}

}
