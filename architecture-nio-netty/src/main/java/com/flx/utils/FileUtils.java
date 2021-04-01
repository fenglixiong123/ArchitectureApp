package com.flx.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author Fenglixiong
 * @Create 2021/3/31 2:12
 * @Description
 **/
public class FileUtils {




    public static void close(FileInputStream fis, FileOutputStream fos, FileChannel fisChannel, FileChannel fosChannel){
        close(fis);
        close(fos);
        close(fisChannel);
        close(fosChannel);
    }

    public static void close(RandomAccessFile randomAccessFile, FileChannel fileChannel){
        close(randomAccessFile);
        close(fileChannel);
    }

    public static void close(SocketChannel socketChannel){
        try {
            if(socketChannel!=null) {
                socketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(ServerSocketChannel serverChannel){
        try {
            if(serverChannel!=null) {
                serverChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(FileChannel fileChannel){
        try {
            if(fileChannel!=null) {
                fileChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(RandomAccessFile randomAccessFile){
        try {
            if(randomAccessFile!=null) {
                randomAccessFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(FileInputStream fileInputStream){
        try {
            if(fileInputStream!=null) {
                fileInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(FileOutputStream fileOutputStream){
        try {
            if(fileOutputStream!=null) {
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(Pipe.SinkChannel sinkChannel){
        try {
            if(sinkChannel!=null) {
                sinkChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(Pipe.SourceChannel sourceChannel){
        try {
            if(sourceChannel!=null) {
                sourceChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
