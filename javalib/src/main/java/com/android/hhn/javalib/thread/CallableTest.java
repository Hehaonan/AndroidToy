package com.android.hhn.javalib.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Author: haonan.he ;<p/>
 * Date: 2/13/23,6:07 PM ;<p/>
 * Description: Callable示例;<p/>
 * Other: ;
 */
class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(3000);// 阻塞任务
                return new Random().nextInt(10);
            }
        };
        //FutureTask同时实现了Runnable, Future接口。既可以作为Runnable被线程执行,又可以作为Future得到Callable的返回值
        FutureTask task = new FutureTask<>(callable);
        Thread thread = new Thread(task);
        thread.setPriority(1000);
        thread.setDaemon(true);
        thread.start();

        System.out.println("task.isDone=" + task.isDone());
        System.out.println("task.result=" + task.get());// get方法会阻塞当前线程，需要注意
        System.out.println("task.isDone=" + task.isDone());//get执行完isDone为true
    }
}
