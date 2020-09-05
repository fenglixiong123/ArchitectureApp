package com.flx.multi.thread.wangwenjun.design.observer;

import lombok.Getter;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 14:23
 * @Description
 **/
public abstract class ObserverRunnable implements Runnable{

    final protected LifeCycleListener listener;

    public ObserverRunnable(final LifeCycleListener listener) {
        this.listener = listener;
    }

    protected void notifyChange(final RunnableEvent event){
        listener.onEvent(event);
    }

    public enum RunnableState{
        RUNNING,ERROR,DONE
    }

    public static class RunnableEvent{
        @Getter
        private final RunnableState state;
        @Getter
        private final Thread thread;
        @Getter
        private final Throwable cause;

        public RunnableEvent(RunnableState state, Thread thread, Throwable cause) {
            this.state = state;
            this.thread = thread;
            this.cause = cause;
        }
    }

}
