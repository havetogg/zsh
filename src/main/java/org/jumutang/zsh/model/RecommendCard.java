package org.jumutang.zsh.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 推荐办卡实体类
 * 
 * @author 鲁雨
 * @since20170104
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class RecommendCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String recommendId;
	
	private String customerName;
	private String sex;
	private String telephone;
	private String address;
	private String openId;
	private Short recommendStatus;
	private String recommenDate;
	public String getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Short getRecommendStatus() {
		return recommendStatus;
	}
	public void setRecommendStatus(Short recommendStatus) {
		this.recommendStatus = recommendStatus;
	}
	public String getRecommenDate() {
		return recommenDate;
	}
	public void setRecommenDate(String recommenDate) {
		this.recommenDate = recommenDate;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
