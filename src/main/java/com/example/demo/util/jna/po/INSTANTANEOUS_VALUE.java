package com.example.demo.util.jna.po;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class INSTANTANEOUS_VALUE extends Structure {
    public double Ua; // 电压
    public double Ub;
    public double Uc;
    public double Ia; // 电流
    public double Ib;
    public double Ic;
    public double P; // 有功功率
    public double Pa;
    public double Pb;
    public double Pc;
    public double Q; // 无功功率
    public double Qa;
    public double Qb;
    public double Qc;
    public double PF; // 功率因数
    public double PFa;
    public double PFb;
    public double PFc;
    public double Hz; // 频率

    public static class ByReference extends INSTANTANEOUS_VALUE implements Structure.ByReference { }
    public static class ByValue  extends INSTANTANEOUS_VALUE  implements Structure.ByValue { }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[]{"Ua", "Ub", "Uc", "Ia","Ib","Ic","P","Pa","Pb",
                "Pc","Q","Qa","Qb","Qc","PF","PFa","PFb","PFc","Hz"});
    }

    @Override
    public String toString() {
        return "INSTANTANEOUS_VALUE{" +
                "Ua=" + Ua +
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
                ", PF=" + PF +
                ", PFa=" + PFa +
                ", PFb=" + PFb +
                ", PFc=" + PFc +
                ", Hz=" + Hz +
                '}';
    }
}
