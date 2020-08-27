public class Solution {
    public static void main(String[] args) {
        Trees.TreeNode root = new Trees.TreeNode(1000);
        root.left = new Trees.TreeNode(200);

        System.out.println(Trees.hasPathSum(root, 1000));
    }
}