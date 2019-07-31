package com.example.demo.util.jna.test;

import com.example.demo.util.jna.po.S_CLOU102_PACK;
import com.example.demo.util.jna.po.S_CLOU102_UNPACK;
import com.sun.jna.Library;
import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public interface clou102 extends Library {
    clou102 INSTANCE = (clou102) Native.loadLibrary("clou123", clou102.class);
    public int pack_clou102(S_CLOU102_PACK.ByReference pack, PointerByReference send, IntByReference len);
    public int unpack_clou102(byte[] recvbuf, int len, S_CLOU102_UNPACK.ByReference unpack);
}
