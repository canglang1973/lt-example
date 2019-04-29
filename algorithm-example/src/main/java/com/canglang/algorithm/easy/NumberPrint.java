package com.canglang.algorithm.easy;

/**
 * @author leitao.
 * @category
 * @time: 2019/4/28 0028-12:32
 * @version: 1.0
 * @description: 在命令行输入一个自然数N，实现如下面实例：
 * <p>
 * N=3时：
 * <p>
 * 1 2 3
 * 8 9 4
 * 7 6 5
 * <p>
 * N=6时：
 * <p>
 *  01  02  03  04  05  06 
 *  20  21  22  23  24  07 
 *  19  32  33  34  25  08 
 *  18  31  36  35  26  09 
 *  17  30  29  28  27  10 
 *  16  15  14  13  12  11
 **/
public class NumberPrint {
    /**
     * 长度大小
     */
    private static int LENGTH = 6;

    public static void main(String[] args) {

        int max = LENGTH * LENGTH;
        int maxNumberWeiShu = (max + "").length();
        System.out.println("最大数的位数为：" + maxNumberWeiShu);
        String formatStr = " %0" + maxNumberWeiShu + "d ";

        int[][] array = new int[LENGTH][LENGTH];

        int index = 1;
        int i = 0, j = 0;
        while (index < max) {
            while (j + 1 < LENGTH && array[i][j + 1] == 0) {
                array[i][++j] = index++;
                System.out.println("next  右");
            }
            while (i + 1 < LENGTH && array[i + 1][j] == 0) {
                array[++i][j] = index++;
                System.out.println("next  下");
            }
            while (j - 1 >= 0 && array[i][j - 1] == 0) {
                array[i][--j] = index++;
                System.out.println("next  左");
            }
            while (i - 1 > 0 && array[i - 1][j] == 0) {
                array[--i][j] = index++;
                System.out.println("next  上");
            }
        }
        for (int row = 0; row < LENGTH; row++) {
            for (int col = 0; col < LENGTH; col++) {
                System.out.printf(formatStr, array[row][col] + 1);
            }
            System.out.println();
        }
    }
}

