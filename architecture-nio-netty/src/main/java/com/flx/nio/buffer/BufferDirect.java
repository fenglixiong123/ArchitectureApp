package com.flx.nio.buffer;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/25 12:22
 * @Description: 直接缓冲区和非直接缓冲区
 *
 * 非直接缓冲区：通过allocate()方法分配的缓冲区，将缓冲区建立在jvm内存中
 * 直接缓冲区：通过allocateDirect()方法分配的缓冲区，将缓冲区建立在物理内容中，可以提高效率
 *
 */
public class BufferDirect {
}
