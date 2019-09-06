package com.bigbig;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

/**
 * @author vn0ow6b
 * @Title: GuavaCacheTest1
 * @Description: TODO
 * @date 27/08/2019 10:42
 */
public class GuavaCacheTest1 {
    public static void main(String[] args) {

        CacheBuilder builder=CacheBuilder.newBuilder().expireAfterWrite(1000, TimeUnit.NANOSECONDS).removalListener(
                new RemovalListener<String, String>(){
                    {
//                        logger.debug("Removal Listener created");
                    }
                    public void onRemoval(RemovalNotification notification) {
                        System.out.println("Going to remove data from InputDataPool");
//                        logger.info("Following data is being removed:"+notification.getKey());
                        if(notification.getCause()== RemovalCause.EXPIRED)
                        {
//                            logger.fatal("This data expired:"+notification.getKey());
                        }else
                        {
//                            logger.fatal("This data didn't expired but evacuated intentionally"+notification.getKey());
                        }

                    }}
        );
        LoadingCache cache=builder.build(new CacheLoader(){

            @Override
            public Object load(Object key) throws Exception {
//                logger.info("Following data being loaded"+(Integer)key);
//                Integer uniqueId=(Integer)key;
//                return InputDataPool.getInstance().getAndRemoveDataFromPool(uniqueId);
                return key;

            }

        });
    }
}