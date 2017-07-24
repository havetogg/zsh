package org.jumutang.zsh.model;

import java.io.Serializable;

/**
 * 配送实体类
 * 
 * @author 鲁雨
 * @since20161225
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class DistributionModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String oilcompany;
	private String customercode;
	private String oiladdress;
	private String oiltel;
	//收油联系人
	private String oilcontacts;
	//油品种
	private String vars;
	//数量
	private String amount;
	private String time;
	//运费结算
	private String balance;
	//业务经理
	private String manager;
	private String state;
	//审核状态
	private String auditstate;
	private String driver;
	//提单号
	private String no;
	private String driverno;
	private String cardno;
	private String plandate;
	//员工id
    private String employeeId;
    
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getPlandate() {
		return plandate;
	}
	public void setPlandate(String plandate) {
		this.plandate = plandate;
	}
	public String getDriverno() {
		return driverno;
	}
	public void setDriverno(String driverno) {
		this.driverno = driverno;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}
	public String getCustomercode() {
		return customercode;
	}
	public void setCustomerCode(String customercode) {
		this.customercode = customercode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOilcompany() {
		return oilcompany;
	}
	public void setOilcompany(String oilcompany) {
		this.oilcompany = oilcompany;
	}
	public String getOiladdress() {
		return oiladdress;
	}
	public void setOiladdress(String oiladdress) {
		this.oiladdress = oiladdress;
	}
	public String getOiltel() {
		return oiltel;
	}
	public void setOiltel(String oiltel) {
		this.oiltel = oiltel;
	}
	public String getOilcontacts() {
		return oilcontacts;
	}
	public void setOilcontacts(String oilcontacts) {
		this.oilcontacts = oilcontacts;
	}
	public String getVars() {
		return vars;
	}
	public void setVars(String vars) {
		this.vars = vars;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAuditstate() {
		return auditstate;
	}
	public void setAuditstate(String auditstate) {
		this.auditstate = auditstate;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	
	
}	
