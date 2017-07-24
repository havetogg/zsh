package org.jumutang.zsh.services;

import java.util.List;
import java.util.Map;

import org.jumutang.zsh.model.PageMode;
import org.jumutang.zsh.model.SignModel;

/**
 * 签到服务接口
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public interface SignServiceI {

	/**
	 * 添加签到信息
	 * @param signModel
	 * @return
	 */
	public int addSign(SignModel signModel);
	
	/**
	 * 查询签到
	 * @param signModel
	 * @return
	 */
	public List<SignModel> querySign(SignModel signModel);
	
	/**
	 * 更新签到状态
	 * @param param
	 * @return
	 */
	public int updateSignStatus(SignModel signModel);
	
	/**
	 * 更新签到信息
	 * @param signModel
	 * @return
	 */
	public int updateSigne(SignModel signModel);
	
	/**
	 * 分页查询签到信息
	 * @param param
	 * @return
	 */
	public PageMode queryByPage(Map<String,Object> param,PageMode pageMode);
	
	/**
	 * 统计数据条数
	 * @param param
	 * @return
	 */
	public int countSign(Map<String,Object> param);
}
