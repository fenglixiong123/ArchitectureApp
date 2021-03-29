package com.flx.multi.thread.wangwenjun.cases;

import com.flx.multi.thread.common.utils.ListPageUtils;
import com.flx.multi.thread.common.utils.PageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Fenglixiong
 * @Create 2020/7/29 2:07
 * @Description
 * 小案例：向10w用户发送信息
 **/

public class UserSendMessageCase {

    public static void main(String[] args) {
        //获取所有用户数据
        List<User> userList = initUser();
        //计算分页页码
        int totalPage = PageUtils.getTotalPage(userList.size(),2);
        List<Thread> threadList = new ArrayList<>();
        //获取所有的线程
        for (int i = 1; i <= totalPage; i++) {
            List<User> users = ListPageUtils.pageList(userList,i,2);
            threadList.add(new Thread(new SendMessageTask(users)));
        }
        //执行所有线程进行发送信息
        threadList.forEach(Thread::start);
    }

    /**
     * 初始化数据
     * @return
     */
    private static List<User> initUser(){
        List<User> userList = new ArrayList<User>();
        for (int i = 1; i <= 10; i++) {
            userList.add(new User("userId-"+i,"userName-"+i));
        }
        return userList;
    }

    /**
     * 定义任务类
     */
    static class SendMessageTask implements Runnable{

        private final List<User> userList;

        public SendMessageTask(List<User> userList){
            this.userList = userList;
        }

        public void run() {
            userList.forEach(e->{
                System.out.println("Finish SendMessage to "+e.getUserId()+","+e.getUserName());
            });
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class User{
        private String userId;
        private String userName;
    }

}




