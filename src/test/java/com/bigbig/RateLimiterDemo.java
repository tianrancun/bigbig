package com.bigbig;


import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterDemo {
    RateLimiter rateLimiter= RateLimiter.create(2);
}