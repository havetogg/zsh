package org.jumutang.zsh.model;

import java.io.Serializable;

/**
 * 礼物实体类
 * 
 * @author 鲁雨
 * @since20161225
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class GiftModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String giftId;
	private String giftName;
	private String openId;
	private Short giftStatus;
	private String giftDate;
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Short getGiftStatus() {
		return giftStatus;
	}
	public void setGiftStatus(Short giftStatus) {
		this.giftStatus = giftStatus;
	}
	public String getGiftDate() {
		return giftDate;
	}
	public void setGiftDate(String giftDate) {
		this.giftDate = giftDate;
	}
	
	
}
