package com.bigbig;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

/**
 * @author vn0ow6b
 * @Title: InputDataPool
 * @Description: TODO
 * @date 27/08/2019 11:21
 */
public class InputDataPool {

    private static  InputDataPool clsInputDataPool;

    private InputDataPool() {

        LoadingCache cache = CacheBuilder.newBuilder().expireAfterWrite(1000, TimeUnit.NANOSECONDS).removalListener(
                new RemovalListener() {
                    {
//                        logger.debug("Removal Listener created");
                    }

                    public void onRemoval(RemovalNotification notification) {
                        System.out.println("Going to remove data from InputDataPool");
//                        logger.info("Following data is being removed:"+notification.getKey());
                        if (notification.getCause() == RemovalCause.EXPIRED) {
//                            logger.fatal("This data expired:"+notification.getKey());
                        } else {
//                            logger.fatal("This data didn't expired but evacuated intentionally"+notification.getKey());
                        }

                    }
                }
        ).build(new CacheLoader() {

            @Override
            public Object load(Object key) throws Exception {
//                logger.info("Following data being loaded"+(Integer)key);
                Integer uniqueId = (Integer) key;
                return InputDataPool.getInstance().getAndRemoveDataFromPool(uniqueId);

            }

        });

    }

    public static InputDataPool getInstance(){
        if(clsInputDataPool==null){
            synchronized(InputDataPool.class){
                if(clsInputDataPool==null)
                {
                    clsInputDataPool=new InputDataPool();
                }
            }
        }
        return clsInputDataPool;
    }

    private Object getAndRemoveDataFromPool(Integer uniqueId){
        return null;
    }
}


