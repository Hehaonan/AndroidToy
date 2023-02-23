package com.android.hhn.javalib.desginpattern.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Author: haonan.he ;<p/>
 * Date: 2/16/23,3:56 PM ;<p/>
 * Description: 生产者消费者示例;<p/>
 * Other: ;
 */
class ProducerConsumerTest {
    public static void main(String[] args) {
        Factory factory = new Factory();
        Producer producer = new Producer(factory);
        Consumer consumer = new Consumer(factory);
        producer.start();
        consumer.start();
    }
}

class Factory {
    private Queue<Integer> mQueue = new LinkedList<>();
    private static final int MAX = 10;

    public synchronized void produce(int element) throws InterruptedException {
        if (mQueue.size() > MAX) { // 满足某些条件，阻塞生产者
            this.wait();
        }
        mQueue.add(element);//生产产品，添加到队列中
        notify();// 通知消费者消费产品
    }

    public synchronized int consume() throws InterruptedException {
        if (mQueue.size() <= 0) {  // 满足某些条件，阻塞消费者
            this.wait();
        }
        int e = mQueue.poll();//消费产品
        notify();// 通知生产者继续生产
        return e;
    }
}

class Producer extends Thread {
    private Factory mFactory;

    public Producer(Factory factory) {
        this.mFactory = factory;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                mFactory.produce(i);//生产者生产
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer extends Thread {
    private Factory mFactory;

    public Consumer(Factory factory) {
        this.mFactory = factory;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                int e = mFactory.consume();//消费者消费
                System.out.println("Consumer consume=" + e);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
