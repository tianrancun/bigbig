package com.bigbig;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author vn0ow6b
 * @Title: TestLIst
 * @Description: TODO
 * @date 27/06/2019 14:56
 */
public class TestLIst {
    //按每3个一组分割
    private static final Integer MAX_SEND = 3;

    public static void main(String[] args) {
        List<Student> list = new ArrayList(){{
            add(new Student("a",18,'m',"d"));
            add(new Student("b",18,'f',"d"));
            add(new Student("c",18,'f',"d"));
            add(new Student("d",18,'f',"d"));
            add(new Student("e",18,'f',"d"));
            add(new Student("e",18,'f',"d"));
            add(new Student("f",18,'f',"d"));
            add(new Student("g",18,'f',"d"));
        }};

        list.stream().forEach(e->{
                if(e.getName().equals("e")){
                    return;
                }
            System.out.println(e.toString());
        });

        List s= list.stream().map(Student::getName).collect(Collectors.toList());
        List<Map<String, Object>> data = list.stream().map(n-> new HashMap<String,Object>(){{
            put("name",n.getName());
            put("age",n.getAge());
        }})
                .collect(Collectors.toList());
//        List s2= list.stream().map(n->{n.getName(),n.getAge()}aa).collect(Collectors.toList());
        System.out.println();
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
//        int limit = countStep(list.size());
//        //方法一：使用流遍历操作
//        List<List<Integer>> mglist = new ArrayList<>();
//        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
//            mglist.add(list.stream().skip(i * MAX_SEND).limit(MAX_SEND).collect(Collectors.toList()));
//        });
//
//        System.out.println(mglist);

        //方法二：获取分割后的集合
//        List<List<Integer>> splitList = Stream.iterate(0, n -> n + 1).limit(limit).parallel().map(a -> list.stream().skip(a * MAX_SEND).limit(MAX_SEND).parallel().collect(Collectors.toList())).collect(Collectors.toList());
//
//        System.out.println(splitList);
    }

    /**
     * 计算切分次数
     */
    private static Integer countStep(Integer size) {
        return (size + MAX_SEND - 1) / MAX_SEND;
    }
}