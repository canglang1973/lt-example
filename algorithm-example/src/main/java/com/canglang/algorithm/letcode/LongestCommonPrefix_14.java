package com.canglang.algorithm.letcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/23 0023-14:00
 * @version: 1.0
 * @description: 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * 示例 1:
 * <p>
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 * 示例 2:
 * <p>
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 * 说明:
 * <p>
 * 所有输入只包含小写字母 a-z 。
 **/
public class LongestCommonPrefix_14 {

    public static void main(String[] args) {
        String[] strings = new String[]{"ab","a"};
        System.out.println(longestCommonPrefix_2(strings));
    }

    public static String longestCommonPrefix(String[] strs) {
        String reslut = "";
        if (strs != null && strs.length > 0) {
            boolean flag = false;
            for (int i = 0; i < strs[0].length(); i++) {
                String a = strs[0].substring(i, i + 1);
                for (int j = 1; j < strs.length; j++) {
                    if (strs[j].length() == 0 || strs[j].length() <= i) {
                        flag = true;
                        break;
                    }
                    String b = strs[j].substring(i, i + 1);
                    if (!a.equals(b)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    reslut = strs[0].substring(0, i);
                    break;
                }
            }
            if (!flag) {
                reslut = strs[0];
            }
        }
        return reslut;
    }

    public static String longestCommonPrefix_2(String[] strs) {
        String reslut = "";
        if (strs.length == 1) {
            return strs[0];
        }
        if (strs.length > 0) {
            TreeSet<String> set = new TreeSet<>(new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    int num = Integer.compare(s1.length(), s2.length());
                    //如果长度相等，则根据内容排序
                    if (num == 0) {
                        return s2.compareTo(s1);
                    }
                    return num;
                }
            });
            set.addAll(Arrays.asList(strs));
            String first = set.pollFirst();
            int index = 0;
            for (int i = 1; i <= first.length(); i++) {
                String temp = first.substring(0, i);
                for (String s : set) {
                    if (!s.startsWith(temp)) {
                        return temp.substring(0, i-1);
                    }
                }
                index = i;
            }
            return first.substring(0, index);
        }
        return reslut;
    }

}
