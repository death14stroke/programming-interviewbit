import java.util.*;

class HeapsMaps {
    // https://www.interviewbit.com/problems/n-max-pair-combinations/
    @SuppressWarnings("ConstantConditions")
    static int[] nMaxPairCombinations(int[] A, int[] B) {
        int n = A.length;
        int[] res = new int[n];

        // sort both the arrays
        Arrays.sort(A);
        Arrays.sort(B);

        // max heap based on the sum of pairs
        PriorityQueue<Node> pq = new PriorityQueue<>(
                (n1, n2) -> n2.sum - n1.sum
        );
        pq.add(new Node(n - 1, n - 1, A[n - 1] + B[n - 1]));

        // set to keep track of added pairs
        Set<String> set = new HashSet<>();
        set.add((n - 1) + "," + (n - 1));

        // fill N entries
        for (int k = 0; k < n; k++) {
            Node top = pq.poll();
            // get the topmost sum pair
            res[k] = top.sum;
            int i = top.x, j = top.y;

            String s = i + "," + (j - 1);
            // add (i, j - 1) to heap if not visited
            if (j - 1 >= 0 && !set.contains(s)) {
                pq.add(new Node(i, j - 1, A[i] + B[j - 1]));
                set.add(s);
            }

            s = (i - 1) + "," + j;
            // add (i - 1, j) pair to heap if not visited
            if (i - 1 >= 0 && !set.contains(s)) {
                pq.add(new Node(i - 1, j, A[i - 1] + B[j]));
                set.add(s);
            }
        }

        return res;
    }

    // util node class
    static class Node {
        int x, y, sum;

        Node(int x, int y, int sum) {
            this.x = x;
            this.y = y;
            this.sum = sum;
        }
    }

    // https://www.interviewbit.com/problems/magician-and-chocolates/
    static int maxChocolates(int A, int[] B) {
        int n = B.length;
        // if time is 0 or no bags present
        if (A == 0 || n == 0)
            return 0;

        int p = 1000000007;
        long res = 0;

        // build max heap from the boxes
        buildMaxHeap(B);

        // for each unit of time, keep adding max from the heap and heapify after halving it
        while (A > 0 && B[0] > 0) {
            res = (res + B[0]) % p;

            B[0] /= 2;
            maxHeapify(B, n, 0);

            A--;
        }

        return (int) res;
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

    // util to swap two positions in an array
    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
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

    static ListNode mergeKSortedLists(ArrayList<ListNode> a) {
        // dummy node and head pointer for resultant list
        ListNode dummy = new ListNode(-1), head = dummy;

        // add first node of all the lists in min heap
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.val));
        pq.addAll(a);

        // keep popping nodes one by one and add its child to the min heap till the end
        while (!pq.isEmpty()) {
            ListNode top = pq.poll();
            if (top.next != null)
                pq.add(top.next);

            // link current node to output
            head.next = top;
            head = head.next;
        }

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/distinct-numbers-in-window/
    static int[] distinctNumbersInWindow(int[] A, int B) {
        int n = A.length;
        // if window size is larger than array size
        if (B > n)
            return new int[0];

        int[] res = new int[n - B + 1];
        Map<Integer, Integer> map = new HashMap<>();
        // insert count of elements of first window
        for (int i = 0; i < B; i++)
            map.put(A[i], map.getOrDefault(A[i], 0) + 1);

        for (int i = B; i < n; i++) {
            // calculate result for current window
            res[i - B] = map.size();

            // increase count of the new element
            map.put(A[i], map.getOrDefault(A[i], 0) + 1);
            // decrease count of the old window start
            int freq = map.get(A[i - B]) - 1;
            if (freq == 0)
                map.remove(A[i - B]);
            else
                map.put(A[i - B], freq);
        }

        // calculate result for the last window
        res[n - B] = map.size();

        return res;
    }

    // https://www.interviewbit.com/problems/lru-cache/
    static class LRUCache {
        // hashmap to store key with values
        private final Map<Integer, Node> map;
        // capacity of the cache
        private final int capacity;
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
            // else if cache is full
            else if (map.size() == capacity) {
                // remove the least recently used
                map.remove(head.key);

                // if capacity is 1, update head node's values and put in map
                if (capacity == 1) {
                    head.key = key;
                    head.val = value;
                    map.put(key, head);
                } else {
                    // remove head
                    removeHead();
                    // add new node at the end in least recently used list and put in map
                    Node curr = addNodeAtEnd(key, value);
                    map.put(key, curr);
                }
            }
            // else cache is not full and does not contain key
            else {
                // cache is empty
                if (head == null) {
                    head = new Node(key, value);
                    tail = head;
                    map.put(key, head);
                } else {
                    // add new node at the end of doubly linked list
                    Node curr = addNodeAtEnd(key, value);
                    map.put(key, curr);
                }
            }
        }

        // util to remove head of the doubly linked list
        private void removeHead() {
            head = head.next;
            head.prev = null;
        }

        // util to add a node at the end of the doubly linked list
        private Node addNodeAtEnd(int key, int val) {
            Node curr = new Node(key, val);
            // link new node at end
            tail.next = curr;
            curr.prev = tail;
            // update tail
            tail = curr;

            return curr;
        }

        // util to shift a node at the end
        private void shiftNodeToEnd(Node curr) {
            // if one node or current node itself is tail
            if (head == tail || curr == tail)
                return;

            // link next node's prev to current's prev
            curr.next.prev = curr.prev;
            // if curr is not head, skip current node from between
            if (curr != head)
                curr.prev.next = curr.next;
                // else skip head node
            else
                head = curr.next;

            // link current node to tail
            curr.prev = tail;
            tail.next = curr;
            curr.next = null;
            // update tail
            tail = curr;
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

    // https://www.interviewbit.com/problems/maximum-sum-combinations/
    @SuppressWarnings("ConstantConditions")
    static int[] maxSumCombinations(int[] A, int[] B, int C) {
        int n = A.length;
        int[] res = new int[C];

        // sort both the arrays
        Arrays.sort(A);
        Arrays.sort(B);

        // max heap based on the sum of pairs
        PriorityQueue<Node> pq = new PriorityQueue<>(
                (n1, n2) -> n2.sum - n1.sum
        );
        pq.add(new Node(n - 1, n - 1, A[n - 1] + B[n - 1]));

        // set to keep track of added pairs
        Set<String> set = new HashSet<>();
        set.add((n - 1) + "," + (n - 1));

        // fill C entries
        for (int k = 0; k < C; k++) {
            Node top = pq.poll();
            // get the topmost sum pair
            res[k] = top.sum;
            int i = top.x, j = top.y;

            String s = i + "," + (j - 1);
            // add (i, j - 1) to heap if not visited
            if (j - 1 >= 0 && !set.contains(s)) {
                pq.add(new Node(i, j - 1, A[i] + B[j - 1]));
                set.add(s);
            }

            s = (i - 1) + "," + j;
            // add (i - 1, j) pair to heap if not visited
            if (i - 1 >= 0 && !set.contains(s)) {
                pq.add(new Node(i - 1, j, A[i - 1] + B[j]));
                set.add(s);
            }
        }

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

    // https://www.interviewbit.com/problems/profit-maximisation/
    static int profitMax(int[] A, int B) {
        // build max heap from the rows
        buildMaxHeap(A);

        int res = 0;
        // select the topmost row everytime, add profit, update empty and heapify
        while (B > 0 && A[0] > 0) {
            res += A[0];

            A[0]--;
            maxHeapify(A, A.length, 0);

            B--;
        }

        return res;
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
}