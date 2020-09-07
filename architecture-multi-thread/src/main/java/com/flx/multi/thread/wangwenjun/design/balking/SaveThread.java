package com.flx.multi.thread.wangwenjun.design.balking;

/**
 * @Author Fenglixiong
 * @Create 2020/9/7 23:33
 * @Description
 **/
public class SaveThread extends Thread{

    private BalkingData balkingData;

    public SaveThread(String name, BalkingData balkingData) {
        super(name);
        this.balkingData = balkingData;
    }

    @Override
    public void run() {
        try {
            while (true){
                balkingData.save();//模拟保存方法
                Thread.sleep(2000);//休息1秒
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
