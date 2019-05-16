package com.canglang.algorithm.easy;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/16 0016-9:12
 * @version: 1.0
 * @description: 位运算
 **/
public class BitOperation {


    public static void main(String[] args) {
//        isOddNumber(0);
//        swapNum(1, 4);
//        findOnceNum(new int[]{1, 2, 1, 2, 3, 3, 4, 5, 5, 6, 6});
//        pow(3);
        findN(15);
    }

    /**
     * 找出不大于N的最大的2的幂指数
     */
    public static void findN(int n) {
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;//整型一般是32位,上面我是假设8位
        System.out.println("找出不大于N的最大的2的幂指数:" + ((n + 1) >> 1));
    }

    /**
     * 使用位运算计算2的n次方
     * 时间复杂度O(logn)
     *
     * @param n
     */
    public static void pow(int n) {
        int m = n;
        int sum = 1;
        int temp = 2;
        while (n != 0) {
            if ((n & 1) == 1) {
                sum *= temp;
            }
            temp *= temp;
            n = n >> 1;
        }
        System.out.println("2的" + m + "次方是:" + sum);
    }

    /**
     * 使用位运算找出一个列表中只出现过1次的数字,其余数字均出现2次
     *
     * @param arr
     */
    public static void findOnceNum(int[] arr) {
        int temp = arr[0];
        for (int i = 1; i < arr.length; i++) {
            temp ^= arr[i];
        }
        System.out.println("只出现过一次的数字是:" + temp);
    }

    /**
     * 使用位运算交换两个数的值
     *
     * @param x
     * @param y
     */
    public static void swapNum(int x, int y) {
        System.out.println("x=" + x + ";y=" + y);
        x = x ^ y;
        y = x ^ y;
        x = x ^ y;
        System.out.println("x=" + x + ";y=" + y);
    }

    public static void isOddNumber(int x) {
        //方法1
        if (x % 2 == 1) {
            System.out.println("方法1判断是奇数!");
        }
        //方法2
        if ((x & 1) == 1) {
            System.out.println("方法2判断是奇数!");
        }
        System.out.println(x + "是偶数");
    }
}
