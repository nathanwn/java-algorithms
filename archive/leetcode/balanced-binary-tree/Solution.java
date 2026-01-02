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
    public boolean isBalanced(TreeNode root) {
        return traverse(root).isBalanced;
    }

    public Result traverse(TreeNode root) {
        if (root == null) {
            return new Result(true, 0);
        }
        Result leftResult = traverse(root.left);
        Result rightResult = traverse(root.right);
        int height = Math.max(leftResult.height, rightResult.height) + 1;
        boolean isBalanced = Math.abs(leftResult.height - rightResult.height) < 2;
        isBalanced &= leftResult.isBalanced;
        isBalanced &= rightResult.isBalanced;
        return new Result(isBalanced, height);
    }
}

class Result {
    boolean isBalanced;
    int height;

    Result(boolean isBalanced, int height) {
        this.isBalanced = isBalanced;
        this.height = height;
    }
}
