package com.example.demo.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lwx
 * @since 2019-05-28
 */
public class Charge extends Model<Charge> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 峰时刻
     */
    private String peakTime;

    /**
     * 峰时刻集美电费
     */
    private Double jPeakCharge;

    /**
     * 峰时刻同安电费
     */
    private Double tPeakCharge;

    /**
     * 峰时刻湖里电费
     */
    private Double hPeakCharge;

    /**
     * 平时刻
     */
    private String flatTime;

    /**
     * 平时刻集美电费
     */
    private Double jFlatCharge;

    /**
     * 平时刻同安电费
     */
    private Double tFlatCharge;

    /**
     * 平时刻湖里电费
     */
    private Double hFlatCharge;

    /**
     * 谷时刻
     */
    private String ravineTime;

    /**
     * 谷时刻集美电费
     */
    private Double jRavineCharge;

    /**
     * 谷时刻同安电费
     */
    private Double tRavineCharge;

    /**
     * 谷时刻湖里电费
     */
    private Double hRavineCharge;

    /**
     * 0-无效数据，1-有效数据
     */
    private Integer Status;

    /**
     * 创建时间
     */
    private String CreatedDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getPeakTime() {
        return peakTime;
    }

    public void setPeakTime(String peakTime) {
        this.peakTime = peakTime;
    }
    public Double getjPeakCharge() {
        return jPeakCharge;
    }

    public void setjPeakCharge(Double jPeakCharge) {
        this.jPeakCharge = jPeakCharge;
    }
    public Double gettPeakCharge() {
        return tPeakCharge;
    }

    public void settPeakCharge(Double tPeakCharge) {
        this.tPeakCharge = tPeakCharge;
    }
    public Double gethPeakCharge() {
        return hPeakCharge;
    }

    public void sethPeakCharge(Double hPeakCharge) {
        this.hPeakCharge = hPeakCharge;
    }
    public String getFlatTime() {
        return flatTime;
    }

    public void setFlatTime(String flatTime) {
        this.flatTime = flatTime;
    }
    public Double getjFlatCharge() {
        return jFlatCharge;
    }

    public void setjFlatCharge(Double jFlatCharge) {
        this.jFlatCharge = jFlatCharge;
    }
    public Double gettFlatCharge() {
        return tFlatCharge;
    }

    public void settFlatCharge(Double tFlatCharge) {
        this.tFlatCharge = tFlatCharge;
    }
    public Double gethFlatCharge() {
        return hFlatCharge;
    }

    public void sethFlatCharge(Double hFlatCharge) {
        this.hFlatCharge = hFlatCharge;
    }
    public String getRavineTime() {
        return ravineTime;
    }

    public void setRavineTime(String ravineTime) {
        this.ravineTime = ravineTime;
    }
    public Double getjRavineCharge() {
        return jRavineCharge;
    }

    public void setjRavineCharge(Double jRavineCharge) {
        this.jRavineCharge = jRavineCharge;
    }
    public Double gettRavineCharge() {
        return tRavineCharge;
    }

    public void settRavineCharge(Double tRavineCharge) {
        this.tRavineCharge = tRavineCharge;
    }
    public Double gethRavineCharge() {
        return hRavineCharge;
    }

    public void sethRavineCharge(Double hRavineCharge) {
        this.hRavineCharge = hRavineCharge;
    }
    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }
    public String getCreatedDateTime() {
        return CreatedDateTime;
    }

    public void setCreatedDateTime(String CreatedDateTime) {
        this.CreatedDateTime = CreatedDateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Charge{" +
        "id=" + id +
        ", peakTime=" + peakTime +
        ", jPeakCharge=" + jPeakCharge +
        ", tPeakCharge=" + tPeakCharge +
        ", hPeakCharge=" + hPeakCharge +
        ", flatTime=" + flatTime +
        ", jFlatCharge=" + jFlatCharge +
        ", tFlatCharge=" + tFlatCharge +
        ", hFlatCharge=" + hFlatCharge +
        ", ravineTime=" + ravineTime +
        ", jRavineCharge=" + jRavineCharge +
        ", tRavineCharge=" + tRavineCharge +
        ", hRavineCharge=" + hRavineCharge +
        ", Status=" + Status +
        ", CreatedDateTime=" + CreatedDateTime +
        "}";
    }
}
