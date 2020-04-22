package com.android.hhn.javalib.linkedlist;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/4/21,7:13 PM ;<p/>
 * Description: 单链表练习;<p/>
 * Other: ;
 */
public class SinglyLinkedList {

    public static class Node {
        public String data;
        public int value;
        public Node next;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
            this.value = data.isEmpty() ? 0 : data.charAt(0);
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    /**
     * 打印链表
     *
     * @param head
     */
    private static void printLinkedList(Node head) {
        while (head != null) {
            System.out.print(head.data + "->");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node h = new Node("H", null);
        Node g = new Node("G", h);
        Node f = new Node("F", g);
        Node e = new Node("E", f);
        Node d = new Node("D", e);
        Node c = new Node("C", d);
        Node b = new Node("B", c);
        Node head = new Node("A", b);

        printLinkedList(head);
    }

}
