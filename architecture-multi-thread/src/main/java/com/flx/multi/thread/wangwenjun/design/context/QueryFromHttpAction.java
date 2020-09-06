package com.flx.multi.thread.wangwenjun.design.context;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 15:41
 * @Description
 **/
public class QueryFromHttpAction {

    public void execute(){
        Context context = ActionContext.getInstance().getContext();
        String name = context.getName();
        String cardId = getCardId(name);
        context.setCardId(cardId);
    }

    public String getCardId(String name){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "411381199301062598 "+Thread.currentThread().getName();
    }

}
