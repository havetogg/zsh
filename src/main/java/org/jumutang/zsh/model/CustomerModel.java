package org.jumutang.zsh.model;

import java.io.Serializable;

/**
 * 客户实体类
 * 
 * @author 鲁雨
 * @since20161225
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class CustomerModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//id
	private String customerId;
	//1客户  2.员工
	private Short customerType;
	//用户名
	private String customerName;
	//微信号
	private String weiXinId;
	//手机号
	private String phone;
	//企业号userId
	private String customerOpenId;


	//客户代码
	private String customerCode;

	//客户地址
	private String customerAddress;
	//联系人
	private String phonePerson;
	//客户积分
	private Integer customerIntegral;
	//客户等级 0.普通青铜 1.大客户白银 2.VIP黄金
	private short customerLevel;
	//客户头像
	private String customerHead;
	//客户状态 1.正常 2.冻结 3.删除
	private Short customerStatus;
	//客户经理
	private String manager;
	//客户经理电话
	private String managerPhone;
	//爱好
	private String hobby;
	//生日
	private String birthday;
	//一户一册
	private String oneHousehold;
	//油价推送 0推送 1或者其他 不推送
	private Short oilPush;

	//员工id
	private String employeeId;
	//员工编号
	private String employeeNo;
	//性别
	private String gender;
	//邮箱
	private String email;
	//所属加油站
	private String oilStation;
	//员工岗位
	private String position;
	//业绩
	private String achievement;

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeiXinId() {
		return weiXinId;
	}
	public void setWeiXinId(String weiXinId) {
		this.weiXinId = weiXinId;
	}
	public String getOilStation() {
		return oilStation;
	}
	public void setOilStation(String oilStation) {
		this.oilStation = oilStation;
	}
	public String getAchievement() {
		return achievement;
	}
	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getPhonePerson() {
		return phonePerson;
	}
	public void setPhonePerson(String phonePerson) {
		this.phonePerson = phonePerson;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getCustomerIntegral() {
		return customerIntegral;
	}
	public void setCustomerIntegral(Integer customerIntegral) {
		this.customerIntegral = customerIntegral;
	}
	public String getCustomerHead() {
		return customerHead;
	}
	public void setCustomerHead(String customerHead) {
		this.customerHead = customerHead;
	}
	public Short getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(Short customerStatus) {
		this.customerStatus = customerStatus;
	}
	public String getCustomerOpenId() {
		return customerOpenId;
	}
	public void setCustomerOpenId(String customerOpenId) {
		this.customerOpenId = customerOpenId;
	}
	public Short getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Short customerType) {
		this.customerType = customerType;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getManagerPhone() {
		return managerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public short getCustomerLevel() {
		return customerLevel;
	}
	public void setCustomerLevel(short customerLevel) {
		this.customerLevel = customerLevel;
	}
	public String getOneHousehold() {
		return oneHousehold;
	}
	public void setOneHousehold(String oneHousehold) {
		this.oneHousehold = oneHousehold;
	}
	public Short getOilPush() {
		return oilPush;
	}
	public void setOilPush(Short oilPush) {
		this.oilPush = oilPush;
	}
}
