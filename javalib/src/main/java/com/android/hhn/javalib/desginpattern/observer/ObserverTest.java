package com.android.hhn.javalib.desginpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: haonan.he ;<p/>
 * Date: 2/7/23,2:53 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
class ObserverTest {
    public static void main(String[] args) {
        Subject subject = new Subject();
        ItemObserver itemObserver = new ItemObserver();
        subject.addObserver(itemObserver);
        subject.notifyObserver("notify msg");
        subject.remove(itemObserver);
    }
}

class Subject { // 可观察事项
    // 容器
    private List<Observer> container = new ArrayList<>();

    //add
    public void addObserver(Observer observer) {
        container.add(observer);
    }

    //remove
    public void remove(Observer observer) {
        container.remove(observer);
    }

    // 通知更新
    public void notifyObserver(Object object) {
        for (Observer o : container) {
            o.update(object);
        }
    }
}

interface Observer { // 观察者接口
    void update(Object object);
}

class ItemObserver implements Observer { // 具体观察者实现
    @Override
    public void update(Object object) {
        System.out.println("ItemObserver received:" + object);
    }
}