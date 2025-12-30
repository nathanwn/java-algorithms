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
    public int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        getHeight(root);
        return diameter;
    }

    // height == 0 if root == null
    // height == 1 if root is leaf
    public int getHeight(TreeNode root) {
        if (root == null) return 0;
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        int height = Math.max(leftHeight, rightHeight) + 1;
        diameter = Math.max(diameter, leftHeight + rightHeight);
        return height;
    }
}
