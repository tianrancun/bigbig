package com.bigbig;

import com.bigbig.metadata.DispositionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BigbigApplicationTests {

	@Test
	public void contextLoads() {
		List list = Arrays.asList(DispositionType.SHIP,DispositionType.DISP);
		System.out.println(list);
	}

}
