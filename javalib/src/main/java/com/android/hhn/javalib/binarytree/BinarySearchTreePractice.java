package com.android.hhn.javalib.binarytree;

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

    private static boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    private static boolean isValidBST(TreeNode root, TreeNode minNode, TreeNode maxNode) {
        if (null == root) {
            return true;
        }
        //针对左子树 如果大于节点的值 BTS不成立
        if (null != maxNode && root.value >= maxNode.value)
            return false;
        //针对右子树 如果小于节点的值 BTS不成立
        if (null != minNode && root.value <= minNode.value)
            return false;
        // 左子树：当前root->maxNode
        // 右子树：当前root->minNode
        return isValidBST(root.left, minNode, root) && isValidBST(root.right, root, maxNode);
    }

    public static void main(String[] args) {
        TreeNode k = new TreeNode(null, null, 20);
        TreeNode j = new TreeNode(null, null, 14);
        TreeNode i = new TreeNode(j, k, 17);
        TreeNode h = new TreeNode(null, null, 12);
        TreeNode c = new TreeNode(h, i, 13);

        TreeNode g = new TreeNode(null, null, 6);
        TreeNode f = new TreeNode(null, null, 2);
        TreeNode e = new TreeNode(null, null, 9);
        TreeNode d = new TreeNode(f, g, 5);
        TreeNode b = new TreeNode(d, e, 8);
        TreeNode root = new TreeNode(b, c, 10);

        System.out.println("是否是BTS：" + isValidBST(root));
    }

}
