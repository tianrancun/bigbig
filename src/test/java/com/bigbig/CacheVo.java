package com.bigbig;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author vn0ow6b
 * @Title: CacheVo
 * @Description: TODO
 * @date 26/08/2019 10:23
 */
public class CacheVo {
    private final LoadingCache<String, Map<String,String>> cache_code_name;
    /**
     * 缓存赋值
     * expireAfterWrite是在指定项在一定时间内没有创建/覆盖时，会移除该key，下次取的时候从loading中取
     * expireAfterAccess是指定项在一定时间内没有读写，会移除该key，下次取的时候从loading中取
     * refreshAfterWrite是在指定时间内没有被创建/覆盖，则指定时间过后，再次访问时，会去刷新该缓存，在新值没有到来之前，始终返回旧值
     * duration  持续时间
     */
    public CacheVo(){
        cache_code_name= CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Map<String,String>>() {
                    @Override
                    public Map<String,String> load(String s) throws Exception {
                        Map<String,Map<String,String>> strMap = Maps.newHashMap();
                        Map<String,String> item1 = Maps.newHashMap();
                        item1.put("1001","ACODE10001");
                        item1.put("1002","ACODE10002");
                        item1.put("1003","ACODE10003");
                        item1.put("1004","ACODE10004");
                        strMap.put("item1",item1);
                        Map<String,String> item2 = Maps.newHashMap();
                        item2.put("1001","BCODE10001");
                        item2.put("1002","BCODE10002");
                        item2.put("1003","BCODE10003");
                        item2.put("1004","BCODE10004");
                        strMap.put("item2",item2);
                        System.out.println("------------缓存更新完成-----------");
                        return strMap.get(s);
                    }


                });
    }

    /**
     * 获取MAP
     * @param code
     * @return
     * @throws ExecutionException
     */
    public Map<String, String> name(String code) throws ExecutionException {
        Map<String, String> strMap = cache_code_name.get(code);
        return strMap;
    }
    // 执行
    public static void main(String[] args) throws Exception{
        CacheVo vo = new CacheVo();
        List<String> ls = Lists.newArrayList();
        ls.add("item1");
        ls.add("item2");
        for (int i = 0; i < 100; i++) {
            int v = (int) (Math.random() * 2);
            int itemNumber = (int) (Math.random() * 4)+1;
            Map<String, String> name = vo.name(ls.get(v));
            System.out.println(ls.get(v) + "---------->"+name.get("100"+itemNumber));
            TimeUnit.SECONDS.sleep(1);
        }
    }

}