package com.canglang.algorithm.letcode;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/24 0024-11:21
 * @version: 1.0
 * @description: 实现 strStr() 函数。
 * <p>
 * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
 * <p>
 * 示例 1:
 * <p>
 * 输入: haystack = "hello", needle = "ll"
 * 输出: 2
 * 示例 2:
 * <p>
 * 输入: haystack = "aaaaa", needle = "bba"
 * 输出: -1
 * 说明:
 * <p>
 * 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
 * <p>
 * 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与C语言的 strstr() 以及 Java的 indexOf() 定义相符。
 **/
public class StrStr_28 {

    public static void main(String[] args) {
        System.out.println(strStr("a", "a"));
    }

    public static int strStr(String haystack, String needle) {
        if ("".equals(needle) || haystack.equals(needle)) {
            return 0;
        }
        if (haystack.length() > needle.length()) {
            char[] chars = haystack.toCharArray();
            char[] chars1 = needle.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int n = i;
                for (int j = 0; j < chars1.length; j++) {
                    if (n>=chars.length){break;}
                    if (chars[n++] != chars1[j]) {
                        break;
                    }
                    if (j == chars1.length - 1) {
                        return n - needle.length();
                    }
                }
            }
        }
        return -1;
    }
}
