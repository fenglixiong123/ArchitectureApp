package com.flx.ark.loader.custom;

/**
 * @Author: Fenglixiong
 * @Date: 2020/10/26 17:00
 * @Description: 打破双亲委派的类加载机制
 */
public class ExClassLoader extends MyClassLoader {

    /**
     * 重写loadClass,打破双亲委派机制
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = null;

        if(name.startsWith("java.")){
            try {
                ClassLoader sys = ClassLoader.getSystemClassLoader();
                clazz = sys.loadClass(name);
                if(clazz!=null){
                    if(resolve){
                        resolveClass(clazz);
                    }
                    return clazz;
                }
            }catch (Exception e){

            }
        }

        try{
            clazz = findClass(name);
        }catch (Exception e){

        }
        if(clazz==null&&getParent()!=null){
            getParent().loadClass(name);
        }

        return clazz;
    }

}
