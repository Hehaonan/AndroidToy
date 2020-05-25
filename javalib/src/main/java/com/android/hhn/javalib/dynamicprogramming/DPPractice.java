package com.android.hhn.javalib.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/5/14,6:29 PM ;<p/>
 * Description: 动态规划问题;<p/>
 * Other: ;
 */
public class DPPractice {

    /**
     * 走方格问题，递归实现
     *
     * @param n 边长
     * @param m 边长
     *
     * @return
     */
    private static int throughSquareStepsRec(int n, int m) {
        if (n == 1 || m == 1)
            return 1;
        return throughSquareStepsRec(n - 1, m) + throughSquareStepsRec(n, m - 1);
    }

    /**
     * 走方格问题，DP实现
     *
     * @param n 边长
     * @param m 边长
     *
     * @return
     */
    private static int throughSquareStepsDP(int n, int m) {
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; ++i) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < m; ++i) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < m; ++j) {
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
            }
        }
        return dp[n - 1][m - 1];
    }

    /**
     * 零钱兑换问题
     *
     * @param coins
     * @param amount
     *
     * @return
     */
    private static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount);
        dp[0] = 0;
        System.out.println(Arrays.toString(dp));
        for (int i = 1; i <= amount; i++) { // 每个金额
            for (int j = 0; j < coins.length; j++) { // 面值
                if (i - coins[j] >= 0) {
                    dp[i] = Math.min(dp[i - coins[j]] + 1, dp[i]);
                }
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[amount] == amount ? -1 : dp[amount];
    }

    // 递推推到过程：
    //    recursion(int level, int pos, arrays[][]) {
    //        if (level == arrays.size - 1) {
    //            return arrays[level][pos] //返回倒数第二层的数值 终止递归
    //        }
    //        int left = recursion(level + 1, pos, arrays) // 层级+1
    //        int right = recursion(level + 1, pos + 1, arrays)
    //        return Min(cur, next) + arrays[level][pos]// +最后一层的值
    //    }

    /**
     * 三角形二维数组，寻找最小路径，递归法
     *
     * @param triangle
     *
     * @return
     */
    private static int findMinPathRec(List<List<Integer>> triangle) {
        return recursion(0, 0, triangle);
    }

    private static int recursion(int level, int c, List<List<Integer>> triangle) {
        if (level == triangle.size() - 1) {
            return triangle.get(level).get(c);
        }
        int left = recursion(level + 1, c, triangle);
        int right = recursion(level + 1, c + 1, triangle);
        return Math.min(left, right) + triangle.get(level).get(c);
    }

    /**
     * 三角形二维数组，寻找最小路径，DP法
     *
     * @param triangle
     *
     * @return
     */
    private static int findMinPathDP(List<List<Integer>> triangle) {
        if (null == triangle || triangle.isEmpty()) {
            System.out.println("参数不合法");
            return -1;
        }
        int row = triangle.size(); // 原始行数
        int[][] dp = new int[row + 1][row + 1]; // 二维数组需要行、列+1，因为要处理i、j+1
        // 优化：可以只需要一维数组记录最值，二维数组方便推到DP公式
        for (int i = triangle.size() - 1; i >= 0; i--) {
            List<Integer> rows = triangle.get(i);
            for (int j = 0; j < rows.size(); j++) {
                // DP公式
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + rows.get(j); // rows.get(j)代表自身的值
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        //        System.out.println(throughSquareStepsRec(4, 5));
        //        System.out.println(throughSquareStepsDP(4, 5));
        //        int[] coins = {1, 2, 5};
        //        System.out.println(coinChange(coins, 20));

        List<Integer> list1 = new ArrayList<>();
        list1.add(2);
        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(4);
        List<Integer> list3 = new ArrayList<>();
        list3.add(6);
        list3.add(5);
        list3.add(7);
        List<Integer> list4 = new ArrayList<>();
        list4.add(4);
        list4.add(1);
        list4.add(8);
        list4.add(3);
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(list1);
        triangle.add(list2);
        triangle.add(list3);
        triangle.add(list4);
        System.out.println(findMinPathRec(triangle));
        System.out.println(findMinPathDP(triangle));
    }
}
