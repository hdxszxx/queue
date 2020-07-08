package com.queue.demo.common;

import com.queue.demo.entity.Queue;

/**
 * @author zxx
 * @create 2020-07-02 16:27
 */
public class QueueRunnable implements Runnable{
    private String name;

    public QueueRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (true){
            Queue b = QueueKeys.getQueue(name);
            if(b!=null) {
                try {
                    // 开始试玩
                    System.out.println("当前使用的云机线程为："+name+"，当前开始试玩为：" + b.toString());
                    Thread.sleep(30000);
                    // 试玩结束开始获取下一个
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}