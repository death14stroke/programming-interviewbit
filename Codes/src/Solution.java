public class Solution {
    public static void main(String[] args) {
        Trees.TreeNode root = new Trees.TreeNode(1);
        root.left = new Trees.TreeNode(2);
        root.right = new Trees.TreeNode(3);
        root.left.right = new Trees.TreeNode(4);
        root.left.right.right = new Trees.TreeNode(5);

        System.out.println(Trees.rightView(root));
    }
}