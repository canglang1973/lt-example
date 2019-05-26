package com.canglang.algorithm.sort;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/26/026-9:57
 * @version: 1.0
 * @description: 自顶向下的归并排序
 **/
public class MergeSort extends Sort {

    /**
     * 归并所需要的辅助数组
     */
    private static Comparable[] aux;

    public void sort(Comparable[] a) {
        //一次性分配空间
        aux = new Comparable[a.length];
        sort(a,0,a.length-1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        //将数组a[lo..hi]排序
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid); //将左边排序
        sort(a, mid + 1, hi);//将右边排序
        merge(a, lo, mid, hi);//归并结果
    }

    /**
     * 将a[lo..mid]和a[mid+1..hi]归并
     * @param a
     * @param lo
     * @param mid
     * @param hi
     */
    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        //将a[lo..hi] 复制到aux[lo..hi]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        //归并到a[lo..hi]
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    public static void main(String[] args){
        args = new String[]{"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X","A","M","P","L","E"};
        MergeSort sort = new MergeSort();
        sort.sort(args);
        assert isSort(args);
        show(args);
    }


}
