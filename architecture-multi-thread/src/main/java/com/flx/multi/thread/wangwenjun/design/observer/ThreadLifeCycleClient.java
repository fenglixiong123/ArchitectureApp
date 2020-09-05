package com.flx.multi.thread.wangwenjun.design.observer;

import java.util.Arrays;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 14:33
 * @Description
 **/
public class ThreadLifeCycleClient{

    public static void main(String[] args) {
        ThreadLifeCycleObserver listener = new ThreadLifeCycleObserver();
        listener.concurrentQuery(Arrays.asList("1","2","3"));
    }

}
