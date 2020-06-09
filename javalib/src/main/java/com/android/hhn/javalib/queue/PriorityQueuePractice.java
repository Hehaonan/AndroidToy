package com.android.hhn.javalib.queue;

import java.util.Arrays;
import java.util.Comparator;
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
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k, getComparator());
        for (int i : array) {
            if (priorityQueue.size() < k) {
                priorityQueue.offer(i);
            } else {
                // 默认小顶堆 可以找出最大值
                if (priorityQueue.peek() < i) { // 如果是大顶堆 queue.peek > i，入栈规则不一样
                    priorityQueue.poll();//弹出栈顶最小的
                    priorityQueue.offer(i);
                }
            }
        }
        System.out.println(Arrays.toString(priorityQueue.toArray()));
        return priorityQueue.peek() == null ? -1 : priorityQueue.peek();
    }

    /**
     * 自定义 Comparator 接口
     *
     * @return
     */
    private static Comparator<Integer> getComparator() {
        // 默认小顶堆 找最大值 升序排列 o1-o2
        // 大顶堆 找最小值 降序排列 o2-o1
        Comparator<Integer> comparator = new Comparator<Integer>() { // lambda表达式 (o1, o2) -> o1 - o2;
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
        return comparator;
    }

}
