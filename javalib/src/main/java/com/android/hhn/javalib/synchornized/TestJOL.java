package com.android.hhn.javalib.synchornized;

import org.openjdk.jol.info.ClassLayout;

/**
 * Author: haonan.he ;<p/>
 * Date: 3/2/21,5:22 PM ;<p/>
 * Description: 使用ClassLayout观察对象内存分布;<p/>
 * Other:
 * JOL:https://github.com/openjdk/jol
 * JOL Sample:https://hg.openjdk.java.net/code-tools/jol/file/tip/jol-samples/src/main/java/org/openjdk/jol/samples/
 * ;
 */
class TestJOL {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
    // 未上锁对象：
    // java.lang.Object object internals:
    // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
    //      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
    //      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
    //      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
    //     12     4        (loss due to the next object alignment) // 对齐损失
    // Instance size: 16 bytes // new一个对象默认16个字节
    // Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

    // 上锁对象:
    // java.lang.Object object internals:
    // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
    //      0     4        (object header)                           90 f9 ce 01 (10010000 00111001 11010001 00001010) (181483920)
    //      4     4        (object header)                           00 70 00 00 (00000000 01110000 00000000 00000000) (28672)
    //      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
    //     12     4        (loss due to the next object alignment)
    // Instance size: 16 bytes
    // Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
}
