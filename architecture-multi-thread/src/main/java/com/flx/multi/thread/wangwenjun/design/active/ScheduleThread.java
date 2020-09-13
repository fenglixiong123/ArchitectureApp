package com.flx.multi.thread.wangwenjun.design.active;

import com.flx.multi.thread.wangwenjun.design.active.request.MethodRequest;

/**
 * @Author Fenglixiong
 * @Create 2020/9/13 10:54
 * @Description
 **/
public class ScheduleThread<T> extends Thread{

    private final ActivationQueue<T> queue;

    public ScheduleThread(ActivationQueue<T> queue) {
        this.queue = queue;
    }

    public void invoke(MethodRequest<T> request){
        queue.put(request);
    }

    @Override
    public void run() {
        while (true){
            queue.take().execute();
        }
    }
}
