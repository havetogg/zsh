package org.jumutang.zsh.dao;

import org.jumutang.zsh.model.SignModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 签到dao接口
 * 
 * @author 鲁雨
 * @since20170104
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
@Repository
public interface SignDaoI {
	
	/**
	 * 添加签到信息
	 * @param signModel
	 * @return
	 */
	public int addSign(SignModel signModel);
	
	/**
	 * 查询签到信息
	 * @param signModel
	 * @return
	 */
	public List<SignModel> querySign(SignModel signModel);
	
	/**
	 * 更新签到状态
	 * @param param
	 * @return
	 */
	public int updateSignStatus(Map<String,Object> param);
	
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
	public List<SignModel> queryByPage(Map<String,Object> param);
	
	/**
	 * 统计数据条数
	 * @param param
	 * @return
	 */
	public int countSign(Map<String,Object> param);
}
