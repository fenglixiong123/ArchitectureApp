package com.flx.multi.thread.wangwenjun.design.context;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 15:54
 * @Description
 **/
public class ActionContext {

    private static final ThreadLocal<Context> contextLocal = new ThreadLocal<Context>(){
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    //构造函数私有化
    private ActionContext(){

    }

    private static class ContextHolder{
        private final static ActionContext actionContext = new ActionContext();
    }

    public static ActionContext getInstance(){
        return ContextHolder.actionContext;
    }

    public Context getContext(){
        return contextLocal.get();
    }

}
