package com.bigbig;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DivTask implements Runnable {
    int a,b;
    @Override
    public void run() {

            double result = a/b;
            System.out.println(result);


    }

    public DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
       ExecutorService pool=  Executors.newFixedThreadPool(5);
        for (int i = 1; i < 5; i++) {
            pool.submit(new DivTask(100,i));
//            pool.execute(new DivTask(100,i));
//            Future future = pool.submit(new DivTask(100,i));
//            future.get();
        }

        pool.shutdown();
    }
}