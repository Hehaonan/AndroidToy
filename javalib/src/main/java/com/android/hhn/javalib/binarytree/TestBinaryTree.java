package com.android.hhn.javalib.binarytree;

import java.util.ArrayList;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/4/1,5:14 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class TestBinaryTree {

    private static class TreeNode {
        TreeNode left; // 左节点
        TreeNode right; // 右节点
        String data; // 存储的数据

        public TreeNode(TreeNode left, TreeNode right, String data) { // 节点初始化时指明子节点与数据
            this.left = left;
            this.right = right;
            this.data = data;
        }


        /**
         * 获取节点的数值
         * 大写字母A-Z的ASCII码为：65-90；
         *
         * @return
         */
        public int dataInt() {
            if (data.isEmpty())
                return 0;
            return ((int) data.charAt(0)) / 10;
        }

        public void preTraverse(TreeNode root) {
            if (root == null) {
                return;
            }
            System.out.print(root.dataInt() + "->");
            preTraverse(root.left);
            preTraverse(root.right);
        }

        public void midTraverse(TreeNode root) {
            if (root == null) {
                return;
            }
            midTraverse(root.left);
            System.out.print(root.data + "->");
            midTraverse(root.right);
        }

        public void backTraverse(TreeNode root) {
            if (root == null) {
                return;
            }
            backTraverse(root.left);
            backTraverse(root.right);
            System.out.print(root.data + "->");
        }
    }

    /**
     * 全部遍历方式
     *
     * @param root
     */
    private static void allTraverse(TreeNode root) {
        System.out.println("前序：");
        root.preTraverse(root);
        System.out.println("\n中序：");
        root.midTraverse(root);
        System.out.println("\n后序：");
        root.backTraverse(root);
    }

    private static void allNodePlusOne(TreeNode root) {
        if (root == null)
            return;
        root.data += "+1";
        allNodePlusOne(root.left);
        allNodePlusOne(root.right);
    }

    /**
     * 判断两棵树是否相同
     *
     * @param root1
     * @param root2
     *
     * @return
     */
    private static boolean isSameTree(TreeNode root1, TreeNode root2) {
        // 都为空的话，显然相同
        if (root1 == null && root2 == null)
            return true;
        // 一个为空，一个非空，显然不同
        if (root1 == null || root2 == null)
            return false;
        // 两个都非空，但 val 不一样也不行
        if (!root1.data.equals(root2.data))
            return false;
        // root1 和 root2 该比的都比完了
        return isSameTree(root1.left, root2.left)
                && isSameTree(root1.right, root2.right);
    }

    /**
     * 求二叉树的深度
     *
     * @param root 二叉树根结点
     *
     * @return 深度
     */
    private static int treeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int lDepth = treeDepth(root.left);
        int rDepth = treeDepth(root.right);
        return 1 + Math.max(lDepth, rDepth);
    }


    /**
     * 找出二叉树中和为某一值的路径（必须从根节点到叶节点）
     *
     * @param root   二叉树的根结点
     * @param target 目标值
     *
     * @return 结果list
     */
    private static ArrayList<ArrayList<String>> findPath(TreeNode root, int target) {
        findPathR(root, target, new ArrayList<String>());
        return res;
    }

    private static ArrayList<ArrayList<String>> res = new ArrayList<>();

    private static void findPathR(TreeNode root, int target, ArrayList<String> list) {
        if (root == null) {
            return;
        }
        list.add(root.data);
        target -= root.dataInt();
        if (target == 0 && root.left == null && root.right == null) {
            res.add(new ArrayList<String>(list));
        } else {
            findPathR(root.left, target, list);
            findPathR(root.right, target, list);
        }
        list.remove(list.size() - 1);
    }

    public static void main(String[] args) {
        TreeNode g = new TreeNode(null, null, "G");
        TreeNode f = new TreeNode(null, null, "F");
        TreeNode d = new TreeNode(null, null, "D");
        TreeNode e = new TreeNode(null, null, "E");
        TreeNode c = new TreeNode(f, g, "C");
        TreeNode b = new TreeNode(d, e, "B");
        TreeNode root = new TreeNode(b, c, "A");

        //        allTraverse(root);

        //        allNodePlusOne(root);
        //        root.preTraverse(root);

        //        TreeNode root2 = new TreeNode(d, e, "A");
        //        System.out.println(isSameTree(root, root));

        //        System.out.println(treeDepth(root));

        //        findPath(root, 19);
        //        for (ArrayList<String> temp : res) {
        //            for (String str : temp) {
        //                System.out.print(str + ":");
        //            }
        //            System.out.println();
        //        }

    }

}
