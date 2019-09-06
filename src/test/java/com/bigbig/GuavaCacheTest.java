package com.bigbig;

import com.google.common.cache.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author vn0ow6b
 * @Title: GuavaCacheTest
 * @Description: TODO
 * @date 27/08/2019 10:34
 */
public class GuavaCacheTest {
    public static void main(String[] args) {

    }

    @Test
    public void cacheTest() {
        CacheBuilder<String, String> defectiveReturnsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .removalListener(new RemovalListener<String, String>(){
                    @Override
                    public void onRemoval(RemovalNotification notification) {
                        System.out.println("Following data is being removed from defectiveReturnsCache:"+notification.getKey());

                        if(RemovalCause.EXPIRED == notification.getCause()){
                            //call catalog manager's updateRealTimeDefectiveData method from here
                            System.out.println("call updateRealTimeDefectiveData");
                        }
                        else{
                            System.out.println("This data didn't expired but evacuated intentionally"+notification.getKey());
                        }
                    }
                });
        for (int i = 0; i < 5; i ++){
            String key = "key" + i;
            String value = "value" + i;
            defectiveReturnsCache.build().put(key,value);
            System.out.println("[" + key + ":" + value + "] is put into cache!");
        }
        for (int i = 0; i < 5; i++) {
            String key = "key" + i;
            try {
                System.out.println(defectiveReturnsCache.build().getIfPresent(key));
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void test() throws InterruptedException {
        RemovalListener<String, String> removalListener =new RemovalListener<String, String>() {
            public void onRemoval(RemovalNotification<String, String> removal) {
                System.out.println("[" + removal.getKey() + ":" + removal.getValue() + "] is evicted!");
            }
        };

        LoadingCache<String, String> testCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
//                .removalListener(removalListener);
                .build(new CacheLoader<String, String> () {
                    @Override
                    public String load(String key) throws Exception {
                      System.out.println(key + " is loaded from a cacheLoader!");
                    return key + "'s value";
                    }
                });

        for (int i = 0; i < 10; i ++){
            String key = "key" + i;
            String value = "value" + i;

           /* if(i==4){
                System.out.println("key3's value:"+testCache.getIfPresent("key3"));
            }
            if(i>=5){
                System.out.println("sleep 20 seconds.................");
                TimeUnit.SECONDS.sleep(20);
            }*/
            testCache.put(key,value);
            System.out.println("[" + key + ":" + value + "] is put into cache!");
        }

        TimeUnit.SECONDS.sleep(20);
        System.out.println("key3's value:"+testCache.getIfPresent("key3"));
        System.out.println("end.................");
    }

    @Test
    public void cacheTest2() throws InterruptedException {
        RemovalListener<String, String> removalListener =new RemovalListener<String, String>() {
            public void onRemoval(RemovalNotification<String, String> removal) {
                System.out.println("[" + removal.getKey() + ":" + removal.getValue() + "] is evicted!"+removal.getCause());
            }
        };

        Cache<String, String> testCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .removalListener(removalListener)
                .build();

        for (int i = 0; i < 10; i ++){
            String key = "key" + i;
            String value = "value" + i;
            TimeUnit.SECONDS.sleep(1);
            if(i==50){
                System.out.println("clean up start");
                testCache.cleanUp();
                System.out.println("clean up end");
                System.out.println(".....................................sleep 10 seconds");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("clean up start");

                testCache.cleanUp();
                System.out.println(testCache.stats());
                System.out.println("clean up end");


            }
            testCache.put(key,value);

            System.out.println("[" + key + ":" + value + "] is put into cache!");
            testCache.cleanUp();


        }
        System.out.println(".....................................sleep 20 seconds");
        TimeUnit.SECONDS.sleep(20);
        System.out.println(".....................................sleep 20 seconds end");
        for (int i = 20; i < 40; i ++) {
            String key = "key" + i;
            String value = "value" + i;
            testCache.put(key,value);
            TimeUnit.SECONDS.sleep(1);
        }
        testCache.cleanUp();

       /* System.out.println("invalidateAll start");
        testCache.cleanUp();
        System.out.println("invalidateAll end");
        TimeUnit.SECONDS.sleep(20);
        System.out.println("get key start ");
        System.out.println("key3's value:"+testCache.getIfPresent("key3"));*/
        System.out.println(testCache.stats());
        System.out.println("end.................");

    }

    @Test
    public void cacheAsynchronousListenerTest() throws InterruptedException {
        RemovalListener<String, String> removalListener =RemovalListeners.asynchronous(removal -> {
            System.out.println("[" + removal.getKey() + ":" + removal.getValue() + "] is evicted!"+removal.getCause());
            updateRealTimeDefectiveData();
        }, Executors.newFixedThreadPool(3)) ;

        Cache<String, String> testCache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .removalListener(removalListener)
                .build();

        for (int i = 0; i < 20; i ++){
            String key = "key" + i;
            String value = "value" + i;
            testCache.put(key,value);

            System.out.println("[" + key + ":" + value + "] is put into cache!");

        }
        //sleep 30 seconds so that all data is expired
        TimeUnit.SECONDS.sleep(30);
        testCache.cleanUp();

        TimeUnit.MINUTES.sleep(2);
        System.out.println("end.................");
    }

    public void  updateRealTimeDefectiveData(){
        try {
            //call apic
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}