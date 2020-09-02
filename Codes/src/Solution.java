public class Solution {
    public static void main(String[] args) {
        Trees.TreeNode root = new Trees.TreeNode(1);
        root.left = new Trees.TreeNode(2);
        root.left.left = new Trees.TreeNode(3);

        System.out.println(Trees.isBalancedTree(root));
    }
}