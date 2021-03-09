package com.android.hhn.javalib.synchornized;

/**
 * Author: haonan.he ;<p/>
 * Date: 3/9/21,3:46 PM ;<p/>
 * Description: 验证CPU存在乱序执行，使用volatile可以保证有序性和可见性;<p/>
 * Other: ;
 */
class TestDisorder {
    private static int x, y = 0;
    private static int a, b = 0;

    public static void main(String[] args) throws Exception {
        int j = 0;
        for (; ; ) {
            j++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });
            Thread two = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();
            two.start();
            one.join();// 保持顺序执行
            two.join();
            if (x == 0 && y == 0) {
                // 代表 x=b 和 y=a 先于上面的指令执行，出现CPU乱序执行
                System.out.println("第" + j + "次，出现乱序");
                break;
            }
        }
    }
}
