package com.flx.multi.thread.wangwenjun.design.context;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 15:35
 * @Description
 **/
public class ExecutionTask implements Runnable{

    private QueryFromDBAction queryFromDBAction = new QueryFromDBAction();

    private QueryFromHttpAction queryFromHttpAction = new QueryFromHttpAction();

    @Override
    public void run() {
        Context context = ActionContext.getInstance().getContext();
        queryFromDBAction.execute();
        System.out.println("userName:"+context.getName());
        queryFromHttpAction.execute();
        System.out.println("cardId:"+context.getCardId());
    }
}
