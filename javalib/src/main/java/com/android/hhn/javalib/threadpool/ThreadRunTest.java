package com.android.hhn.javalib.threadpool;

/**
 * Author: haonan.he ;<p/>
 * Date: 2/10/23,11:23 PM ;<p/>
 * Description: run()与start()方法的区别，理解线程池复用时调用worker.run()的逻辑;<p/>
 * Other: ;
 */
class ThreadRunTest {
    public static void main(String[] args) {
        new MyThread("thread-1").run();// 方法级别调用：当前demo是在主线程运行
        new MyThread("thread-2").start();// 线程级别调用：启动一个新线程执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 谁调用run()方法，当前的任务体就会在调用者的线程内执行
                // 线程池的复用也是使用该逻辑
                new MyThread("thread-4").run();
            }
        }, "thread-3").start();

        // 结果
        //currentThread = main（主线程）,thread name = thread-1
        //currentThread = Thread-1(新线程，自动命名),thread name = thread-2
        //currentThread = thread-3(主动命名),thread name = thread-4
    }
}

class MyThread extends Thread {

    private String mName;

    public MyThread(String name) {
        mName = name;
    }

    @Override
    public void run() {
        System.out.println("currentThread = " + Thread.currentThread().getName() + ",thread name = " + mName);
    }
}