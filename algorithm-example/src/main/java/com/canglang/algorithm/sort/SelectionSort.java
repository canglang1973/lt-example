package com.canglang.algorithm.sort;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/3/003-15:29
 * @version: 1.0
 * @description: 选择排序
 **/
public class SelectionSort extends Sort{
    @Override
    public void sort(Comparable[] a) {
        //将a按升序排列
        int N = a.length;
        for (int i =0;i<N;i++){
            //将a[i] 和 a[i+1 .. N]中最小元素交换
            int min  = i;
            for (int j= i+1;j<N;j++){
                if (less(a[j],a[min])){
                    min = j;
                }
            }
            exch(a,i,min);
        }
    }
    public static void main(String[] args) {
        args = new String[] {"S","O","R","T","E","X","A","M","P","L","E"};
        SelectionSort sort = new SelectionSort();
        sort.sort(args);
        assert isSort(args);
        show(args);
    }
}
