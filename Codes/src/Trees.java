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

    // https://www.interviewbit.com/problems/kth-smallest-element-in-tree/
    private static int pos, ans;

    static int kthSmallest(TreeNode root, int k) {
        // init global variables to keep track of position and result in inorder traversal
        pos = ans = 0;
        // inorder traversal as it will traverse in ascending order
        inorder(root, k);

        return ans;
    }

    // util to perform inorder traversal
    private static void inorder(TreeNode root, int k) {
        if (root == null)
            return;

        // visit left child
        inorder(root.left, k);

        // visit current node. Update position
        pos++;
        // if found kth node, update result variable and return
        if (pos == k) {
            ans = root.val;
            return;
        }

        // visit right child
        inorder(root.right, k);
    }

    // https://www.interviewbit.com/problems/2sum-binary-tree/
    static int twoSumBst(TreeNode root, int B) {
        // if null tree or no children
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
                while (curr1.left != null) {
                    s1.push(curr1);
                    curr1 = curr1.left;
                }

                // get the smaller value from inorder traversal and then shift to right child
                if (!s1.empty()) {
                    curr1 = s1.pop();
                    val1 = curr1.val;
                    curr1 = curr1.right;
                }

                // mark inorder traversal as done
                done1 = true;
            }

            // if should perform reverse inorder traversal
            if (!done2) {
                // keep moving right till possible
                while (curr2.right != null) {
                    s2.push(curr2);
                    curr2 = curr2.right;
                }

                // get the smaller value from reverse inorder traversal and then shift to left child
                if (!s2.empty()) {
                    curr2 = s2.pop();
                    val2 = curr2.val;
                    curr2 = curr2.left;
                }

                // mark reverse inorder traversal as done
                done2 = true;
            }

            // found pair
            if (val1 != val2 && val1 + val2 == B)
                return 1;
                // sum is less...move ahead in inorder traversal
            else if (val1 + val2 < B)
                done1 = false;
                // sum is more...move ahead in reverse inorder traversal
            else if (val1 + val2 > B)
                done2 = false;

            // no pairs found
            if (val1 >= val2)
                return 0;
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
            TreeNode node = s.pop();
            int val = node.val;

            // move to right node
            node = node.right;
            // perform inorder traversal
            while (node != null) {
                s.push(node);
                node = node.left;
            }

            return val;
        }
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

    // https://www.interviewbit.com/problems/path-to-given-node/
    static ArrayList<Integer> findPath(TreeNode root, int B) {
        ArrayList<Integer> res = new ArrayList<>();
        // traverse all paths till we reach destination
        findPathUtil(root, B, res);

        return res;
    }

    // util to try all paths
    private static boolean findPathUtil(TreeNode root, int B, ArrayList<Integer> res) {
        // reached end
        if (root == null)
            return false;

        // add current node to path
        res.add(root.val);
        // reached destination
        if (root.val == B)
            return true;

        // if found target by moving left or right
        if (findPathUtil(root.left, B, res) || findPathUtil(root.right, B, res))
            return true;

        // remove current node from path
        res.remove(res.size() - 1);

        return false;
    }

    // https://www.interviewbit.com/problems/vertical-order-traversal-of-binary-tree/
    static ArrayList<ArrayList<Integer>> verticalOrderTraversal(TreeNode root) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;

        // perform level order traversal and mark vertical level of each node
        Queue<Pair<Integer, TreeNode>> q = new LinkedList<>();
        q.add(new Pair<>(0, root));

        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        while (!q.isEmpty()) {
            Pair<Integer, TreeNode> top = q.poll();

            // map current node to its vertical level
            map.putIfAbsent(top.first, new ArrayList<>());
            map.get(top.first).add(top.second.val);

            // go to next horizontal level
            if (top.second.left != null)
                q.offer(new Pair<>(top.first - 1, top.second.left));
            if (top.second.right != null)
                q.offer(new Pair<>(top.first + 1, top.second.right));
        }

        // find min and max vertical level
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int level : map.keySet()) {
            min = Math.min(min, level);
            max = Math.max(max, level);
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
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        ArrayList<Integer> res = new ArrayList<>();

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

    // https://www.interviewbit.com/problems/cousins-in-binary-tree/
    @SuppressWarnings("ConstantConditions")
    static ArrayList<Integer> cousins(TreeNode root, int B) {
        ArrayList<Integer> res = new ArrayList<>();

        // root doesn't have any cousins
        if (root.val == B)
            return res;

        boolean found = false;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        // perform level order traversal till required node is found as child
        while (!found) {
            int n = q.size();
            // store next level nodes in the result
            res = new ArrayList<>();

            // for the current level
            for (int i = 0; i < n; i++) {
                TreeNode front = q.poll();

                // if the front node is parent of required node, don't push its children to queue
                if ((front.left != null && front.left.val == B) || (front.right != null && front.right.val == B)) {
                    found = true;
                    continue;
                }

                // push left child and add to result
                if (front.left != null) {
                    q.add(front.left);
                    res.add(front.left.val);
                }
                // push right child and add to result
                if (front.right != null) {
                    q.add(front.right);
                    res.add(front.right.val);
                }
            }
        }

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

    // https://www.interviewbit.com/problems/hotel-reviews/
    static int[] hotelReviews(String A, String[] B) {
        Trie trie = new Trie();
        // insert each good word into trie
        for (String word : A.split("_"))
            trie.add(word);

        int n = B.length;
        // create list of pair of no of good words and index of review
        List<Pair<Integer, Integer>> cntList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            String review = B[i];

            // check if each word of review is present in trie or not
            for (String word : review.split("_")) {
                if (trie.contains(word))
                    cnt++;
            }

            cntList.add(new Pair<>(cnt, i));
        }

        // sort the list by number of good words in decreasing order
        cntList.sort((p1, p2) -> p2.first - p1.first);

        // copy indices to result array
        int[] res = new int[n];
        int i = 0;
        for (Pair<Integer, Integer> pair : cntList) {
            res[i] = pair.second;
            i++;
        }

        return res;
    }

    // trie data structure
    static class Trie {
        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        // add a word to trie
        void add(String word) {
            TrieNode curr = root;

            for (char c : word.toCharArray()) {
                if (curr.child[c - 'a'] == null)
                    curr.child[c - 'a'] = new TrieNode();

                curr = curr.child[c - 'a'];
            }

            curr.isEnd = true;
        }

        // check if a word is present in trie or not
        boolean contains(String word) {
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
            isEnd = false;
        }
    }

    // https://www.interviewbit.com/problems/shortest-unique-prefix/
    static String[] shortestUniquePrefix(String[] words) {
        PrefixTrie trie = new PrefixTrie();
        // add all words in prefix trie
        for (String word : words)
            trie.add(word);

        int n = words.length;
        String[] res = new String[n];
        // get shortest prefix with last char as unique for each word inserted
        for (int i = 0; i < n; i++)
            res[i] = trie.getPrefix(words[i]);

        return res;
    }

    // util class for prefix trie
    // prefix trie will contain count of each node occurring in all the words inserted
    static class PrefixTrie {
        PrefixTrieNode root;

        PrefixTrie() {
            root = new PrefixTrieNode();
        }

        // add word
        void add(String word) {
            PrefixTrieNode curr = root;

            for (char c : word.toCharArray()) {
                if (curr.child[c - 'a'] == null)
                    curr.child[c - 'a'] = new PrefixTrieNode();

                curr = curr.child[c - 'a'];
                // update count of this node
                curr.cnt++;
            }

            curr.isEnd = true;
        }

        // get prefix string with the last character freq as 1
        String getPrefix(String word) {
            PrefixTrieNode curr = root;
            StringBuilder res = new StringBuilder();

            // search for word
            for (char c : word.toCharArray()) {
                res.append(c);

                curr = curr.child[c - 'a'];
                // found last unique char
                if (curr.cnt == 1)
                    break;
            }

            return res.toString();
        }
    }

    // node for Prefix Trie
    static class PrefixTrieNode {
        PrefixTrieNode[] child;
        boolean isEnd;
        int cnt;

        PrefixTrieNode() {
            child = new PrefixTrieNode[26];
            isEnd = false;
            cnt = 0;
        }
    }

    // https://www.interviewbit.com/problems/path-sum/
    static int hasPathSum(TreeNode root, int sum) {
        // empty node
        if (root == null)
            return 0;

        // if leaf node, check if path sum is satisfied
        if (root.left == null && root.right == null)
            return sum - root.val == 0 ? 1 : 0;

        // recursively check if left or right path satisfy path sum
        if (hasPathSum(root.left, sum - root.val) == 1 || hasPathSum(root.right, sum - root.val) == 1)
            return 1;
        return 0;
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

    // https://www.interviewbit.com/problems/reverse-level-order/
    static ArrayList<Integer> reverseLevelOrder(TreeNode root) {
        // stack to save last level last node on top
        Stack<TreeNode> s = new Stack<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        // perform level order traversal
        while (!q.isEmpty()) {
            TreeNode front = q.poll();
            // push front to stack
            s.push(front);

            // reverse: take right child first then left for level order
            if (front.right != null)
                q.add(front.right);
            if (front.left != null)
                q.add(front.left);
        }

        ArrayList<Integer> res = new ArrayList<>();
        while (!s.empty())
            res.add(s.pop().val);

        return res;
    }

    // https://www.interviewbit.com/problems/maximum-level-sum/
    @SuppressWarnings("ConstantConditions")
    static int maxLevelSum(TreeNode root) {
        // maximum sum among all levels
        int maxSum = 0;
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

            // find max level sum till this level
            maxSum = Math.max(sum, maxSum);
        }

        return maxSum;
    }
}