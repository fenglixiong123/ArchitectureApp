package com.flx.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/29 15:42
 * @Description: 文件使用channel读取case
 */
public class FileChannelCase {

    public static void main(String[] args) throws IOException {
        long time = copyFile("E:\\test1\\aaaa.zip", "E:\\test1\\bbb.zip");
        System.out.println("channel copy time = "+time);//35231--->16260
//        copyFiles("E:\\test1\\apple.jpg","E:\\test2\\hhhh.jpg");
    }

    /**
     * 通道直接复制文件（直接缓冲区）
     * @param source 源文件
     * @param dest 目标文件
     * @return 执行时间
     * @throws IOException
     */
    private static long simpleCopyFile(String source,String dest) throws IOException{
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

    private static void close(FileInputStream fis,FileOutputStream fos,FileChannel fisChannel,FileChannel fosChannel){
        try {
            if(fis!=null) {
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(fos!=null) {
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(fisChannel!=null) {
                fisChannel.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(fosChannel!=null) {
                fosChannel.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
