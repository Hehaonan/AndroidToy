package com.android.hhn.javalib.other;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: haonan.he ;<p/>
 * Date: 4/18/21,2:40 PM ;<p/>
 * Description:
 * 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
 * 你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。
 * 示例:
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1];
 * Other: ;
 */
class TwoNumberSum {
    public static void main(String[] args) {
        int[] arrays = {21, 3, 1, 5, 6, 4, 9};
        int target = 12;
        // findTwoSum(arrays, target);
        findTwoSumByMap(arrays, target);
    }

    /**
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     *
     * @param arrays
     * @param target
     *
     * @return
     */
    public static int[] findTwoSum(int[] arrays, int target) {
        for (int i = 0; i < arrays.length; i++) {
            for (int j = i + 1; j < arrays.length && arrays[i] < target; j++) {
                System.out.println(">>> " + arrays[j]);
                if (arrays[j] == target - arrays[i]) {
                    System.out.println(i + "," + j);
                    return new int[]{i, j};
                }
            }
        }
        System.out.println(-1 + "," + -1);
        return new int[]{-1, -1};
    }

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     *
     * @param arrays
     * @param target
     *
     * @return
     */
    public static int[] findTwoSumByMap(int[] arrays, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arrays.length; i++) {
            int complement = target - arrays[i];
            if (map.containsKey(complement)) {
                System.out.println(map.get(complement) + "," + i);
                return new int[]{map.get(complement), i};
            }
            map.put(arrays[i], i);
        }
        System.out.println(-1 + "," + -1);
        return new int[]{-1, -1};
    }


}
