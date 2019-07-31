package com.example.demo.entity;

import com.example.demo.util.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

import java.io.Serializable;

@Excel("各电表电量")
public class ExportTotal implements Serializable {

    @ExcelField(value = "电表ID")
    private int EMeterID;

    @ExcelField(value = "电表名")
    private String EMeterName;
    /**
     * 正有功总
     */
    @ExcelField(value = "部门名称")
    private String EDepartmentName;

    @ExcelField(value = "部门比例")
    private double EDepartmentRatio;

    @ExcelField(value = "成本中心")
    private String CostCenterName;

    @ExcelField(value = "成本中心比例")
    private double CostCenterRatio;

    @ExcelField(value = "正有功总")
    private double powerTotal;

    @ExcelField(value = "倍率")
    private double num;

    @ExcelField(value = "读数差")
    private double difValue;

    @ExcelField(value = "起始表底读数")
    private double beginNumber;

    @ExcelField(value = "结束表底读数")
    private double endNumber;

    @ExcelField(value = "起始表底抄表时间",writeConverter = TimeConverter.class)
    private String beginTime;

    @ExcelField(value = "结束表底抄表时间",writeConverter = TimeConverter.class)
    private String endTime;

    public int getEMeterID() {
        return EMeterID;
    }

    public void setEMeterID(int EMeterID) {
        this.EMeterID = EMeterID;
    }

    public String getEMeterName() {
        return EMeterName;
    }

    public void setEMeterName(String EMeterName) {
        this.EMeterName = EMeterName;
    }

    public String getEDepartmentName() {
        return EDepartmentName;
    }

    public void setEDepartmentName(String EDepartmentName) {
        this.EDepartmentName = EDepartmentName;
    }

    public double getEDepartmentRatio() {
        return EDepartmentRatio;
    }

    public void setEDepartmentRatio(double EDepartmentRatio) {
        this.EDepartmentRatio = EDepartmentRatio;
    }

    public String getCostCenterName() {
        return CostCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        CostCenterName = costCenterName;
    }

    public double getCostCenterRatio() {
        return CostCenterRatio;
    }

    public void setCostCenterRatio(double costCenterRatio) {
        CostCenterRatio = costCenterRatio;
    }

    public double getPowerTotal() {
        return powerTotal;
    }

    public void setPowerTotal(double powerTotal) {
        this.powerTotal = powerTotal;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public double getDifValue() {
        return difValue;
    }

    public void setDifValue(double difValue) {
        this.difValue = difValue;
    }

    public double getBeginNumber() {
        return beginNumber;
    }

    public void setBeginNumber(double beginNumber) {
        this.beginNumber = beginNumber;
    }

    public double getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(double endNumber) {
        this.endNumber = endNumber;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ExportTotal{" +
                "EMeterID=" + EMeterID +
                ", EMeterName='" + EMeterName + '\'' +
                ", EDepartmentName='" + EDepartmentName + '\'' +
                ", EDepartmentRatio=" + EDepartmentRatio +
                ", CostCenterName='" + CostCenterName + '\'' +
                ", CostCenterRatio=" + CostCenterRatio +
                ", powerTotal=" + powerTotal +
                ", num=" + num +
                ", difValue=" + difValue +
                ", beginNumber=" + beginNumber +
                ", endNumber=" + endNumber +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
