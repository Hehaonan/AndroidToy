package com.android.hhn.javalib;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/4/14,5:59 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class Fibonacci {
    /**
     * 平推方法实现
     */
    public static long fibLoop(int num) {
        if (num < 1 || num > 92)
            return 0;
        long first = 1;
        long second = 1;
        long temp;
        for (int i = 3; i <= num; i++) {
            temp = first;
            first = second;
            second = second + temp;
        }
        return second;
    }

    /**
     * 递归方法实现
     * f(n) = f(n - 1) + f(n - 2)
     * 最高支持 n = 92 ，否则超出 Long.MAX_VALUE
     *
     * @param num n
     *
     * @return f(n)
     */
    public static long fibRec(int num) {
        if (num < 1)
            return 0;
        if (num < 3)
            return 1;
        return fibRec(num - 1) + fibRec(num - 2);
    }

}
