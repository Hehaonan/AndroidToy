package com.android.hhn.javalib.linkedlist;

/**
 * Author: haonan.he ;<p/>
 * Date: 4/18/21,3:30 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class LinkedListUtil {

    public static LinkedNode getNewNode(int value, LinkedNode node) {
        return new LinkedNode(value, node);
    }

    public static LinkedNode getNewNode(String data, LinkedNode node) {
        return new LinkedNode(data, node);
    }

    /**
     * 打印链表
     *
     * @param head
     */
    public static void printLinkedList(LinkedNode head) {
        if (head == null) {
            System.out.println("链表为空");
            return;
        }
        while (head != null) {

            System.out.print((head.data != null ? head.data : "") + "(" + head.value + ")" + "->");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * 打印节点
     *
     * @param node
     */
    public static void printNode(LinkedNode node) {
        if (node == null) {
            System.out.println("节点为空");
            return;
        }
        System.out.println(node.data + "(" + node.value + ")");
    }

}
