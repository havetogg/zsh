package org.jumutang.zsh.model;

import java.io.Serializable;

/**
 * Created by RuanYJ on 2017/3/6.
 */
public class ReceiveAddress implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String rId;
    private String userId;
    private String rName;
    private String rPhone;
    private String rAddress;
    private String createTime;
    private String isDefault;
    private String isDelete;

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrPhone() {
        return rPhone;
    }

    public void setrPhone(String rPhone) {
        this.rPhone = rPhone;
    }

    public String getrAddress() {
        return rAddress;
    }

    public void setrAddress(String rAddress) {
        this.rAddress = rAddress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "ReceiveAddress{" +
                "rId='" + rId + '\'' +
                ", userId='" + userId + '\'' +
                ", rName='" + rName + '\'' +
                ", rPhone='" + rPhone + '\'' +
                ", rAddress='" + rAddress + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", isDelete='" + isDelete + '\'' +
                '}';
    }
}
