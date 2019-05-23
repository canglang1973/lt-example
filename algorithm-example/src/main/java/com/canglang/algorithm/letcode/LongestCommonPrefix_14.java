package com.canglang.algorithm.letcode;
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
        String[] strings = new String[]{"c"};
        System.out.println(longestCommonPrefix(strings));
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
            if (!flag){
                reslut = strs[0];
            }
        }
        return reslut;
    }

}
