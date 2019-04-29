package com.canglang.algorithm.letcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leitao.
 * @category
 * @time: 2019/3/29 0029-14:17
 * @version: 1.0
 * @description: 两数之和 https://leetcode-cn.com/problems/two-sum/
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * <p>
 * 示例:
 * <p>
 * 给定 nums = [2, 7, 11, 15], target = 9
 * <p>
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 **/
public class TwoIntegerSum {

    public static void main(String[] agres) {
        int[] nums = {1, 4, 3, 5, 2, 11, 15};
        int target = 9;
        int[] ints = twoSum_1(nums, target);
        System.out.println("[" + ints[0] + "," + ints[1] + "]");
        int[] ints_2 = twoSum_2(nums, target);
        System.out.println("[" + ints_2[0] + "," + ints_2[1] + "]");
        int[] ints_3 = twoSum_3(nums, target);
        System.out.println("[" + ints_3[0] + "," + ints_3[1] + "]");
    }

    /**
     * 方法一
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum_1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    /**
     * 方法二
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum_2(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i],i);
        }
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)){
                return new int[]{i,map.get(diff)};
            }
        }
        return null;
    }
    /**
     * 方法三
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum_3(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)) {
                return new int[] { map.get(diff), i };
            }
            map.put(nums[i], i);
        }
        return null;
    }

}
