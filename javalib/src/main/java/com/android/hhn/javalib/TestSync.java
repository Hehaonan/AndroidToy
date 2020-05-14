package com.android.hhn.javalib;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/5/14,8:04 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class TestSync {

    static Object object1 = new Object(); //创建静态对象object1
    static Object object2 = new Object(); //创建静态对象object2

    public static void main(String[] args) {
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

}
