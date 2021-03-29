package com.flx.multi.thread.wangwenjun.juc.atomic;

import com.flx.multi.thread.wangwenjun.apple.Student;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/1 15:43
 * @Description: 解决ABA问题，带版本标志的数据
 */
public class AtomicStampedReferenceTest {

    public static void main(String[] args) {


        Student jack = Student.of("jack",22);
        Student mary = Student.of("mary",18);

        //stamp版本起始为1
        AtomicStampedReference<Student> stampedReference = new AtomicStampedReference<>(jack,1);

        System.out.println(stampedReference.getReference());
        //期望版本为1，设置新版本为2
        stampedReference.compareAndSet(jack,mary,1,3);
        System.out.println(stampedReference.getReference());

        //这里有个小技巧
        //一个方法获取两个值，利用数组带一个值
        int[] stampHolder = new int[2];
        Student student = stampedReference.get(stampHolder);
        System.out.println(student);
        System.out.println(stampHolder[0]);


    }

    private static AtomicStampedReference<Integer> atomicStamp = new AtomicStampedReference<>(100,0);

    /**
     * 解决ABA问题小案例
     * @throws InterruptedException
     */
    public static void solveABA() throws InterruptedException {

        Thread t1 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                boolean success = atomicStamp.compareAndSet(100,101,atomicStamp.getStamp(),atomicStamp.getStamp()+1);
                System.out.println("T1 = "+success);
                success = atomicStamp.compareAndSet(101,100,atomicStamp.getStamp(),atomicStamp.getStamp()+1);
                System.out.println("T1 = "+success);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(()->{
            try {
                int stamp = atomicStamp.getStamp();
                System.out.println("T2 before sleep,stamp = "+stamp);
                TimeUnit.SECONDS.sleep(2);
                boolean success = atomicStamp.compareAndSet(100,101,stamp,stamp+1);
                System.out.println("T2 = "+success);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

}
