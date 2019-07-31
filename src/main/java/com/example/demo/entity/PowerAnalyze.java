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
 * @since 2019-05-30
 */
public class PowerAnalyze extends Model<PowerAnalyze> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 电表ID
     */
    private Integer EMeterID;

    /**
     * 统计时间
     */
    private Date TimeTag;

    /**
     * 每天的总电量
     */
    private Double PowerTotal;

    /**
     * 每天的峰值
     */
    private Double peak;

    /**
     * 每天的平值
     */
    private Double flat;

    /**
     * 每天的谷值
     */
    private Double ravine;

    /**
     * 峰值占比
     */
    private Double peakPercent;

    /**
     * 创建时间
     */
    private Date CreatedDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getEMeterID() {
        return EMeterID;
    }

    public void setEMeterID(Integer EMeterID) {
        this.EMeterID = EMeterID;
    }
    public Date getTimeTag() {
        return TimeTag;
    }

    public void setTimeTag(Date TimeTag) {
        this.TimeTag = TimeTag;
    }
    public Double getPowerTotal() {
        return PowerTotal;
    }

    public void setPowerTotal(Double PowerTotal) {
        this.PowerTotal = PowerTotal;
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
    public Date getCreatedDateTime() {
        return CreatedDateTime;
    }

    public void setCreatedDateTime(Date CreatedDateTime) {
        this.CreatedDateTime = CreatedDateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PowerAnalyze{" +
        "id=" + id +
        ", EMeterID=" + EMeterID +
        ", TimeTag=" + TimeTag +
        ", PowerTotal=" + PowerTotal +
        ", peak=" + peak +
        ", flat=" + flat +
        ", ravine=" + ravine +
        ", peakPercent=" + peakPercent +
        ", CreatedDateTime=" + CreatedDateTime +
        "}";
    }
}
