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
        ArrayDeque<TreeNode> st = new ArrayDeque<>();
        TreeNode cur = root;
        int cnt = 0;
        while (root != null || !st.isEmpty()) {
            while (cur != null) {
                st.addLast(cur);
                cur = cur.left;
            }
            cur = st.removeLast();
            cnt++;
            if (cnt == k) {
                return cur.val;
            }
            cur = cur.right;
        }
        return -1;
    }
}
