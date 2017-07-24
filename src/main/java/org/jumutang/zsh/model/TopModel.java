package org.jumutang.zsh.model;

import java.io.Serializable;

/**
 * 排名实体类
 * 
 * @author 鲁雨
 * @since20161223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class TopModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String topId;
	private String employeeName;
	private String employeeHead;
	private Short employeeTop;
	private Short topType;
	private String topDate;
	private Short topStatus;
	
	public String getTopDate() {
		return topDate;
	}
	public void setTopDate(String topDate) {
		this.topDate = topDate;
	}
	public Short getTopStatus() {
		return topStatus;
	}
	public void setTopStatus(Short topStatus) {
		this.topStatus = topStatus;
	}
	public String getTopId() {
		return topId;
	}
	public void setTopId(String topId) {
		this.topId = topId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeHead() {
		return employeeHead;
	}
	public void setEmployeeHead(String employeeHead) {
		this.employeeHead = employeeHead;
	}
	public Short getEmployeeTop() {
		return employeeTop;
	}
	public void setEmployeeTop(Short employeeTop) {
		this.employeeTop = employeeTop;
	}
	public Short getTopType() {
		return topType;
	}
	public void setTopType(Short topType) {
		this.topType = topType;
	}	
}
