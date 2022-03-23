import java.util.*;

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
        TreeNode succ = null;
        // find the required node in tree and mark all greater nodes as successor
        while (root.val != x) {
            if (x < root.val) {
                succ = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        // if right child of the found node is not null, successor will be the leftmost child of this right child
        if (root.right != null) {
            succ = root.right;
            while (succ.left != null)
                succ = succ.left;
        }
        // else the last found greater element in the path will be successor
        return succ;
    }

    // https://www.interviewbit.com/problems/valid-bst-from-preorder/
    static int isValidBSTFromPreorder(int[] A) {
        int n = A.length;
        // always a valid BST
        if (n <= 2)
            return 1;
        // take a 3 node BST [1, 2, 3]. Among all preorder traversals, [2, 3, 1] is not a valid BST. Hence,
        // if A[b] > A[a] and A[a] > A[c] where a, b, c are three consecutive positions in the preorder traversal, it is not a valid BST
        for (int a = 0, b = 1, c = 2; c < n; a++, b++, c++) {
            if (A[b] > A[a] && A[a] > A[c])
                return 0;
        }
        // none of the triplets satisfy the invalid condition
        return 1;
    }

    // https://www.interviewbit.com/problems/kth-smallest-element-in-tree/
    private static int pos, res;

    static int kthSmallest(TreeNode root, int k) {
        // init global variables to keep track of position and result in inorder traversal
        pos = k;
        res = 0;
        // inorder traversal as it will traverse in ascending order
        inorder(root);

        return res;
    }

    // util to perform inorder traversal
    private static boolean inorder(TreeNode root) {
        // reached end
        if (root == null)
            return false;
        // kth node found in left subtree
        if (inorder(root.left))
            return true;

        // visit current node. Update position
        pos--;
        // found kth node, update result variable and return
        if (pos == 0) {
            res = root.val;
            return true;
        }
        // visit right child
        return inorder(root.right);
    }

    // https://www.interviewbit.com/problems/2sum-binary-tree/
    static int twoSumBst(TreeNode root, int B) {
        // if 0 or 1 child
        if (root == null || (root.left == null && root.right == null))
            return 0;

        // stacks to keep track of inorder and reverse inorder traversal
        Stack<TreeNode> s1 = new Stack<>(), s2 = new Stack<>();
        boolean done1 = false, done2 = false;
        int val1 = 0, val2 = 0;
        TreeNode curr1 = root, curr2 = root;

        while (true) {
            // if should perform inorder traversal
            if (!done1) {
                // keep moving left till possible
                while (curr1 != null) {
                    s1.push(curr1);
                    curr1 = curr1.left;
                }
                // get the smaller value from inorder traversal and then shift to right child
                curr1 = s1.pop();
                val1 = curr1.val;
                curr1 = curr1.right;
                // mark inorder traversal as done
                done1 = true;
            }
            // if should perform reverse inorder traversal
            if (!done2) {
                // keep moving right till possible
                while (curr2 != null) {
                    s2.push(curr2);
                    curr2 = curr2.right;
                }
                // get the smaller value from reverse inorder traversal and then shift to left child
                curr2 = s2.pop();
                val2 = curr2.val;
                curr2 = curr2.left;
                // mark reverse inorder traversal as done
                done2 = true;
            }

            // no pairs found
            if (val1 >= val2)
                return 0;
            // found pair
            if (val1 + val2 == B)
                return 1;
                // sum is less...move ahead in inorder traversal
            else if (val1 + val2 < B)
                done1 = false;
                // sum is more...move ahead in reverse inorder traversal
            else
                done2 = false;
        }
    }

    // https://www.interviewbit.com/problems/bst-iterator/
    static class BSTIterator {
        // stack to store leftmost of inorder traversal
        private final Stack<TreeNode> s;

        BSTIterator(TreeNode root) {
            s = new Stack<>();
            // perform inorder traversal
            while (root != null) {
                s.push(root);
                root = root.left;
            }
        }

        /**
         * @return whether we have a next smallest number
         */
        public boolean hasNext() {
            return !s.empty();
        }

        /**
         * @return the next smallest number
         */
        public int next() {
            // get next minimum
            TreeNode curr = s.pop();
            int val = curr.val;
            // move to right node
            curr = curr.right;
            // perform inorder traversal
            while (curr != null) {
                s.push(curr);
                curr = curr.left;
            }

            return val;
        }
    }

    // https://www.interviewbit.com/problems/recover-binary-search-tree/
    private static TreeNode first, second, prev;

    static int[] recoverBST(TreeNode root) {
        // initialize
        first = second = prev = null;
        // check by performing inorder traversal
        recoverBSTUtil(root);
        // if tree was not correct, return nodes to be corrected
        if (first != null && second != null)
            return new int[]{second.val, first.val};
        // tree was already correct
        return new int[0];
    }

    // util to check if BST is correct by inorder traversal
    private static void recoverBSTUtil(TreeNode root) {
        if (root == null)
            return;

        // check if left subtree is correct BST
        recoverBSTUtil(root.left);
        // if previous node is greater than current in inorder traversal
        if (prev != null && prev.val > root.val) {
            // if not found first node to swap
            if (first == null)
                first = prev;
            // update second node to swap (can be adjacent node or a far away node)
            second = root;
        }

        prev = root;
        // check if right subtree is correct BST
        recoverBSTUtil(root.right);
    }

    // https://www.interviewbit.com/problems/xor-between-two-arrays/
    static int maxXor(int[] A, int[] B) {
        BinaryTrie trie = new BinaryTrie();
        // add all elements of A into binary trie
        for (int val : A)
            trie.add(val);

        int res = 0;
        // for each value in B, find max xor
        for (int val : B)
            res = Math.max(res, maxXorUtil(trie.root, val));

        return res;
    }

    // util to find xor with number differing in most positions in trie
    private static int maxXorUtil(BinaryTrieNode root, int x) {
        BinaryTrieNode curr = root;
        // for each bit pos
        for (int i = 31; i >= 0; i--) {
            int bit = (x & (1 << i)) == 0 ? 0 : 1;
            // if opposite bit present
            if (curr.child[1 - bit] != null)
                curr = curr.child[1 - bit];
                // else have to traverse same bit
            else
                curr = curr.child[bit];
        }
        // return max xor value
        return curr.val ^ x;
    }

    // binary trie
    static class BinaryTrie {
        final BinaryTrieNode root;

        BinaryTrie() {
            root = new BinaryTrieNode();
        }

        public void add(int x) {
            BinaryTrieNode curr = root;
            // add all bit positions from MSB to trie
            for (int i = 31; i >= 0; i--) {
                int bit = (x & (1 << i)) == 0 ? 0 : 1;

                if (curr.child[bit] == null)
                    curr.child[bit] = new BinaryTrieNode();
                curr = curr.child[bit];
            }

            curr.val = x;
        }
    }

    // binary trie node
    static class BinaryTrieNode {
        BinaryTrieNode[] child;
        int val;

        BinaryTrieNode() {
            child = new BinaryTrieNode[2];
        }
    }

    // https://www.interviewbit.com/problems/hotel-reviews/
    static int[] hotelReviews(String A, String[] B) {
        Trie trie = new Trie();
        // insert each good word into trie
        for (String word : A.split("_"))
            trie.add(word);

        int n = B.length;
        // create array of a pair of index and #good words of review
        int[][] reviews = new int[n][2];
        for (int i = 0; i < n; i++) {
            int freq = 0;
            // check if each word of review is present in trie or not
            for (String word : B[i].split("_")) {
                if (trie.contains(word))
                    freq++;
            }

            reviews[i] = new int[]{i, freq};
        }
        // sort the list by #good words in decreasing order
        Arrays.sort(reviews, (a, b) -> b[1] - a[1]);
        // copy indices to result array
        int[] res = new int[n];
        for (int i = 0; i < n; i++)
            res[i] = reviews[i][0];
        return res;
    }

    // trie data structure
    static class Trie {
        private final TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        // add a word to trie
        public void add(String word) {
            TrieNode curr = root;

            for (char c : word.toCharArray()) {
                if (curr.child[c - 'a'] == null)
                    curr.child[c - 'a'] = new TrieNode();
                curr = curr.child[c - 'a'];
            }

            curr.isEnd = true;
        }

        // check if a word is present in trie or not
        public boolean contains(String word) {
            TrieNode curr = root;

            for (char c : word.toCharArray()) {
                if (curr.child[c - 'a'] == null)
                    return false;

                curr = curr.child[c - 'a'];
            }

            return curr.isEnd;
        }
    }

    // trie node structure
    static class TrieNode {
        TrieNode[] child;
        boolean isEnd;

        TrieNode() {
            child = new TrieNode[26];
        }
    }

    // https://www.interviewbit.com/problems/shortest-unique-prefix/
    static String[] shortestUniquePrefix(String[] words) {
        PrefixTrie trie = new PrefixTrie();
        // add all words in prefix trie
        for (String word : words)
            trie.add(word);
        // get the shortest prefix with last char as unique for each word inserted
        for (int i = 0; i < words.length; i++)
            words[i] = trie.getPrefix(words[i]);
        return words;
    }

    // prefix trie will contain count of each node occurring in all the words inserted
    static class PrefixTrie {
        private final PrefixTrieNode root;

        PrefixTrie() {
            root = new PrefixTrieNode();
        }

        // add word
        public void add(String word) {
            PrefixTrieNode curr = root;

            for (char c : word.toCharArray()) {
                if (curr.child[c - 'a'] == null)
                    curr.child[c - 'a'] = new PrefixTrieNode();

                curr = curr.child[c - 'a'];
                // update count of this node
                curr.cnt++;
            }
        }

        // get prefix string with the last character freq as 1
        public String getPrefix(String word) {
            PrefixTrieNode curr = root;
            // search for word
            for (int i = 0; i < word.length(); i++) {
                curr = curr.child[word.charAt(i) - 'a'];
                // found last unique char
                if (curr.cnt == 1)
                    return word.substring(0, i + 1);
            }

            return "";
        }
    }

    // node for Prefix Trie
    static class PrefixTrieNode {
        PrefixTrieNode[] child;
        int cnt;

        PrefixTrieNode() {
            child = new PrefixTrieNode[26];
        }
    }

    // https://www.interviewbit.com/problems/path-to-given-node/
    static ArrayList<Integer> findPath(TreeNode root, int B) {
        ArrayList<Integer> res = new ArrayList<>();
        // traverse all paths till we reach destination
        findPathUtil(root, B, res);

        return res;
    }

    // util to try all paths
    private static boolean findPathUtil(TreeNode root, int B, ArrayList<Integer> res) {
        // add current node to path
        res.add(root.val);
        // reached destination
        if (root.val == B)
            return true;
        // if found target by moving left or right
        if (root.left != null && findPathUtil(root.left, B, res))
            return true;
        if (root.right != null && findPathUtil(root.right, B, res))
            return true;

        // remove current node from path
        res.remove(res.size() - 1);

        return false;
    }

    // https://www.interviewbit.com/problems/remove-half-nodes/
    static TreeNode removeHalfNodes(TreeNode root) {
        if (root == null)
            return null;

        // recursively check for left and right children
        root.left = removeHalfNodes(root.left);
        root.right = removeHalfNodes(root.right);
        // if leaf node, do not modify root
        if (root.left == null && root.right == null)
            return root;
        // if only right child, make right child as root
        if (root.left == null)
            return root.right;
        // if only left child, make left child as root
        if (root.right == null)
            return root.left;
        // else both children present, do not modify root
        return root;
    }

    // https://www.interviewbit.com/problems/nodes-at-distance-k/
    static ArrayList<Integer> distanceK(TreeNode root, int B, int C) {
        ArrayList<Integer> res = new ArrayList<>();
        // recursively search for node with value B
        distanceKUtil(root, B, C, res);

        return res;
    }

    // util to return the distance from target node if found else -1
    private static int distanceKUtil(TreeNode root, int B, int C, ArrayList<Integer> res) {
        // empty node
        if (root == null)
            return -1;

        // found target node
        if (root.val == B) {
            // add all nodes at distance C in both subtrees of current node
            findExactDistanceNodes(root.left, C, res);
            findExactDistanceNodes(root.right, C, res);
            // return the distance from target
            return 0;
        }

        int dist = root.left != null ? distanceKUtil(root.left, B, C, res) : -1;
        // if found target node in left subtree
        if (dist != -1) {
            // update distance
            dist++;
            // if current node is at distance C from target, add to result
            if (dist == C)
                res.add(root.val);
                // else search for nodes in right subtree at remaining distance
            else
                findExactDistanceNodes(root.right, C - dist, res);
            // return the distance from target
            return dist;
        }

        dist = root.right != null ? distanceKUtil(root.right, B, C, res) : -1;
        // if found target node in right subtree
        if (dist != -1) {
            // update distance
            dist++;
            // if current node is at distance C from target, add to result
            if (dist == C)
                res.add(root.val);
                // else search for nodes in left subtree at remaining distance
            else
                findExactDistanceNodes(root.left, C - dist, res);
            // return the distance from target
            return dist;
        }
        // target node not found
        return -1;
    }

    // util to add all nodes at exact distance in result list
    private static void findExactDistanceNodes(TreeNode root, int dist, ArrayList<Integer> res) {
        // reached end
        if (root == null)
            return;

        // update remaining distance
        dist--;
        // reached node at required distance
        if (dist == 0) {
            res.add(root.val);
            return;
        }
        // recursively find exact distance nodes in both subtrees
        findExactDistanceNodes(root.left, dist, res);
        findExactDistanceNodes(root.right, dist, res);
    }

    // https://www.interviewbit.com/problems/balanced-binary-tree/
    static int isBalancedTree(TreeNode root) {
        // recursive util to calculate max height and check balancing
        return isBalancedTreeUtil(root) == -1 ? 0 : 1;
    }

    // util which returns -1 if not balanced else returns height of the current subtree
    private static int isBalancedTreeUtil(TreeNode root) {
        if (root == null)
            return 0;

        int lHeight = isBalancedTreeUtil(root.left);
        // left subtree is not balanced
        if (lHeight == -1)
            return -1;
        int rHeight = isBalancedTreeUtil(root.right);
        // right subtree is not balanced
        if (rHeight == -1)
            return -1;

        // if current node is not balanced
        if (Math.abs(lHeight - rHeight) >= 2)
            return -1;
        // return height of this subtree
        return Math.max(lHeight, rHeight) + 1;
    }

    // https://www.interviewbit.com/problems/maximum-edge-removal/
    @SuppressWarnings("unchecked")
    static int maxEdgeRemoval(int A, int[][] B) {
        List<Integer>[] adj = new List[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new LinkedList<>();
        // prepare adjacency list representation for the graph
        for (int[] edge : B) {
            int u = edge[0], v = edge[1];
            adj[u].add(v);
        }

        res = 0;
        // perform dfs to find no of nodes in current component
        dfs(1, adj);

        return res;
    }

    // recursive util to perform dfs
    private static int dfs(int u, List<Integer>[] adj) {
        int noOfNodes = 1;
        // for each neighbour
        for (int v : adj[u]) {
            // get number of nodes connected to this unvisited node
            int subComponentNodes = dfs(v, adj);
            // if this neighbour has even numbered nodes, detach it from current
            if (subComponentNodes % 2 == 0)
                res++;
                // else don't remove this edge. Let it remain part of current component
            else
                noOfNodes += subComponentNodes;
        }

        return noOfNodes;
    }

    // https://www.interviewbit.com/problems/merge-two-binary-tree/
    static TreeNode mergeBinaryTrees(TreeNode root1, TreeNode root2) {
        // if one node is null, return the other
        if (root1 == null)
            return root2;
        if (root2 == null)
            return root1;

        // update value of current node as it overlaps
        root1.val += root2.val;
        // recursively create left and right subtrees
        root1.left = mergeBinaryTrees(root1.left, root2.left);
        root1.right = mergeBinaryTrees(root1.right, root2.right);
        // return updated tree 1
        return root1;
    }

    // https://www.interviewbit.com/problems/symmetric-binary-tree/
    static int isSymmetric(TreeNode root) {
        // check if tree is mirror with self or not
        return isMirror(root, root) ? 1 : 0;
    }

    // util to check if two trees are mirror image of each other
    private static boolean isMirror(TreeNode root1, TreeNode root2) {
        // both nodes null so mirror image
        if (root1 == null && root2 == null)
            return true;
        // one node is null, trees are not mirror image
        if (root1 == null || root2 == null)
            return false;
        // trees are mirror image if the values at current nodes are same and
        // left and right subtrees of tree-1 are mirror with right and left subtrees of tree-2 respectively
        return root1.val == root2.val && isMirror(root1.left, root2.right) && isMirror(root1.right, root2.left);
    }

    // https://www.interviewbit.com/problems/identical-binary-trees/
    static int isSameTree(TreeNode root1, TreeNode root2) {
        // both nodes are null
        if (root1 == null && root2 == null)
            return 1;
        // either node is null or the values of nodes are not equal
        if (root1 == null || root2 == null || root1.val != root2.val)
            return 0;
        // current nodes are equal. Check recursively if left and right subtrees are equal or not
        return isSameTree(root1.left, root2.left) & isSameTree(root1.right, root2.right);
    }

    // https://www.interviewbit.com/problems/vertical-order-traversal-of-binary-tree/
    static ArrayList<ArrayList<Integer>> verticalOrderTraversal(TreeNode root) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;

        // perform level order traversal and mark vertical level of each node
        Queue<Pair<TreeNode, Integer>> q = new LinkedList<>();
        q.add(new Pair<>(root, 0));
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();

        while (!q.isEmpty()) {
            Pair<TreeNode, Integer> front = q.poll();
            // map current node to its vertical level
            map.computeIfAbsent(front.second, k -> new ArrayList<>()).add(front.first.val);
            // go to next horizontal level
            if (front.first.left != null)
                q.add(new Pair<>(front.first.left, front.second - 1));
            if (front.first.right != null)
                q.add(new Pair<>(front.first.right, front.second + 1));
        }

        // find min and max vertical level
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int dist : map.keySet()) {
            min = Math.min(min, dist);
            max = Math.max(max, dist);
        }
        // prepare output list
        for (int i = min; i <= max; i++)
            res.add(map.get(i));
        return res;
    }

    static class Pair<K, V> {
        K first;
        V second;

        Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    }

    // https://www.interviewbit.com/problems/diagonal-traversal/
    static ArrayList<Integer> diagonalTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            TreeNode front = q.poll();
            // keep moving right for current node (same diagonal)
            while (front != null) {
                res.add(front.val);
                // if any left child found, add to queue
                if (front.left != null)
                    q.add(front.left);
                // move along in same diagonal
                front = front.right;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/inorder-traversal/
    static ArrayList<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        // loop till all nodes traversed
        while (root != null || !s.empty()) {
            // keep pushing to stack till the leftmost node
            while (root != null) {
                s.push(root);
                root = root.left;
            }
            // process topmost node
            root = s.pop();
            res.add(root.val);
            // Now move to right child for processing
            root = root.right;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/preorder-traversal/
    static ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        // empty tree
        if (root == null)
            return res;

        Stack<TreeNode> s = new Stack<>();
        s.push(root);

        while (!s.empty()) {
            root = s.pop();
            // add root node to output
            res.add(root.val);
            // push right child first so that left child comes first in output
            if (root.right != null)
                s.push(root.right);
            if (root.left != null)
                s.push(root.left);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/postorder-traversal/
    static ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        // empty tree
        if (root == null)
            return res;

        // perform reverse postorder and then reverse output (D-R-L) - similar to preorder traversal
        Stack<TreeNode> s = new Stack<>();
        s.push(root);

        while (!s.empty()) {
            root = s.pop();
            // add root node to output
            res.add(root.val);
            // push left child first so that right child comes first in output
            if (root.left != null)
                s.push(root.left);
            if (root.right != null)
                s.push(root.right);
        }
        // reverse the output
        Collections.reverse(res);

        return res;
    }

    // https://www.interviewbit.com/problems/right-view-of-binary-tree/
    @SuppressWarnings("ConstantConditions")
    static ArrayList<Integer> rightView(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        // empty tree
        if (root == null)
            return res;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        // perform level order traversal
        while (!q.isEmpty()) {
            int n = q.size();
            TreeNode front = q.peek();
            // traverse each node in this level and add its children to queue
            for (int i = 0; i < n; i++) {
                front = q.poll();
                if (front.left != null)
                    q.add(front.left);
                if (front.right != null)
                    q.add(front.right);
            }
            // add the last node in this level to result
            res.add(front.val);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/cousins-in-binary-tree/
    @SuppressWarnings("ConstantConditions")
    static int[] cousins(TreeNode root, int B) {
        // root doesn't have any cousins
        if (root.val == B)
            return new int[0];

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        // perform level order traversal
        while (!q.isEmpty()) {
            int n = q.size();
            boolean found = false;
            // for the current level
            for (int i = 0; i < n; i++) {
                TreeNode front = q.poll();
                // if the front node is parent of required node, don't push its children to queue
                if (!found && (front.left != null && front.left.val == B) || (front.right != null && front.right.val == B)) {
                    found = true;
                    continue;
                }
                // push left child
                if (front.left != null)
                    q.add(front.left);
                // push right child
                if (front.right != null)
                    q.add(front.right);
            }
            // found parent of B in current level. Queue will contain all nodes of next level except children of B
            if (found) {
                n = q.size();
                // copy queue to array
                int[] res = new int[n];
                for (int i = 0; i < n; i++)
                    res[i] = q.poll().val;
                return res;
            }
        }

        return new int[0];
    }

    // https://www.interviewbit.com/problems/reverse-level-order/
    static ArrayList<Integer> reverseLevelOrder(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        // perform level order traversal
        while (!q.isEmpty()) {
            TreeNode front = q.poll();
            // add front node to level order result
            res.add(front.val);
            // reverse: take right child first then left for level order
            if (front.right != null)
                q.add(front.right);
            if (front.left != null)
                q.add(front.left);
        }
        // reverse the current level order traversal to get last level at top and left nodes in each level before right nodes
        Collections.reverse(res);

        return res;
    }

    // https://www.interviewbit.com/problems/zigzag-level-order-traversal-bt/
    @SuppressWarnings("ConstantConditions")
    static ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        boolean reverse = false;
        // deque to change directions while traversing
        Deque<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<>();
            int n = q.size();
            // move from left to right in level
            if (!reverse) {
                // poll from deque front
                for (int i = 0; i < n; i++) {
                    TreeNode front = q.pollFirst();
                    level.add(front.val);

                    if (front.left != null)
                        q.addLast(front.left);
                    if (front.right != null)
                        q.addLast(front.right);
                }
            }
            // move from right to left in level
            else {
                // poll from deque back
                for (int i = 0; i < n; i++) {
                    TreeNode back = q.pollLast();
                    level.add(back.val);

                    if (back.right != null)
                        q.addFirst(back.right);
                    if (back.left != null)
                        q.addFirst(back.left);
                }
            }
            // add current level to result and flip direction
            res.add(level);
            reverse = !reverse;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/populate-next-right-pointers-tree/
    // https://www.interviewbit.com/problems/next-pointer-binary-tree/
    static class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;

        TreeLinkNode(int val) {
            this.val = val;
        }
    }

    static void rightNextPointers(TreeLinkNode root) {
        // empty tree
        if (root == null)
            return;

        TreeLinkNode level = root;
        // traverse each level
        while (level != null) {
            TreeLinkNode curr = level;
            // move next along the level
            while (curr != null) {
                if (curr.left != null) {
                    // left child's next pointer will be right child
                    if (curr.right != null)
                        curr.left.next = curr.right;
                        // else find the next node in the level
                    else
                        curr.left.next = getNextNode(curr.next);
                }
                // make right child point to next node in the level
                if (curr.right != null)
                    curr.right.next = getNextNode(curr.next);

                curr = curr.next;
            }
            // move to next level
            if (level.left != null)
                level = level.left;
            else if (level.right != null)
                level = level.right;
            else
                level = getNextNode(level.next);
        }
    }

    // get the next node for current node's right child (or left child if right is absent)
    private static TreeLinkNode getNextNode(TreeLinkNode root) {
        // keep moving in current level till node is not a leaf
        while (root != null) {
            if (root.left != null)
                return root.left;
            if (root.right != null)
                return root.right;
            root = root.next;
        }
        // all nodes are leaves in the parent level
        return null;
    }

    // https://www.interviewbit.com/problems/burn-a-tree/
    static int burnTree(TreeNode root, int B) {
        // time taken to burn the whole tree
        res = 0;
        // recursively burn tree from leaf node
        burnTreeUtil(root, B);

        return res;
    }

    // util to find if a subtree contains the leaf node and return the time taken to reach back to current node
    private static int burnTreeUtil(TreeNode root, int B) {
        // found the leaf node
        if (root.val == B)
            return 0;

        // search for the leaf in left subtree
        int time = root.left != null ? burnTreeUtil(root.left, B) : -1;
        // if found the leaf in left subtree
        if (time != -1) {
            // update current time
            time++;
            // time taken to burn this subtree = time taken from leaf to reach current node + height of right subtree
            res = Math.max(res, time + height(root.right));

            return time;
        }
        // search for the leaf in right subtree
        time = root.right != null ? burnTreeUtil(root.right, B) : -1;
        // if found the leaf in right subtree
        if (time != -1) {
            // update current time
            time++;
            // time taken to burn this subtree = time taken from leaf to reach current node + height of left subtree
            res = Math.max(res, time + height(root.left));

            return time;
        }
        // starting leaf node not found
        return -1;
    }

    // util to compute height of tree
    private static int height(TreeNode root) {
        if (root == null)
            return 0;
        return Math.max(height(root.left), height(root.right)) + 1;
    }

    // https://www.interviewbit.com/problems/max-depth-of-binary-tree/
    static int maxDepth(TreeNode root) {
        // empty node
        if (root == null)
            return 0;
        // max depth at this point is max of the left and right depths + 1
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    // https://www.interviewbit.com/problems/sum-root-to-leaf-numbers/
    static int sumNumbers(TreeNode root) {
        if (root == null)
            return 0;
        return sumNumbersUtil(root, 0, 1003);
    }

    // util to form number from digits on the path and update cumulative path sum
    private static int sumNumbersUtil(TreeNode root, int curr, int MOD) {
        // update current number
        curr = (curr * 10 + root.val) % MOD;
        // if leaf node, update cumulative path sum
        if (root.left == null && root.right == null)
            return curr;
        // recursively find path sum of digits in left and right direction
        int left = root.left != null ? sumNumbersUtil(root.left, curr, MOD) : 0;
        int right = root.right != null ? sumNumbersUtil(root.right, curr, MOD) : 0;

        return (left + right) % MOD;
    }

    // https://www.interviewbit.com/problems/path-sum/
    static int hasPathSum(TreeNode root, int B) {
        // empty node
        if (root == null)
            return 0;
        // update remaining sum
        B -= root.val;
        // if leaf node, check if path sum is satisfied
        if (root.left == null && root.right == null)
            return B == 0 ? 1 : 0;
        // recursively check if left or right path satisfy path sum
        return hasPathSum(root.left, B) | hasPathSum(root.right, B);
    }

    // https://www.interviewbit.com/problems/min-depth-of-binary-tree/
    static int minDepth(TreeNode root) {
        // empty node - path cannot end here
        if (root == null)
            return Integer.MAX_VALUE;
        // leaf node
        if (root.left == null && root.right == null)
            return 1;
        // min depth at this is min of the left and right depths + 1
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    // https://www.interviewbit.com/problems/root-to-leaf-paths-with-sum/
    static ArrayList<ArrayList<Integer>> findAllPathSum(TreeNode root, int B) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        // empty tree
        if (root == null)
            return res;
        // recursively try all paths
        findAllPathSumUtil(root, B, new ArrayList<>(), res);

        return res;
    }

    // util to traverse all paths and check path sum
    private static void findAllPathSumUtil(TreeNode root, int B, ArrayList<Integer> curr, ArrayList<ArrayList<Integer>> res) {
        // add current node to path and update remaining sum
        curr.add(root.val);
        B -= root.val;
        // if leaf node
        if (root.left == null && root.right == null) {
            // no remaining sum hence add current path to result
            if (B == 0)
                res.add(new ArrayList<>(curr));
            // backtrack
            curr.remove(curr.size() - 1);
            return;
        }
        // recursively check left and right paths
        if (root.left != null)
            findAllPathSumUtil(root.left, B, curr, res);
        if (root.right != null)
            findAllPathSumUtil(root.right, B, curr, res);
        // backtrack
        curr.remove(curr.size() - 1);
    }

    // https://www.interviewbit.com/problems/inorder-traversal-of-cartesian-tree/
    static TreeNode buildCartesianTree(int[] inorder) {
        int n = inorder.length;
        RangeMaxSegmentTree st = new RangeMaxSegmentTree(n);
        st.buildSegmentTree(0, 0, n - 1, inorder);
        // build cartesian tree recursively
        return buildCartesianTreeUtil(inorder, 0, n - 1, st);
    }

    private static TreeNode buildCartesianTreeUtil(int[] inorder, int l, int r, RangeMaxSegmentTree st) {
        // empty node
        if (l > r)
            return null;

        // root is the maximum node in the inorder traversal
        int m = st.rangeMax(0, 0, inorder.length - 1, l, r, inorder);
        TreeNode root = new TreeNode(inorder[m]);
        // recursively build left and right subtrees
        root.left = buildCartesianTreeUtil(inorder, l, m - 1, st);
        root.right = buildCartesianTreeUtil(inorder, m + 1, r, st);

        return root;
    }

    // range maximum segment tree
    static class RangeMaxSegmentTree {
        private final int[] st;

        RangeMaxSegmentTree(int n) {
            // Note: #nodes = 2n - 1, height = log2(2n), size = 2 ^ (height + 1) - 1
            // segment tree array approximate size
            st = new int[4 * n];
        }

        // recursively build segment tree
        public int buildSegmentTree(int si, int l, int r, int[] arr) {
            // leaf node
            if (l == r)
                return st[si] = l;

            int mid = l + (r - l) / 2;
            // get left and right range maximum indices
            int l1 = buildSegmentTree(2 * si + 1, l, mid, arr);
            int r1 = buildSegmentTree(2 * si + 2, mid + 1, r, arr);
            // update current range maximum number index
            if (arr[l1] > arr[r1])
                st[si] = l1;
            else
                st[si] = r1;

            return st[si];
        }

        public int rangeMax(int si, int sl, int sr, int l, int r, int[] arr) {
            // no overlap
            if (sr < l || r < sl)
                return -1;
            // total overlap
            if (l <= sl && sr <= r)
                return st[si];

            int mid = sl + (sr - sl) / 2;
            // find left and right range maximum element indexes
            int l1 = rangeMax(2 * si + 1, sl, mid, l, r, arr);
            int r1 = rangeMax(2 * si + 2, mid + 1, sr, l, r, arr);
            // left range not exists
            if (l1 == -1)
                return r1;
            // right range not exists
            if (r1 == -1)
                return l1;
            // calculate maximum element index from left and right maximums
            if (arr[l1] > arr[r1])
                return l1;
            return r1;
        }
    }

    // https://www.interviewbit.com/problems/sorted-array-to-balanced-bst/
    static TreeNode buildBalancedBST(List<Integer> A) {
        return buildBalancedBSTUtil(A, 0, A.size() - 1);
    }

    // util to build balanced BST recursively
    private static TreeNode buildBalancedBSTUtil(List<Integer> A, int l, int r) {
        // reached end
        if (l > r)
            return null;

        // root will be the middle node in sorted array
        int mid = l + (r - l) / 2;
        TreeNode root = new TreeNode(A.get(mid));
        // recursively build left and right subtree
        root.left = buildBalancedBSTUtil(A, l, mid - 1);
        root.right = buildBalancedBSTUtil(A, mid + 1, r);

        return root;
    }

    // https://www.interviewbit.com/problems/construct-binary-tree-from-inorder-and-preorder/
    static TreeNode constructTreeFromInAndPre(int[] preorder, int[] inorder) {
        int n = inorder.length;
        // map inorder elements to position for O(1) searching
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++)
            map.put(inorder[i], i);

        // first node in preorder is root
        pos = 0;
        // recursively construct tree
        return constructFromInAndPreUtil(preorder, 0, n - 1, map);
    }

    // util to construct tree node from inorder and preorder traversal
    private static TreeNode constructFromInAndPreUtil(int[] preorder, int start, int end, Map<Integer, Integer> map) {
        // reached end of traversal
        if (start > end)
            return null;

        TreeNode root = new TreeNode(preorder[pos++]);
        // search for element in inorder
        int inorderPos = map.get(root.val);
        // recursively create left and right subtrees
        root.left = constructFromInAndPreUtil(preorder, start, inorderPos - 1, map);
        root.right = constructFromInAndPreUtil(preorder, inorderPos + 1, end, map);

        return root;
    }

    // https://www.interviewbit.com/problems/binary-tree-from-inorder-and-postorder/
    static TreeNode constructTreeFromInAndPost(int[] inorder, int[] postorder) {
        int n = inorder.length;
        // map inorder elements to position for O(1) searching
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++)
            map.put(inorder[i], i);

        // last node in postorder is root
        pos = n - 1;
        // recursively construct tree
        return constructFromInAndPostUtil(postorder, 0, n - 1, map);
    }

    // util to construct tree node from inorder and postorder traversal
    private static TreeNode constructFromInAndPostUtil(int[] postorder, int start, int end, Map<Integer, Integer> map) {
        // reached end of traversal
        if (start > end)
            return null;

        // create current node
        TreeNode root = new TreeNode(postorder[pos--]);
        // search for element in inorder
        int inorderPos = map.get(root.val);
        // recursively create right and left subtrees
        root.right = constructFromInAndPostUtil(postorder, inorderPos + 1, end, map);
        root.left = constructFromInAndPostUtil(postorder, start, inorderPos - 1, map);

        return root;
    }

    // https://www.interviewbit.com/problems/invert-the-binary-tree/
    static TreeNode invertTree(TreeNode root) {
        // empty node
        if (root == null)
            return null;

        TreeNode left = root.left;
        // recursively invert left and right subtrees
        root.left = invertTree(root.right);
        root.right = invertTree(left);

        return root;
    }

    // https://www.interviewbit.com/problems/least-common-ancestor/
    // also possible using binary lifting DP
    private static boolean v1, v2;

    static int lca(TreeNode root, int B, int C) {
        // set found for both nodes as false
        v1 = v2 = false;
        // recursively find lca
        TreeNode lca = lcaUtil(root, B, C);
        // if visited both nodes during lca or if search for other node returns true
        if (v1 && v2 || v1 && findNode(lca, C) || v2 && findNode(lca, B))
            return lca.val;
        // one or more nodes not found
        return -1;
    }

    // recursive util to find lca of two nodes at a point
    private static TreeNode lcaUtil(TreeNode root, int B, int C) {
        // empty node
        if (root == null)
            return null;
        // found node B
        if (root.val == B) {
            v1 = true;
            return root;
        }
        // found node C
        if (root.val == C) {
            v2 = true;
            return root;
        }

        // recursively search in left and right subtrees
        TreeNode left = lcaUtil(root.left, B, C);
        TreeNode right = lcaUtil(root.right, B, C);
        // found both nodes in subtrees
        if (left != null && right != null)
            return root;
        // else return the node found
        return left != null ? left : right;
    }

    // util to search for a node
    private static boolean findNode(TreeNode root, int val) {
        // reached end
        if (root == null)
            return false;
        // found node else search recursively in left and right subtrees
        return root.val == val || findNode(root.left, val) || findNode(root.right, val);
    }

    // https://www.interviewbit.com/problems/flatten-binary-tree-to-linked-list/
    private static TreeNode last;

    static TreeNode flattenBinaryTreeToLinkedList(TreeNode root) {
        TreeNode dummy = new TreeNode(-1);
        // node to keep track of current node in linked-list
        last = dummy;
        // recursively flatten tree
        flattenUtil(root);

        return dummy.right;
    }

    // recursive util to flatten tree
    private static void flattenUtil(TreeNode root) {
        // shift current subtree to right child of last node
        last.right = root;
        // make left child null
        last.left = null;
        // move ahead in the newly appended linked-list
        last = last.right;

        // save right child reference
        TreeNode right = root.right;
        // recursively flatten left and right subtrees
        if (root.left != null)
            flattenUtil(root.left);
        if (right != null)
            flattenUtil(right);
    }

    // https://www.interviewbit.com/problems/order-of-people-heights/
    static int[] orderOfHeights(int[] heights, int[] inFronts) {
        int n = heights.length;
        // create array of each person's height and count of people in front
        int[][] people = new int[n][2];
        for (int i = 0; i < n; i++)
            people[i] = new int[]{heights[i], inFronts[i]};
        // sort by height increasing first then by no of people increasing
        Arrays.sort(people, (p1, p2) -> p1[0] != p2[0] ? p1[0] - p2[0] : p1[1] - p2[1]);
        // build index sum segment tree with each leaf node as 1
        SumSegmentTree st = new SumSegmentTree(n);
        st.buildSegmentTree(0, 0, n - 1);

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            // find (people[i].inFront + 1)th empty position using segment tree in O(log n)
            int index = st.query(people[i][1] + 1, 0, 0, n - 1);
            // place the person at found index
            res[index] = people[i][0];
            // update segment tree by making found index as unavailable
            st.update(0, 0, n - 1, index, -1);
        }

        return res;
    }

    // segment tree for range sum with leaf nodes as 1
    static class SumSegmentTree {
        private final int[] st;

        SumSegmentTree(int n) {
            // approximate size of array
            st = new int[4 * n];
        }

        // recursively build segment tree
        public int buildSegmentTree(int si, int sl, int sr) {
            // leaf node
            if (sl == sr)
                return st[si] = 1;

            int mid = sl + (sr - sl) / 2;
            // compute current range using left range and right range
            return st[si] = buildSegmentTree(2 * si + 1, sl, mid) + buildSegmentTree(2 * si + 2, mid + 1, sr);
        }

        // query index segment tree for index x
        public int query(int x, int si, int sl, int sr) {
            // leaf node
            if (sl == sr)
                return sl;

            int leftSum = st[2 * si + 1];
            int mid = sl + (sr - sl) / 2;
            // query index is in left range
            if (x <= leftSum)
                return query(x, 2 * si + 1, sl, mid);
            // query index is in right range
            return query(x - leftSum, 2 * si + 2, mid + 1, sr);
        }

        // update segment tree
        public void update(int si, int sl, int sr, int pos, int diff) {
            // invalid range
            if (pos < sl || pos > sr)
                return;
            // update current range node
            st[si] += diff;
            // if not leaf, recursively update left and right range nodes
            if (sl != sr) {
                int mid = sl + (sr - sl) / 2;
                update(2 * si + 1, sl, mid, pos, diff);
                update(2 * si + 2, mid + 1, sr, pos, diff);
            }
        }
    }

    // https://www.interviewbit.com/problems/vertical-sum-of-a-binary-tree/
    static int[] verticalSum(TreeNode root) {
        if (root == null)
            return new int[0];

        Map<Integer, Integer> map = new HashMap<>();
        Queue<Pair<TreeNode, Integer>> q = new LinkedList<>();
        q.add(new Pair<>(root, 0));
        // perform level order traversal and mark vertical level of each node
        while (!q.isEmpty()) {
            Pair<TreeNode, Integer> front = q.poll();
            // add current node to its vertical level sum
            map.put(front.second, map.getOrDefault(front.second, 0) + front.first.val);
            // go to next horizontal level
            if (front.first.left != null)
                q.add(new Pair<>(front.first.left, front.second - 1));
            if (front.first.right != null)
                q.add(new Pair<>(front.first.right, front.second + 1));
        }

        // find min and max vertical level
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int dist : map.keySet()) {
            min = Math.min(min, dist);
            max = Math.max(max, dist);
        }
        // prepare output array
        int[] res = new int[max - min + 1];
        for (int i = 0; i < res.length; i++)
            res[i] = map.get(min + i);
        return res;
    }

    // https://www.interviewbit.com/problems/construct-bst-from-preorder/
    static TreeNode constructBST(int[] A) {
        pos = 0;
        return constructBSTUtil(A, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static TreeNode constructBSTUtil(int[] A, int min, int max) {
        // tree finished or current value is out of bounds
        if (pos == A.length || A[pos] < min || A[pos] > max)
            return null;

        TreeNode root = new TreeNode(A[pos++]);
        // recursively create left and right subtrees
        root.left = constructBSTUtil(A, min, root.val - 1);
        root.right = constructBSTUtil(A, root.val + 1, max);

        return root;
    }

    // https://www.interviewbit.com/problems/covered-uncovered-nodes/
    @SuppressWarnings("ConstantConditions")
    public long coveredNodes(TreeNode root) {
        // compute sum of covered and uncovered nodes
        long covered = 0, uncovered = 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        // perform level order traversal
        while (!q.isEmpty()) {
            int n = q.size();
            for (int i = 0; i < n; i++) {
                TreeNode front = q.poll();
                // first and last node of each level will be uncovered nodes
                if (i == 0 || i == n - 1)
                    uncovered += front.val;
                else
                    covered += front.val;

                if (front.left != null)
                    q.add(front.left);
                if (front.right != null)
                    q.add(front.right);
            }
        }

        return Math.abs(covered - uncovered);
    }

    // https://www.interviewbit.com/problems/last-node-in-a-complete-binary-tree/
    static int lastNode(TreeNode root) {
        // T.C = O(log n * log n)
        // get depth of leftmost node of current subtree
        int h = getLeftHeight(root);
        // no left subtree present
        if (h == 1)
            return root.val;

        // if depth of leftmost node of right subtree = depth of leftmost node of left subtree, search for last node in right subtree
        if (getLeftHeight(root.right) == h - 1)
            return lastNode(root.right);
        // else search for last node in left subtree
        return lastNode(root.left);
    }

    // util to get depth of leftmost node
    private static int getLeftHeight(TreeNode root) {
        int h = 0;
        // keep moving left and update depth
        while (root != null) {
            h++;
            root = root.left;
        }

        return h;
    }

    // https://www.interviewbit.com/problems/consecutive-parent-child/
    public static int consecutiveNodes(TreeNode root) {
        if (root == null)
            return 0;

        int cnt = 0;
        if (root.left != null) {
            // if left child is consecutive, update count
            if (Math.abs(root.val - root.left.val) == 1)
                cnt++;
            // recursively count consecutive parent-child in left subtree
            cnt += consecutiveNodes(root.left);
        }

        if (root.right != null) {
            // if right child is consecutive, update count
            if (Math.abs(root.val - root.right.val) == 1)
                cnt++;
            // recursively count consecutive parent-child in right subtree
            cnt += consecutiveNodes(root.right);
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/maximum-level-sum/
    @SuppressWarnings("ConstantConditions")
    static int maxLevelSum(TreeNode root) {
        int res = 0;
        // queue for level order traversal
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int n = q.size();
            // calculate sum of nodes for each level
            int sum = 0;

            for (int i = 0; i < n; i++) {
                TreeNode front = q.poll();
                sum += front.val;

                if (front.left != null)
                    q.add(front.left);
                if (front.right != null)
                    q.add(front.right);
            }
            // update level sum
            res = Math.max(res, sum);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/inversions/
    static int countInversions(int[] A) {
        return mergeSortAndCount(A, 0, A.length - 1);
    }

    // recursively merge sort and count number of swaps
    private static int mergeSortAndCount(int[] A, int l, int r) {
        // swaps count for current range
        int count = 0;

        if (l < r) {
            int mid = l + (r - l) / 2;
            // merge sort left half and add its swaps
            count += mergeSortAndCount(A, l, mid);
            // merge sort right half and add its swaps
            count += mergeSortAndCount(A, mid + 1, r);
            // merge both the halves and add their swaps
            count += mergeAndCount(A, l, mid, r);
        }

        return count;
    }

    // merge util that counts number of swaps
    private static int mergeAndCount(int[] A, int l, int mid, int r) {
        // left and right halves
        int[] left = Arrays.copyOfRange(A, l, mid + 1);
        int[] right = Arrays.copyOfRange(A, mid + 1, r + 1);
        int i = 0, j = 0, k = l, swaps = 0;
        // merge both the halves
        while (i < left.length && j < right.length) {
            // no swap needed
            if (left[i] <= right[j])
                A[k++] = left[i++];
                // swap needed from right half to left half
            else {
                A[k++] = right[j++];
                // swaps += (size of first half - last position filled previously in first half)
                swaps += (mid - l + 1) - i;
            }
        }
        // merge remaining left half
        while (i < left.length)
            A[k++] = left[i++];
        // merge remaining right half
        while (j < right.length)
            A[k++] = right[j++];

        return swaps;
    }
}