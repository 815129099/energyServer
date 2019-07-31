package com.example.demo.util.jna.po;
import com.sun.jna.*;

import java.util.Arrays;
import java.util.List;

public class S_CLOU102_PACK extends Structure{
    public byte typeID;
    public byte address;// 子站地址
    // 最小间隔时间。主站要求的电量最小间隔，单位：分。
    // 例如，本数字为15，则采集器只送0分、15分、30分、45分的数据。
    public byte minTimeInterval;
    public byte compareTheTime;// 0为不对时，1为对时
    public byte[] time = new byte[20];// 时间
    public short framenum;// 帧号
    public NativeLong meternum;// 表号
    public TIME_FRAME timeFrame; //时间范围信息体
    public byte recordnum; //记录

    public static class ByReference extends S_CLOU102_PACK implements Structure.ByReference { }
    public static class ByValue  extends S_CLOU102_PACK  implements Structure.ByValue { }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[]{"typeID", "address", "minTimeInterval", "compareTheTime","time","framenum","meternum","timeFrame","recordnum"});
    }

}
