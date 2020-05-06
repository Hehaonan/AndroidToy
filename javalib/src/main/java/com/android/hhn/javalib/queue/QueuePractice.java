package com.android.hhn.javalib.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/5/6,6:30 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class QueuePractice {

    // 队列实现栈，都用栈的方法
    private static class MyStack {
        Queue<Integer> q = new LinkedList<>();
        int top = 0; // 时刻记录栈顶元素

        /** 添加元素到栈顶 */
        public void push(int x) {
            // 入栈是队列的队尾，是栈的栈顶
            q.offer(x);
            top = x;
        }

        /** 返回栈顶元素 */
        public int peek() {
            return top;
        }

        /** 删除栈顶的元素并返回 */
        public int pop() {
            int size = q.size();
            // 留下2个元素 是为了方便记录 栈顶元素 和 该pop出的元素
            while (size > 2) {
                q.offer(q.poll());// 对头入队尾 循环操作
                size--;
            }
            // 这时队列第一个元素 是栈顶元素
            top = q.peek();
            // 记录完成之后 取出再加入队尾
            q.offer(q.poll());
            return q.poll(); // 最后真正取出栈顶元素
        }

        /** 判断栈是否为空 */
        public boolean isEmpty() {
            return q.isEmpty();
        }

    }

    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        // 队列实际为：3,2,1,
        System.out.println(stack.peek());// 栈顶 3
        System.out.println(stack.pop());// 栈顶弹出 3
        System.out.println(stack.peek());// 栈顶 2
        // 队列实际为：2，1，
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + ",");
        }
    }
}
