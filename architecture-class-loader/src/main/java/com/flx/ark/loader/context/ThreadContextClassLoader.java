package com.flx.ark.loader.context;

import com.flx.ark.loader.custom.MyClassLoader;

/**
 * @Author: Fenglixiong
 * @Date: 2020/11/18 14:14
 * @Description:
 */
public class ThreadContextClassLoader {

    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);

        //人为改变classLoader
        Thread.currentThread().setContextClassLoader(new MyClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());

    }

}
