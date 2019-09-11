package com.bigbig.Delay;


import java.time.LocalDateTime;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Student implements Runnable, Delayed {
    private String studentName;
    private LocalDateTime workTime;
    private LocalDateTime subTime;

    @Override
    public long getDelay(TimeUnit unit) {
        return TimeUnit.NANOSECONDS.convert(subTime.compareTo(subTime),TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed student) {
        Student s = (Student) student;
        return 0;
    }

    @Override
    public void run() {

    }
}