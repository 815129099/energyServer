package com.example.demo.util.jna.po;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class POWER extends Structure {
    public double positive_active_power; // 正向有功
    public double reverse_active_power; // 反向有功
    public double positive_reative_power; // 正向无功
    public double reverse_reactive_power; // 反向无

    public static class ByReference extends POWER implements Structure.ByReference { }
    public static class ByValue  extends POWER  implements Structure.ByValue { }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[]{"positive_active_power", "reverse_active_power",
                "positive_reative_power", "reverse_reactive_power"});
    }

    @Override
    public String toString() {
        return "POWER{" +
                "positive_active_power=" + positive_active_power +
                ", reverse_active_power=" + reverse_active_power +
                ", positive_reative_power=" + positive_reative_power +
                ", reverse_reactive_power=" + reverse_reactive_power +
                '}';
    }
}
