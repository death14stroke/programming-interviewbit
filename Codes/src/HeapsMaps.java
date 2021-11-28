import java.util.*;

class HeapsMaps {
    // https://www.interviewbit.com/problems/ways-to-form-max-heap/
    private final int p = 1_000_000_007;
    private int[][] comb;
    private int[] log2;
    private long[] dp;

    public int waysToFormMaxHeap(int A) {
        // comb[n][k] = C(n, k)
        comb = new int[A + 1][A + 1];
        for (int[] row : comb)
            Arrays.fill(row, -1);
        // dp[i] = #max heaps for i nodes
        dp = new long[A + 1];
        Arrays.fill(dp, -1);
        // log2[i] = log(i) to the base 2
        log2 = new int[A + 1];
        int currPow2 = 1, currLog2 = -1;
        for (int i = 1; i <= A; i++) {
            if (currPow2 == i) {
                currLog2++;
                currPow2 *= 2;
            }

            log2[i] = currLog2;
        }

        return (int) numberOfHeaps(A);
    }

    private long numberOfHeaps(int n) {
        // base case
        if (n <= 1)
            return 1;
        // memoization
        if (dp[n] != -1)
            return dp[n];

        // count nodes in left subtree
        // l + r = n - 1 as root will always be the max element
        int l = getLeft(n);
        // dp[i] = C(n - 1, l) * numberOfHeaps(l) * numberOfHeaps(r)
        dp[n] = (C(n - 1, l) * numberOfHeaps(l)) % p;
        dp[n] = (dp[n] * numberOfHeaps(n - 1 - l)) % p;

        return dp[n];
    }

    // util to count nodes in left subtree of heap of size n
    private int getLeft(int n) {
        // base-case
        if (n == 1)
            return 0;

        // height of the heap
        int h = log2[n];
        // #nodes in last level if complete
        int numh = 1 << h;
        // #nodes in last level for current heap
        int last = n - ((1 << h) - 1);

        // if left subtree is fully filled
        if (last >= numh / 2)
            return (1 << h) - 1;
        return (1 << h) - 1 - ((numh / 2) - last);
    }

    // util to compute and memoize combinations
    private int C(int n, int k) {
        // base-cases
        if (k > n)
            return 0;
        if (n <= 1 || k == 0)
            return 1;
        // memoization
        if (comb[n][k] != -1)
            return comb[n][k];

        // C(n, k) = C(n - 1, k) + C(n - 1, k - 1)
        comb[n][k] = (C(n - 1, k) + C(n - 1, k - 1)) % p;

        return comb[n][k];
    }

