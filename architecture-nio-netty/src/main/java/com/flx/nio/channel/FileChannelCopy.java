package com.flx.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.flx.utils.FileUtils.close;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/29 15:42
 * @Description: 文件使用channel读取case
 */
public class FileChannelCopy {

    public static void main(String[] args) throws IOException {

        copyFile("E:\\test1\\apple.jpg","E:\\test2\\hhhh.jpg");

    }



    /**
     * 通道复制文件方法，原始代码版本（非直接缓冲区）
     * @param source 源文件
     * @param dest 目标文件
     * @return 花费时间
     * @throws IOException 异常
     */
    private static long copyFile(String source,String dest) throws IOException{
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fisChannel = null;
        FileChannel fosChannel = null;
        try {
            long start = System.currentTimeMillis();
            fis = new FileInputStream(source);
            fos = new FileOutputStream(dest);
            //获取通道
            fisChannel = fis.getChannel();
            fosChannel = fos.getChannel();
            //分配缓冲区
            ByteBuffer bf = ByteBuffer.allocate(1024);
            //将通道中的数据存入缓冲区
            while (fisChannel.read(bf)!=-1){
                //切换读数据模式
                bf.flip();
                //将缓冲区中的数据写入通道
                fosChannel.write(bf);
                //清空缓冲区
                bf.clear();
            }
            return System.currentTimeMillis() - start;
        } finally {
            close(fis,fos,fisChannel,fosChannel);
        }
    }



}
