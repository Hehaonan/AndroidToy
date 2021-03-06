package com.android.hhn.javalib.synchornized;

import java.util.concurrent.CountDownLatch;

/**
 * Author: haonan.he ;<p/>
 * Date: 3/9/21,5:58 PM ;<p/>
 * Description: 缓存行伪共享;<p/>
 * Other: ;
 */
class TestCacheLine {
    public static long COUNT = 1_0000_0000L;

    private static class T {
        public volatile long x = 0L;
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < COUNT; i++) {
                    arr[0].x = i;
                }
                latch.countDown();
            }
        });
        Thread two = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < COUNT; i++) {
                    arr[1].x = i;
                }
                latch.countDown();
            }
        });
        final long start = System.nanoTime();
        one.start();
        two.start();
        latch.await();
        System.out.println((System.nanoTime() - start) / 100_0000);
    }
}
