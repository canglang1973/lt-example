package com.canglang.algorithm.letcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/6 0006-10:56
 * @version: 1.0
 * @description: 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * <p>
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 * <p>
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 **/
public class LengthOfLongestSubstring {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("dvdf"));
    }

    public static int lengthOfLongestSubstring(String s) {
        List<Character> characters = new ArrayList<>();
        int max = 0;
        for (int index = 0; index < s.length(); index++) {
            char c = s.charAt(index);
            if (!characters.contains(c)) {
                characters.add(c);
                if (index == s.length() - 1) {
                    //判断是否是遍历到最后一个字符
                    max = characters.size() > max ? characters.size() : max;
                }
            } else {
                max = characters.size() > max ? characters.size() : max;
                for (int i = 0; i <= characters.indexOf(c); ) {
                    characters.remove(i);
                }
                characters.add(c);
            }
        }
        return max;
    }
}
