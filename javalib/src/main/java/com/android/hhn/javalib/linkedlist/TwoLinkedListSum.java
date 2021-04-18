package com.android.hhn.javalib.linkedlist;

/**
 * Author: haonan.he ;<p/>
 * Date: 4/18/21,3:08 PM ;<p/>
 * Description:
 * 给定两个代表非负数的链表，数字在链表中是反向存储的
 * （链表头结点处的数字是个位数，第二个结点上的数字是十位数...），
 * 求这个两个数的和，结果也用链表表示。
 * 输入：(2->4->3)+(5->6->4)
 * 返回：(7->0->8)
 * Other: ;
 */
class TwoLinkedListSum {

    private static LinkedNode getNewNode(int value, LinkedNode node) {
        return LinkedListUtil.getNewNode(value, node);
    }

    public static void main(String[] args) {
        LinkedNode l1 = new LinkedNode(2, getNewNode(4, getNewNode(3, null)));
        LinkedNode l2 = new LinkedNode(5, getNewNode(6, getNewNode(4, null)));
        LinkedListUtil.printLinkedList(twoLinkedListSum(l1, l2));
    }

    public static LinkedNode twoLinkedListSum(LinkedNode l1, LinkedNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        LinkedNode head = new LinkedNode(0, null);// 哨兵节点，用于控制链表返回
        LinkedNode p = head;// 移动的指针节点
        int temp = 0;// 记录对应节点之和
        while (l1 != null || l2 != null || temp != 0) {
            if (l1 != null) {
                temp += l1.value;// 对应节点都与temp相加
                l1 = l1.next;
            }
            if (l2 != null) {
                temp += l2.value;// 对应节点都与temp相加
                l2 = l2.next;
            }
            // 对10取余，余数记录到当前节点
            p.next = new LinkedNode(temp % 10, null);
            p = p.next;// 移动指针
            temp /= 10; // 计算进位，参与下个节点的计算
        }
        return head.next;
    }


}
