package com.flx.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @Author Fenglixiong
 * @Create 2021/3/23 1:48
 * @Description
 * 一、缓冲区：在Nio中负责数据的存取（白话：缓冲区就是数组，用于存储不同数据类型的数据）
 * 根据数据类型的不同，提供了相应类型的缓存区
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 上述缓冲区的管理方法几乎一致，都是通过allocate()获取缓冲区
 *
 * 二、缓冲区存取数据的两个核心方法
 * put():存入数据到缓冲区中
 * get():获取缓冲区中的数据
 *
 * 三、缓冲区的四个核心属性
 * capacity:容量，表示缓冲区最大存储数据的容量。一旦声明不能改变
 * limit:界限，表示缓冲区中可以操作数据的大小(limit后面的数据不能进行读写)
 * position:位置，表示缓冲区中正在操作数据的位置
 * mark:
 * mark<=position<=limit<=capacity
 **/
public class BufferApi {

    public static void main(String[] args) {

        //分配一个指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("capacity = "+byteBuffer.capacity());
        System.out.println("limit = "+byteBuffer.limit());
        System.out.println("position = "+byteBuffer.position());
    }

}
