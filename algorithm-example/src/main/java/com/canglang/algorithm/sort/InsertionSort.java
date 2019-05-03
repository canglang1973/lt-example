package com.canglang.algorithm.sort;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/3/003-15:42
 * @version: 1.0
 * @description: 插入排序
 **/
public class InsertionSort extends Sort {
    @Override
    public void sort(Comparable[] a) {
        //将a 按升序排列
        int N = a.length;
        for (int i = 1; i < N; i++) {
            //将a[i]插入到a[i-1]、a[i-2],a[i-3]...之中
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    public static void main(String[] args) {
        args = new String[]{"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        InsertionSort sort = new InsertionSort();
        sort.sort(args);
        assert isSort(args);
        show(args);
    }
}
