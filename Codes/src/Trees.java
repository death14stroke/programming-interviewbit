class Trees {
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // https://www.interviewbit.com/problems/valid-binary-search-tree/
    static int isValidBST(TreeNode root) {
        return isValidBSTUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE) ? 1 : 0;
    }

    // util to check if tree is BST or not
    private static boolean isValidBSTUtil(TreeNode root, int minRange, int maxRange) {
        if (root == null)
            return true;

        // check if root is in the range or not and are the children in their respective ranges or not
        return root.val >= minRange && root.val <= maxRange &&
                isValidBSTUtil(root.left, minRange, root.val - 1) &&
                isValidBSTUtil(root.right, root.val + 1, maxRange);
    }

    // https://www.interviewbit.com/problems/next-greater-number-bst/
    static TreeNode getSuccessor(TreeNode root, int x) {
        TreeNode successor = null;
        // find the required node in tree and mark all greater nodes as successor
        while (root.val != x) {
            if (x < root.val) {
                successor = root;
                root = root.left;
            } else
                root = root.right;
        }

        // if right child of the found node is not null, successor will be the leftmost child of this right child
        if (root.right != null) {
            root = root.right;
            while (root.left != null)
                root = root.left;

            return root;
        }

        // else the last found greater element in the path will be successor
        return successor;
    }

    // https://www.interviewbit.com/problems/valid-bst-from-preorder/
    static int isValidBSTFromPreorder(int[] A) {
        int n = A.length;
        // always a valid BST
        if (n < 3)
            return 1;

        // take a 3 node BST [1, 2, 3]. Among all preorder traversals, [2, 3, 1] is not a valid BST.
        // Hence if A[a] < A[b] and A[b] > A[c] and A[a] > A[c] where a, b, c are
        // three consecutive positions in the preorder traversal, it is not a valid BST
        int a = 0, b = 1, c = 2;
        while (c < n) {
            if (A[a] < A[b] && A[b] > A[c] && A[a] > A[c])
                return 0;

            // check next triplet
            a++;
            b++;
            c++;
        }

        // none of the triplets satisfy the invalid condition
        return 1;
    }
}