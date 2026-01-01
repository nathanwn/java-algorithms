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
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int depth = 1;
        if (root.left != null) {
            depth = Math.max(depth, maxDepth(root.left) + 1);
        }
        if (root.right != null) {
            depth = Math.max(depth, maxDepth(root.right) + 1);
        }
        return depth;
    }
}
