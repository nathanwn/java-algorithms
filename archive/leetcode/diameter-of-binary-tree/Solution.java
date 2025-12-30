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
    HashMap<TreeNode, Integer> height = new HashMap<>();

    public int diameterOfBinaryTree(TreeNode root) {
        int leftLongestPath = 0;
        int leftDiameter = -1;
        if (root.left != null) {
            leftLongestPath = getLongestPathFromRoot(root.left) + 1;
            leftDiameter = diameterOfBinaryTree(root.left);
        }
        int rightLongestPath = 0;
        int rightDiameter = -1;
        if (root.right != null) {
            rightLongestPath = getLongestPathFromRoot(root.right) + 1;
            rightDiameter = diameterOfBinaryTree(root.right);
        }
        int bestThroughRoot = leftLongestPath + rightLongestPath;
        int ans = bestThroughRoot;
        ans = Math.max(ans, leftDiameter);
        ans = Math.max(ans, rightDiameter);
        return ans;
    }

    public int getLongestPathFromRoot(TreeNode root) {
        if (height.containsKey(root)) {
            return height.get(root);
        }
        int ans = 0;
        if (root.left != null) {
            ans = Math.max(ans, getLongestPathFromRoot(root.left) + 1);
        }
        if (root.right != null) {
            ans = Math.max(ans, getLongestPathFromRoot(root.right) + 1);
        }
        height.put(root, ans);
        return ans;
    }
}
