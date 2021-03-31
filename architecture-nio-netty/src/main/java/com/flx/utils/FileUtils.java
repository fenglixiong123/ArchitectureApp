package com.flx.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Author Fenglixiong
 * @Create 2021/3/31 2:12
 * @Description
 **/
public class FileUtils {

    public static void close(FileInputStream fis, FileOutputStream fos, FileChannel fisChannel, FileChannel fosChannel){
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

    public static void close(RandomAccessFile file, FileChannel fileChannel){
        try {
            if(file!=null) {
                file.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(fileChannel!=null){
                fileChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
