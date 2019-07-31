package com.example.demo.util.jna.util;

import java.util.HashMap;
import java.util.Map;

public class InsertUtil {

    public static void insertD(byte[] bytes){

        //System.out.println(HexUtil.bytesToHexFun1(bytes));
        byte[] bytes1 = new byte[6];
        byte[] bytes2 = new byte[1];
        System.arraycopy(bytes,9,bytes1,0,6);
        System.arraycopy(bytes,6,bytes2,0,1);
        //System.out.println("电表号"+Arrays.toString(bytes2));
        String hexStr = HexUtil.bytesToHexFun1(bytes1);
        //String MeterNum = HexUtil.bytesToHexFun1(bytes2);
        System.out.println("电表号："+bytes2[0]);
        long num = Long.parseLong(hexStr,16);
        double power = num/1000.00;
        System.out.println(power);
        Map map = new HashMap();
        map.put("TimeTag",HexUtil.getDate());
        map.put("ZxygZ",power);
        map.put("MeterNum",bytes2[0]);
        SqlServerUtil.insertDao(map);
    }

}
