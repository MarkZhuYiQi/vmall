package com.mark.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {
    public void demo() {
        final CountDownLatch latch = new CountDownLatch(2);
        ExecutorService threadPool = Executors.newCachedThreadPool();

        for(int i = 1; i <= 2; i++) {
            int index = i;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try{
                        System.out.println(index + "正在执行任务...");
                        Thread.sleep((long)(Math.random() * 1000));
                        System.out.println(index + "任务执行完毕");
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            threadPool.execute(r);
        }
        threadPool.shutdown();
        try{
            latch.await();
            System.out.println("所有子任务执行完毕，主线程开始执行任务：" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
