package com.bigbig;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamTest {
    public static void main(String[] args) {
/*
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String [] strArray = new String[] {"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
        stream.forEach(System.out::println);
*/
        test();
//        test2();
//        testReduce();
    }

    public void streamToObject(){/*
//        String[] str =Stream.of("a","b","c").toArray(String[]::new);
        Stream stream = Stream.of("a", "b", "c");stream.to
        // 1. Array
        String[] strArray1 = stream.toArray(String[]::new);
        // 2. Collection
        List<String> list1 = stream.collect(Collectors.toList());
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
        Set set1 = stream.collect(Collectors.toSet());
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = stream.collect(Collectors.joining()).toString();*/
    }

    public static void test(){
        // 1. Array
        String[] strArray1 = Stream.of("a","b","c").toArray(String[]::new);
        // 2. Collection
        List<String> list1 = Stream.of("a","b","c","a").collect(toList());

        list1.stream().distinct().forEach(System.out::println);

        List<String> list2 = Stream.of("a","b","c").collect(Collectors.toCollection(ArrayList::new));
        Set set1 = Stream.of("a","b","c").collect(Collectors.toSet());
        Stack stack1 = Stream.of("a","b","c").collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = Stream.of("a","b","c").collect(Collectors.joining()).toString();
    }

    public static void test2(){
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());

        outputStream.forEach(System.out::println);
    }

    public static void testReduce(){
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
    }

    public static void testMax() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("c:\\SUService.log"));
        int longest = br.lines().
                mapToInt(String::length).
                max().
                getAsInt();
        br.close();
        System.out.println(longest);
    }
    public static void testt() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("c:\\SUService.log"));
        List<String> words = br.lines().
                flatMap(line -> Stream.of(line.split(" "))).
                filter(word -> word.length() > 0).
                map(String::toLowerCase).
                distinct().
                sorted().
                collect(toList());
        br.close();
        System.out.println(words);
    }
}



