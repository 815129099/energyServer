package com.example.demo.util.jna.util;


import com.example.demo.util.jna.po.S_CLOU102_PACK;
import com.example.demo.util.jna.po.S_CLOU102_UNPACK;
import com.example.demo.util.jna.test.clou102;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DllUtil {
    protected final static Logger logger = LoggerFactory.getLogger(DllUtil.class);

    public static String pack(S_CLOU102_PACK.ByReference pack){
        PointerByReference send = new PointerByReference(Pointer.NULL);
        IntByReference len = new IntByReference();
        int num = clou102.INSTANCE.pack_clou102(pack,send,len);
        String str = "";
        if(num==0){
            str = send.getValue().getString(0);
        }else {
            str = "error";
        }
        logger.info("pack生成："+str+" 长度："+len.getValue()+" 返回值："+num+" 当前线程名："+Thread.currentThread().getName()+" 时间："+System.currentTimeMillis());
        return str;
    }

    public static int unPack(byte[] bytes, int len, S_CLOU102_UNPACK.ByReference unpack){//返回1表示请求链路成功 //返回2表示约参成功 //3表示应答成功
        //LOGGER.info("bytes:"+HexUtil.bytesToHexFun1(bytes));
        //for(int i=0;i<len;i++){
            //System.out.print(HexUtil.bytesToHexFun4(bytes[i])+" ");
        //}
       // System.out.println("len:"+len);
        int num = clou102.INSTANCE.unpack_clou102(bytes,len,unpack);
        logger.info("unPack返回值："+num +" 当前线程名："+Thread.currentThread().getName()+" 时间："+System.currentTimeMillis());
        return num;
    }
}
