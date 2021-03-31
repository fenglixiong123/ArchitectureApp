package com.flx.nio.channel;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author Fenglixiong
 * @Create 2021/3/31 2:14
 * @Description
 **/
public class FileChannelTransfer {

    public static void main(String[] args) throws IOException {

        long time = copyFile("E:\\test1\\aaaa.zip", "E:\\test1\\bbb.zip");
        System.out.println("channel copy time = "+time);//35231--->16260

    }

    /**
     * 通道直接复制文件（直接缓冲区）
     * @param source 源文件
     * @param dest 目标文件
     * @return 执行时间
     * @throws IOException
     */
    private static long copyFile(String source,String dest) throws IOException{
        long start = System.currentTimeMillis();
        //打开读模式的通道
        FileChannel fisChannel = FileChannel.open(Paths.get(source), StandardOpenOption.READ);
        //打开写模式的通道，如果文件不存在就创建
        FileChannel fosChannel = FileChannel.open(Paths.get(dest), StandardOpenOption.READ,StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        fisChannel.transferTo(0,fisChannel.size(),fosChannel);
        fisChannel.close();
        fosChannel.close();
        return System.currentTimeMillis() - start;
    }

}
