package com.android.hhn.javalib.binarytree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/4/15,5:14 PM ;<p/>
 * Description: 二叉搜索树练习;<p/>
 * Other: ;
 */
public class BinarySearchTreePractice {

    private static class TreeNode {
        TreeNode left; // 左节点
        TreeNode right; // 右节点
        int value; // 存储的数据 int值

        public TreeNode(int value) {
            this.left = null;
            this.right = null;
            this.value = value;
        }

        public TreeNode(TreeNode left, TreeNode right, int value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        /**
         * 前序遍历
         *
         * @param root
         */
        public void preOrderTraversal(TreeNode root) {
            if (root == null) {
                return;
            }
            System.out.print(root.value + "->");
            preOrderTraversal(root.left);
            preOrderTraversal(root.right);
        }

        /**
         * 中序遍历
         *
         * @param root
         */
        public void inOrderTraversal(TreeNode root) {
            if (root == null) {
                return;
            }
            inOrderTraversal(root.left);
            System.out.print(root.value + "->");
            inOrderTraversal(root.right);
        }

        /**
         * 后序遍历
         *
         * @param root
         */
        public void postOrderTraversal(TreeNode root) {
            if (root == null) {
                return;
            }
            postOrderTraversal(root.left);
            postOrderTraversal(root.right);
            System.out.print(root.value + "->");
        }
    }

    /**
     * 全部遍历方式
     *
     * @param root
     */
    private static void allTraverse(TreeNode root) {
        System.out.println("前序：");
        root.preOrderTraversal(root);
        System.out.println("\n中序：");
        root.inOrderTraversal(root);
        System.out.println("\n后序：");
        root.postOrderTraversal(root);
    }

    // 辅助打印方法
    private static ArrayList<ArrayList<String>> printTreeByLinesBFS(TreeNode pRoot) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        if (pRoot == null) {
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(pRoot);// 根节点入队列
        while (!queue.isEmpty()) {
            int len = queue.size();// 每层的个数
            ArrayList<String> res = new ArrayList<>();
            for (int i = 0; i < len; i++) { // 循环同层子节点的个数，实现分组
                TreeNode node = queue.poll();
                if (node.left != null) {// 先左后右
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                res.add(node.value + "");// 同层添加到一组
            }
            list.add(res);
        }
        return list;
    }

    /**
     * 判断BST的合法性
     *
     * @param root
     *
     * @return
     */
    private static boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 排除只有一个节点的情况
        if (root.left == null && root.right == null) {
            return false;
        }
        return isValidBST(root, null, null);
    }

    private static boolean isValidBST(TreeNode root, TreeNode minNode, TreeNode maxNode) {
        if (root == null) {
            return true;
        }
        //针对左子树 如果大于节点的值 BTS不成立
        if (maxNode != null && root.value >= maxNode.value)
            return false;
        //针对右子树 如果小于节点的值 BTS不成立
        if (minNode != null && root.value <= minNode.value)
            return false;
        // 左子树：当前节点为最大节点
        // 右子树：当前节点为最小节点
        return isValidBST(root.left, minNode, root) && isValidBST(root.right, root, maxNode);
    }

    /**
     * 判断BST中是否存在某值，递归实现，recursive
     *
     * @param root
     * @param target
     *
     * @return
     */
    private static boolean isInBSTByRec(TreeNode root, int target) {
        if (null == root) {
            return false;
        }
        if (root.value == target)
            return true;
        if (target > root.value)
            return isInBSTByRec(root.right, target);
        return isInBSTByRec(root.left, target);
    }

    /**
     * 判断BST中是否存在某值，循环实现
     *
     * @param root
     * @param target
     *
     * @return
     */
    private static boolean isInBSTByLoop(TreeNode root, int target) {
        if (null == root) {
            return false;
        }
        while (root != null) {
            if (root.value == target) {
                return true;
            } else if (root.value > target) { // 查找走左子树
                root = root.left;
            } else { // root.value < target , 查找走左子树
                root = root.right;
            }
        }
        return false;
    }

