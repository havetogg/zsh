package org.jumutang.zsh.model;

import java.io.Serializable;

/**
 * 油价实体类
 *
 *
 */
public class OilModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//油价Id
	private String oilId;
	//油价创建时间
	private String oilDate;
	//油价类型 0.国际油价 1.基准油价 2.客户油价
	private Short oilType;
	//客户类型 0.普通用户 1.vip 2.白银 3.黄金 4.钻石
	private Short customerType;
	//油价种类
	private String oilCategory;
	//油状态 0.无货 1.有货
	private Short oilStatus;
	//油价价格
	private double oilPrice;

	private String remarks;

	public String getOilId() {
		return oilId;
	}

	public void setOilId(String oilId) {
		this.oilId = oilId;
	}

	public String getOilDate() {
		return oilDate;
	}

	public void setOilDate(String oilDate) {
		this.oilDate = oilDate;
	}

	public Short getOilType() {
		return oilType;
	}

	public void setOilType(Short oilType) {
		this.oilType = oilType;
	}

	public Short getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Short customerType) {
		this.customerType = customerType;
	}

	public String getOilCategory() {
		return oilCategory;
	}

	public void setOilCategory(String oilCategory) {
		this.oilCategory = oilCategory;
	}

	public Short getOilStatus() {
		return oilStatus;
	}

	public void setOilStatus(Short oilStatus) {
		this.oilStatus = oilStatus;
	}

	public double getOilPrice() {
		return oilPrice;
	}

	public void setOilPrice(double oilPrice) {
		this.oilPrice = oilPrice;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
