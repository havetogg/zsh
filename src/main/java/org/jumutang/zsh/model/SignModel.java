package org.jumutang.zsh.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到信息实体类
 * 
 * @author 鲁雨
 * @since20161225
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 */
public class SignModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String signId;
	private String openId;
	private String userName;
	private String signDate;
	//签到备注
	private String signMarks;
	private String signAddress;
	//
	private String signCoordinate;
	private String scenePhotos;
	private Short signType;
	private Short signStatus;
	
	public String getSignMarks() {
		return signMarks;
	}
	public void setSignMarks(String signMarks) {
		this.signMarks = signMarks;
	}
	public Short getSignType() {
		return signType;
	}
	public void setSignType(Short signType) {
		this.signType = signType;
	}
	public Short getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(Short signStatus) {
		this.signStatus = signStatus;
	}
	public String getSignId() {
		return signId;
	}
	public void setSignId(String signId) {
		this.signId = signId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	
	public String getSignAddress() {
		return signAddress;
	}
	public void setSignAddress(String signAddress) {
		this.signAddress = signAddress;
	}
	public String getSignCoordinate() {
		return signCoordinate;
	}
	public void setSignCoordinate(String signCoordinate) {
		this.signCoordinate = signCoordinate;
	}
	public String getScenePhotos() {
		return scenePhotos;
	}
	public void setScenePhotos(String scenePhotos) {
		this.scenePhotos = scenePhotos;
	}
	
	
	
}
