package com.flx.multi.thread.wangwenjun.design.future;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 23:34
 * @Description
 * Future: 代表的是未来的一个凭据
 * FutureTask: 代表的是将你的调用逻辑进行隔离,也即是你的任务
 * FutureService: 桥接 Future和FutureTask
 * 用户只需要感知FutureService，Future和FutureTask也不需要互相感知
 *
 * 类似于看电影模式：先购票，拿到票据，等自己闲的时候去兑换电影
 *
 **/
public class SyncInvoker {

    public static void main(String[] args) throws InterruptedException {

        FutureService futureService = new FutureService();
        //主动拿结果
        Future<String> future = futureService.submit(SyncInvoker::doTask);
        //被动接受结果
        futureService.submit(SyncInvoker::doTask,System.out::println);

        System.out.println("................");
        Thread.sleep(1000);

        String result = future.get();
        System.out.println(result);

    }

    public static String doTask(){
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "FINISHED";
    }

}
