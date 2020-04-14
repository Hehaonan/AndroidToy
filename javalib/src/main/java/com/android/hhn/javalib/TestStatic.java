package com.android.hhn.javalib;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/4/14,5:48 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class TestStatic {
    private static class Base {
        static {
            System.out.println("1");
        }

        private Base() {
            System.out.println("2");
        }
    }

    public static class Test extends Base {
        static {
            System.out.println("3");
        }

        public Test() {
            super();
            System.out.println("4");
        }
    }

    public static void main(String[] args) {
        new Test(); // 1 3 2 4
    }
}
