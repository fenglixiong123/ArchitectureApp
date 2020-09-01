package com.flx.multi.thread.wangwenjun.base;

/**
 * @Author Fenglixiong
 * @Create 2020/8/23 18:45
 * @Description
 *
 * 这个程序不要执行会把电脑卡死的
 *
 **/
public class ThreadStackMax {

    public static int counter = 0;

//    public static int size = Integer.MAX_VALUE;
    public static int size = 10;

    public static void main(String[] args) {

        try {
            for (int i = 0; i < size; i++) {
                counter ++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //栈帧的宽度
//                        byte[] data = new byte[1024*1024*2];
                        try {
                            Thread.sleep(3600*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                System.out.println("Thread : "+counter);
            }
        }catch (Error e){
            System.out.println("Max thread size = "+counter);
        }

    }

}
