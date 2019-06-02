package com.canglang.algorithm.sort;

/**
 * @author leitao.
 * @category
 * @time: 2019/6/2/002-15:14
 * @version: 1.0
 * @description: 基本快速排序算法
 **/
public class QuickSort extends Sort {
    @Override
    public void sort(Comparable[] a) {
        sort(a,0,a.length-1);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        //切分
        int j = partition(a, lo, hi);
        //将左半部分排序
        sort(a, lo, j - 1);
        //将右半部分排序
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        //将数组切分为a[lo..i-1],a[i],a[i+1..hi]
        //左右扫描指针
        int i = lo, j = hi + 1;
        //切分元素
        Comparable v = a[lo];
        while (true) {
            //扫描左右,检查扫描是否结束并交换元素
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exch(a, i, j);
        }
        //将v = a[j] 放入正确的位置
        exch(a, lo, j);
        //a[lo..j-1] <= a[j] <= a[j+1..hi]达成
        return j;
    }

    public static void main(String[] args) {
        args = new String[]{"Q", "U", "I", "C", "K", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        QuickSort sort = new QuickSort();
        sort.sort(args);
        assert isSort(args);
        show(args);
    }
}
