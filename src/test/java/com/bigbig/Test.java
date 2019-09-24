package com.bigbig;


public class Test {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            Thread.sleep(1000);
        }

//        System.out.println("start");
//        System.out.println(SingletonDemo.status);
//        System.out.println("==========");
//        Thread.sleep(1000);
//        SingletonDemo singletonDemo= SingletonDemo.getInstance();
//
//        Thread.sleep(1000);
//        singletonDemo.pirnt("dada");
    }
}