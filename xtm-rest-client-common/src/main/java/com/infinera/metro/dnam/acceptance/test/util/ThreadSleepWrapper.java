package com.infinera.metro.dnam.acceptance.test.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadSleepWrapper {
    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
