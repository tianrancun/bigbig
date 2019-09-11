package com.bigbig.Delay;


import ch.qos.logback.core.util.TimeUtil;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import java.util.concurrent.*;

public class DelayTest {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new javax.swing.Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hehe");
            }
        });

        timer.start();
        TimeUnit.SECONDS.sleep(10L);
        timer.stop();
    }

    @Test
    public void test1() {
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("delay");
            }
        },1000);
    }

    @Test
    public void test2() {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
        schedule.schedule(new Runnable() {
            @Override
            public void run() {

            }
        },1L,TimeUnit.MINUTES);
    }
}