package com.android.hhn.javalib.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/4/1,5:14 PM ;<p/>
 * Description: 二叉树练习;<p/>
 * Other: ;
 */
public class BinaryTreePractice {

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
        // return 1 + Math.min(lDepth, rDepth); // 最小深度
    }


    private static int treeDepthByLoop(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        if (root == null)
            return 0;
        q.offer(root);// 根节点入队列
        int level = 0;
        while (!q.isEmpty()) {
            int len = q.size();
            System.out.println("每层个数" + len);
            level++;
            while (len-- > 0) { // 每一层遍历完再向下
                TreeNode tmp = q.poll(); // 弹出队列的head
                if (null != tmp.left)
                    q.offer(tmp.left);
                if (null != tmp.right)
                    q.offer(tmp.right);
            }
        }
        return level;
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

    /**
     * 从上到下打印二叉树
     *
     * @param root 二叉树根节点
     *
     * @return 结果list
     */
    private static ArrayList<String> printTreeTopToBottom(TreeNode root) {
        ArrayList<String> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);// 根节点入队列
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll(); // 先进先出
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            list.add(node.data);
        }
        return list;
    }

    /**
     * 把二叉树打印成多行
     *
     * @param pRoot 二叉树根节点
     *
     * @return 结果list
     */
    private static ArrayList<ArrayList<String>> printTreeTopToBottomByLine(TreeNode pRoot) {
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
                res.add(node.data);// 同层添加到一组
            }
            list.add(res);
        }
        return list;
    }

    /**
     * 输入两棵二叉树A，B，判断B是不是A的子结构
     *
     * @param root1
     * @param root2
     *
     * @return
     */
    private static boolean hasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return false;
        }
        return isSame(root1, root2) || hasSubtree(root1.left, root2) || hasSubtree(root1.right, root2);
    }

    private static boolean isSame(TreeNode root1, TreeNode root2) {
        if (root2 == null) {// 子树可以为空
            return true;
        }
        // 主树不能为空
        if (root1 == null) {
            return false;
        }
        if (!root1.data.equals(root2.data)) {
            return false;
        }
        return isSame(root1.left, root2.left) && isSame(root1.right, root2.right);
    }

    /**
     * 判断是否是平衡二叉树
     *
     * @param root 二叉树根结点
     *
     * @return 是否是平衡二叉树
     */
    private static boolean isBalanced(TreeNode root) {
        if (root == null) { // 空树也是平衡二叉树
            return true;
        }
        isBalanced = true;
        treeDepthIsBalanced(root);
        return isBalanced;
    }

    private static boolean isBalanced;

    private static int treeDepthIsBalanced(TreeNode root) {
        if (root == null || !isBalanced) {
            return 0;
        }
        int lDepth = treeDepthIsBalanced(root.left);
        int rDepth = treeDepthIsBalanced(root.right);
        // 后序遍历逻辑
        if (Math.abs(lDepth - rDepth) > 1) {
            isBalanced = false;
        }
        return 1 + Math.max(lDepth, rDepth);
    }

    /**
     * 判断两棵树否是镜像二叉树
     *
     * @param root1
     * @param root2
     *
     * @return
     */
    private static boolean isMirroredTree(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }
        // 前序逻辑
        if (!root1.data.equals(root2.data)) {
            return false;
        }
        return isMirroredTree(root1.left, root2.right) && isMirroredTree(root1.right, root2.left);
    }

    public static void main(String[] args) {
        TreeNode g = new TreeNode(null, null, "G");
        TreeNode f = new TreeNode(null, null, "F");
        TreeNode e = new TreeNode(null, null, "E");
        TreeNode d = new TreeNode(null, null, "D");
        TreeNode c = new TreeNode(f, g, "C");
        TreeNode b = new TreeNode(d, e, "B");
        TreeNode root = new TreeNode(b, c, "A");

        //        allTraverse(root);

        //        allNodePlusOne(root);
        //        root.preTraverse(root);

        //        TreeNode root2 = new TreeNode(d, e, "A");
        //        System.out.println(isSameTree(root, root));

        //        System.out.println(treeDepth(root));
        //        System.out.println("深度：" + treeDepthByLoop(root));
        //        findPath(root, 19);
        //        for (ArrayList<String> temp : res) {
        //            for (String str : temp) {
        //                System.out.print(str + ":");
        //            }
        //            System.out.println();
        //        }

        // ArrayList<String> list = printTreeTopToBottom(root);
        // System.out.println(Arrays.toString(list.toArray()));
        // ArrayList<ArrayList<String>> lines = printTreeTopToBottomByLine(root);
        // System.out.println(Arrays.toString(lines.toArray()));

        // TreeNode root2 = new TreeNode(b, f, "C");
        // System.out.println(hasSubtree(root, root2));

        // System.out.println(isBalanced(root));

        TreeNode root1 = new TreeNode(f, g, "A");
        TreeNode root2 = new TreeNode(g, f, "A");
        System.out.println(isMirroredTree(root1, root2));
    }

}
