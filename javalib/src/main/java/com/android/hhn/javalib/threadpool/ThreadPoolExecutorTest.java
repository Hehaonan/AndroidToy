package com.android.hhn.javalib.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: haonan.he ;<p/>
 * Date: 2/11/23,5:35 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        // Executors 不要使用
        ExecutorService executorService1 = Executors.newCachedThreadPool(); // 快
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);// 慢
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();// 最慢

        // 核心10个，阻塞队列里能放10个，非核心线程能创建10个，线程池总线程数20个
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20,
                0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10));

        for (int i = 1; i < 100; i++) {
            //executorService1.execute(new MyTask(i));
            threadPoolExecutor.execute(new MyTask(i));// 执行到第31个，报异拒绝异常
            // Exception in thread "main" java.util.concurrent.RejectedExecutionException:
            // at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution
        }
        // 结果：
        // 先执行核心线程任务1-10，再执行非核心线程任务21-30，阻塞队列任务有线程池空闲线程执行(核心或非核心)
        //pool-4-thread-2--->2
        //pool-4-thread-6--->6
        //pool-4-thread-5--->5
        //pool-4-thread-4--->4
        //pool-4-thread-7--->7
        //pool-4-thread-1--->1
        //pool-4-thread-3--->3
        //pool-4-thread-8--->8
        //pool-4-thread-9--->9
        //pool-4-thread-10--->10
        //pool-4-thread-11--->21
        //pool-4-thread-12--->22
        //pool-4-thread-13--->23
        //pool-4-thread-14--->24
        //pool-4-thread-15--->25
        //pool-4-thread-16--->26
        //pool-4-thread-17--->27
        //pool-4-thread-18--->28
        //pool-4-thread-19--->29
        //pool-4-thread-20--->30
        //pool-4-thread-7--->11
        //pool-4-thread-6--->15
        //pool-4-thread-11--->12
        //pool-4-thread-14--->13
        //pool-4-thread-16--->14
        //pool-4-thread-4--->16
        //pool-4-thread-8--->17
        //pool-4-thread-13--->19
        //pool-4-thread-15--->20
        //pool-4-thread-2--->18
    }
}

class MyTask implements Runnable {
    final private int index;

    public MyTask(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "--->" + index);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
