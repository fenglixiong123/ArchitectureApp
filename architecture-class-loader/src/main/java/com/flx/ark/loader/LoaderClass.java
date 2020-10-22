package com.flx.ark.loader;

import com.flx.ark.loader.entity.Student;

/**
 * @Author Fenglixiong
 * @Create 2020/9/14 23:23
 * @Description
 * 类加载简单来说，就是将class文件中的二进制数据读取到内存中，将其放在方法区，
 * 然后在堆区创建一个java.lang.Class对象，用来封装在方法区的数据结构
 *
 * 类加载的最终产物是位于堆区的Class对象
 *
 **/
public class LoaderClass {

    public static void main(String[] args) {
        Student student1 = new Student();
        Student student2 = new Student();
        System.out.println(student1.getClass()==student2.getClass());//true
    }

}
