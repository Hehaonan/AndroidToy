package com.android.hhn.javalib.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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

        /**
         * 前序遍历
         *
         * @param root
         */
        public void preOrderTraversal(TreeNode root) {
            if (root == null) {
                return;
            }
            System.out.print(root.dataInt() + "->");
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
            System.out.print(root.data + "->");
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
        root.preOrderTraversal(root);
        System.out.println("\n中序：");
        root.inOrderTraversal(root);
        System.out.println("\n后序：");
        root.postOrderTraversal(root);
    }

    /**
     * 所有节点的值+1
     *
     * @param root
     */
    private static void allNodePlusOne(TreeNode root) {
        if (root == null)
            return;
        root.data += "+1";
        allNodePlusOne(root.left);
        allNodePlusOne(root.right);
    }

    /**
     * 计算有多少个节点
     *
     * @param root
     *
     * @return
     */
    private static int countTreeNode(TreeNode root) {
        if (null == root) { // 终止条件
            return 0;
        }
        return 1 + countTreeNode(root.left) + countTreeNode(root.right);
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
     * 求二叉树的深度，递归，深度优先
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

    /**
     * 打印栈
     *
     * @param stack
     */
    private static void printStack(Stack<TreeNode> stack) {
        // 打印栈的情况
        TreeNode[] array = stack.toArray(new TreeNode[0]);
        for (TreeNode t : array) {
            System.out.print(t.data + "->");
        }
        System.out.println();
    }

    /**
     * 求二叉树的深度，非递归，stack实现，深度优先
     *
     * @param root 二叉树根结点
     *
     * @return 深度
     */
    private static int treeDepthByStack(TreeNode root) {
        if (root == null)
            return 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode lastVisit = root;// 最后访问的节点
        int depth = 0;
        while (!stack.isEmpty()) {
            depth = Math.max(depth, stack.size());
            // 打印栈的情况,能更好的理解
            printStack(stack);
            TreeNode top = stack.peek();
            // 栈顶节点的左或者右，不等于之前访问的节点，代表该路径没有走过
            // left、right为null 代表循环到了树的叶节点
            System.out.println("top:" + top.data);
            if (top.left != null && lastVisit != top.left && lastVisit != top.right) {
                stack.push(top.left);// 先左后右
            } else if (top.right != null && lastVisit != top.right) {
                stack.push(top.right);
            } else {
                lastVisit = stack.pop();// 记录最后访问的节点，并弹栈
                System.out.println("lastVisit: " + lastVisit.data + ",并弹出" + lastVisit.data);
            }
        }
        return depth;
        //A->
        //A->B->
        //A->B->D->
        //lastVisit: D,并弹出D
        //A->B->
        //A->B->E->
        //lastVisit: E,并弹出E
        //A->B->
        //lastVisit: B,并弹出B
        //A->
        //A->C->
        //A->C->F->
        //lastVisit: F,并弹出F
        //A->C->
        //A->C->G->
        //lastVisit: G,并弹出G
        //A->C->
        //lastVisit: C,并弹出C
        //A->
        //lastVisit: A,并弹出A
    }

    /**
     * 求二叉树的深度，循环，使用队列，广度优先
     *
     * @param root
     *
     * @return
     */
    private static int treeDepthByLoop(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        if (root == null)
            return 0;
        q.offer(root);// 根节点入队列
        int depth = 0;
        while (!q.isEmpty()) {
            int len = q.size();
            System.out.println("每层个数" + len);
            depth++;
            while (len-- > 0) { // 每一层遍历完再向下
                TreeNode tmp = q.poll(); // 弹出队列的head
                if (tmp.left != null)
                    q.offer(tmp.left);
                if (tmp.right != null)
                    q.offer(tmp.right);
            }
        }
        return depth;
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
     * 从上到下打印二叉树，不分行
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
     * 把二叉树打印成多行，广度优先
     *
     * @param pRoot 二叉树根节点
     *
     * @return 结果list
     */
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
                res.add(node.data);// 同层添加到一组
            }
            list.add(res);
        }
        return list;
    }

    /**
     * 把二叉树打印成多行，深度优先
     *
     * @param root
     *
     * @return
     */
    private static ArrayList<ArrayList<String>> printTreeByLinesDFS(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        dfs(root, list, 1);
        return list;
    }

    private static void dfs(TreeNode root, ArrayList<ArrayList<String>> list, int level) {
        if (root == null)
            return;
        if (list.size() < level) {
            list.add(new ArrayList<String>());
        }
        list.get(level - 1).add(root.data);
        dfs(root.left, list, level + 1);
        dfs(root.right, list, level + 1);
    }

    /**
     * 输入两棵二叉树A，B，判断B是不是A的子结构
     *
     * @param root1
     * @param root2
     *
     * @return
     */
    private static boolean isSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return false;
        }
        return isSame(root1, root2) || isSubtree(root1.left, root2) || isSubtree(root1.right, root2);
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

    /**
     * 将二叉树转换为它的镜像
     *
     * @param root
     */
    private static void getTreeMirrored(TreeNode root) {
        if (root == null) {// 不能为空
            return;
        }
        if (root.left == null && root.right == null) {// 左右都为空
            return;
        }
        // 左右互换
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        getTreeMirrored(root.left);
        getTreeMirrored(root.right);
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

        // System.out.println("深度：" + treeDepthByStack(root));

        //        findPath(root, 19);
        //        for (ArrayList<String> temp : res) {
        //            for (String str : temp) {
        //                System.out.print(str + ":");
        //            }
        //            System.out.println();
        //        }

        // ArrayList<String> list = printTreeTopToBottom(root);
        // System.out.println(Arrays.toString(list.toArray()));
        // ArrayList<ArrayList<String>> lines = printTreeByLinesBFS(root);
        // System.out.println(Arrays.toString(lines.toArray()));

        // ArrayList<ArrayList<String>> lines = printTreeByLinesDFS(root);
        // System.out.println(Arrays.toString(lines.toArray()));

        // TreeNode root2 = new TreeNode(b, f, "C");
        // System.out.println(hasSubtree(root, root2));

        // System.out.println(isBalanced(root));

        // TreeNode root1 = new TreeNode(f, g, "A");
        // TreeNode root2 = new TreeNode(g, f, "A");
        // System.out.println(isMirroredTree(root1, root2));

        // getTreeMirrored(root);
        // ArrayList<ArrayList<String>> lines = printTreeTopToBottomByLine(root);
        // System.out.println(Arrays.toString(lines.toArray()));

        System.out.println(countTreeNode(root));

//        Stack<TreeNode> stack = new Stack<>();
//        stack.push(d);
//        stack.push(e);
//        stack.push(f);
//        System.out.println(Arrays.toString(new ArrayList<>(getUsedStackList(stack)).toArray()));
    }

    private static List<String> getUsedStackList(Stack<TreeNode> stack) {
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        List<String> hybridIds = new ArrayList<>();
        TreeNode[] stackArr = stack.toArray(new TreeNode[0]);
        for (TreeNode t : stackArr) {
            hybridIds.add("\"" + t.data + "\"");
        }
        return hybridIds;
    }


}
