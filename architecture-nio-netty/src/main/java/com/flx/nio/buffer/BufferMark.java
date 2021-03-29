package com.flx.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

/**
 * @Author Fenglixiong
 * @Create 2021/3/24 23:18
 * @Description
 **/
public class BufferMark {

    public static void main(String[] args) {

        String str = "fenglixiong";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        byte[] dst = new byte[byteBuffer.limit()];
        byteBuffer.get(dst,0,2);
        System.out.println(new String(dst,0,2));

        byteBuffer.mark();

        byteBuffer.get(dst,2,2);
        System.out.println(new String(dst,2,2));

        byteBuffer.reset();
        byteBuffer.get(dst,4,2);
        System.out.println(new String(dst,4,2));

    }

}
