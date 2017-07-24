package org.jumutang.zsh.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author ³��
 * @since 20161224
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class DateUtil {

	/**
	 * 当前时间转化字符串
	 * @return string
	 */
	public static String getDate(){
		Date date = new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("YYYY/MM/dd");
		return sdf.format(date);
	}
	
	/**
	 * 当前时间转化字符串
	 * @return
	 */
	public static String formatDate(){
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 获取当前时间的时间戳
	 * @return
	 */
	public static String getTime(){
		Date date = new Date();
		return date.getTime()+"";
	}
	
	/**
	 * 获取详细的时间
	 * @return
	 */
	public static String getFullTime(){
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}
}
