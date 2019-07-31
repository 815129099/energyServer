package com.example.demo.util.jna.po;

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class S_CLOU102_UNPACK extends Structure {
    public byte address;// 子站地址（报文第6字节）
    //unsigned char* time;// 时间
    //public String time ;// 时间
    public byte[] time = new byte[20];
    public byte infoObjNum;// 信息体数量
    public byte powerTypeNum;// 功率类型数量
    public byte powerTariffNum;// 费率数量
    public NativeLong meternum;// 表号
    public byte infoType;// 信息体类型
    public short framenum;// 帧号
    /*char* power1[64][30];// 返回总电量数据，长度最长的应为时间，电量数字长度小于时间*/
    public POWER power;// 返回总电量数据
    public INSTANTANEOUS_VALUE instantaneous_value;
//《数据结构（C语言描述）》（修订版）王晓东编著 电子工业出版社 2011年
//《C++程序设计教程（第二版）》，钱能编，清华大学出版社 2005年

    public static class ByReference extends S_CLOU102_UNPACK implements Structure.ByReference { }
    public static class ByValue  extends S_CLOU102_UNPACK  implements Structure.ByValue { }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[]{"address", "time", "infoObjNum", "powerTypeNum","powerTariffNum",
                "meternum","infoType","framenum","power","instantaneous_value"});
    }

    @Override
    public String toString() {
        String str = "";


        try {
            str= "S_CLOU102_UNPACK{" +
                    "address=" + address +
                    ", time='" + new String(time,"GBK") + '\'' +
                    ", infoObjNum=" + infoObjNum +
                    ", powerTypeNum=" + powerTypeNum +
                    ", powerTariffNum=" + powerTariffNum +
                    ", meternum=" + meternum +
                    ", infoType='" + infoType + '\'' +
                    ", framenum=" + framenum +
                    ", power=" + power +
                    ", instantaneous_value=" + instantaneous_value +
                    '}';
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return str;
    }
}
