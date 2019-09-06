package com.bigbig;

import com.google.common.cache.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author vn0ow6b
 * @Title: GuavaCacheDemo1
 * @Description: TODO
 * @date 26/08/2019 15:08
 */
public class GuavaCacheDemo1 {
    public static void main(String[] args){

        RemovalListener<String, String> removalListener =new RemovalListener<String, String>() {
                    public void onRemoval(RemovalNotification<String, String> removal) {
                        System.out.println("[" + removal.getKey() + ":" + removal.getValue() + "] is evicted!");
                    }
                };

        CacheBuilder<String, String> testCache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
//                .refreshAfterWrite(2,TimeUnit.SECONDS)
                .removalListener(removalListener);
//                .ticker()
              /*  .build(new CacheLoader<String, String> () {
                    Map<String, String> map = new HashMap<String, String>();
                    @Override
                    public String load(String key) throws Exception {
                    System.out.println(key + " is loaded from a cacheLoader!");
                    return key + "'s value";
                    }
                });*/

        for (int i = 0; i < 10; i ++){
            String key = "key" + i;
            String value = "value" + i;
            testCache.build(new CacheLoader<String, String> () {
                @Override
                public String load(String key1) throws Exception {
                    System.out.println(key1 + " is loaded from a cacheLoader!");
                    return key1;
//                    return key + "'s value";
                }
            });//.put(key,value)
            System.out.println("[" + key + ":" + value + "] is put into cache!");
        }

        for (int i = 0; i < 10; i++) {
            String key = "key" + i;
            try {
//                System.out.println(testCache.weakKeys(key));
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        for (int i = 0; i < 10; i++) {
//            String key = "key" + i;
//            try {
//                System.out.println(testCache.weakKeys(key));
//                TimeUnit.SECONDS.sleep(1);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }
}