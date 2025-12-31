/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Solver solver = new Solver(k);
        solver.traverse(root);
        return solver.res.val;
    }
}

class Solver {
    int c;
    int k;
    TreeNode res;

    Solver(int k) {
        this.c = 0;
        this.k = k;
    }

    void traverse(TreeNode root) {
        if (root == null) return;
        traverse(root.left);
        c++;
        if (c == k) {
            res = root;
            return;
        }
        traverse(root.right);
    }
}
