package com.bigbig;



import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo implements Runnable{
    static  CountDownLatch latch = new CountDownLatch(5);

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getId()+": thread is start");
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println(Thread.currentThread().getId()+": thread is done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            latch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo demo = new CountDownLatchDemo();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        ExecutorService executor2= Executors.newScheduledThreadPool(5);
        ExecutorService executor3= Executors.newSingleThreadExecutor();
        ExecutorService executor4= Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            executor.submit(demo);
        }
        latch.await();
        System.out.println("end...");
        executor.shutdown();
    }

}