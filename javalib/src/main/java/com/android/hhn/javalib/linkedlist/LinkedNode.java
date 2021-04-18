package com.android.hhn.javalib.linkedlist;

/**
 * Author: haonan.he ;<p/>
 * Date: 4/18/21,3:30 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class LinkedNode {
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
