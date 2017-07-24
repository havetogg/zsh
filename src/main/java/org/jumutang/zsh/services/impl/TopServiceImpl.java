package org.jumutang.zsh.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jumutang.zsh.dao.TopDaoI;
import org.jumutang.zsh.model.OilModel;
import org.jumutang.zsh.model.TopModel;
import org.jumutang.zsh.services.OilServiceI;
import org.jumutang.zsh.services.TopServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("topService")
public class TopServiceImpl implements TopServiceI {

	@Autowired
	private TopDaoI topDaoI;
	
	/**
	 * 失败状态为2
	 */
	private final static Short LOSE_EFFICACY=2;
	
	/**
	 * 默认ok状态未1
	 */
	private final static Short OK_STATUS=1;
	/**
	 * 查询排名信息
	 * @return list
	 */
	public List<TopModel> queryAll() {

		return topDaoI.queryAll(OK_STATUS);
	}

	/**
	 * 批量更新
	 * 
	 * @param list
	 * @return int
	 */
	public int batchInsertTop(List<TopModel> list) {
		return topDaoI.batchInsertTop(list);	
	}
	
	/**
	 * 更新排名信息
	 * 
	 * @param param
	 * 
	 * @return int
	 */
	public int updateTop() {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("osEfficacy", LOSE_EFFICACY);
		param.put("now", OK_STATUS);
		return topDaoI.updateTop(param);
	}

	/**
	 * 根据类型查询排名信息
	 * @param type
	 * @return 
	 */
	public List<TopModel> queryTopByType(Short type) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("status", OK_STATUS);
		param.put("type", type);
		return topDaoI.queryTopByType(param);
	}
}
