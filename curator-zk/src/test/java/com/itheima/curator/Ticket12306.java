package com.itheima.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

public class Ticket12306 implements Runnable{
    private int tickets = 10;// 数据库的余票
    private InterProcessMutex lock;// 分布式锁实现的类

    public Ticket12306() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);
        CuratorFramework client = CuratorFrameworkFactory.newClient("106.13.1.55:2181",
                60 * 1000, 15 * 1000, retryPolicy);
        client.start();
        // 构造器注入锁资源 使用/lock节点实现分布式锁
        lock = new InterProcessMutex(client, "/lock");
    }

    @Override
    public void run() {
       while (true) {
           // 获取锁 参数是等待时间，等待3秒不等了，3秒没获取到待会儿再来
           // 没获取到锁，就会抛异常，但是这个异常已经被curator处理了，
           try {
               lock.acquire(3, TimeUnit.SECONDS);
               if(tickets > 0) {
                   System.out.println(Thread.currentThread() + ":" + tickets);
                   tickets--;
               }
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               // 释放锁
               try {
                   lock.release();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }


       }
    }
}
