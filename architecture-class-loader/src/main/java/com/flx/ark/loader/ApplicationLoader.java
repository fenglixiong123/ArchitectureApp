package com.flx.ark.loader;

/**
 * @Author: Fenglixiong
 * @Date: 2020/9/22 15:58
 * @Description:
 * BootstrapClassLoader====>加载%JRE_HOME%/lib/rt.jar、resources.jar、charset.jar等
 * ExtensionClassLoader====>加载%JRE_HOME%/lib/ext目录下的jar包
 * ApplicationClassLoader====>加载当前应用的class
 *
 */
public class ApplicationLoader {

    public static void main(String[] args) throws ClassNotFoundException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);//ApplicationClassLoader====>加载当前应用的class
        System.out.println(loader.getParent());//ExtensionClassLoader====>加载%JRE_HOME%/lib/ext目录下的jar包
        System.out.println(loader.getParent().getParent());//BootstrapClassLoader====>加载%JRE_HOME%/lib/rt.jar、resources.jar、charset.jar等
        ClassLoader appLoader = ApplicationLoader.class.getClassLoader();//获取ApplicationClassLoader
        System.out.println(appLoader);
        //使用ClassLoader.loadClass()来加载类，不会执行初始化块
        appLoader.loadClass("com.flx.ark.loader.entity.Student");
        //使用Class.forName()来加载类，指定ClassLoader，初始化时不执行静态块
        Class<?> studentClass1 = Class.forName("com.flx.ark.loader.entity.Student",false,appLoader);
        //使用Class.forName()来加载类，默认会执行初始化块 Class.forName("Fdd")
        Class<?> studentClass2 = Class.forName("com.flx.ark.loader.entity.Student");
        System.out.println(studentClass1);
        System.out.println(studentClass2);
        System.out.println(studentClass1==studentClass2);
    }


}
