package com.flx.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/8 15:51
 * @Description:
 */
public class NettyUnpooledBuffer {

    public static void main(String[] args) {

        String source = "Hello,Jack!";

        //通过字符串创建buffer
        ByteBuf buffer = Unpooled.copiedBuffer(source, CharsetUtil.UTF_8);

        //直接读取数组
        if(buffer.hasArray()){
            byte[] array = buffer.array();
            System.out.println("array = "+new String(array,0,buffer.readableBytes(),CharsetUtil.UTF_8));
        }

        //数组偏移量
        System.out.println("arrayOffset = "+buffer.arrayOffset());//0
        //读取索引
        System.out.println("readerIndex = "+buffer.readerIndex());//0
        //写入索引
        System.out.println("writerIndex = "+buffer.writerIndex());//11
        //buffer容量
        System.out.println("capacity = "+buffer.capacity());//36
        //可读数据长度
        System.out.println("readableBytes = "+buffer.readableBytes());//11

        //读取部分长度
        System.out.println("shut0~5 = "+buffer.getCharSequence(0,5,CharsetUtil.UTF_8));

        //读取每个字节
        for (int i = 0; i < buffer.readableBytes(); i++) {
            System.out.print((char)buffer.getByte(i));
        }

    }

}
