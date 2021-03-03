package com.android.hhn.javalib.synchornized;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Author: haonan.he ;<p/>
 * Date: 3/2/21,3:59 PM ;<p/>
 * Description: 使用CAS;<p/>
 * Other: ;
 */
class TestCAS {

    private static int n = 0;
    private static AtomicInteger m = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread[] threads = new Thread[100];
        CountDownLatch countDown = new CountDownLatch(threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    m.incrementAndGet();// CAS用法 ：compareAndSwapInt
                    n++;// 不加锁 n的值多线程修改会出错
                }
            });
            countDown.countDown();
        }
        Arrays.stream(threads).forEach(thread -> thread.start());
        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m);
        System.out.println(n);
    }
}

