package com.flx.ark.loader;

import com.flx.ark.loader.custom.MyClassLoader;

import java.lang.reflect.InvocationTargetException;


/**
 * @Author: Fenglixiong
 * @Date: 2020/10/26 16:58
 * @Description:
 */
public class TestApplication {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        testParentLoaderCount();

    }

    /**
     * 测试自定义类加载器去加载类并且调用方法
     */
    public static void testClassLoader()throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader myClassLoader = new MyClassLoader("myClassLoader",Thread.currentThread().getContextClassLoader().getParent());
        Class<?> aClass = myClassLoader.loadClass("com.flx.ark.loader.custom.MyObject");
        System.out.println(aClass);
        System.out.println(aClass.getClassLoader());
        System.out.println(aClass.getClassLoader().getParent());
        //创建对象，加载静态代码块
        Object o = aClass.newInstance();
        //通过反射调用方法
        Object result = aClass.getMethod("hello", new Class<?>[]{}).invoke(o, new Object[]{});
        System.out.println(result);
    }

    /**
     * 父类委托机制
     */
    public static void testParentLoader()throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader myClassLoader1 = new MyClassLoader("myClassLoader",Thread.currentThread().getContextClassLoader().getParent());
        //如果改变路径是找不到类的，但是通过父类委托机制会指派父类去加载类
        //MyClassLoader myClassLoader2 = new MyClassLoader("myClassLoader",Thread.currentThread().getContextClassLoader().getParent());
        MyClassLoader myClassLoader2 = new MyClassLoader("myClassLoader",myClassLoader1);
        myClassLoader2.setDir("...");
        Class<?> aClass = myClassLoader2.loadClass("com.flx.ark.loader.custom.MyObject");
        System.out.println(aClass);
    }

    /**
     * 父类委托机制
     * 测试两个加载器加载的类在方法区有多少份
     * 结论：
     * 1.如果两个类加载器最终由同一个类加载器加载类则只保留一份
     * 2.如果由不同类加载器加载则会有多份
     */
    public static void testParentLoaderCount()throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader myClassLoader1 = new MyClassLoader("myClassLoader1",Thread.currentThread().getContextClassLoader().getParent());
        MyClassLoader myClassLoader2 = new MyClassLoader("myClassLoader2",Thread.currentThread().getContextClassLoader().getParent());
        Class<?> aClass1 = myClassLoader1.loadClass("com.flx.ark.loader.custom.MyObject");
        Class<?> aClass2 = myClassLoader2.loadClass("com.flx.ark.loader.custom.MyObject");
        System.out.println(aClass1.hashCode());
        System.out.println(aClass2.hashCode());
    }

}
