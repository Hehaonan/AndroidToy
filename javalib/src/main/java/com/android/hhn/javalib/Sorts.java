package com.android.hhn.javalib;

import java.util.Arrays;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/3/15,5:18 PM ;<p/>
 * Description: 排序;<p/>
 * Other: ;
 */
public class Sorts {

    private static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
            if (i == array.length - 1) {
                System.out.print("\n");
            }
        }
    }

    private static void printStep(int step, int[] array) {
        System.out.println("第" + (step) + "次：");
        printArray(array);
    }

    private static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {   // 这里需要-1
                if (array[j] > array[j + 1]) { // 前者大于后者，更小的冒出向前，更大的下沉向后
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            printStep(i, array);
            //第1次：
            //2,1,7,4,5,3,8,6,9,
            //第2次：
            //1,2,4,5,3,7,6,8,9,
            //第3次：
            //1,2,4,3,5,6,7,8,9,
            //第4次：
            //1,2,3,4,5,6,7,8,9,
            //第5次：
            //1,2,3,4,5,6,7,8,9,
            //第6次：
            //1,2,3,4,5,6,7,8,9,
            //第7次：
            //1,2,3,4,5,6,7,8,9,
            //第8次：
            //1,2,3,4,5,6,7,8,9,
            //第9次：
            //1,2,3,4,5,6,7,8,9,
        }
    }

    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int minIndex = i; // 记录最小的位置
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIndex])
                    minIndex = j;//找到最小的数，将最小数的索引保存
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;

            printStep(i, array);
            //第1次：
            //1,9,2,7,4,5,3,8,6,
            //第2次：
            //1,2,9,7,4,5,3,8,6,
            //第3次：
            //1,2,3,7,4,5,9,8,6,
            //第4次：
            //1,2,3,4,7,5,9,8,6,
            //第5次：
            //1,2,3,4,5,7,9,8,6,
            //第6次：
            //1,2,3,4,5,6,9,8,7,
            //第7次：
            //1,2,3,4,5,6,7,8,9,
            //第8次：
            //1,2,3,4,5,6,7,8,9,
            //第9次：
            //1,2,3,4,5,6,7,8,9
        }
    }

    private static int step = 1;

    private static int[] quickSort(int[] array, int start, int end) {
        int pivot = array[start];
        //System.out.println("======");
        System.out.println("pivot：" + pivot);
        int i = start;
        int j = end;
        //System.out.println("start: " + start + ", end: " + end);
        while (i < j) {
            while ((i < j) && (array[j] > pivot)) {
                j--;
                //System.out.println("j>>>" + j);
            }
            while ((i < j) && (array[i] < pivot)) {
                i++;
                //System.out.println("i >>>" + i);
            }
            if ((array[i] == array[j]) && (i < j)) {
                i++;
                //System.out.println("i 2 >>>" + i);
            } else {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                //System.out.println("change:");
                //printStep(step, array);
            }
        }
        if (i - 1 > start) {
            printStep(step++, array);
            array = quickSort(array, start, i - 1);
        }
        if (j + 1 < end) {
            printStep(step++, array);
            array = quickSort(array, j + 1, end);
        }
        return (array);

        //pivot：2
        //第1次：
        //1,2,9,7,4,5,3,8,6,
        //pivot：9
        //第2次：
        //1,2,6,7,4,5,3,8,9,
        //pivot：6
        //第3次：
        //1,2,3,5,4,6,7,8,9,
        //pivot：3
        //第4次：
        //1,2,3,5,4,6,7,8,9,
        //pivot：5
        //第5次：
        //1,2,3,4,5,6,7,8,9,

    }

    public static void main(String[] args) {
        int[] array = {2, 9, 1, 7, 4, 5, 3, 8, 6};
        //bubbleSort(array);
        //selectionSort(array);
        //quickSort(array, 0, array.length - 1);
        // insertionSort(array);
        // shellSort(array);
        array = mergeSort(array);
        System.out.println("\n排序结果：");
        printArray(array);
    }

    private static void insertionSort(int[] array) {
        int currentValue;//当前值
        for (int i = 0; i < array.length - 1; i++) {
            currentValue = array[i + 1];// 默认从第二个数排序
            int preIndex = i; // 记录第一位的指针
            while (preIndex >= 0 && currentValue < array[preIndex]) { //当前值比前一位还要小，说明需要前插
                array[preIndex + 1] = array[preIndex]; // 前一位直接覆盖后一位，相当于大数后移一位
                preIndex--; // 前一位的指针-1 且 保证大于0
            }
            array[preIndex + 1] = currentValue; // 当前值向最前位置覆盖
            printStep(i, array);
        }
        //第1次：
        //2,9,1,7,4,5,3,8,6,
        //第2次：
        //1,2,9,7,4,5,3,8,6,
        //第3次：
        //1,2,7,9,4,5,3,8,6,
        //第4次：
        //1,2,4,7,9,5,3,8,6,
        //第5次：
        //1,2,4,5,7,9,3,8,6,
        //第6次：
        //1,2,3,4,5,7,9,8,6,
        //第7次：
        //1,2,3,4,5,7,8,9,6,
        //第8次：
        //1,2,3,4,5,6,7,8,9,
    }

    private static void shellSort(int[] array) {
        int len = array.length;
        int temp, gap = len / 2;
        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                temp = array[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && array[preIndex] > temp) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp;
                printStep(i, array);
            }
            gap /= 2;
        }
    }

    private static int[] mergeSort(int[] array) {
        if (array.length < 2) {
            return array;
        }
        int mid = array.length / 2;

        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        printStep(-111, array);
        return merge(mergeSort(left), mergeSort(right)); // 递归的方法，最小拆分到1位或者两位的数组
    }

    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     */
    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length)
                result[index] = right[j++];
            else if (j >= right.length)
                result[index] = left[i++];
            else if (left[i] > right[j])
                result[index] = right[j++];
            else
                result[index] = left[i++];
        }
        printStep(-222, result);
        return result;
    }

}
