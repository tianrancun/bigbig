package com.bigbig;


import com.bigbig.entity.Student;
import com.bigbig.util.JsonUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestJsonUtil {
    @Test
    public void testObject2Map() {
        Student s = new Student("zhangshan",18,"grade3");
        Map<String,Object> map = JsonUtil.Obj2Map(s);
        System.out.println(map);
    }

    @Test
    public void testMap2Ojbcet() {
        Map<String,Object> map = new HashMap<>();
        map.put("grade","grade3");
        map.put("age",20);
        map.put("name","Lisi");
        Student s = (Student)JsonUtil.map2Obj(map,Student.class);
        System.out.println(s);

    }
}