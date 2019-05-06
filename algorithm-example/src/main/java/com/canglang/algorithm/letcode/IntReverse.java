package com.canglang.algorithm.letcode;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/6 0006-12:29
 * @version: 1.0
 * @description:
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *
 * 示例 1:
 *
 * 输入: 123
 * 输出: 321
 *  示例 2:
 *
 * 输入: -123
 * 输出: -321
 * 示例 3:
 *
 * 输入: 120
 * 输出: 21
 * 注意:
 *
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0
 **/
public class IntReverse {
    public static void main(String[] args) {
        int reverse = reverse(-2147483648);
        System.out.println(reverse);
    }

    /**
     * 取模取余逆转
     * @param x
     * @return
     */
    public static int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int a = x % 10;
            x = x / 10;
            if (result > Integer.MAX_VALUE / 10 || result < Integer.MIN_VALUE / 10) {
                return 0;
            }
            result = a + result * 10;
        }
        return result;
    }

    /**
     * 字符串逆转
     * @param x
     * @return
     */
    public static int reverse_2(int x) {
        if (x < -2E32 || x > 2E32) {
            return 0;
        }
        String xStr = Math.abs(x) + "";
        StringBuffer stringBuffer = new StringBuffer();
        boolean flag = false;
        for (int i = xStr.length() - 1; i >= 0; i--) {
            if (xStr.charAt(i) == 0 && !flag) {
                continue;
            }
            flag = true;
            stringBuffer.append(xStr.charAt(i));
        }
        try {
            if (x < 0) {
                x = 0 - Integer.parseInt(stringBuffer.toString());
            } else {
                x = Integer.parseInt(stringBuffer.toString());
            }
        } catch (Exception e) {
            return 0;
        }
        return x;
    }
}
