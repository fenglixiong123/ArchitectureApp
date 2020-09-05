package com.flx.multi.thread.wangwenjun.design.immutable;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 22:37
 * @Description
 **/
public class PersonThread extends Thread{

    private Person person;

    public PersonThread(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        while (true){
            System.out.println(Thread.currentThread().getName()+" "+person.toString());
        }
    }
}
