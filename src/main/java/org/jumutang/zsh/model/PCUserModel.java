package org.jumutang.zsh.model;

import java.io.Serializable;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 9:56 2017/6/26
 * @Modified By:
 */
public class PCUserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String username;

    private String password;

    private Short role;

    private String createTime;

    private String updateTime;

    private Short flag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Short getFlag() {
        return flag;
    }

    public void setFlag(Short flag) {
        this.flag = flag;
    }
}
