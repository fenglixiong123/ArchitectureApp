package com.flx.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/29 15:42
 * @Description:
 */
public class FileChannelCase {

    public static void main(String[] args) {

        String path = "data/nio-data.txt";
        try {
            RandomAccessFile randomFile = new RandomAccessFile(path,"rw");
            FileChannel fileChannel = randomFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = fileChannel.read(byteBuffer);
            while (read!=-1){
                System.out.println("read : "+read);
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()){
                    System.out.println((char)byteBuffer.get());
                }
                byteBuffer.clear();
                read = fileChannel.read(byteBuffer);
            }
            fileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
