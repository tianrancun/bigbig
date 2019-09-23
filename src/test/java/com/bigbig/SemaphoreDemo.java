package com.bigbig;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo implements Runnable{
    Semaphore semp = new Semaphore(5);

    @Override
    public void run() {
        try {
            semp.acquire();
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getId()+": is done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semp.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        for (int i = 0; i <30 ; i++) {
            executor.submit(semaphoreDemo);
        }
    }
}