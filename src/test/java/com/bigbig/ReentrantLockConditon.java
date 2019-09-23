package com.bigbig;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ReentrantLockConditon implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    @Override
    public void run() {

        try {
            lock.lock();
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockConditon r = new ReentrantLockConditon();
        Thread t1 = new Thread(r);
        t1.start();
        Thread.sleep(9000);
        System.out.println("notify... going on");
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}