package com.flx.ark.loader.custom;

import com.flx.ark.loader.utils.EncryptUtils;

import java.io.*;

/**
 * @Author: Fenglixiong
 * @Date: 2020/11/2 10:55
 * @Description:
 */
public class DecryptClassLoader extends ClassLoader {

    private final static String DEFAULT_DIR = "D:\\home";

    private String dir = DEFAULT_DIR;

    public DecryptClassLoader(){
        super();
    }

    public DecryptClassLoader(ClassLoader parent){
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = name.replace(".","/");
        File classFile = new File(dir,classPath+".class");
        if(!classFile.exists()){
            throw new ClassNotFoundException("class not found !");
        }
        byte[] bytes = loadClassBytes(classFile);
        if(bytes==null||bytes.length==0){
            throw new ClassNotFoundException("class loader error !");
        }
        return super.defineClass(name,bytes,0,bytes.length);
    }

    private byte[] loadClassBytes(File classFile){
        try(FileInputStream fis = new FileInputStream(classFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            int data;
            while ((data = fis.read())!=-1){
                bos.write(data^ EncryptUtils.ENCRYPT_FACTOR);
            }
            bos.flush();
            return bos.toByteArray();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
