package com.flx.multi.thread.wangwenjun.design.balking;


/**
 * @Author Fenglixiong
 * @Create 2020/9/7 23:35
 * @Description
 **/
public class ChangeThread extends Thread{

    private BalkingData balkingData;

    public ChangeThread(String name, BalkingData balkingData) {
        super(name);
        this.balkingData = balkingData;
    }

    @Override
    public void run() {
        int i = 1;
        while (true){
            balkingData.change("AAA "+i);
            try {
                Thread.sleep(5000);
                balkingData.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
