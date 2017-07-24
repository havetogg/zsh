package org.jumutang.zsh.tools;

import org.apache.log4j.Logger;
import org.jumutang.zsh.model.WCUserModel;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 通讯录管理工具
 *
 * @author 鲁雨
 * @since 20170222
 * @version v1.0
 *
 * copyright Luyu(18994139782@163.com)
 */
public class UserManagerUtil {
	private static Logger logger = Logger.getLogger(UserManagerUtil.class);
	
	//创建用户信息
	private  static String CREATE_USER="https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
	//更新用户信息
	private  static String UPDATE_USER="https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
	//删除用户信息
	private  static String DELETE_USER="https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=USERID";
	//批量删除
	private  static String BATCH_DELETR_USER="https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=ACCESS_TOKEN";
	//获取用户信息
	private  static String GET_USER="https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
	//获取用户部门信息     fetch_child:1/0：是否递归获取子部门下面的成员        status:0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4
	private  static String GET_DEP_USER="https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
	
	/**
	 * 创建用户
	 * @param wcUserModel
	 * @return
	 */
	public static String createUser(WCUserModel wcUserModel) throws Exception{
		String access_token = AccessTokenUtil.getAccessToken();
		logger.info("获取的access_token----------------"+access_token);
		CREATE_USER = CREATE_USER.replace("ACCESS_TOKEN", access_token);
		JSONObject json = JSONObject.fromObject(wcUserModel);
		List<String> delArr = new ArrayList<>();
		Iterator it = json.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = json.getString(key);
			if("".equals(value)){
				delArr.add(key);
			}
		}
		for(String s:delArr){
			json.discard(s);
		}
		json.put("enable",1);
		logger.info(json);
		String result = HttpUtil.sendPost(CREATE_USER, "UTF-8", json.toString());
		return result ;
	}

	/**
	 * 修改用户
	 * @param wcUserModel
	 * @return
	 */
	public static String updateUser(WCUserModel wcUserModel) throws Exception{
		String access_token = AccessTokenUtil.getAccessToken();
		logger.info("获取的access_token----------------"+access_token);
		UPDATE_USER = UPDATE_USER.replace("ACCESS_TOKEN", access_token);
		JSONObject json = JSONObject.fromObject(wcUserModel);
		List<String> delArr = new ArrayList<>();
		Iterator it = json.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = json.getString(key);
			if("".equals(value)){
				delArr.add(key);
			}
		}
		for(String s:delArr){
			json.discard(s);
		}
		json.put("enable",1);
		String result = HttpUtil.sendPost(UPDATE_USER, "UTF-8", json.toString());
		return result ;
	}

	public static String delUser(String userId) throws Exception{
		String access_token = AccessTokenUtil.getAccessToken();
		logger.info("获取的access_token----------------"+access_token);
		DELETE_USER = DELETE_USER.replace("ACCESS_TOKEN", access_token);
		DELETE_USER = DELETE_USER.replace("USERID", userId);
		String result = HttpUtil.sendPost(DELETE_USER, "UTF-8", null);
		return result ;
	}

	public static void main(String[] args) throws Exception{
		System.out.println(UserManagerUtil.delUser("15756006008"));
	}
}
