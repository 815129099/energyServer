package com.example.demo.entity;

import com.example.demo.util.TimeConverter;
import com.example.demo.util.TimeConverter1;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import java.io.Serializable;

@Excel("峰平谷分析表")
public class ExportPeak implements Serializable {

    /**
     * 电表ID
     */
    @ExcelField(value = "电表ID")
    private Integer EMeterID;

    @ExcelField(value = "电表名")
    private String EMeterName;

    /**
     * 统计时间
     */
    @ExcelField(value = "日期",writeConverter = TimeConverter1.class)
    private String timeTag;



    /**
     * 每天的峰值
     */
    @ExcelField(value = "峰值")
    private Double peak;

    /**
     * 每天的平值
     */
    @ExcelField(value = "平值")
    private Double flat;

    /**
     * 每天的谷值
     */
    @ExcelField(value = "谷值")
    private Double ravine;



    /**
     * 每天的总电量
     */
    @ExcelField(value = "总电量")
    private Double powerTotal;

    /**
     * 峰值占比
     */
    @ExcelField(value = "峰占比")
    private Double peakPercent;

    public String getEMeterName() {
        return EMeterName;
    }

    public void setEMeterName(String EMeterName) {
        this.EMeterName = EMeterName;
    }

    public Integer getEMeterID() {
        return EMeterID;
    }

    public void setEMeterID(Integer EMeterID) {
        this.EMeterID = EMeterID;
    }

    public String getTimeTag() {
        return timeTag;
    }

    public void setTimeTag(String timeTag) {
        this.timeTag = timeTag;
    }

    public Double getPowerTotal() {
        return powerTotal;
    }

    public void setPowerTotal(Double powerTotal) {
        this.powerTotal = powerTotal;
    }

    public Double getPeak() {
        return peak;
    }

    public void setPeak(Double peak) {
        this.peak = peak;
    }

    public Double getFlat() {
        return flat;
    }

    public void setFlat(Double flat) {
        this.flat = flat;
    }

    public Double getRavine() {
        return ravine;
    }

    public void setRavine(Double ravine) {
        this.ravine = ravine;
    }

    public Double getPeakPercent() {
        return peakPercent;
    }

    public void setPeakPercent(Double peakPercent) {
        this.peakPercent = peakPercent;
    }
}
