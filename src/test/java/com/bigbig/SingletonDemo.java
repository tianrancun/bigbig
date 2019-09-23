package com.bigbig;


public class SingletonDemo {

    private SingletonDemo(){
        System.out.println("static singleton is create");
    }

   /* private static class  SingletonHonlder{

        private static SingletonDemo instance = new SingletonDemo();
    }

    public  static SingletonDemo getInstance(){
         return SingletonHonlder.instance;
    }*/

    /**
     * 预加载
     */
    /*public static int status=0;
    private static SingletonDemo instance = new SingletonDemo();
    public static SingletonDemo getInstance(){
        return instance;
    }*/

    /**
     *懒加载
     */




    public void pirnt(String s){
        System.out.println("hello," +s);
    }
}