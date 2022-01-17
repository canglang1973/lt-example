package com.canglang.algorithm.letcode;

/**
 * @author leitao.
 * @time: 2022/1/17 0017-13:11
 * @description:
 **/
public class LengthOfLastWord_58 {

    public static void main(String[] args) {
        System.out.println(lengthOfLastWord("sdf asdf "));
    }
    public static int lengthOfLastWord(String s) {
        String[] split = s.split(" ");
        return split[split.length-1].length();
    }

}