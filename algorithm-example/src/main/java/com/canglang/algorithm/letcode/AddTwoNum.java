package com.canglang.algorithm.letcode;

/**
 * @author leitao.
 * @category
 * @time: 2019/4/30 0030-16:22
 * @version: 1.0
 * @description: 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 示例：
 * <p>
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 **/
public class AddTwoNum {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        ListNode resultNode = addTwoNumbers(l1, l2);
        while (true) {
            if (resultNode.next == null) {
                System.out.println(resultNode.val);
                break;
            } else {
                System.out.printf(resultNode.val + " -> ");
                resultNode = resultNode.next;
            }
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode temp = new ListNode(0);
        int curry = 0;
        int n = 0;
        while (l1 != null || l2 != null) {
            int val_1 = 0;
            int val_2 = 0;
            if (l1 != null) {
                val_1 = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                val_2 = l2.val;
                l2 = l2.next;
            }
            int sum = val_1 + val_2 + curry;
            curry = sum / 10;
            ListNode sul = new ListNode(sum % 10);
            if (n == 0) {
                result = sul;
                temp = sul;
                n++;
            } else {
                temp.next = sul;
                temp = sul;
            }
            if (curry != 0){
                temp.next = new ListNode(curry);
            }
        }
        return result;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
