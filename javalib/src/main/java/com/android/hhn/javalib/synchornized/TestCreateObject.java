package com.android.hhn.javalib.synchornized;

/**
 * Author: haonan.he ;<p/>
 * Date: 3/9/21,4:11 PM ;<p/>
 * Description: 对象创建过程;<p/>
 * Other: ;
 */
class TestCreateObject {
    // 对象创建过程 字节码实现
    //0 new #2 <com/android/hhn/javalib/synchornized/T> // 分配对象堆内存 此时num=0
    //3 dup
    //4 invokespecial #3 <com/android/hhn/javalib/synchornized/T.<init>> // 调用对象构造方法 赋值内部属性 此时num=5
    //7 astore_1 // 将栈中t指向堆内存中的T对象，建立关联
    //8 return
    public static void main(String[] args) {
        T t = new T();
    }
}
