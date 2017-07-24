package org.jumutang.zsh.model;

import java.io.Serializable;

/**
 * 兑换物实体类
 * 
 * @author 鲁雨
 * @since 20170222
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 */
public class GoodModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String goodId;
	/*
	 * 兑换商品名
	 */
	private String goodName;
	/*
	 * 库存
	 */
	private Integer inventory;
	/*
	 * 兑换量
	 */
	private Integer receive;
	/*
	 * 商品状态
	 */
	private Integer goodState;
	/*
	 * 兑换所需积分
	 */
	private Integer integration;
	
	/*
	 * 奖品创建时间
	 */
	private String createTime;
	
	/*
	 * 结束时间
	 */
	private String endTime;

	//奖品的图片地址
	private String picUrl;

	//详细图片
	private String detailPicUrl;

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public Integer getInventory() {
		return inventory;
	}
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	public Integer getReceive() {
		return receive;
	}
	public void setReceive(Integer receive) {
		this.receive = receive;
	}
	public Integer getGoodState() {
		return goodState;
	}
	public void setGoodState(Integer goodState) {
		this.goodState = goodState;
	}
	public Integer getIntegration() {
		return integration;
	}
	public void setIntegration(Integer integration) {
		this.integration = integration;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getDetailPicUrl() {
		return detailPicUrl;
	}
	public void setDetailPicUrl(String detailPicUrl) {
		this.detailPicUrl = detailPicUrl;
	}
}
