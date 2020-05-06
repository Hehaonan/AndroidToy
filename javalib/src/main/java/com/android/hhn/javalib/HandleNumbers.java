package com.android.hhn.javalib;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/5/6,5:26 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class HandleNumbers {


    // 核心思想
    //如果一个整数不是0，那么它的二进制一定至少有一位为1。如果把这个整数减一，会把右边数第一个1变为0，并且把这个1右边的所有0变为1。接着把这两个数做与运算，那么除了这个1以及这个1右边的所有数会变为0，其余位数不变。
    //
    //举个栗子，110101000，减一之后为：110100111，做与运算之后为：110100000
    //
    //这个数就变小了，在重复上述步骤：
    //减一之后为：110011111，做与运算之后为：110000000
    //减一之后为：101111111，做与运算之后为：100000000
    //减一之后为：011111111，做与运算之后为：000000000
    //
    //所以每一步都会从最右开始，把一个1变成0，有几个1就会做几次，直到这个数变成0为止。

    // 与运算符 “&” 表示
    // 两个操作数中位都为1，结果才为1，否则结果为0

    // 或运算符 “|” 表示
    // 两个位只要有一个为1，那么结果就是1，否则就为0，

    // 非运算符 “~”表示
    // 如果位为0，结果是1，如果位为1，结果是0，

    /**
     * 二进制中查找位数是1的个数
     *
     * @param num
     *
     * @return
     */
    private static int binaryFind1Count(int num) {
        int temp;
        int answer = 0;
        while (num != 0) {
            temp = num - 1;
            num = num & temp;
            answer++;
        }
        return answer;
    }

    /**
     * 二进制中查找位数是1的个数，将二进制转为字符串
     *
     * @param num
     *
     * @return
     */
    private static int binaryFind1Count2(int num) {
        int count = 0;
        String str = Integer.toBinaryString(num);
        for (int j = 0; j < str.length(); j++) {
            if (str.charAt(j) == '1') {
                count++;
            }
        }
        return count;
    }


    private static int stringChangeToInt(String s) {
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            n = 10 * n + (c - '0');
        }
        // 因为变量c是一个 ASCII 码，如果不加括号就会先加后减，想象一下n如果接近 INT_MAX，就会溢出。
        return n;
    }

    public static void main(String[] args) {
        System.out.println(binaryFind1Count(5));
        System.out.println(binaryFind1Count2(5));
        System.out.println(stringChangeToInt("465") + 1);
    }

}
