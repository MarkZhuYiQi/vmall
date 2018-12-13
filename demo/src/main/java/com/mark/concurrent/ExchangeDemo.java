package com.mark.concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangeDemo {
    public void demo() {
        Exchanger<String> ec = new Exchanger<String>();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String returnStr = ec.exchange("肤白貌美萌萌哒");
                    System.out.println("绑架者用肤白貌美萌萌哒换回" + returnStr);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String returnStr = ec.exchange("1000万");
                    System.out.println("我用1000万换回" + returnStr);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPool.shutdown();
    }
}
