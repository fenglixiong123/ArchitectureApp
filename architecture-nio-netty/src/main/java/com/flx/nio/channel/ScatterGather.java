package com.flx.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import static com.flx.utils.FileUtils.close;

/**
 * @Author Fenglixiong
 * @Create 2021/3/31 1:35
 * @Description 分散读取，聚集写入
 * 分散读取：(Scatter reads)将通道中的数据分散到多个缓冲区
 * 聚集写入：(Gather writes)将多个缓冲区中的数据聚集到通道中
 **/
public class ScatterGather {

    public static void main(String[] args) {
        String path1 = "E:\\test1\\a.txt";
        String path2 = "E:\\test1\\b.txt";
        scatterRead(path1);
        gatherWrite(path2);
    }

    /**
     * 分散读取
     * @param path 文件路径
     */
    private static void scatterRead(String path) {
        RandomAccessFile randomFile = null;
        FileChannel fileChannel = null;
        try {
            randomFile = new RandomAccessFile(path,"rw");
            //1.获取通道
            fileChannel = randomFile.getChannel();
            //2.分配缓冲区
            ByteBuffer buffer1 = ByteBuffer.allocate(1024);
            ByteBuffer buffer2 = ByteBuffer.allocate(1024);
            ByteBuffer buffer3 = ByteBuffer.allocate(1024);
            //3.分散读取
            ByteBuffer[] byteBuffers = {buffer1,buffer2,buffer3};
            fileChannel.read(byteBuffers);
            //4.切换模式
            Arrays.asList(byteBuffers).forEach(ByteBuffer::flip);
            //5.查看缓冲区中读取的数据
            for (int i=0;i<byteBuffers.length;i++){
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println(new String(byteBuffers[i].array(),0,byteBuffers[i].limit()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(randomFile,fileChannel);
        }
    }

    /**
     * 聚集写入
     * @param path 文件路径
     */
    private static void gatherWrite(String path) {
        RandomAccessFile randomFile = null;
        FileChannel fileChannel = null;
        try {
            randomFile = new RandomAccessFile(path,"rw");
            //1.获取通道
            fileChannel = randomFile.getChannel();
            //2.分配缓冲区
            ByteBuffer buffer1 = ByteBuffer.allocate(1024);
            ByteBuffer buffer2 = ByteBuffer.allocate(1024);
            ByteBuffer buffer3 = ByteBuffer.allocate(1024);
            buffer1.put("jack\n".getBytes());
            buffer2.put("tom\n".getBytes());
            buffer3.put("marry\n".getBytes());
            //3.切换模式
            ByteBuffer[] bfs = {buffer1,buffer2,buffer3};
            Arrays.asList(bfs).forEach(ByteBuffer::flip);
            //4.聚集写入
            fileChannel.write(bfs);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(randomFile,fileChannel);
        }
    }



}
