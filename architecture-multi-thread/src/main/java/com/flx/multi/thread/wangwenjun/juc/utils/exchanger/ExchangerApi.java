package com.flx.multi.thread.wangwenjun.juc.utils.exchanger;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.concurrent.Exchanger;

/**
 * 此对象只有两个api
 * exchange()
 * exchange(timeout)
 * 传递的是对象本身，而不是副本copy
 * 带来的注意：如果是list等，注意对象会被改变
 */
public class ExchangerApi {

    public static void main(String[] args) {

        final Exchanger<Student> exchanger = new Exchanger<>();

        Student jack = new Student("jack",22);
        Student marry = new Student("marry",18);
        System.out.println(jack);
        System.out.println(marry);
        new Thread(()->{
            try {
                Thread.sleep(2000);
                Student result = exchanger.exchange(jack);
                System.out.println(Thread.currentThread().getName()+" get exchange result "+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                Thread.sleep(4000);
                Student result = exchanger.exchange(marry);
                System.out.println(Thread.currentThread().getName()+" get exchange result "+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @NoArgsConstructor
    @AllArgsConstructor
    static class Student{
        private String name;
        private Integer age;
    }

}
