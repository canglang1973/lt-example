package com.canglang.algorithm.letcode;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/6 0006-14:09
 * @version: 1.0
 * @description:
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 *
 * 示例 1:
 *
 * 输入: 121
 * 输出: true
 * 示例 2:
 *
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 *
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 **/
public class IsPalindrome {

    public static void main(String[] args){
        System.out.println(isPalindrome_2(123321));
    }

    public static boolean isPalindrome(int x) {
        if (x<0){
            return false;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String xStr = x+"";
        for (int i=xStr.length()-1;i>=0;i--){
            stringBuffer.append(xStr.charAt(i));
        }
        if (stringBuffer.toString().equals(xStr)){
            return true;
        }
        return false;
    }
    public static boolean isPalindrome_2(int x) {
        if (x<0){
            return false;
        }
        int y = x;
        int result = 0;
        while (y !=0){
            int a = y % 10;
            y = y /10;
            result = a + result * 10;
        }
        if (result==x){
            return true;
        }
        return false;
    }
}
