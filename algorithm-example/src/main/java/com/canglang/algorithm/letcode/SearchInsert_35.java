package com.canglang.algorithm.letcode;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/24 0024-13:36
 * @version: 1.0
 * @description: 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * <p>
 * 你可以假设数组中无重复元素。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [1,3,5,6], 5
 * 输出: 2
 * 示例 2:
 * <p>
 * 输入: [1,3,5,6], 2
 * 输出: 1
 * 示例 3:
 * <p>
 * 输入: [1,3,5,6], 7
 * 输出: 4
 * 示例 4:
 * <p>
 * 输入: [1,3,5,6], 0
 * 输出: 0
 **/
public class SearchInsert_35 {

    public static void main(String[] args) {
        System.out.println(searchInsert(new int[]{0, 2, 3, 4}, 1));
    }

    public static int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
            if (i == nums.length - 1 && nums[i]<target) {
                return nums.length;
            }
        }
        return 0;
    }
}
