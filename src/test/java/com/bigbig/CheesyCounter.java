package com.bigbig;



public class CheesyCounter {
    // Employs the cheap read-write lock trick
    // All mutative operations MUST be done with the 'this' lock held

    private volatile int value;

    public int getValue() { return value; }

    public synchronized int increment() {
        return value++;
    }
}