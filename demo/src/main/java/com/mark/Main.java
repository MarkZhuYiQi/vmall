package com.mark;

import com.mark.concurrent.CountDownLatchDemo;
import com.mark.concurrent.CyclicBarrierDemo;
import com.mark.concurrent.ExchangeDemo;
import com.mark.concurrent.SemaphoreDemo;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
//        Main main = new Main();
//        main.execute();
        CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo();
        countDownLatchDemo.demo();
    }
    private void execute() {
        final Semaphore semaphore = new Semaphore(2);
        // 创建线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for(int i = 0; i < 20; i++) {
            threadPool.execute(new SemaphoreDemo(semaphore, (i+1)));
        }
        threadPool.shutdown();
    }
}
