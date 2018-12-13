package com.mark.concurrent;

import java.util.concurrent.Semaphore;

/**
 * 模拟多用户并发操作，资源有限，只有2个，但是大家都使用
 * 资源数量根据传入的Semaphore
 */
public class SemaphoreDemo implements Runnable{
    private Semaphore semaphore;
    private int user;
    public SemaphoreDemo(Semaphore semaphore, int user) {
        this.semaphore = semaphore;
        this.user = user;
    }
    @Override
    public void run() {
        try{
            semaphore.acquire();
            System.out.println("用户" + user + "进入线程, 开始操作");
            Thread.sleep(1000);
            System.out.println("用户" + user + "操作完成，准备离开线程");
            Thread.sleep(1000);
            System.out.println("用户" + user + "离开线程");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
