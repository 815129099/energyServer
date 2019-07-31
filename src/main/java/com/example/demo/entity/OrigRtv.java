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
public class OrigRtv extends Model<OrigRtv> {

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
     * A相电压
     */
    private Double Ua;

    /**
     * B相电压
     */
    private Double Ub;

    /**
     * C相电压
     */
    private Double Uc;

    /**
     * A相电流
     */
    private Double Ia;

    /**
     * B相电流
     */
    private Double Ib;

    /**
     * C相电流
     */
    private Double Ic;

    /**
     * 有功功率
     */
    private Double P;

    /**
     * A相有功功率
     */
    private Double Pa;

    /**
     * B相有功功率
     */
    private Double Pb;

    /**
     * C相有功功率
     */
    private Double Pc;

    /**
     * 无功功率
     */
    private Double Q;

    /**
     * A相无功功率
     */
    private Double Qa;

    /**
     * B相无功功率
     */
    private Double Qb;

    /**
     * C相无功功率
     */
    private Double Qc;

    /**
     * 功率因数
     */
    private Double Cos;

    /**
     * A相功率因数
     */
    private Double CosA;

    /**
     * B相功率因数
     */
    private Double CosB;

    /**
     * C相功率因数
     */
    private Double CosC;

    /**
     * 频率
     */
    private Double Hz;

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
    public Double getUa() {
        return Ua;
    }

    public void setUa(Double Ua) {
        this.Ua = Ua;
    }
    public Double getUb() {
        return Ub;
    }

    public void setUb(Double Ub) {
        this.Ub = Ub;
    }
    public Double getUc() {
        return Uc;
    }

    public void setUc(Double Uc) {
        this.Uc = Uc;
    }
    public Double getIa() {
        return Ia;
    }

    public void setIa(Double Ia) {
        this.Ia = Ia;
    }
    public Double getIb() {
        return Ib;
    }

    public void setIb(Double Ib) {
        this.Ib = Ib;
    }
    public Double getIc() {
        return Ic;
    }

    public void setIc(Double Ic) {
        this.Ic = Ic;
    }
    public Double getP() {
        return P;
    }

    public void setP(Double P) {
        this.P = P;
    }
    public Double getPa() {
        return Pa;
    }

    public void setPa(Double Pa) {
        this.Pa = Pa;
    }
    public Double getPb() {
        return Pb;
    }

    public void setPb(Double Pb) {
        this.Pb = Pb;
    }
    public Double getPc() {
        return Pc;
    }

    public void setPc(Double Pc) {
        this.Pc = Pc;
    }
    public Double getQ() {
        return Q;
    }

    public void setQ(Double Q) {
        this.Q = Q;
    }
    public Double getQa() {
        return Qa;
    }

    public void setQa(Double Qa) {
        this.Qa = Qa;
    }
    public Double getQb() {
        return Qb;
    }

    public void setQb(Double Qb) {
        this.Qb = Qb;
    }
    public Double getQc() {
        return Qc;
    }

    public void setQc(Double Qc) {
        this.Qc = Qc;
    }
    public Double getCos() {
        return Cos;
    }

    public void setCos(Double Cos) {
        this.Cos = Cos;
    }
    public Double getCosA() {
        return CosA;
    }

    public void setCosA(Double CosA) {
        this.CosA = CosA;
    }
    public Double getCosB() {
        return CosB;
    }

    public void setCosB(Double CosB) {
        this.CosB = CosB;
    }
    public Double getCosC() {
        return CosC;
    }

    public void setCosC(Double CosC) {
        this.CosC = CosC;
    }
    public Double getHz() {
        return Hz;
    }

    public void setHz(Double Hz) {
        this.Hz = Hz;
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
        return "OrigRtv{" +
        "ErtuID=" + ErtuID +
        ", EMeterNum=" + EMeterNum +
        ", EMeterID=" + EMeterID +
        ", TimeTag=" + TimeTag +
        ", Ua=" + Ua +
        ", Ub=" + Ub +
        ", Uc=" + Uc +
        ", Ia=" + Ia +
        ", Ib=" + Ib +
        ", Ic=" + Ic +
        ", P=" + P +
        ", Pa=" + Pa +
        ", Pb=" + Pb +
        ", Pc=" + Pc +
        ", Q=" + Q +
        ", Qa=" + Qa +
        ", Qb=" + Qb +
        ", Qc=" + Qc +
        ", Cos=" + Cos +
        ", CosA=" + CosA +
        ", CosB=" + CosB +
        ", CosC=" + CosC +
        ", Hz=" + Hz +
        ", Source=" + Source +
        ", Status=" + Status +
        ", CreatedDateTime=" + CreatedDateTime +
        ", UpdatedDateTime=" + UpdatedDateTime +
        "}";
    }
}
