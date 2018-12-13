package com.mark.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 公司组织周末聚餐吃饭，首先员工们（线程）各自从家里到聚餐地点，全部到齐之后，才开始一起吃东西（同步点）。
 * 假如人员没有到齐（阻塞），到的人只能够等待，直到所有人都到齐之后才开始吃饭。
 */
public class CyclicBarrierDemo {
    public void demo() {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("全员到齐，准备出击！");
            }
        });
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for(int i = 1; i <= 3; i++) {
            int user = i;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep((long)(Math.random() * 10000));
                        System.out.println(user + "号已经到达集合点，当前有" + (cyclicBarrier.getNumberWaiting() + 1) + "人到达");
                        // 阻塞, 等待其他线程
                        cyclicBarrier.await();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            threadPool.execute(r);
        }
        threadPool.shutdown();
    }
}