    /**
     * @param root
     *
     * @return
     */
    private static TreeNode findMinByLoop(TreeNode root) {
        if (root == null) {
            return null;
        }
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    private static TreeNode findMinByRec(TreeNode root) {
        if (root.left == null) {
            return root;
        }
        return findMinByRec(root.left);
    }

    /**
     * @param root
     *
     * @return
     */
    private static TreeNode findMaxByLoop(TreeNode root) {
        if (root == null) {
            return null;
        }
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    /**
     * @param root
     *
     * @return
     */
    private static TreeNode findMaxByRec(TreeNode root) {
        if (root.right == null) {
            return root;
        }
        return findMaxByRec(root.right);
    }

    /**
     * BST中插入一个值，循环实现
     *
     * @param root
     * @param value
     */
    private static void insertNodeInBSTByLoop(TreeNode root, int value) {
        if (root == null) { // 空树
            root = new TreeNode(null, null, value);
            return;
        }
        while (root != null) {
            if (value > root.value) {
                if (root.right == null) {
                    root.right = new TreeNode(null, null, value);
                    return;
                }
                root = root.right;
            } else { // data < root.data
                if (root.left == null) {
                    root.left = new TreeNode(null, null, value);
                    return;
                }
                root = root.left;
            }
        }
    }

    /**
     * BST中插入一个值，递归法
     *
     * @param root
     * @param value
     */
    private static TreeNode insertNodeInBSTByRec(TreeNode root, int value) {
        if (root == null) { // 空树
            return new TreeNode(null, null, value);
        }
        if (value > root.value) {
            root.right = insertNodeInBSTByRec(root.right, value);
        }
        if (value < root.value) {
            root.left = insertNodeInBSTByRec(root.left, value);
        }
        return root;
    }

    /**
     * BST中删除一个节点，递归实现
     *
     * @param root
     * @param target
     *
     * @return
     */
    private static TreeNode deleteNodeInBSTByRec(TreeNode root, int target) {
        if (root == null)
            return null;
        // 找到目标节点
        if (root.value == target) {
            System.out.println("找到删除的节点:" + root.value);
            // 情况1:左右都为空、情况2：左右有一个不为空
            if (root.left == null)
                return root.right;
            if (root.right == null)
                return root.left;

            // 情况3 A：找到左子树找到最大节点
            TreeNode leftMaxNode = findMaxByLoop(root.left);// 借助之前的方法
            System.out.println("需要删除左子树最大节点:" + leftMaxNode.value);
            root.value = leftMaxNode.value;// 先替换值
            root.left = deleteNodeInBSTByRec(root.left, leftMaxNode.value);// 再真正的删除节点，最终递归到情况1中

            // 情况3 B：找到右子树找到最小节点 (只需要写一种)
            // TreeNode rightMinNode = findMinByLoop(root.right);// 借助之前的方法
            // System.out.println("需要删除右子树最小节点:" + leftMaxNode.value);
            // root.value = rightMinNode.value; // 先替换值
            // root.right = deleteNodeInBSTByRec(root.right, rightMinNode.value);// 再真正的删除节点，最终递归到情况1中

        } else if (root.value > target) { // 向左，处理未找到的情况
            root.left = deleteNodeInBSTByRec(root.left, target);
        } else if (root.value < target) { // 向右，处理未找到的情况，理论上不考虑值相等的情况
            root.right = deleteNodeInBSTByRec(root.right, target);
        }
        // 如果为找到会返回当前树
        return root;
    }

    /**
     * BST中删除一个节点，循环实现
     *
     * @param root
     * @param target
     *
     * @return
     */
    private static void deleteNodeInBSTByLoop(TreeNode root, int target) {
        TreeNode rootParent = null; // 标记当前节点的父节点
        while (root != null && root.value != target) {
            rootParent = root;// 赋值父节点
            if (root.value > target) {// value > target 向右 反之 向左
                root = root.left;
            } else {
                root = root.right;
            }
        }
        if (root == null) {// 没有找到
            System.out.println("当前BST中不包含：" + target);
            return;
        }
        System.out.println("找到删除的节点:" + root.value);
        // 情况3：要删除的节点有两个子节点
        if (root.left != null && root.right != null) {
            // 情况3 A：查找左子树中最大节点
            TreeNode leftMaxNode = root.left;// 当前节点的左子树的最大节点
            TreeNode leftTreeParent = root; // 右子树父节点
            while (leftMaxNode.right != null) {
                leftTreeParent = leftMaxNode;// 记录父节点
                leftMaxNode = leftMaxNode.right;
            }
            root.value = leftMaxNode.value; // 将目标节点值替换为左子树最大值
            System.out.println("需要删除左子树最大节点:" + leftMaxNode.value);
            root = leftMaxNode;// 移动指针到需要删除的节点
            rootParent = leftTreeParent; // 这时候rootParent已变成需要删除节点的父节点了

            // 情况3 B：查找右子树中最小节点
            // TreeNode rightMinNode = root.right;// 当前节点的右子树的最小节点
            // TreeNode rightTreeParent = root; // 右子树父节点
            // while (rightMinNode.left != null) {
            //     rightTreeParent = rightMinNode;// 记录父节点
            //     rightMinNode = rightMinNode.left;
            // }
            // root.value = rightMinNode.value; // 将目标节点值替换为右子树最小值
            // root = rightMinNode;// 移动指针到需要删除的节点
            // rootParent = rightTreeParent; // 这时候rootParent已变成需要删除节点的父节点了
        }
        // 先处理子集逻辑，后处理全集逻辑，减少操作步骤，所以先处理情况3
        // 请况2：仅有一个子节点 或 情况1：删除节点是叶子节点
        TreeNode child; // p的子节点
        if (root.left != null)
            child = root.left;
        else if (root.right != null)
            child = root.right;
        else
            child = null;

        // 最后整理删除节点
        if (rootParent == null)
            root = child; // 删除的是根节点
        else if (rootParent.left == root)
            rootParent.left = child;
        else
            rootParent.right = child;
        System.out.println("找到删除的节点:" + root.value);
    }


    public static void main(String[] args) {
        TreeNode k = new TreeNode(null, null, 20);
        TreeNode j = new TreeNode(null, null, 15);
        TreeNode i = new TreeNode(j, k, 17);
        TreeNode h = new TreeNode(null, null, 12);
        TreeNode c = new TreeNode(h, i, 13);

        TreeNode g = new TreeNode(null, null, 6);
        TreeNode f = new TreeNode(null, null, 2);
        TreeNode e = new TreeNode(null, null, 9);
        TreeNode d = new TreeNode(f, g, 5);
        TreeNode b = new TreeNode(d, e, 8);
        TreeNode root = new TreeNode(b, c, 10);

        //System.out.println("是否是BTS：" + isValidBST(root));

//        int target = 12;
//        System.out.println(target + "是否在BTS：" + isInBSTByRec(root, target));
//        System.out.println(target + "是否在BTS：" + isInBSTByLoop(root, target));

        //        System.out.println("min:" + findMinByLoop(root).value);
        //        System.out.println("max:" + findMaxByLoop(root).value);

        //        insertNodeInBSTByLoop(root, 7);
        //        printTree(root);

        //        TreeNode temp = insertNodeInBSTByRec(root, 11);
        //        printTree(temp);

        // printTree(root);
        // deleteNodeInBSTByRec(root, 13);
        // deleteNodeInBSTByLoop(root, 8);
        // printTree(root);
    }

    /**
     * 打印树
     *
     * @param root
     */
    private static void printTree(TreeNode root) {
        ArrayList<ArrayList<String>> lines = printTreeByLinesBFS(root);
        System.out.println(Arrays.toString(lines.toArray()));
    }

}
