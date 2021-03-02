package com.flx.multi.thread.wangwenjun.juc.atomic;


import lombok.Value;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/2 17:48
 * @Description:
 */
@SuppressWarnings("rwt")
public class UnsafeTest {

    private String name;

    public static void main(String[] args) throws NoSuchFieldException {

        System.out.println(UnsafeTest.class);
        System.out.println(UnsafeTest.class.getName());
        System.out.println(UnsafeTest.class.getDeclaredField("name"));
        System.out.println(UnsafeTest.class.getClassLoader());
        System.out.println(UnsafeTest.class.getClassLoader().getParent());
        System.out.println(UnsafeTest.class.getAnnotations().length);

        //通过反射获取Unsafe对象
        Unsafe unsafe = getUnsafe();
        System.out.println(unsafe);
    }

    /**
     * 通过反射获取Unsafe
     * @return
     */
    private static Unsafe getUnsafe(){
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return  (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
