package com.android.hhn.javalib.desginpattern.singleton;

import javax.naming.Context;

/**
 * Author: haonan.he ;<p/>
 * Date: 4/2/21,3:40 PM ;<p/>
 * Description: 静态内部类单例;<p/>
 * Other: ;
 */
class StaticInnerClassSingleton {

    public static int mNum;
    public static Context mContext;

    private StaticInnerClassSingleton() {
    }

    private StaticInnerClassSingleton(int num) {
        mNum = num;
    }

    private static class SingletonPatternHolder {
        private static final StaticInnerClassSingleton singletonPattern = new StaticInnerClassSingleton(mNum);
    }

    public static StaticInnerClassSingleton getInstance(int num) {
        mNum = num;
        return SingletonPatternHolder.singletonPattern;
    }

    public static void main(String[] args) {
        System.out.println(StaticInnerClassSingleton.getInstance(8).mNum);
    }
}
