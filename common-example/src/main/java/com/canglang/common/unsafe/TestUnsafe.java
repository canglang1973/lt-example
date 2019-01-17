package com.canglang.common.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author leitao.
 * @category
 * @time: 2018/12/3 0003-10:59
 * @version: 1.0
 * @description:
 **/
public class TestUnsafe {

    public static void main(String[] args) {
        try {
            //通过java反射实例化Unsafe
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            Unsafe u = (Unsafe) unsafeField.get(null);
            Class<?> tk = A.class;
            A a = new A();

            // 下面这些偏移量实际都可以用常量来表示，在一次程序运行过程中它们是不变的
            long offset = u.objectFieldOffset(tk.getDeclaredField("x"));
            System.err.println(u.getInt(a, offset)); // 读取x

            long finalOffset = u.objectFieldOffset(tk.getDeclaredField("finalX"));
            System.err.println(u.getInt(a, finalOffset)); // 读取finalX 233
            u.putInt(a, finalOffset, 100); // 可以用来更改final的值
            System.err.println(u.getInt(a, finalOffset)); // 读取finalX 100

            Object staticBase = u.staticFieldBase(tk.getDeclaredField("staticX")); // 这里就是返回 A.class，跟逻辑是一致
            long staticOffset = u.staticFieldOffset(tk.getDeclaredField("staticX"));
            System.err.println(u.getInt(staticBase, staticOffset));

            long staticFinalOffset = u.staticFieldOffset(tk.getDeclaredField("staticFinalX"));
            System.err.println(u.getInt(staticBase, staticFinalOffset));

            System.err.println(A.class == staticBase); // true
            System.err.println(u.getInt(A.class, staticOffset)); // 等价于u.getInt(staticBase, staticOffset)
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

class A {
    private int x = 123456;
    private final int finalX = 233;
    private static int staticX = 987654;
    private static final int staticFinalX = 666;
}

