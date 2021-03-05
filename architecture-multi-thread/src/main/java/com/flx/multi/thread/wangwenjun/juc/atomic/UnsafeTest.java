package com.flx.multi.thread.wangwenjun.juc.atomic;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/2 17:48
 * @Description: Unsafe类操作和使用，java操作硬件资源
 *
 * arrayBaseOffset 数组基础偏移量
 * arrayIndexScale 数组元素间隔
 *
 * allocateInstance 创建类对象
 * objectFieldOffset 获取对象属性的偏移量
 * putObject 通过偏移量设置对象的值
 *
 * allocateMemory 分配内存
 * setMemory 设置内存用于初始化
 * putInt putBoolean putDouble 在指定内存位置设置值
 */
@SuppressWarnings("rwt")
public class UnsafeTest {

    private String name;

    public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        System.out.println(UnsafeTest.class);
        System.out.println(UnsafeTest.class.getName());
        System.out.println(UnsafeTest.class.getDeclaredField("name"));
        System.out.println(UnsafeTest.class.getClassLoader());
        System.out.println(UnsafeTest.class.getClassLoader().getParent());
        System.out.println(UnsafeTest.class.getAnnotations().length);

        //通过反射获取Unsafe对象
        Unsafe unsafe = getUnsafe();
        System.out.println(unsafe);

        System.out.println("-----------------------");
        System.out.println("address size = "+unsafe.addressSize());
        System.out.println("page size = "+unsafe.pageSize());
        System.out.println("array base offset = "+unsafe.ARRAY_INT_BASE_OFFSET);

        System.out.println("-----------------------");
        operateArray(unsafe);
        System.out.println("-----------------------");
        operateObject(unsafe);
        System.out.println("-----------------------");
        operateMemory(unsafe);
        System.out.println("-----------------------");
        operateTest(unsafe);
    }

    /**
     * 通过反射获取Unsafe实例
     * @return
     */
    public static Unsafe getUnsafe(){
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return  (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("obtain Unsafe error!");
        }
    }

    /**
     * 操作对象
     */
    private static void operateObject(Unsafe unsafe) throws InstantiationException, NoSuchFieldException {
        //创建类对象
        Student student = (Student) unsafe.allocateInstance(Student.class);
        student.setName("jack");
        student.setAge(22);
        System.out.println(student);
        Field nameField = Student.class.getDeclaredField("name");
        Field ageField = Student.class.getDeclaredField("age");
        System.out.println("nameField = "+nameField);
        System.out.println("ageField = "+ageField);
        //获取成员变量相当于对象的偏移量
        long nameFieldOffset = unsafe.objectFieldOffset(nameField);
        long ageFieldOffset = unsafe.objectFieldOffset(ageField);
        System.out.println("nameFieldOffset = "+nameFieldOffset);
        System.out.println("ageFieldOffset = "+ageFieldOffset);
        //直接修改成员变量的值
        unsafe.putObject(student,nameFieldOffset,"marry");
        unsafe.putInt(student,ageFieldOffset,18);
        System.out.println(student);
    }

    /**
     * 操作数组
     */
    private static void operateArray(Unsafe unsafe){
        int[] array = new int[]{1,2,3,4,5,6,7,8,9};
        //获取数组的偏移量
        long arrayBaseOffset = unsafe.arrayBaseOffset(int[].class);
        System.out.println("arrayBaseOffset = "+arrayBaseOffset);
        //获取元素的间隔
        long scale = unsafe.arrayIndexScale(int[].class);
        System.out.println("scale = "+scale);
        //设置数组第二个元素
        unsafe.putInt(array,arrayBaseOffset+(scale*2),100);
        //获取数组第二个元素
        int element = unsafe.getInt(array,arrayBaseOffset+(scale*2));
        System.out.println("element = "+element);
    }

    /**
     * 操作内存
     * @param unsafe
     */
    private static void operateMemory(Unsafe unsafe){
        //分配一个8byte的内存
        long address = unsafe.allocateMemory(8L);
        //初始化内存值
        unsafe.setMemory(address,8L,(byte)1);
        System.out.println("从内存["+address+"]获取值为："+unsafe.getInt(address));
        unsafe.putInt(address, 0x7fffffff);
        unsafe.putInt(address + 4, 0x80000000);
        System.out.println("add byte to memory:" + unsafe.getInt(address));
        System.out.println("add byte to memory:" + unsafe.getInt(address + 4));
    }

    /**
     * Unsafe加载class类
     */
    private static void operateClass(Unsafe unsafe) throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //加载并读取类
        File file = new File("D:\\test\\A.class");
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        int len;
        while ((len = fis.read(temp))!=-1){
            bos.write(temp,0,len);
        }
        bos.flush();
        byte[] content = bos.toByteArray();
        //构造类
        Class<?> aClass = unsafe.defineClass(null, content, 0, content.length, null, null);
        aClass.getMethod("get").invoke(aClass.newInstance(),null);

    }

    private static void operateTest(Unsafe unsafe) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchFieldException {

        //不会调用构造函数
        //Class.forName("com.flx.multi.thread.wangwenjun.juc.atomic.UnsafeTest$Student");
        //会调用构造函数
        //Student student = Student.class.newInstance();
        //直接在内存中开辟对象空间
        Student instance = (Student) unsafe.allocateInstance(Student.class);
        instance.setName("kkk");
        instance.setAge(1);
        System.out.println(instance);
        System.out.println(instance.getClass());
        System.out.println(instance.getClass().getClassLoader());

        Guard guard = new Guard();
        guard.work();
        //使用unsafe直接修改私有成员属性的值
        Field access_allowed = guard.getClass().getDeclaredField("ACCESS_ALLOWED");
        long fieldOffset = unsafe.objectFieldOffset(access_allowed);
        unsafe.putInt(guard,fieldOffset,42);
        guard.work();

    }

    static class Guard{
        private int ACCESS_ALLOWED = 1;
        private boolean allow(){
            return ACCESS_ALLOWED == 42;
        }
        public void work(){
            if(allow()){
                System.out.println("allowed,work start!");
            }
        }
    }

    @Data
    private static class Student{
        private String name;
        private int age;

        public Student() {
            System.out.println("student construct...");
        }

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}
