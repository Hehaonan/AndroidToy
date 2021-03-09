package com.android.hhn.javalib.desginpattern.singleton;

/**
 * Author: haonan.he ;<p/>
 * Date: 3/9/21,4:33 PM ;<p/>
 * Description: double check lock单例模式解释;<p/>
 * Other: ;
 */
class DCLSingleton {

    // ④ DCL中需不需要加 volatile修饰？？
    private static volatile DCLSingleton INSTANCE = null;

    private DCLSingleton() {
    }

    // class T {
    //    int num = 5;
    // }
    // 对象创建过程 字节码实现
    //0 new #2 <com/android/hhn/javalib/synchornized/T> // 分配对象堆内存 此时num=0
    //3 dup
    //4 invokespecial #3 <com/android/hhn/javalib/synchornized/T.<init>> // 调用对象构造方法 赋值内部属性 此时num=5
    //7 astore_1 // 将栈中t指向堆内存中的T对象，建立关联
    //8 return
    // public static void main(String[] args) {
    //    T t = new T();
    // }

    @SuppressWarnings("InstantiationOfUtilityClass")
    public static DCLSingleton getInstance() {// double check lock单例
        if (null == INSTANCE) {// ① 第一次校验
            synchronized (DCLSingleton.class) {// ③ 锁，同步代码块，保证线程安全，减少锁力度
                // thread 1执行过①处的判断，上锁的前期,被thread 2抢占锁资源
                // thread 2执行完new代码后，会导致thread 1再次进入同步代码块中，再次创建对象
                if (null == INSTANCE) {// ② 第二次校验，上锁的过程中防止其他线程已经new过单例对象
                    INSTANCE = new DCLSingleton();// ④ 需要使用volatile修饰
                    // 对象的创建过程分主要为三个指令 new、invokespecial、astore_1
                    // invokespecial会调用类的初始化方法，对内部属性进行赋值
                    // 如果thread 1在执行完new指令后，后面的指令被CPU进行指令重排序，先指向堆内存地址，再进行内部变量赋值
                    // 会导致 thread 2 通过第一次校验 得到单例对象，但是内部属性依然是初始值，thread 2会拿到半初始化状态对象
                }
            }
        }
        return INSTANCE;
    }
}
