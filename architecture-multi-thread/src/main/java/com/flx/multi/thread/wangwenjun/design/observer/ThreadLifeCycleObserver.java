package com.flx.multi.thread.wangwenjun.design.observer;

import java.util.List;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 14:33
 * @Description
 **/
public class ThreadLifeCycleObserver implements LifeCycleListener{

    @Override
    public void onEvent(ObserverRunnable.RunnableEvent event) {
        synchronized (this){
            System.out.println("The runnable ["+event.getThread().getName()+"] state is : "+event.getState());
            if(event.getCause()!=null){
                event.getCause().printStackTrace();
            }
        }
    }

    public void concurrentQuery(List<String> ids){
        ids.forEach(id->new Thread(new ObserverRunnable(this) {
            @Override
            public void run() {
                try{
                    notifyChange(new RunnableEvent(RunnableState.RUNNING,Thread.currentThread(),null));
                    System.out.println("query for id:"+id);
                    Thread.sleep(1000);
                    notifyChange(new RunnableEvent(RunnableState.DONE,Thread.currentThread(),null));
                }catch (Exception e){
                    notifyChange(new RunnableEvent(RunnableState.ERROR,Thread.currentThread(),e));
                }
            }
        },""+id).start());
    }
}
