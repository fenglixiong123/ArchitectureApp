package com.flx.multi.thread.wangwenjun.design.active.object;

import com.flx.multi.thread.wangwenjun.design.active.ActivationQueue;
import com.flx.multi.thread.wangwenjun.design.active.ScheduleThread;

/**
 * @Author Fenglixiong
 * @Create 2020/9/13 11:23
 * @Description 工厂方法
 **/
public class ActiveObjectFactory {

    private ActiveObjectFactory(){

    }

    public static ActiveObject buildActiveObject(){
        Servant servant = new Servant();
        ActivationQueue<String> queue = new ActivationQueue<>();
        ScheduleThread<String> stringScheduleThread = new ScheduleThread<>(queue);
        ActiveObjectProxy proxy = new ActiveObjectProxy(stringScheduleThread,servant);
        stringScheduleThread.start();
        return proxy;
    }

}
