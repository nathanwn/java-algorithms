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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p == q) return p;
        TreeNode a = null;
        TreeNode pp = root;
        TreeNode qq = root;
        while (pp == qq) {
            a = pp;
            if (p.val < pp.val) {
                pp = pp.left;
            } else if (p.val > pp.val) {
                pp = pp.right;
            }
            if (q.val < qq.val) {
                qq = qq.left;
            } else if (q.val > qq.val) {
                qq = qq.right;
            }
        }
        return a;
    }
}

