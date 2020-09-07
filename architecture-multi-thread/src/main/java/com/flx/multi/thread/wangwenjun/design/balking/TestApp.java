package com.flx.multi.thread.wangwenjun.design.balking;

/**
 * @Author Fenglixiong
 * @Create 2020/9/7 23:39
 * @Description
 * 场景：
 * 写文档自动保存，等你手动保存时候发现已经保存了
 **/
public class TestApp {

    public static void main(String[] args) throws InterruptedException {

        BalkingData balkingData = new BalkingData("data.txt","*************");

        SaveThread saveThread = new SaveThread("SaveThread",balkingData);
        ChangeThread changeThread = new ChangeThread("ChangeThread",balkingData);

        saveThread.start();
        changeThread.start();

        saveThread.join();
        changeThread.join();

        System.out.println("end!!!");
    }

}
