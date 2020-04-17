package com.android.hhn.javalib.binarytree;

import java.util.ArrayList;
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
     * 判断BST中是否存在某值
     *
     * @param root
     * @param target
     *
     * @return
     */
    private static boolean isInBST(TreeNode root, int target) {
        if (null == root) {
            return false;
        }
        if (root.value == target)
            return true;
        if (target > root.value)
            return isInBST(root.right, target);
        return isInBST(root.left, target);
    }

    /**
     * @param root
     *
     * @return
     */
    private static int findMin(TreeNode root) {
        if (root == null) {
            return 0;
        }
        while (root.left != null) {
            root = root.left;
        }
        return root.value;
    }

    /**
     * @param root
     *
     * @return
     */
    private static int findMax(TreeNode root) {
        if (root == null) {
            return 0;
        }
        while (root.right != null) {
            root = root.right;
        }
        return root.value;
    }


    /**
     * BST中插入一个值，循环实现
     *
     * @param root
     * @param value
     */
    private static void insertBSTByLoop(TreeNode root, int value) {
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
    private static TreeNode insertBSTByRec(TreeNode root, int value) {
        if (root == null) { // 空树
            return new TreeNode(null, null, value);
        }
        if (value > root.value) {
            root.right = insertBSTByRec(root.right, value);
        }
        if (value < root.value) {
            root.left = insertBSTByRec(root.left, value);
        }
        return root;
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

        //int target = 14;
        //System.out.println(target + "是否在BTS：" + isInBST(root, target));

        //        System.out.println("min:" + findMin(root));
        //        System.out.println("max:" + findMax(root));

        //        insertBSTByLoop(root, 7);
        //        ArrayList<ArrayList<String>> lines = printTreeByLinesBFS(root);
        //        System.out.println(Arrays.toString(lines.toArray()));

        //        TreeNode temp = insertBSTByRec(root, 11);
        //        ArrayList<ArrayList<String>> lines2 = printTreeByLinesBFS(temp);
        //        System.out.println(Arrays.toString(lines2.toArray()));
    }

}
