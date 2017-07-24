package org.jumutang.zsh.tools;

/**
 * 返回实体类
 * 
 * @author 鲁雨
 * @since 20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class ResponseModel {
	
	/**
	 * 错误返回
	 * @param describe
	 * @return
	 */
	public static ResResult errorActive(String describe){
		return new ResResult(1,describe);
	}
	
	/**
	 * 成功返回
	 * @param describe
	 * @return
	 */
	public static ResResult successActive(){
		return new ResResult(0);
	}
	
	/**
	 * 成功并有数据返回
	 * 
	 * @param data
	 * @param status
	 */
	public static ResResult returnData(Object data){
		return new ResResult(0,data);
	}
}
