package com.android.hhn.javalib.thread;

import java.util.concurrent.TimeUnit;

/**
 * Author: haonan.he ;<p/>
 * Date: 2/13/23,7:18 PM ;<p/>
 * Description: 线程控制示例;<p/>
 * Other: ;
 */
class ThreadControlTest {
    public static void main(String[] args) {
        testJoin();
        testInterrupt();
    }

    private static void testJoin() {
        System.out.println("主线程开始");
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("子线程：i=" + i);
            }
        }, "Thread-A");
        thread.start();
        try {
            thread.join();// 插入主线程，子线程执行完在执行主线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");
    }

    private static void testInterrupt() {
        MyThread myThread = new MyThread();
        myThread.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myThread.interrupt();
        //--1--
        //false
        //java.lang.InterruptedException: sleep interrupted
        //	at java.lang.Thread.sleep(Native Method)
        //	at java.lang.Thread.sleep(Thread.java:340)
        //	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        //	at com.android.hhn.javalib.thread.MyThread.run(ThreadControlTest.java:51)
        //--3--
        //false
        //--4--
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("--1--");
            System.out.println(this.isInterrupted());// false
            TimeUnit.SECONDS.sleep(5);
            System.out.println("--2--");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("--3--");
            System.out.println(this.isInterrupted());// false
        }
        System.out.println("--4--");
    }
}
