package org.jumutang.zsh.services.impl;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.dao.GoodDaoI;
import org.jumutang.zsh.model.GoodModel;
import org.jumutang.zsh.model.PageMode;
import org.jumutang.zsh.services.GoodServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("goodServiceI")
public class GoodServiceImpl implements GoodServiceI {
	
	@Autowired
	private GoodDaoI goodDaoI;

	/*
	 * 默认每页十条
	 */
	private final static Integer PAGE_SIZE=500000;
	/**
	 * 添加礼物兑换信息
	 * @param goodModel
	 * @return
	 */
	public int addGood(GoodModel goodModel){
		goodModel.setGoodId(ZshUtil.createUuid());
		goodModel.setGoodState(1);
		goodModel.setCreateTime(DateUtil.getFullTime());
		goodModel.setReceive(0);
		return goodDaoI.addGood(goodModel);
	}
	
	/**
	 * 修改礼物信息
	 * @param goodModel
	 * @return
	 */
	public int updateGood(GoodModel goodModel){
		return goodDaoI.updateGood(goodModel);
	}
	
	/**
	 * 查询礼物信息
	 * @param goodModel
	 * @return
	 */
	public PageMode queryGood(Map<String,Object>	param,PageMode pageMode){
		Integer pageIndex = pageMode.getPageIndex();
		pageMode.setPageNow(pageIndex);
		pageIndex = (pageIndex-1)*PAGE_SIZE;
		param.put("pageIndex", pageIndex);
		param.put("pageSize", PAGE_SIZE);
		List<GoodModel> list =  goodDaoI.queryGood(param);
		pageMode.setList(list);
		return pageMode;
	}

	/**
	 * 统计数据条数
	 * @param param
	 * @return
	 */
	public int countGood(Map<String, Object> param) {
		return goodDaoI.countGood(param);
	
	}
	
	/**
	 * 查信息
	 * @param goodModel
	 * @return
	 */
	public List<GoodModel> queryGoodInfo(GoodModel goodModel){
		goodModel.setGoodState(1);
		return goodDaoI.queryGoodInfo(goodModel);
	}
}
