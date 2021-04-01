package com.flx.nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

import static com.flx.utils.FileUtils.close;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/1 20:16
 * @Description: 管道
 * 管道是两个线程之间的单向数据连接
 * pipe有一个source通道和一个sink通道
 * 数据会被写到sink通道，从source通道读取
 */
public class PipeApi {

    public static void main(String[] args) {

        Pipe.SinkChannel sinkChannel = null;
        Pipe.SourceChannel sourceChannel = null;

        try {
            //1.获取管道
            Pipe pipe = Pipe.open();
            //2.将缓冲区数据写入管道
            ByteBuffer bf = ByteBuffer.allocate(1024);
            sinkChannel = pipe.sink();
            bf.put("Hello,World!".getBytes());
            bf.flip();
            sinkChannel.write(bf);
            bf.clear();
            //3.读取缓冲区数据
            sourceChannel = pipe.source();
            while (sourceChannel.read(bf)>0){
                bf.flip();
                System.out.println(new String(bf.array(),0,bf.limit()));
                bf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(sinkChannel);
            close(sourceChannel);
        }

    }

}
