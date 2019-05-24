package com.canglang.algorithm.letcode;

/**
 * @author leitao.
 * @category
 * @time: 2019/5/23 0023-16:37
 * @version: 1.0
 * @description: 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * <p>
 * 示例：
 * <p>
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 **/
public class MergeTwoLists_21 {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);
        ListNode resultNode = mergeTwoLists(l1, l2);
        print(resultNode);
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    private static void print(ListNode resultNode) {
        if (resultNode == null) {
            System.out.println();
            return;
        }
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

}
