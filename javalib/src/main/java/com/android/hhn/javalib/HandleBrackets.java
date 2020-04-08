package com.android.hhn.javalib;

import java.util.Stack;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/3/17,5:18 PM ;<p/>
 * Description: 处理成对括号算法题;<p/>
 * Other: ;
 */
public class HandleBrackets {

    public static void main(String[] args) {
        String test = "{()[([]{[]})]}"; //"{([)]}"
        System.out.println(isValid(test));
    }

    private static boolean isValid(String str) {
        Stack<Character> left = new Stack<>();
        for (char c : str.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                left.push(c);
            } else // 字符 c 是右括号
                if (!left.empty() && leftOf(c) == left.peek()) {//取出栈顶的元素查看
                    left.pop();
                } else {
                    // 和最近的左括号不匹配
                    return false;
                }
        }
        // 是否所有的左括号都被匹配了
        return left.empty();
    }

    private static char leftOf(char c) {
        if (c == '}')
            return '{';
        if (c == ')')
            return '(';
        return '[';
    }


}
