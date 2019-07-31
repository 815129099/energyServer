package com.example.demo.entity;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */
public class OrigDL extends Model<OrigDL> {

    private static final long serialVersionUID = 1L;

    /**
     * 采集器编号[关联到：（采集配置）电采集器]
     */
    private Integer ErtuID;

    /**
     * 开关号[关联到：（电网结构）计量表计]
     */
    private String EMeterNum;

    /**
     * 电表ID[关联到：（电网结构）计量表计]
     */
    private Integer EMeterID;

    /**
     * 采集器采集时间
     */
    private Date TimeTag;

    /**
     * 正向有功 总
     */
    private Double ZxygZ;

    /**
     * 反向有功 总
     */
    private Double FxygZ;

    /**
     * 正向无功 总
     */
    private Double ZxwgZ;

    /**
     * 反向无功 总
     */
    private Double FxwgZ;

    /**
     * 0-采集，1-手工录入，2-批量导入，3-录入的表底度
     */
    private Integer Source;

    /**
     * 0-无效数据，1-有效数据（手工修改时，保留原来的记录，标记为无效）
     */
    private Integer Status;

    /**
     * 创建时间
     */
    private Date CreatedDateTime;

    /**
     * 修改时间
     */
    private Date UpdatedDateTime;

    public Integer getErtuID() {
        return ErtuID;
    }

    public void setErtuID(Integer ErtuID) {
        this.ErtuID = ErtuID;
    }
    public String getEMeterNum() {
        return EMeterNum;
    }

    public void setEMeterNum(String EMeterNum) {
        this.EMeterNum = EMeterNum;
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
    public Double getZxygZ() {
        return ZxygZ;
    }

    public void setZxygZ(Double ZxygZ) {
        this.ZxygZ = ZxygZ;
    }
    public Double getFxygZ() {
        return FxygZ;
    }

    public void setFxygZ(Double FxygZ) {
        this.FxygZ = FxygZ;
    }
    public Double getZxwgZ() {
        return ZxwgZ;
    }

    public void setZxwgZ(Double ZxwgZ) {
        this.ZxwgZ = ZxwgZ;
    }
    public Double getFxwgZ() {
        return FxwgZ;
    }

    public void setFxwgZ(Double FxwgZ) {
        this.FxwgZ = FxwgZ;
    }
    public Integer getSource() {
        return Source;
    }

    public void setSource(Integer Source) {
        this.Source = Source;
    }
    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }
    public Date getCreatedDateTime() {
        return CreatedDateTime;
    }

    public void setCreatedDateTime(Date CreatedDateTime) {
        this.CreatedDateTime = CreatedDateTime;
    }
    public Date getUpdatedDateTime() {
        return UpdatedDateTime;
    }

    public void setUpdatedDateTime(Date UpdatedDateTime) {
        this.UpdatedDateTime = UpdatedDateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.ErtuID;
    }

    @Override
    public String toString() {
        return "OrigDL{" +
        "ErtuID=" + ErtuID +
        ", EMeterNum=" + EMeterNum +
        ", EMeterID=" + EMeterID +
        ", TimeTag=" + TimeTag +
        ", ZxygZ=" + ZxygZ +
        ", FxygZ=" + FxygZ +
        ", ZxwgZ=" + ZxwgZ +
        ", FxwgZ=" + FxwgZ +
        ", Source=" + Source +
        ", Status=" + Status +
        ", CreatedDateTime=" + CreatedDateTime +
        ", UpdatedDateTime=" + UpdatedDateTime +
        "}";
    }
}
