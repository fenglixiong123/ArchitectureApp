package com.flx.multi.thread.wangwenjun.design.gate;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 20:53
 * @Description
 * 门为共享资源，临界值
 * 引发思考：加锁影响效率，读的操作其实没必要加锁
 * 读写分离锁设计
 **/
public class Gate {

    private int counter = 0;
    private String name = "Nobody";
    private String address = "Nowhere";

    /**
     * 这里需要加锁，否则会出现线程安全问题
     * 写操作
     * @param name
     * @param address
     */
    public synchronized void pass(String name,String address){
        this.counter ++;
        this.name = name;
        this.address = address;
        verify();
    }

    /**
     * 读操作
     */
    private void verify() {
        if(this.name.charAt(0)!=this.address.charAt(0)){
            System.out.println("*******Broken********"+toString());
        }
    }

    /**
     * 读操作
     * @return
     */
    public String toString(){
        return "No."+counter+",name:"+name+",address:"+address;
    }

}
