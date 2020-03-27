package com.canglang.common.commonbase;

import java.io.File;

/**
 * @author leitao.
 * @category
 * @time: 2019/9/16 0016-9:44
 * @version: 1.0
 * @description: Java &、&&、|、||、^、<<、>>、~、>>>等运算符
 * <p>
 * 十进制负数转二进制:
 * 示例:-5
 * 5的二进制:0000 0101 (原码)
 * 取反===>:1111 1010  (反码)
 * 反码加1=>:1111 1011 (补码) 补码就是负数在计算机中的二进制表示方法
 * 那么，1111 1011表示8位的-5，
 * 如果要表示16位的-5, 在左边添上8个1即可:1111 1111 1111 1011
 * <p>
 * 二进制负数转十进制:
 * 示例:1111 1101
 * 减一==> 1111 1100
 * 取反==> 0000 0011
 * 计算结果是:3
 * 那么这个二进制数表示的十进制数就是:-3
 **/
@CustomAnnotation
public class JavaOperator {

    @CustomAnnotation
    public static void main(String[] args) throws Exception{
        bitwiseAnd(3, 5);
        logicalAnd(true, false);
        bitwiseOr(3, 5);
        logicalOr(true, false);
        exclusiveOr(3, 5);
        leftShift(3, 5);
        rightShift(-5, 1);
        reverse(-5);
        unsignedRightShift(-5,1);
    }

    /**
     * 按位与:&
     * <p>
     * &按位与的运算规则是将两边的数转换为二进制位，然后运算最终值，运算规则即(两个为真才为真)1&1=1 , 1&0=0 , 0&1=0 , 0&0=0
     * 3的二进制位是0000 0011 ， 5的二进制位是0000 0101 ， 那么就是011 & 101，由按位与运算规则得知，001 & 101等于0000 0001，最终值为1
     */
    private static void bitwiseAnd(int a, int b) {
        int i = a & b;
        System.out.println(a + "&" + b + "=" + i);
    }

    /**
     * 逻辑与:&&
     * &&逻辑与也称为短路逻辑与，先运算&&左边的表达式，一旦为假，后续不管多少表达式，均不再计算，一个为真，再计算右边的表达式，两个为真才为真。
     */
    private static void logicalAnd(boolean a, boolean b) {
        boolean i = a && b;
        System.out.println(a + "&&" + b + "=" + i);
    }

    /**
     * 按位或:|
     * |按位或和&按位与计算方式都是转换二进制再计算，不同的是运算规则(一个为真即为真)1|0 = 1 , 1|1 = 1 , 0|0 = 0 , 0|1 = 1
     * 6的二进制位0000 0110 , 2的二进制位0000 0010 , 110|010为110，最终值0000 0110，故6|2等于6
     */
    private static void bitwiseOr(int a, int b) {
        int i = a | b;
        System.out.println(a + "|" + b + "=" + i);
    }

    /**
     * 逻辑或:||
     * 逻辑或||的运算规则是一个为真即为真，后续不再计算，一个为假再计算右边的表达式。
     */
    private static void logicalOr(boolean a, boolean b) {
        boolean i = a && b;
        System.out.println(a + "||" + b + "=" + i);
    }

    /**
     * 异或运算符:^
     * ^异或运算符顾名思义，异就是不同，其运算规则为1^0 = 1 , 1^1 = 0 , 0^1 = 1 , 0^0 = 0
     * 5的二进制位是0000 0101 ， 9的二进制位是0000 1001，也就是0101 ^ 1001,结果为1100 , 00001100的十进制位是12
     */
    private static void exclusiveOr(int a, int b) {
        int i = a ^ b;
        System.out.println(a + "^" + b + "=" + i);
    }

    /**
     * 左移:<<
     * 5<<2的意思为5的二进制位往左挪两位，右边补0，5的二进制位是0000 0101 ，
     * 就是把有效值101往左挪两位就是0001 0100 ，正数左边第一位补0，负数补1，等于乘于2的n次方，十进制位是20
     */
    private static void leftShift(int a, int b) {
        int i = a << b;
        System.out.println(a + "<<" + b + "=" + i);
    }

    /**
     * 右移:>>
     * 凡位运算符都是把值先转换成二进制再进行后续的处理，5的二进制位是0000 0101，
     * 右移两位就是把101左移后为0000 0001，正数左边第一位补0，负数补1，等于除于2的n次方，结果为1
     */
    private static void rightShift(int a, int b) {
        int i = a >> b;
        System.out.println(a + ">>" + b + "=" + i);
    }

    /**
     * 取反: ~
     * 取反就是1为0,0为1,5的二进制位是0000 0101，取反后为1111 1010，值为-6
     */
    private static void reverse(int a) {
        int i = ~a;
        System.out.println("~" + a + "=" + i);
    }

    /**
     * 无符号右移:
     * 正数无符号右移
     * 无符号右移运算符和右移运算符的主要区别在于负数的计算，因为无符号右移是高位补0，移多少位补多少个0。
     * 15的二进制位是0000 1111 ,右移2位0000 0011，结果为3
     */
    private static void unsignedRightShift(int a, int b) {
        int i = a >>> b;
        System.out.println(a + ">>>" + b + "=" + i);
    }



}
