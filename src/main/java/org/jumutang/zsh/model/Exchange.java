package org.jumutang.zsh.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分兑换实体类
 * 
 * @author 鲁雨
 * @since20161225
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class Exchange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exchangeId;
	//商品id
	private String goodId;
	//商品名称
	private String goodName;
	//兑换数量
	private Integer exchangeCount;
	//兑换积分
	private Integer integration;
	//兑换人openId
	private String openId;
	//收货状态 1自提 2选择收货地址
	private short receiveStatus;
	//兑换状态 1.未兑换 2.已兑换 3.已发货 4.取消
	private Short exchangeStatus;
	//兑换时间
	private String exchangeDate;
	//发货时间
	private String deliveryDate;
	//收货地址id
	private String addressId;
	//自提收货地址
	private String pickupAddress;

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Integer getExchangeCount() {
		return exchangeCount;
	}

	public void setExchangeCount(Integer exchangeCount) {
		this.exchangeCount = exchangeCount;
	}

	public Integer getIntegration() {
		return integration;
	}

	public void setIntegration(Integer integration) {
		this.integration = integration;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public short getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(short receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public Short getExchangeStatus() {
		return exchangeStatus;
	}

	public void setExchangeStatus(Short exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

	public String getExchangeDate() {
		return exchangeDate;
	}

	public void setExchangeDate(String exchangeDate) {
		this.exchangeDate = exchangeDate;
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
