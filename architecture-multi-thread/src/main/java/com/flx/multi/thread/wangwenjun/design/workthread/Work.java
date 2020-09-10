package com.flx.multi.thread.wangwenjun.design.workthread;

/**
 * @Author Fenglixiong
 * @Create 2020/9/10 23:45
 * @Description
 **/
public class Work {

    private final String name;
    private final int number;

    public Work(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public void execute(){
        System.out.println(Thread.currentThread().getName()+" executed "+this);
    }

    @Override
    public String toString() {
        return "Work{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
