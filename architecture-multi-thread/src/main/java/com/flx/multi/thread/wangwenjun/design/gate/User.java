package com.flx.multi.thread.wangwenjun.design.gate;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 20:59
 * @Description
 **/
public class User extends Thread{

    private final String name;
    private final String address;
    private final Gate gate;

    public User(String name, String address, Gate gate) {
        this.name = name;
        this.address = address;
        this.gate = gate;
    }

    @Override
    public void run() {
        System.out.println("User:name="+name+",address="+address);
        while (true){
            gate.pass(name,address);
        }
    }
}
