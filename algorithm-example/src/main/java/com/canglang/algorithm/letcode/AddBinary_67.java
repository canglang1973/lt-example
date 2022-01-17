package com.canglang.algorithm.letcode;

import java.util.Arrays;

/**
 * @author leitao.
 * @time: 2022/1/17 0017-13:55
 * @description:
 **/
public class AddBinary_67 {
    public static void main(String[] args) {

        System.out.println(addBinary("1", "111"));
    }

    public static String addBinary(String a, String b) {
        int index = 1;
        int mod = 0;
        char[] max = a.length() > b.length() ? a.toCharArray() : b.toCharArray();
        char[] min = a.length() > b.length() ? b.toCharArray() : a.toCharArray();
        char[] result = new char[max.length + 1];
        for (int i = max.length - 1; i >= 0; i--) {
            int a1 = Integer.parseInt(new String(new char[]{max[i]}));
            int b1 = 0;
            if (index <= min.length) {
                b1 = Integer.parseInt(new String(new char[]{min[min.length - index]}));
                index++;
            }
            int sum = a1 + b1 + mod;
            mod = sum / 2;
            result[i + 1] = (sum % 2 + "").charAt(0);
        }
        if (mod == 0) {
            return new String(Arrays.copyOfRange(result, 1, result.length));
        } else {
            result[0] = (mod + "").charAt(0);
        }
        return new String(result);
    }
}