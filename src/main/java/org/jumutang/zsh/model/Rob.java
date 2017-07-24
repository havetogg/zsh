package org.jumutang.zsh.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀实体类
 * 
 * @author 鲁雨
 * @since20161225
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class Rob implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//id
	private String robId;
	//openId
	private String openId;
	//activityId
	private String activityId;
	//秒杀时间
	private String robDate;
	//收货状态 1.自提 2.选择收货地址
	private Short receiveStatus;
	//兑换物发售情况 1.未兑换 2.已兑换 3.已发货 4.取消
	private Short robStatus;
	//发货时间
	private String deliveryDate;
	//收货地址id
	private String addressId;
	//兑换收货地址
	private String pickupAddress;

	public String getRobId() {
		return robId;
	}

	public void setRobId(String robId) {
		this.robId = robId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getRobDate() {
		return robDate;
	}

	public void setRobDate(String robDate) {
		this.robDate = robDate;
	}

	public Short getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(Short receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public Short getRobStatus() {
		return robStatus;
	}

	public void setRobStatus(Short robStatus) {
		this.robStatus = robStatus;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
}
