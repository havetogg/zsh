package org.jumutang.zsh.model;

public class WCUserModel {

	private String userid;
	private String name;
	private String[] department;
	//岗位
	private String position;
	//手机号
	private String mobile;
	//性别
	private String gender;
	//邮箱
	private String email;
	//微信号
	private String weixinid;
	//启用/禁用成员。1表示启用成员，0表示禁用成员
	private Integer enable;
	private String avatar_mediaid;
	//拓展信息
	private Object extattr;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getDepartment() {
		return department;
	}
	public void setDepartment(String[] department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeixinid() {
		return weixinid;
	}
	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public String getAvatar_mediaid() {
		return avatar_mediaid;
	}
	public void setAvatar_mediaid(String avatar_mediaid) {
		this.avatar_mediaid = avatar_mediaid;
	}
	public Object getExtattr() {
		return extattr;
	}
	public void setExtattr(Object extattr) {
		this.extattr = extattr;
	}
	
	
}
