package com.example.demo.entity;

import java.util.Date;

public class Params {
    private String parameter;
    private String EMeterName;
    private String EStationName;
    private Date beginTime;
    private Date endTime;
    //数据来源
    private String Source;
    //功率类型
    private String powerType;
    private String pageNum;
    private String pageSize;
    //时间间隔
    private String dateType;
    private int EMeterID;

    public int getEMeterID() {
        return EMeterID;
    }

    public void setEMeterID(int EMeterID) {
        this.EMeterID = EMeterID;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getEMeterName() {
        return EMeterName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    public void setEMeterName(String EMeterName) {
        this.EMeterName = EMeterName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getPowerType() {
        return powerType;
    }

    public String getEStationName() {
        return EStationName;
    }

    public void setEStationName(String EStationName) {
        this.EStationName = EStationName;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    @Override
    public String toString() {
        return "Params{" +
                "parameter='" + parameter + '\'' +
                ", EMeterName='" + EMeterName + '\'' +
                ", EStationName='" + EStationName + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", Source='" + Source + '\'' +
                ", powerType='" + powerType + '\'' +
                ", pageNum='" + pageNum + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", dateType='" + dateType + '\'' +
                ", EMeterID=" + EMeterID +
                '}';
    }
}
