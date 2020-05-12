package com.android.hhn.javalib.linkedlist;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/4/21,7:13 PM ;<p/>
 * Description: 单链表练习;<p/>
 * Other: ;
 */
public class SinglyLinkedList {

    public static class LinkedNode {
        public String data;
        public int value;
        public LinkedNode next;

        public LinkedNode(String data, LinkedNode next) {
            this.data = data;
            this.next = next;
            this.value = data.isEmpty() ? 0 : data.charAt(0);
        }

        public LinkedNode(int value, LinkedNode next) {
            this.value = value;
            this.next = next;
        }
    }

    private static LinkedNode getNewNode(int value, LinkedNode node) {
        return new LinkedNode(value, node);
    }

    private static LinkedNode getNewNode(String data, LinkedNode node) {
        return new LinkedNode(data, node);
    }

    /**
     * 打印链表
     *
     * @param head
     */
    private static void printLinkedList(LinkedNode head) {
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
    private static void printNode(LinkedNode node) {
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
    private static LinkedNode findByValue(LinkedNode head, String data) {
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
    private static LinkedNode findByIndex(LinkedNode head, int index) {
        int tempIndex = 0;
        while (head != null && tempIndex != index) {
            head = head.next;
            ++tempIndex;
        }
        return head;
    }

    /**
     * 链表头部插入
     *
     * @param head
     * @param newNode
     *
     * @return
     */
    private static LinkedNode insertToHead(LinkedNode head, LinkedNode newNode) {
        if (head == null) { // 空链表
            head = newNode;
        } else {
            newNode.next = head;// newNode指向head结点
            head = newNode; // head 变成 newNode
        }
        return head;
    }

    /**
     * 链表尾部插入
     *
     * @param head
     * @param newNode
     */
    private static LinkedNode insertToTail(LinkedNode head, LinkedNode newNode) {
        if (head == null) { // 空链表
            head = newNode;
        } else {
            LinkedNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;// 尾结点指向newNode
        }
        return head;
    }

    /**
     * 在目标节点前、后添加新节点
     * 先根据target找到真正的操作节点，先后插，在前插
     *
     * @param head    链表头结点
     * @param target  目标节点（用于找到节点不是真正链表中的节点，可以直接传值）
     * @param newNode 新的节点
     *
     * @return
     */
    private static LinkedNode insertBeforeAndAfter(LinkedNode head, LinkedNode target, LinkedNode newNode) {
        if (target == null || newNode == null) {
            System.out.println("参数不合法");
            return null;
        }
        if (head == null) {
            head = newNode;
        } else if (head.data.equals(target.data)) { // head结点
            LinkedNode newAfterNode = new LinkedNode(newNode.data, null);
            newAfterNode.next = head.next;
            head.next = newAfterNode;
            newNode.next = head;
            return newNode;
        } else {
            LinkedNode temp = head;// 临时节点
            while (!temp.next.data.equals(target.data)) { //下个节点的值不能等于target
                temp = temp.next;
                if (temp.next == null) { // 代表循环到链表尾部，防止空指针
                    break;
                }
            }
            // temp 为目标前一个节点
            if (temp.next == null || !temp.next.data.equals(target.data)) {
                System.out.println(target.data + "节点未找到！");
                return null;
            }
            // 真正的目标节点
            LinkedNode realTarget = temp.next;
            System.out.print("真正的目标节点：");
            printNode(realTarget);
            System.out.print(realTarget.data + "的前节点：");
            printNode(temp);
            // 后插 不影响前续指针
            LinkedNode newAfterNode = new LinkedNode(newNode.data, null);
            newAfterNode.next = realTarget.next;
            realTarget.next = newAfterNode;
            // 前插 会影响后续指针
            newNode.next = realTarget;
            temp.next = newNode;
        }
        return head;
    }

    /**
     * @param head
     * @param target
     *
     * @return
     */
    private static LinkedNode deleteNode(LinkedNode head, LinkedNode target) {
        if (target == null || head == null)
            return null;
        if (target.data.equals(head.data)) { //删除head
            head = head.next;
            return head;
        }
        LinkedNode temp = head;
        while (!temp.next.data.equals(target.data)) {
            temp = temp.next;
            if (temp.next == null) { // 代表循环到链表尾部，防止空指针
                break;
            }
        }
        // temp 为目标前一个节点
        if (temp.next == null || !temp.next.data.equals(target.data)) {
            System.out.println("未找到：" + target.data);
            return null;
        }
        temp.next = temp.next.next;
        return head;
    }

    /**
     * 单链表反转
     *
     * @param head
     *
     * @return
     */
    private static LinkedNode reverseLinkedList(LinkedNode head) {
        LinkedNode curr = head, pre = null;
        while (curr != null) {
            LinkedNode next = curr.next;// 记录真正的next
            curr.next = pre;// 当前节点的next节点，先指向pre才能反转，且不影响cur
            pre = curr; // 更新前节点，为pre赋值
            curr = next; // 更新循环使用的next指针
        }
        return pre;// pre是一个新的链表
    }

    /**
     * 检测链表是否有环
     *
     * @param head
     *
     * @return
     */
    private static boolean checkHasCircle(LinkedNode head) {
        if (head == null)
            return false;
        LinkedNode fast = head.next; //fast在slow前一个
        LinkedNode slow = head;
        while (fast != null && fast.next != null) { // 无环到尾结点就停止
            fast = fast.next.next;// fast每次前进两个
            slow = slow.next;// slow每次前进一个
            if (slow == fast) // 节点相同代表有环，停止循环
                return true;
        }
        return false;
    }

    /**
     * 合并两个有序链表
     *
     * @param l1
     * @param l2
     *
     * @return
     */
    private static LinkedNode mergedTwoLinked(LinkedNode l1, LinkedNode l2) {
        // 利用哨兵结点简化实现难度 构建一个新head
        LinkedNode newHead = new LinkedNode(0, null);
        LinkedNode pos = newHead; // 代表指针 会移动到链表结尾

        while (l1 != null && l2 != null) {
            if (l1.value < l2.value) {
                pos.next = l1;
                l1 = l1.next;
            } else {
                pos.next = l2;
                l2 = l2.next;
            }
            pos = pos.next;
        }

        if (l1 != null) {
            pos.next = l1;
        }
        if (l2 != null) {
            pos.next = l2;
        }
        return newHead.next;
    }

    /**
     * 找出链表倒数第k个节点，k>=1
     *
     * @param head 链表头部
     * @param k    第k个节点
     *
     * @return 倒数第k个节点
     */
    private static LinkedNode findKthNodeFormTail(LinkedNode head, int k) {
        if (head == null || k < 1) {
            System.out.println("参数不合法");
            return null;
        }
        // 关键理解：k - 1 ，这个差距是 第k个节点到尾部的距离
        // 快指针 先走 k-1 个距离 记录位置
        LinkedNode fast = head;
        for (int i = 0; i < k - 1; ++i) {
            if (fast.next != null) {
                fast = fast.next;
            } else { // 都为空了 说明没找到
                System.out.println("k超过链表长度");
                return null;
            }
        }
        // 慢指针再从头走，当fast走到尾部时，slow就是我们找的节点
        // slow 到 fast的差距 就是 k-1
        LinkedNode slow = head;
        LinkedNode kPre = null; // k的前节点
        while (fast.next != null) {
            fast = fast.next; // 两个指针同时移动
            slow = slow.next;
            // 这里可以找到k的前节点，可以用于删除
            if (fast.next != null && fast.next.next == null) {
                System.out.println("倒数K个节点的前节点：");
                printNode(slow);
                kPre = slow;
            }
        }
        // 逻辑删除
        kPre.next = slow.next;
        printLinkedList(head);
        return slow;
    }

    /**
     * 求中间结点
     *
     * @param head
     *
     * @return
     */
    private static LinkedNode findMidInLinkedList(LinkedNode head) {
        if (head == null)
            return null;
        LinkedNode fast = head;
        LinkedNode slow = head;
        // 每次 slow 都在 fast 走过的中间位置
        while (fast != null && fast.next != null) {
            fast = fast.next.next; // fast 每次走两个
            slow = slow.next; // 慢指针每次走一个
        }
        return slow;
    }

    /**
     * 有序链表删除重复节点
     *
     * @param head
     *
     * @return
     */
    private static LinkedNode removeDuplicateNode(LinkedNode head) {
        if (null == head) {
            return null;
        }
        LinkedNode slow = head;
        LinkedNode fast = head.next;
        while (fast != null) {
            // 遇到不重复的节点
            if (slow.value != fast.value) {
                slow.next = fast;// 记录下fast的值
                slow = slow.next;// 移动slow的指针
            }
            fast = fast.next;
        }
        // 断开后面所有的节点
        slow.next = null;
        return head;
    }

    public static void main(String[] args) {
        LinkedNode h = new LinkedNode("H", null);
        LinkedNode g = new LinkedNode("G", h);
        LinkedNode f = new LinkedNode("F", g);
        LinkedNode e = new LinkedNode("E", f);
        LinkedNode d = new LinkedNode("D", e);
        LinkedNode c = new LinkedNode("C", d);
        LinkedNode b = new LinkedNode("B", c);
        LinkedNode head = new LinkedNode("A", b);

        //        printLinkedList(head);

        //        printNode(findByValue(head, "G"));
        //        printNode(findByIndex(head, 4));

        //        printLinkedList(insertToHead(head, new LinkedNode("S", null)));
        //        printLinkedList(insertToTail(head, new LinkedNode("Z", null)));

        //        printLinkedList(insertBeforeAndAfter(head, new LinkedNode("H", null), new LinkedNode("X", null)));
        //        printLinkedList(deleteNode(head, new LinkedNode("B", null)));

        //        printLinkedList(reverseLinkedList(head));

        //        h.next = head;
        //        System.out.println("是否有环：" + checkHasCircle(head));

        //        LinkedNode E = new LinkedNode("E", null);
        //        LinkedNode C = new LinkedNode("C", E);
        //        LinkedNode A = new LinkedNode("A", C);
        //
        //        LinkedNode F = new LinkedNode("F", g);
        //        LinkedNode D = new LinkedNode("D", F);
        //        LinkedNode B = new LinkedNode("B", D);
        //        printLinkedList(mergedTwoLinked(A, B));

        //  printNode(findKthNodeFormTail(head, 3));

        // printNode(findMidInLinkedList(head));

        LinkedNode test = new LinkedNode(1, getNewNode(1, getNewNode(2, getNewNode(2, getNewNode(3, null)))));
        printLinkedList(test);
        printLinkedList(removeDuplicateNode(test));

    }

}
