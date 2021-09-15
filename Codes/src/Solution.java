public class Solution {
    public static void main(String[] args) {
        DP.TreeNode root = new DP.TreeNode(-10);
        root.left = new DP.TreeNode(-20);
        root.right = new DP.TreeNode(-30);

        System.out.println(DP.maxSumPathBinaryTree(root));
    }
}