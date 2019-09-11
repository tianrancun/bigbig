package com.bigbig.Delay;

import java.util.concurrent.TimeUnit;


public class TimeUntiTest {
    public static void main(String[] args) {
        System.out.println(TimeUnit.DAYS.toHours(1));
        System.out.println(TimeUnit.DAYS.toMinutes(1));
        System.out.println(TimeUnit.DAYS.toSeconds(1));
        System.out.println(TimeUnit.HOURS.convert(1,TimeUnit.DAYS));
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}