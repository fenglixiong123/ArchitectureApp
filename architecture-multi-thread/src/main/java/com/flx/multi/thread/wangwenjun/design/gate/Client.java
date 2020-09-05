package com.flx.multi.thread.wangwenjun.design.gate;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 21:02
 * @Description
 * 案例：一个大门，各个线程通过并记录数量、姓名、地址、然后验证身份
 **/
public class Client {

    public static void main(String[] args) {
        Gate gate = new Gate();

        User user1 = new User("jack","jc",gate);
        User user2 = new User("tom","tm",gate);
        User user3 = new User("marry","my",gate);

        user1.start();
        user2.start();
        user3.start();

    }

}
