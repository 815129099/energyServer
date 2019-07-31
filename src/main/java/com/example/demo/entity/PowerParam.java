package com.example.demo.entity;

import java.util.Arrays;
import java.util.List;

public class PowerParam {
    private byte[] tm;
    private int[] eList;
    private int type;
    private List<List> mList;

    public List<List> getmList() {
        return mList;
    }

    public void setmList(List<List> mList) {
        this.mList = mList;
    }

    public byte[] getTm() {
        return tm;
    }

    public void setTm(byte[] tm) {
        this.tm = tm;
    }

    public int[] geteList() {
        return eList;
    }

    public void seteList(int[] eList) {
        this.eList = eList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PowerParam{" +
                "tm=" + Arrays.toString(tm) +
                ", eList=" + Arrays.toString(eList) +
                ", type=" + type +
                ", mList=" + mList +
                '}';
    }
}
