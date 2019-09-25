package com.bigbig;


public class MultiParamsTest {

    public static int sum(int... i){
        int total =0;
        for (int j:i) {
            total = total+j;
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(sum(2));
        System.out.println(sum(2,3));
        System.out.println(sum(2,3,4));
    }
}