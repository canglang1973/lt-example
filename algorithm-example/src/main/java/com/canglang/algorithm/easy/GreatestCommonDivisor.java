package com.canglang.algorithm.easy;

/**
 * @author leitao.
 * @category
 * @time: 2020-3-15-16:15
 * @version: 1.0
 * @description: 求最大公约数
 **/
public class GreatestCommonDivisor {

    public static void main(String[] args) {
        System.out.println(getGreatestCommonDivisor_v1(10, 5));
        System.out.println(getGreatestCommonDivisor_v2(10,51));
        System.out.println(getGreatestCommonDivisor_v3(10,51));
        System.out.println(getGreatestCommonDivisor_v4(10,51));
    }

    /**
     * 暴力枚举法求最大公约数
     *
     * @param a
     * @param b
     * @return
     */
    public static int getGreatestCommonDivisor_v1(int a, int b) {
        int bigger = a > b ? a : b;
        int smaller = a < b ? a : b;
        if (bigger % smaller == 0) {
            return smaller;
        }
        for (int i = smaller / 2; i > 1; i--) {
            if (smaller % i == 0 && bigger % i == 0) {
                return i;
            }
        }
        return 1;
    }

    /**
     * 辗转相除法求最大公约数
     *
     * @param a
     * @param b
     * @return
     */
    public static int getGreatestCommonDivisor_v2(int a, int b) {
        int bigger = a > b ? a : b;
        int smaller = a < b ? a : b;
        if (bigger % smaller == 0) {
            return smaller;
        }
        return getGreatestCommonDivisor_v2(bigger % smaller, smaller);
    }

    /**
     * 使用更相减损术求最大公约数
     * @param a
     * @param b
     * @return
     */
    public static int getGreatestCommonDivisor_v3(int a, int b) {
        if (a == b ) {
            return a;
        }
        int bigger = a > b ? a : b;
        int smaller = a < b ? a : b;
        return getGreatestCommonDivisor_v3(bigger-smaller,smaller) ;
    }

    /**
     * 更相减损术与位移相结合
     * @param a
     * @param b
     * @return
     */
    public static int getGreatestCommonDivisor_v4(int a, int b) {
        if (a==b){
            return a;
        }
        if((a&1)==0 && (b&1)==0){
            return getGreatestCommonDivisor_v4(a>>1,b>>1)<<1;
        }else if((a&1)==0 && (b&1)!=0){
            return getGreatestCommonDivisor_v4(a>>1,b);
        }else if((a&1)!=0 && (b&1)==0){
            return getGreatestCommonDivisor_v4(a,b>>1);
        }else {
            int bigger = a > b ? a : b;
            int smaller = a < b ? a : b;
            return getGreatestCommonDivisor_v4(bigger-smaller,smaller);
        }
    }
}
