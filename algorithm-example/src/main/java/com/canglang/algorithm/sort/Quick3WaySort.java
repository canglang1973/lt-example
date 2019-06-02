package com.canglang.algorithm.sort;

/**
 * @author leitao.
 * @category
 * @time: 2019/6/2/002-15:29
 * @version: 1.0
 * @description: 三向切分的快速排序
 **/
public class Quick3WaySort extends Sort {
    @Override
    public void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                exch(a, lt++, i++);
            } else if (cmp > 0) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }
        //现在a[lo..lt-1]<v=a[lt..gt]<a[gt+1..hi]成立
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    public static void main(String[] args) {
        args = new String[]{"Q", "U", "I", "C", "K", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        Quick3WaySort sort = new Quick3WaySort();
        sort.sort(args);
        assert isSort(args);
        show(args);
    }
}
