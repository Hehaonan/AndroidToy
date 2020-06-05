package com.android.hhn.javalib;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/5/14,8:04 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class TestSync2 {

    private static final Object mObject = new Object();

    public static void main(String[] args) {
        new Thread(new Runnable() { // 为了更好地理解、展示，没有合并Runnable代码
            @Override
            public void run() {
                try {
                    synchronized (mObject) {
                        for (int i = 1; i <= 20; i++) {
                            // 先执行对应逻辑
                            System.out.println(Thread.currentThread().getName() + " ---> " + i);
                            Thread.sleep(1000);
                            if (i % 2 == 0) { // 每过两个数 Thread B 执行
                                //唤醒其他等待在 mObject 上的锁
                                mObject.notify();
                                if (i != 20) { // 结束时 不需要要在wait() 否则会没人唤醒
                                    mObject.wait();
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (mObject) {
                        for (int i = 1; i <= 10; i++) {
                            System.out.println(Thread.currentThread().getName() + " ---> B");
                            Thread.sleep(1000);
                            //唤醒其他等待在 mObject 上的锁
                            mObject.notify();
                            if (i != 10) {  // 结束时 不需要要在wait() 否则会没人唤醒
                                mObject.wait();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-B").start();
    }
}
