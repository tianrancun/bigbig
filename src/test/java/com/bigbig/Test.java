package com.bigbig;

import java.util.HashMap;

/**
 * @author vn0ow6b
 * @Title: Test
 * @Description: TODO
 * @date 25/06/2019 17:20
 */
public class Test {
    private static HashMap< String, String> map = new HashMap< String, String>() {
        {
            put("Name", "zz");
            put("age", "18");

        }
    };
    public Test() {
        System.out.println("Constructor called：构造器被调用");
    }
    static {
        System.out.println("Static block called：静态块被调用");
    }
    {
        System.out.println("Instance initializer called：实例初始化块被调用");
    }
    public static void main(String[] args) {
        new Test();
        System.out.println("=======================");
        new Test();
    }
}