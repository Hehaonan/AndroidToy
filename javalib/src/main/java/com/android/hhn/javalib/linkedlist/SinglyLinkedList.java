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
        if (head == null) {
            System.out.println("链表为空");
            return;
        }
        while (head != null) {
            System.out.print(head.data + "(" + head.value + ")" + "->");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * 打印节点
     *
     * @param node
     */
    private static void printNode(Node node) {
        if (node == null) {
            System.out.println("节点为空");
            return;
        }
        System.out.println(node.data + "(" + node.value + ")");
    }

    /**
     * 通过 data 查找 Node
     *
     * @param head
     * @param data
     *
     * @return
     */
    private static Node findByValue(Node head, String data) {
        while (head != null && head.data != data) {
            head = head.next;
        }
        return head;
    }

    /**
     * 通过位置查找
     *
     * @param head
     * @param index
     *
     * @return
     */
    private static Node findByIndex(Node head, int index) {
        int tempIndex = 0;
        while (head != null && tempIndex != index) {
            head = head.next;
            ++tempIndex;
        }
        return head;
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

        // printLinkedList(head);

        printNode(findByValue(head, "G"));
        printNode(findByIndex(head, 4));

    }

}
