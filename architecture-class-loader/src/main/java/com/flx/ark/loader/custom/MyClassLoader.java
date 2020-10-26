package com.flx.ark.loader.custom;

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: Fenglixiong
 * @Date: 2020/10/26 17:00
 * @Description: 自定义类加载器
 */
public class MyClassLoader extends ClassLoader {

    private final static String DEFAULT_DIR = "D:\\home";

    @Getter
    @Setter
    private String dir = DEFAULT_DIR;

    private String classLoaderName;

    public MyClassLoader() {

    }

    public MyClassLoader(String classLoaderName) {
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader(String classLoaderName,ClassLoader parent){
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    /**
     * com.flx.ark.Student
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = name.replace(".","/");
        File classFile = new File(dir,classPath+".class");
        if(!classFile.exists()){
            throw new ClassNotFoundException("The class "+name+" not found ! ");
        }
        byte[] classBytes = loadCLassBytes(classFile);
        if(null==classBytes||classBytes.length==0){
            throw new ClassNotFoundException("Load class "+name+" failed ! ");
        }
        //将class字节数组转换成class类实例
        return this.defineClass(name,classBytes,0,classBytes.length);
    }

    private byte[] loadCLassBytes(File classFile) {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(classFile)){
            byte[] buffer = new byte[2048];
            int len;
            while ((len=fis.read(buffer))!=-1){
                baos.write(buffer,0,len);
            }
            baos.flush();
            return baos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
