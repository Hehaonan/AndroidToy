package com.android.hhn.javalib.other;

/**
 * Author: haonan.he ;<p/>
 * Date: 4/20/21,12:04 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class BasicDataTypeTest {

    int num = 10;
    Integer num2 = new Integer(10);
    String str = new String("Hello");
    String str2 = "Hello";
    char[] ch = {'a', 'b', 'c'};

    public static void main(String[] args) {
        String s1 = "HelloWorld";
        String s2 = "Hello" + new String("World");
        String s3 = "HelloWorld";
        System.out.println(s1 == s2);// false
        System.out.println(s1 == s3);// true
        System.out.println(s2 == s3);// false
        System.out.println(s1.equals(s2));// true
        System.out.println(s1.equals(s3));// true

        int x = 100;
        int y = 100;
        Integer a = new Integer(100);
        Integer b = new Integer(100);
        Integer c = 111;
        Integer d = 111;
        System.out.println(x == y); // true 基本类型和包装类型进行对比的时候要向下拆箱，调用xxxx.intValue()
        System.out.println(a.equals(x)); // true
        System.out.println("a==b: " + (a == b)); // false 对象不同
        System.out.println("c==d: " + (c == d)); // true 因为Integer是存在一个缓存区，范围是-128~127,数值在这个范围内的都从缓存区获取，超过这个范围的才会重新创建一个新的对象

        Double d1 = 100.0;
        Double d2 = 100.0;
        Double d3 = new Double(200.0);
        Double d4 = new Double(200.0);
        System.out.println(">> " + (d1 == d2));//false
        System.out.println(">> " + (d3 == d4));//false

        short s = 2;
        // s = s + 1;//报错 int 与 short 不兼容
        s += 1;// 不报错 会自动的向下转型

        String str = "abcdef";
        System.out.println("substring：" + str.substring(3, 3));

        BasicDataTypeTest st = new BasicDataTypeTest();
        st.change(st.num, st.num2, st.str, st.str2, st.ch);
        System.out.println(st.num);// 10 基本数据类型
        System.out.println(st.num2);// 10，Integer 引用类型 按照基本数据类型操作
        System.out.println(st.str);// Hello，String 引用类型 按照char类型操作
        System.out.println(st.str2);// Hello，String 引用类型 按照char类型操作
        System.out.println(st.ch[0] + "," + st.ch[1] + "," + st.ch[2]);// b,b,c 引用类型，修改调用方的值
        // 基础数据类型（int，char，……）传值，对象类型（Object，数组，容器……）传引用
        // 传值方式，传递的是值的副本。方法中对副本的修改，不会影响到调用方。
        // 传引用方式，传递的是引用的副本。此时，形参和实参指向同一个内存地址。对引用副本所指向的对象的修改，会影响到调用方。
        // Integer是int包装类、Float是float的包装类等。对包装类的值操作实际上都是通过对其对应的基本类型操作而实现的
        // String就相当于是char[]的包装类。包装类的特质之一就是在对其值进行操作时会体现出其对应的基本类型的性质。


        String str2 = "abc";
        String substring = "abc123".substring(0, 3).intern(); // abc
        System.out.println("substrng:" + substring);
        System.out.println(str2 == substring);

        System.out.println(changeSf());//3
    }

    public static int changeSf() throws NumberFormatException {

        // 1、throw
        //    a)throw是写在方法体中的，代表条件满足后会抛出一个异常出来
        //    b)如果在方法体中抛出一个异常，在这个方法上面需不需要抛出，取决于抛出的是那种异常
        //        a)运行时异常 --》不要抛出或者捕获
        //        b)检查行异常---》要么抛出，要么捕获
        //    c)在方法体抛出某个中异常
        // 2、throws
        //    a)是在方法的声明上面抛出异常，这个方法的被调用者必须要捕获或者抛出异常
        //    b)告诉方法被调用者，这个方法有可能有回出现异常。

        try {
            int i = 10 / 0;
            throw new NumberFormatException();
        } catch (Exception e) {
            System.out.println("22");// catch会处理
            return 2;
        } finally {
            System.out.println("333"); // 只return finally
            return 3;
        }
        // NoClassDefFoundError 和 ClassNoFoundException 区别
        // 1、一个是错误，一个是异常
        // 2、ClassNoFoundException
        //    a)通过反射加载的时候类找不到就会抛出这个异常
        // 3.NoClassDefFoundError --》框架中会看到
        //    a)在编译的时候类是在的，但是运行的时候没有了。
        //    b)出现最多的就是在框架里面，确实依赖或者版本不对

    }

    public void change(int num, Integer num2, String str, String str2, char[] ch) {
        num = 100;
        num2 = 100;
        str = "HelloWorld";
        str = "HelloWorld2";
        ch[0] = 'b';
        ch[1] = 'b';
    }
}
