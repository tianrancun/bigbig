package com.bigbig;

/**
 * @author vn0ow6b
 * @Title: ThreadTest
 * @Description: TODO
 * @date 05/06/2019 16:03
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1= new Thread(){
            @Override
            public void run() {
                super.run();
            }
        };
        t1.stop();
        Thread.sleep(100L);
//        Thread.currentThread().wait()-r'r'r'r'r'r'r'r'r'r'r'r'r'r'r'r'r'r';
    }
}