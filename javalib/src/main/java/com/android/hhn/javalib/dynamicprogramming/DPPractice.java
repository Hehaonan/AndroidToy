package com.android.hhn.javalib.dynamicprogramming;

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

    public static void main(String[] args) {
        System.out.println(throughSquareStepsRec(4, 5));
        System.out.println(throughSquareStepsDP(4, 5));
    }
}
