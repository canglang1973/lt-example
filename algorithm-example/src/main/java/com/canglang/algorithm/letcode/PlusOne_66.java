package com.canglang.algorithm.letcode;

import java.util.Arrays;

/**
 * @author leitao.
 * @time: 2022/1/17 0017-13:14
 * @description:
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 *
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 *
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 * 示例 2：
 *
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 * 示例 3：
 *
 * 输入：digits = [0]
 * 输出：[1]
 *  
 *
 * 提示：
 *
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/plus-one
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 **/
public class PlusOne_66 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(plusOne(new int[]{9, 9, 9})));
    }

    public static int[] plusOne(int[] digits) {
        int[] result = new int[digits.length + 1];
        int jin = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = digits[i] + jin;
            jin = sum / 10;
            result[i+1] = sum % 10;
        }
        if (jin == 0) {
            return Arrays.copyOfRange(result, 1, result.length);
        }else {
            result[0] = jin;
        }
        return result;
    }
}