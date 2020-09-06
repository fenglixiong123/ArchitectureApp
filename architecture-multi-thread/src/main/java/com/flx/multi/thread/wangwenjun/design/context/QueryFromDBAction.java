package com.flx.multi.thread.wangwenjun.design.context;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 15:36
 * @Description
 **/
public class QueryFromDBAction {

    public void execute(){
        try {
            Thread.sleep(1000);
            String name = "Jack "+Thread.currentThread().getName();
            ActionContext.getInstance().getContext().setName(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
