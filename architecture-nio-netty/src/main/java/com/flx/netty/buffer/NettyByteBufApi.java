package com.flx.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/8 15:33
 * @Description: Netty的Buffer
 *
 * 底层维护了一个readIndex和writeIndex
 *
 * 通过readIndex和writeIndex将buffer分成说那个区域
 * 1)0~readIndex 已经读取的区域
 * 2)readIndex~writeIndex 可读的区域
 * 3)writeIndex~capacity 可写的区域
 */
public class NettyByteBufApi {

    public static void main(String[] args) {

        //创建对象，该对象包含一个数组array，是一个byte[10]
        ByteBuf buffer = Unpooled.buffer(10);

        //写入缓冲区内容writeIndex不断增加
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        //输出缓冲区内容readIndex不改变
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }

        //输出缓冲区内容readIndex不断增加
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }

    }

}
