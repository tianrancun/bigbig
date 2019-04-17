package com.bigbig;


import com.bigbig.util.DateTimeUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestDateTime {
    @Test
    public void test() {
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time);
        System.out.println(time.toString());
        System.out.println(time.toLocalDate());
        System.out.println(time.toLocalTime());
        System.out.println(DateTimeUtil.getCurrentDateTimeInUTC());

        //format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");
        System.out.println(time.format(formatter));

        //构造时间
        LocalDateTime startTime = LocalDateTime.of(2018, 1, 1, 20, 31, 20);
        LocalDateTime endTime = LocalDateTime.of(2018, 1, 3, 20, 31, 20);
        //比较时间
        System.out.println(time.isAfter(startTime));
        System.out.println(time.isBefore(endTime));

        //时间运算，相加相减
        System.out.println(time.plusYears(2)); //加2年
        System.out.println(time.plusDays(2)); //加两天
        System.out.println(time.minusYears(2)); //减两年
        System.out.println(time.minusDays(2)); //减两天

        //获取毫秒数(使用Instant)
        System.out.println(time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        //获取秒数(使用Instant)
        System.out.println(time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());

        //字符串转时间
        String dateTimeStr = "2018-07-28 14:11:15";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, df);




    }



}