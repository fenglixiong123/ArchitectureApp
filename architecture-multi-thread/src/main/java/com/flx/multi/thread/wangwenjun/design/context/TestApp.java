package com.flx.multi.thread.wangwenjun.design.context;

import java.util.stream.IntStream;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 15:50
 * @Description
 **/
public class TestApp {

    public static void main(String[] args) {

        ExecutionTask task = new ExecutionTask();

        IntStream.rangeClosed(1,5).forEach(x->{
            new Thread(task).start();
        });

    }

}
