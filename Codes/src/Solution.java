public class Solution {
    public static void main(String[] args) {
        Trees.TreeLinkNode root = new Trees.TreeLinkNode(1);
        root.left = new Trees.TreeLinkNode(2);
        root.right = new Trees.TreeLinkNode(3);
        root.left.left = new Trees.TreeLinkNode(4);
        root.left.right = new Trees.TreeLinkNode(5);
        root.right.left = new Trees.TreeLinkNode(6);
        root.right.right = new Trees.TreeLinkNode(7);

        Trees.rightNextPointers(root);

        Trees.TreeLinkNode p = root;
        while (p != null) {
            Trees.TreeLinkNode q = p;
            while (q != null) {
                System.out.print(q.val + " -> ");
                q = q.next;
            }
            System.out.print("null\n");

            if (p.left != null)
                p = p.left;
            else if (p.right != null)
                p = p.right;
            else
                p = null;
        }
    }
}