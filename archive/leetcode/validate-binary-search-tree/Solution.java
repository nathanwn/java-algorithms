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
    HashMap<TreeNode, TreeNode> maxNodeMap = new HashMap<>();
    HashMap<TreeNode, TreeNode> minNodeMap = new HashMap<>();

    public boolean isValidBST(TreeNode root) {
        if(root == null) return true;
        boolean valid = true;
        if (root.left != null) {
            TreeNode leftMaxNode = getMaxNode(root.left);
            valid &= leftMaxNode.val < root.val;
        }
        if (root.right != null) {
            TreeNode rightMinNode = getMinNode(root.right);
            valid &= root.val < rightMinNode.val;
        }
        valid &= isValidBST(root.left);
        valid &= isValidBST(root.right);
        return valid;
    }

    public TreeNode getMinNode(TreeNode root) {
        if (minNodeMap.containsKey(root)) {
            return minNodeMap.get(root);
        }
        TreeNode result = null;
        if (root.left == null) {
            result = root;
        } else {
            result = getMinNode(root.left);
        }
        minNodeMap.put(root, result);
        return result;
    }

    public TreeNode getMaxNode(TreeNode root) {
        if (maxNodeMap.containsKey(root)) {
            return maxNodeMap.get(root);
        }
        TreeNode result = null;
        if (root.right == null) {
            result = root;
        } else {
            result = getMaxNode(root.right);
        }
        maxNodeMap.put(root, result);
        return result;
    }
}
