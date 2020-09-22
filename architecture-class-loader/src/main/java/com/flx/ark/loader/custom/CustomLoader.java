package com.flx.ark.loader.custom;

import java.io.*;

/**
 * @Author: Fenglixiong
 * @Date: 2020/9/22 16:38
 * @Description:
 */
public class CustomLoader extends ClassLoader {

    private String libPath;

    public CustomLoader(String path){
        this.libPath = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(libPath,fileName);
        try{
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            try{
                while ((len=fis.read())!=-1){
                    bos.write(len);
                }
            }catch (IOException w){
                w.printStackTrace();
            }
            byte[] data = bos.toByteArray();
            fis.close();
            bos.close();
            return defineClass(name,data,0,data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if(index == -1){
            return name+".class";
        }
        else{
            return name.substring(index+1)+".class";
        }
    }
}
