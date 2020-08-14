class HeapsMaps {
    // https://www.interviewbit.com/problems/magician-and-chocolates/
    static int maxChocolates(int A, int[] B) {
        int n = B.length;
        // if time is 0 or no bags present
        if (A == 0 || n == 0)
            return 0;

        int p = 1000000007;
        long res = 0;

        // build max heap from the boxes
        buildHeap(B);

        // for each unit of time, keep adding max from the heap and heapify after halving it
        while (A > 0 && B[0] > 0) {
            res = (res + B[0]) % p;

            B[0] /= 2;
            heapify(B, n, 0);

            A--;
        }

        return (int) res;
    }

    // util to build heap from array in O(n) time
    private static void buildHeap(int[] A) {
        int n = A.length;
        // call heapify from the bottom most node except leaves
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(A, n, i);
    }

    // util to heapify from current node as root
    private static void heapify(int[] A, int n, int i) {
        int largest = i, left = 2 * i + 1, right = 2 * i + 2;

        // check if left or right child are greater
        if (left < n && A[left] > A[largest])
            largest = left;
        if (right < n && A[right] > A[largest])
            largest = right;

        // if any child is greater, swap it with root and heapify recursively from the child
        if (largest != i) {
            swap(A, i, largest);
            heapify(A, n, largest);
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
        buildHeap(A);

        int res = 0;
        // select the topmost row everytime, add profit, update empty and heapify
        while (B > 0 && A[0] > 0) {
            res += A[0];

            A[0]--;
            heapify(A, A.length, 0);

            B--;
        }

        return res;
    }
}