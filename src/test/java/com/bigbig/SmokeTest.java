package com.bigbig;

import com.bigbig.controller.HomeController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * https://spring.io/guides/gs/testing-web/
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {
    @Autowired
    private HomeController controller;

    @Test
    public void contexLoads() throws Exception {
//        assertThat(controller).isNotNull();
//        assertThat(controller.greeting()).isEqualTo("Hello World");
        Assert.assertNotNull(controller);
        Assert.assertEquals(controller.greeting(),"Hello World");
    }
}