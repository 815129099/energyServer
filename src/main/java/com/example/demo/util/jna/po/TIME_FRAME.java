package com.example.demo.util.jna.po;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class TIME_FRAME extends Structure {
    public byte tm_year;
    public byte tm_mon;
    public byte tm_mday;
    public byte tm_hour_start;
    public byte tm_min_start;
    public byte tm_sec_start;
    public byte tm_hour_end;
    public byte tm_min_end;
    public byte tm_sec_end;

    public static class ByReference extends TIME_FRAME implements Structure.ByReference { }
    public static class ByValue  extends TIME_FRAME  implements Structure.ByValue { }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[]{"tm_year","tm_mon","tm_mday","tm_hour_start","tm_min_start","tm_sec_start","tm_hour_end","tm_min_end","tm_sec_end"});
    }
}
