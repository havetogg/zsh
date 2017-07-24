package org.jumutang.zsh.model;

import java.io.Serializable;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 14:59 2017/4/19
 * @Modified By:
 */
public class DailyModel implements Serializable{

    private static final long serialVersionUID = 1L;

    private String dailyId;

    //姓名
    private String customerName;

    //外勤通当月
    private String monthAttendance;

    //外勤通排名
    private String attendanceRank;

    //当日日报
    private String todayDaily;

    //当月日报
    private String monthDaily;

    //完成率
    private String dailyRate;

    //日报排名
    private String dailyRank;

    //当月开发
    private String monthDevelop;

    //奖励
    private String reward;

    //备注
    private String remark;

    //创建日期
    private String createDate;

    public String getDailyId() {
        return dailyId;
    }

    public void setDailyId(String dailyId) {
        this.dailyId = dailyId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMonthAttendance() {
        return monthAttendance;
    }

    public void setMonthAttendance(String monthAttendance) {
        this.monthAttendance = monthAttendance;
    }

    public String getAttendanceRank() {
        return attendanceRank;
    }

    public void setAttendanceRank(String attendanceRank) {
        this.attendanceRank = attendanceRank;
    }

    public String getTodayDaily() {
        return todayDaily;
    }

    public void setTodayDaily(String todayDaily) {
        this.todayDaily = todayDaily;
    }

    public String getMonthDaily() {
        return monthDaily;
    }

    public void setMonthDaily(String monthDaily) {
        this.monthDaily = monthDaily;
    }

    public String getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(String dailyRate) {
        this.dailyRate = dailyRate;
    }

    public String getDailyRank() {
        return dailyRank;
    }

    public void setDailyRank(String dailyRank) {
        this.dailyRank = dailyRank;
    }

    public String getMonthDevelop() {
        return monthDevelop;
    }

    public void setMonthDevelop(String monthDevelop) {
        this.monthDevelop = monthDevelop;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
