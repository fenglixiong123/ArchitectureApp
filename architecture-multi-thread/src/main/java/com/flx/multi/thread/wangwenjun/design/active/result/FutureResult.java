package com.flx.multi.thread.wangwenjun.design.active.result;

/**
 * @Author Fenglixiong
 * @Create 2020/9/12 19:52
 * @Description Future设计模式
 **/
public class FutureResult<T> implements Result<T> {

    private Result<T> result;

    private boolean ready = false;

    public synchronized void done(Result<T> result){
        this.result = result;
        this.ready = true;
        this.notifyAll();
    }

    @Override
    public synchronized T getResult() {
        while (!ready){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result.getResult();
    }

}
