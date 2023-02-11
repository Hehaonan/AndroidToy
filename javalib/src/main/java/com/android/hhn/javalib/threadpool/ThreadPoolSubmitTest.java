package com.android.hhn.javalib.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: haonan.he ;<p/>
 * Date: 2/11/23,5:35 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
class ThreadPoolSubmitTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 核心1个，阻塞队列里能放1个，非核心线程能创建1个，线程池总线程数2个
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 2,
                0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1));
        threadPoolExecutor.shutdownNow();
        threadPoolExecutor.shutdown();

        threadPoolExecutor.execute(() -> {
            throw new RuntimeException("execute RuntimeException");
        });// 线程池的线程中打印
        // Exception in thread "pool-1-thread-1" java.lang.RuntimeException: execute RuntimeException
        //	at com.android.hhn.javalib.threadpool.ThreadPoolSubmitTest.lambda$main$0(ThreadPoolSubmitTest.java:23)
        //	at com.android.hhn.javalib.threadpool.ThreadPoolSubmitTest$$Lambda$1/791452441.run(Unknown Source)
        //	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
        //	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
        //	at java.lang.Thread.run(Thread.java:745)

        // 核心1个，阻塞队列里能放1个，非核心线程能创建1个，线程池总线程数2个
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(
                1, 2,
                0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1));
        Future<String> submit = threadPoolExecutor2.submit(() -> {
            throw new RuntimeException("submit RuntimeException");
        });
        submit.get();// 调用线程打印
        // Exception in thread "main" java.util.concurrent.ExecutionException: java.lang.RuntimeException: submit RuntimeException
        //	at java.util.concurrent.FutureTask.report(FutureTask.java:122)
        //	at java.util.concurrent.FutureTask.get(FutureTask.java:192)
        //	at com.android.hhn.javalib.threadpool.ThreadPoolSubmitTest.main(ThreadPoolSubmitTest.java:35)
        //Caused by: java.lang.RuntimeException: submit RuntimeException
    }
}

