package com.test;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkTest {
    public static void main(String[] args) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("106.13.1.55:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy)
                .namespace("itheima") // 支持命名空间。将来JavaAPi做的所有操作，都会默认认为itheima是根目录
                .build();

        // 开启连接
        client.start();
        String path = null;
        try {
            // 如果创建节点没有指定数据，则默认将当前客户端的ip作为数据存储
            path = client.create().forPath("/app1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(path);
    }
}
