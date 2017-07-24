package org.jumutang.zsh.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jumutang.zsh.dao.SignDaoI;
import org.jumutang.zsh.model.PageMode;
import org.jumutang.zsh.model.SignModel;
import org.jumutang.zsh.services.SignServiceI;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("signServiceI")
public class SignServiceImpl implements SignServiceI {

	@Autowired
	private SignDaoI signDaoI;
	/**
	 * 默认状态为1
	 */
	private final static Short NORMAL_SIGN=1;
	
	/**
	 * 默认签到类型为1
	 */
	private final static Short SIGN_TYPE = 1;
	
	private final static Integer PAGE_SIZE =10; 
	
	
	/**
	 * 添加签到信息
	 * @param signModel
	 * @return
	 */
	public int addSign(SignModel signModel) {
		signModel.setSignId(ZshUtil.createUuid());
		if(signModel.getSignStatus() ==null){
			signModel.setSignStatus(NORMAL_SIGN);
		}
		return signDaoI.addSign(signModel);
	}

	/**
	 * 查询签到信息
	 * @param signModel
	 * @return
	 */
	public List<SignModel> querySign(SignModel signModel) {
		signModel.setSignType(SIGN_TYPE);
		signModel.setSignStatus(NORMAL_SIGN);
		return signDaoI.querySign(signModel);
	}

	/**
	 * 更新签到状态
	 * @param signModel
	 * @return
	 */
	public int updateSignStatus(SignModel signModel) {
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("signStatus", new Short("2"));
		param.put("openId", signModel.getOpenId());
		param.put("signDate", signModel.getSignDate());
		param.put("oldStatus", signModel.getSignStatus());
		return signDaoI.updateSignStatus(param);
	}

	/**
	 * 更新签到信息
	 * @param signModel
	 * @return
	 */
	public int updateSigne(SignModel signModel) {
		
		return signDaoI.updateSigne(signModel);
	}
	
	/**
	 * 分页查询签到信息
	 * @param param
	 * @param pageIndex
	 * @return
	 */
	public PageMode queryByPage(Map<String,Object> param,PageMode pageMode){
		param.put("signType", 1);
		int index = (pageMode.getPageIndex()-1)*PAGE_SIZE;
		param.put("pageIndex", index);
		param.put("pageSize", PAGE_SIZE);
		pageMode.setPageNow(pageMode.getPageIndex());
		List<SignModel> list = signDaoI.queryByPage(param);
		pageMode.setList(list);
		return pageMode;
	}

	/**
	 * 统计数据条数
	 * @param param
	 * @return
	 */
	public int countSign(Map<String,Object> param){
		param.put("signType", 1);
		return signDaoI.countSign(param);
	}

}
