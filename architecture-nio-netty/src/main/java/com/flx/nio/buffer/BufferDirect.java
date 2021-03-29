package com.flx.nio.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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

    public static void main(String[] args) throws IOException {
        ByteBuffer bf = ByteBuffer.allocateDirect(1024);
        System.out.println("isDirect = "+bf.isDirect());
//        copyFile("E:\\test1\\apple.jpg","E:\\test2\\mmm.jpg");
        long time = copyFile("E:\\test1\\aaaa.zip", "E:\\test1\\bbb.zip");
        System.out.println("direct copy time = "+time);//3170--->3050
    }

    /**
     * 通过直接缓冲区来进行文件copy，比通道复制快很多
     * @param source 源文件
     * @param dest  目标文件
     * @throws IOException IO异常
     */
    private static long copyFile(String source,String dest) throws IOException {
        long start = System.currentTimeMillis();
        //打开读模式的通道
        FileChannel fisChannel = FileChannel.open(Paths.get(source), StandardOpenOption.READ);
        //打开写模式的通道，如果文件不存在就创建
        FileChannel fosChannel = FileChannel.open(Paths.get(dest), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        //内存映射文件
        MappedByteBuffer fisMapBuffer = fisChannel.map(FileChannel.MapMode.READ_ONLY, 0, fisChannel.size());
        MappedByteBuffer fosMapBuffer = fosChannel.map(FileChannel.MapMode.READ_WRITE, 0, fisChannel.size());
        //直接对缓冲区中数据进行读写
        byte[] dst = new byte[fisMapBuffer.limit()];
        fisMapBuffer.get(dst);
        fosMapBuffer.put(dst);
        fisChannel.close();
        fosChannel.close();
        return System.currentTimeMillis() - start;
    }

}
