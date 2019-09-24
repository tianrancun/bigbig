package com.bigbig;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

/**
 * 限流
 */
public class DubboService {
    Semaphore semaphore = new Semaphore(10);
    public void process(){
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean process2(){
        return semaphore.tryAcquire();
    }


    public static void main(String[] args) {
//        String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        RateLimiter limiter = RateLimiter.create(1.0); // 这里的1表示每秒允许处理的量为1个
//        for (int i = 1; i <= 10; i++) {
//            double waitTime = limiter.acquire(i);// 请求RateLimiter, 超过permits会被阻塞
//
//            System.out.println("cutTime=" + System.currentTimeMillis() + " call execute:" + i + " waitTime:" + waitTime);
//        }
//        String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        System.out.println("start time:" + start);
//        System.out.println("end time:" + end);


        //数组先开辟内存空间，而后再使用索引进行内容的设置，实际上这种做法都叫做动态初始化，而如果希望数组在定义的时候可以同时出现设置内容，那么就可以采用静态初始化完成
        int[] array = new int[]{0,1,2,3,4,5,6,7,8,9,10,11};

        System.out.println("array2:");
        int array2[] = new int[10];
        System.arraycopy(array,0,array2,0,10);
        Arrays.stream(array2).forEach(v -> System.out.print(String.format("[%s]",v)));

        System.out.println("\narray3:");
        int array3[] =Arrays.copyOf(array,15);
        Arrays.stream(array3).forEach(v -> System.out.print(String.format("[%s]",v)));


        System.out.println("\narray4:");
        int array4[] =Arrays.copyOfRange(array,2,8);
        Arrays.stream(array4).forEach(v -> System.out.print(String.format("[%s]",v)));


    }
}