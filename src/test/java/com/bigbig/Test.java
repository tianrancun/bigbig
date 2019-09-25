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

    /**
     * 递归
     */
    @org.junit.Test
    public void recursion() {
//        System.out.println(sum(4));
//        pageCall(11);
        System.out.println(2<<3);
    }

    private int sum(int num){
        if(num==1){
            return 1;
        }else {
            return num+sum(num-1);
        }
    }

    private void pageCall(int size){
        System.out.println(size);
        if(size!=1){
            pageCall(size-1);
        }
    }
}