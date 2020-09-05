package com.flx.multi.thread.wangwenjun.design.future;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 23:39
 * @Description
 **/
public interface Future<T> {

    T get()throws InterruptedException;

}
