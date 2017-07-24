package org.jumutang.zsh.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tinny.liang on 2017/3/31.
 */
public class ActivityModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String activityId;

    private String theme;

    private String picurl;

    private String illustrate;

    private int num;

    private int integral;

    private String activityDate;

    private String detailPicUrl;

    private String createDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getIllustrate() {
        return illustrate;
    }

    public void setIllustrate(String illustrate) {
        this.illustrate = illustrate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getDetailPicUrl() {
        return detailPicUrl;
    }

    public void setDetailPicUrl(String detailPicUrl) {
        this.detailPicUrl = detailPicUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
