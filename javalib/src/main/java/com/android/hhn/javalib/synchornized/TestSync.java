package com.android.hhn.javalib.synchornized;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/5/14,8:04 PM ;<p/>
 * Description: 死锁实现 和 ReentrantLock实现双线程交替打印1、2;<p/>
 * Other: ;
 */
public class TestSync {

    static Object object1 = new Object(); //创建静态对象object1
    static Object object2 = new Object(); //创建静态对象object2

    private static void testDiedLock() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    while (i < 2) { //未避免执行陷入死循环，这里设置了最多循环2次
                        synchronized (object1) {
                            System.out.println(Thread.currentThread().getName() + "上锁了object1");
                            Thread.sleep(1000);
                            synchronized (object2) {
                                System.out.println(Thread.currentThread().getName() + "上锁了object2");
                            }
                        }
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) { //这里就不改了，正好和上面做个对比
                        synchronized (object2) {
                            System.out.println(Thread.currentThread().getName() + "锁住了object2");
                            Thread.sleep(1000);
                            synchronized (object1) {
                                System.out.println(Thread.currentThread().getName() + "锁住了object1");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    //ReentrantLock支持公平锁和非公平锁
    private static Lock lock = new ReentrantLock();
    // 使用Condition的话,可以使用不同的等待队列,只需要使用lock.newCondition()即可定义一个Condition对象,
    // 每一个Condition对象上都会有一个等待队列(底层使用AQS),
    // 调用某个Condition对象的await()方法,就可以把当前线程加入到这个Condition对象的等待队列上
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    // await 标志
    private static volatile boolean flag = false;

    public static void main(String[] args) {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                printA(10);
            }
        }, "Thread-A");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                printB(10);
            }
        }, "Thread-B");
        threadA.start();
        threadB.start();
    }

    private static void printA(int count) {
        for (int i = 1; i <= count; i++) {
            lock.lock();
            try {
                if (flag) {
                    conditionA.await();// 进入等待状态
                }
                System.out.println(Thread.currentThread().getName() + ": >>> 1");
                flag = true;
                conditionB.signal(); //唤醒线程B
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private static void printB(int count) {
        for (int i = 1; i <= count; i++) {
            lock.lock();
            try {
                if (!flag) {
                    conditionB.await();
                }
                System.out.println(Thread.currentThread().getName() + ": >>> 2");
                flag = false;
                conditionA.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
