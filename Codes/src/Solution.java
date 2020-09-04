public class Solution {
    public static void main(String[] args) {
        Trees.TreeNode root = new Trees.TreeNode(1);
        root.left = new Trees.TreeNode(2);
        root.right = new Trees.TreeNode(5);
        root.left.left = new Trees.TreeNode(3);
        root.left.right = new Trees.TreeNode(4);
        root.right.right = new Trees.TreeNode(6);

        System.out.println(Trees.preorderTraversal(root));

        Trees.TreeNode curr = Trees.flattenBinaryTreeToLinkedList(root);
        while (curr != null) {
            System.out.print(curr.val + " -> ");
            curr = curr.right;
        }
        System.out.print("null");
    }
}