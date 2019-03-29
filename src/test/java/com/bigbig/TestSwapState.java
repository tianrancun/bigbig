package com.bigbig;

import com.bigbig.metadata.SwapState;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author vn0ow6b
 * @Title: TestSwapState
 * @Description: TODO
 * @date 2019/3/29 20:13
 */
@Slf4j
public class TestSwapState {
    @Test
    public void testSwap() {
       // SwapState.valueOf(1);
        log.info(SwapState.valueOf(4).name());
        log.info(SwapState.valueOf("SWAPPED").name());
    }
}