    // https://www.interviewbit.com/problems/n-max-pair-combinations/
    @SuppressWarnings("ConstantConditions")
    static int[] nMaxPairCombinations(int[] A, int[] B) {
        // sort both the arrays
        Arrays.sort(A);
        Arrays.sort(B);

        int n = A.length;
        // min heap based on the sum of pairs - size should be at least n
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        // add the first n sums
        for (int j = n - 1; j >= 0; j--)
            pq.add(A[n - 1] + B[j]);

        // for each remaining pair
        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int sum = A[i] + B[j];
                // if better sum than the minimum among largest n sums, poll and add current sum
                if (sum > pq.peek()) {
                    pq.poll();
                    pq.add(sum);
                }
                // else no further pairs will yield better sum with A[i]
                else {
                    break;
                }
            }
        }

        // fill up res[] in reverse order
        int[] res = new int[n];
        for (int i = n - 1; i >= 0; i--)
            res[i] = pq.poll();

        return res;
    }

    // https://www.interviewbit.com/problems/k-largest-elements/
    static int[] kLargest(int[] A, int B) {
        // build min heap of first B elements
        buildMinHeap(A, B);

        int n = A.length;
        // for all elements starting from Bth position
        for (int i = B; i < n; i++) {
            // if it is greater, swap with root of min heap and heapify
            if (A[i] > A[0]) {
                swap(A, 0, i);
                minHeapify(A, B, 0);
            }
        }

        // now array will contain max B elements in 0 to B positions
        return Arrays.copyOfRange(A, 0, B);
    }

    // util to build min heap of n elements in O(n) time
    private static void buildMinHeap(int[] A, int n) {
        // call heapify from the bottom most node except leaves
        for (int i = n / 2 - 1; i >= 0; i--)
            minHeapify(A, n, i);
    }

    // util to min heapify from current node as root
    private static void minHeapify(int[] A, int n, int i) {
        int smallest = i, left = 2 * i + 1, right = 2 * i + 2;

        // check if left or right child are smaller
        if (left < n && A[left] < A[smallest])
            smallest = left;
        if (right < n && A[right] < A[smallest])
            smallest = right;

        // if any child is smaller, swap it with root and heapify recursively from the child
        if (smallest != i) {
            swap(A, smallest, i);
            minHeapify(A, n, smallest);
        }
    }

    // util to swap two positions in an array
    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    // https://www.interviewbit.com/problems/profit-maximisation/
    static int profitMax(int[] A, int B) {
        // build max heap from the rows
        buildMaxHeap(A);

        int n = A.length, res = 0;
        // select the topmost row everytime, add profit, update empty and heapify
        while (B > 0 && A[0] > 0) {
            res += A[0];
            // update remaining rows
            A[0]--;
            maxHeapify(A, n, 0);
            // update remaining tickets
            B--;
        }

        return res;
    }

    // util to build heap from array in O(n) time
    private static void buildMaxHeap(int[] A) {
        int n = A.length;
        // call heapify from the bottom most node except leaves
        for (int i = n / 2 - 1; i >= 0; i--)
            maxHeapify(A, n, i);
    }

    // util to max heapify from current node as root
    private static void maxHeapify(int[] A, int n, int i) {
        int largest = i, left = 2 * i + 1, right = 2 * i + 2;

        // check if left or right child are greater
        if (left < n && A[left] > A[largest])
            largest = left;
        if (right < n && A[right] > A[largest])
            largest = right;

        // if any child is greater, swap it with root and heapify recursively from the child
        if (largest != i) {
            swap(A, i, largest);
            maxHeapify(A, n, largest);
        }
    }

    // https://www.interviewbit.com/problems/merge-k-sorted-arrays/
    @SuppressWarnings("ConstantConditions")
    static int[] mergeKSorted(int[][] A) {
        int k = A.length, n = A[0].length;
        // array to keep track of current position in each array
        int[] kPos = new int[k];
        int[] res = new int[k * n];

        // min heap of size k
        PriorityQueue<MergeNode> pq = new PriorityQueue<>(Comparator.comparingInt(n2 -> n2.val));
        // add first element of each array in the heap
        for (int i = 0; i < k; i++)
            pq.add(new MergeNode(A[i][0], i));

        for (int pos = 0; pos < k * n; pos++) {
            // get min from the heap and add to res. Update position of the array from which the element belongs to
            MergeNode node = pq.poll();
            res[pos] = node.val;
            kPos[node.k]++;

            // if any elements left in kth array, add to min heap
            if (kPos[node.k] < n)
                pq.add(new MergeNode(A[node.k][kPos[node.k]], node.k));
        }

        return res;
    }

    // util class to store the number and the array it belongs to
    static class MergeNode {
        int val, k;

        MergeNode(int val, int k) {
            this.val = val;
            this.k = k;
        }
    }

    // https://www.interviewbit.com/problems/magician-and-chocolates/
    static int maxChocolates(int A, int[] B) {
        int n = B.length, p = 1_000_000_007;
        // if time is 0 or no bags present
        if (A == 0 || n == 0)
            return 0;

        // build max heap from the boxes
        buildMaxHeap(B);

        long res = 0;
        // keep adding max from the heap and heapify after update till time finishes or chocolates finish
        for (int i = 0; i < A && B[0] > 0; i++) {
            res = (res + B[0]) % p;
            // fill the box by half
            B[0] /= 2;
            maxHeapify(B, n, 0);
        }

        return (int) res;
    }

    // https://www.interviewbit.com/problems/maximum-sum-combinations/
    @SuppressWarnings("ConstantConditions")
    static int[] maxSumCombinations(int[] A, int[] B, int C) {
        // sort both the arrays
        Arrays.sort(A);
        Arrays.sort(B);

        int n = A.length;
        // min heap based on the sum of pairs - size should be at least C
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        // add the first C sums
        int i = n - 1, j = n - 1;
        for (; i >= 0; i--) {
            for (; j >= 0 && pq.size() < C; j--)
                pq.add(A[i] + B[j]);

            // add sums with pairs starting with A[i]
            if (j == -1)
                j = n - 1;
                // else first C sums added to maxHeap
            else
                break;
        }

        // for each remaining pair
        for (; i >= 0; i--) {
            for (; j >= 0; j--) {
                int sum = A[i] + B[j];
                // if better sum than the minimum among largest C sums, poll and add current sum
                if (sum > pq.peek()) {
                    pq.poll();
                    pq.add(sum);
                }
                // else no further pairs will yield better sum with A[i]
                else {
                    break;
                }
            }

            j = n - 1;
        }

        // fill up res[] in reverse order
        int[] res = new int[C];
        for (i = C - 1; i >= 0; i--)
            res[i] = pq.poll();

        return res;
    }

    // https://www.interviewbit.com/problems/merge-k-sorted-lists/
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    static ListNode mergeKSortedLists(ArrayList<ListNode> A) {
        // dummy node and curr pointer for resultant list
        ListNode dummy = new ListNode(-1), curr = dummy;
        // add first node of all the lists in min heap
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.val));
        pq.addAll(A);

        // keep popping nodes one by one and add its child to the min heap till the end
        while (!pq.isEmpty()) {
            ListNode top = pq.poll();
            if (top.next != null)
                pq.add(top.next);

            // link current node to output
            curr.next = top;
            curr = curr.next;
        }

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/distinct-numbers-in-window/
    static int[] distinctNumbersInWindow(int[] A, int B) {
        int n = A.length;
        // if window size is larger than array size
        if (B > n)
            return new int[0];
        // if window size is 1 all windows will have 1 unique
        if (B == 1) {
            Arrays.fill(A, 1);
            return A;
        }

        Map<Integer, Integer> map = new HashMap<>();
        // insert count of elements of first window
        for (int i = 0; i < B; i++)
            map.put(A[i], map.getOrDefault(A[i], 0) + 1);

        int[] res = new int[n - B + 1];
        // calculate result for the first window
        res[0] = map.size();
        for (int i = B; i < n; i++) {
            // decrease count of the old window start
            int freq = map.get(A[i - B]) - 1;
            if (freq == 0)
                map.remove(A[i - B]);
            else
                map.put(A[i - B], freq);
            // increase count of the new element
            map.put(A[i], map.getOrDefault(A[i], 0) + 1);

            // calculate result for current window
            res[i - B + 1] = map.size();
        }

        return res;
    }

    // https://www.interviewbit.com/problems/lru-cache/
    static class LRUCache {
        // capacity of the cache
        private final int capacity;
        // hashmap to store key with values
        private final Map<Integer, Node> map;
        // head and tail pointers of the doubly linked list
        private Node head, tail;

        public LRUCache(int capacity) {
            map = new HashMap<>();
            this.capacity = capacity;
        }

        public int get(int key) {
            // if not present in cache
            if (!map.containsKey(key))
                return -1;

            Node curr = map.get(key);
            // shift current node to the last position in recently used list
            shiftNodeToEnd(curr);

            return curr.val;
        }

        public void set(int key, int value) {
            // if cache contains key
            if (map.containsKey(key)) {
                Node curr = map.get(key);
                // update value
                curr.val = value;
                // shift current node to the last position in recently used list
                shiftNodeToEnd(curr);
            }
            // add new node at the end
            else {
                Node curr = new Node(key, value);
                // make new node as head if empty else add at end and update tail
                if (head == null) {
                    head = tail = curr;
                } else {
                    tail.next = curr;
                    curr.prev = tail;
                    tail = curr;
                }
                // add new node in map
                map.put(key, curr);

                // if map is full, remove head node
                if (map.size() > capacity) {
                    map.remove(head.key);
                    removeHead();
                }
            }
        }

        // util to remove head of the doubly linked list
        private void removeHead() {
            head = head.next;
            head.prev.next = null;
            head.prev = null;
        }

        // util to shift a node at the end
        private void shiftNodeToEnd(Node curr) {
            // if current node itself is tail or only one node
            if (curr == tail || map.size() == 1)
                return;
            // if current node is head node, update new head
            if (curr == head) {
                removeHead();
            } else {
                // link prev node's next ot current's next
                curr.prev.next = curr.next;
                // link next node's prev to current's prev
                curr.next.prev = curr.prev;
            }

            // link current node to tail's next
            tail.next = curr;
            curr.prev = tail;
            // update tail
            tail = curr;
            tail.next = null;
        }

        static class Node {
            int key, val;
            Node prev, next;

            Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }
    }
}