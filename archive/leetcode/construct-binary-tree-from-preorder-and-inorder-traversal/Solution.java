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
    int preorderId;
    int n;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        n = preorder.length;
        Solver solver = new Solver(preorder, inorder);
        return solver.createTree();
    }
}

class Solver {
    int n;
    int[] preorder;
    int[] inorder;
    HashMap<Integer, Integer> valToId;
    int preorderId;

    Solver(int[] preorder, int[] inorder) {
        this.n = preorder.length;
        this.preorder = preorder;
        this.inorder = inorder;
        valToId = new HashMap<>();
        for (int i = 0; i < n; i++) {
            valToId.put(inorder[i], i);
        }
    }

    TreeNode createTree() {
        preorderId = 0;
        return createTree(0, n - 1);
    }

    // Divide & Conquer
    TreeNode createTree(int inorderFrom, int inorderTo) {
        if (inorderFrom > inorderTo) {
            return null;
        }
        int val = preorder[preorderId];
        preorderId++;
        if (inorderFrom == inorderTo) {
            return new TreeNode(val);
        }
        int valId = valToId.get(val);
        TreeNode left = createTree(inorderFrom, valId - 1);
        TreeNode right = createTree(valId + 1, inorderTo);
        return new TreeNode(val, left, right);
    }
}
