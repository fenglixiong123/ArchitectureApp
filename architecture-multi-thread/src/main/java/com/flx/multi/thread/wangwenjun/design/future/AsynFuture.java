package com.flx.multi.thread.wangwenjun.design.future;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 23:43
 * @Description
 **/
public class AsynFuture<T> implements Future<T>{

    private volatile boolean done = false;

    private T result;

    public void done(T result){
        synchronized (this){
            this.result = result;
            this.done = true;
            this.notifyAll();
        }
    }

    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            while (!done) {
                this.wait();
            }
        }
        return result;
    }
}
