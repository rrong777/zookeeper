package com.itheima.curator;

public class Ticket12306 implements Runnable{
    private int tickets = 10;// 数据库的余票

    @Override
    public void run() {
       while (true) {
           if(tickets > 0) {
               System.out.println(Thread.currentThread() + ":" + tickets);
               tickets--;
           }
       }
    }
}
