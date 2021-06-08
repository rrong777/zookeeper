package com.itheima.curator;

public class LockTest {
    public static void main(String[] args) {
        // ticket12306就相当于12306正经卖票服务
        Ticket12306 ticket12306 = new Ticket12306();
        // 两个线程相当于代理买票的，携程飞猪这个角色
        Thread t1 = new Thread(ticket12306, "携程");
        Thread t2 = new Thread(ticket12306, "飞猪");
        t1.start();
        t2.start();
    }
}
