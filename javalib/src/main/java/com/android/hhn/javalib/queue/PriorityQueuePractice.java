package com.android.hhn.javalib.queue;

import java.util.PriorityQueue;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/6/9,6:19 PM ;<p/>
 * Description: 优先队列练习;<p/>
 * Other: ;
 */
public class PriorityQueuePractice {

    public static void main(String[] args) {
        int[] array = {2, 9, 1, 7, 4, 5, 3, 8, 6, 11, 7, 24, 35, 3, 8, 16};
        int k = 3;
        int kthLargest = getKthLargest(array, k);
        System.out.println("第" + k + "大的数：" + kthLargest);
    }

    private static int getKthLargest(int[] array, int k) {
        // java默认是小顶堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k);
        for (int i : array) {
            if (priorityQueue.size() < k) {
                priorityQueue.offer(i);
            } else {
                if (priorityQueue.peek() < i) { // 每个元素和对顶比较
                    priorityQueue.poll();//弹出栈顶最小的
                    priorityQueue.offer(i);
                }
            }
        }
        return priorityQueue.peek() == null ? -1 : priorityQueue.peek();
    }


}
