package com.android.hhn.javalib.stack;

import java.util.Stack;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/5/6,6:05 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class StackPractice {

    // 双栈实现队列，都用队列的方法
    private static class MyQueue {

        private Stack<Integer> s1, s2;

        public MyQueue() {
            s1 = new Stack<>();
            s2 = new Stack<>();
        }

        public void offer(int x) {
            s1.push(x);
        }

        /** 取出队列头元素并删除 */
        public Integer poll() {
            peek();
            return s2.pop();
        }

        /** 取出队列头元素 */
        public Integer peek() {
            if (s2.isEmpty()) {
                // 把 s1 元素压入 s2
                while (!s1.isEmpty()) {
                    s2.push(s1.pop());
                }
            }
            return s2.peek();
        }

        /** 判断队列是否为空 */
        public boolean isEmpty() {
            return s1.isEmpty() && s2.isEmpty();
        }

    }

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.poll();
        queue.offer(4);
        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + ",");
        }
    }
}
