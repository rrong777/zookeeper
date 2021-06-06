package com.itheima.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CuratorTest {
    private CuratorFramework client;
    /**
     * 建立连接
     */
    @Before
    public void testConnect() {
        //CuratorFramework是Java和zk建立连接的客户端对象

        // 第1种方式创建CuratorFramework对象
        /**
         connectString – 连接字符串 - zkserver地址和端口，可以是集群环境，逗号分割即可，"192.168.149.135:2181,192.168.149.133:2181"
         sessionTimeoutMs – 会话超时时间单位 ms，会话就是客户端和服务端建立一次连接。 会话超时时间，是客户端和服务端多长时间没有联通就超时了。 浏览器和服务器建立一次连接，就是这个浏览器和这个服务器，也就是第一次登录这个服务的时间开始
         connectionTimeoutMs – 连接超时时间。3秒没建立上连接就给你超时，
         retryPolicy – 重试策略，当前你要建立连接，连接建立失败怎么办，字符串写错了怎么办，超时了怎么办。没有成功重新发一次请求给你重试，重试策略。
            retryForever  一直重试
            retryNTimes   重试N次
            ……
         sessionTimeoutMs,connectionTimeoutMs不给是有默认值的
         */
        //  3秒一次重试，重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);
//        CuratorFramework client = CuratorFrameworkFactory.newClient("124.70.216.119:2181",
//                60 * 1000, 15 * 1000, retryPolicy);

        // 第2种方式创建CuratorFramework对象
//                client = CuratorFrameworkFactory.builder().connectString("www.yaoan-learn.com:8070/zk").sessionTimeoutMs(60 * 1000)
        client = CuratorFrameworkFactory.builder().connectString("124.70.216.119:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy)
                .namespace("itheima") // 支持命名空间。将来JavaAPi做的所有操作，都会默认认为itheima是根目录
                .build();

        // 开启连接
        client.start();
    }

    /**
     * 创建节点： create 持久/临时 乱序/顺序 节点
     */
    @Test
    public void testCreate() throws Exception {
        // 1. 基本创建  create方法返回值就是创建的节点路径
        String path = client.create().forPath("/app1");
        System.out.println(path);
    }

    @After
    public void close() {
        if(client != null) {
            client.close();
        }
    }
}
