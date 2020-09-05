package com.flx.multi.thread.wangwenjun.design.observer;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 14:31
 * @Description 观察者，观察线程状态，并作出反应
 **/
public interface LifeCycleListener {

    void onEvent(ObserverRunnable.RunnableEvent event);

}
