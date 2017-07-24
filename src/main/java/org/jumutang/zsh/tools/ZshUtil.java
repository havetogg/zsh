package org.jumutang.zsh.tools;

import net.sf.json.JSONObject;

import java.util.UUID;

/**
 * 中石化工具类
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class ZshUtil {
	
	/**
	 * 创建uuId
	 * 
	 * @return string
	 */
	public static String createUuid(){
		String id = UUID.randomUUID().toString();
		return id.replace("-", "");
	}
	public static void main(String[] args) {
		/*JSONObject object = new JSONObject();
		object.put("auth_code","123");
		String[] sArr = ZshUtil.repalceStr("[1,2]").split(",");
		for(String s:sArr) {
			if ("1".equals(s)) {
				System.out.println("hehe");
			}
		}
		System.out.println(Integer.parseInt(ZshUtil.repalceStr("[1,2]")));*/

		System.out.println(createUuid());
	}
	
	/**
	 * 字符串替换
	 *@param string 
	 *@return string
	 */
	public static String repalceStr(String str){
		String h = str.substring(str.indexOf("[")+1,str.indexOf("]"));
		return h;
	}
}
