package com.flx.ark.loader.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2020/10/28 15:34
 * @Description:
 */
public final class EncryptUtils {

    public static final byte ENCRYPT_FACTOR = (byte) 0xff;

    private EncryptUtils(){

    }

    public static void doEncrypt(String source,String target){
        try(FileInputStream fis = new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(target
            )){
            int data;
            while ((data=fis.read())!=-1){
                fos.write(data^ENCRYPT_FACTOR);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        doEncrypt("D:\\home\\com\\flx\\ark\\loader\\custom\\CustomLoader.class","D:\\home\\com\\flx\\ark\\loader\\custom\\CustomLoader1.class");
    }

}
