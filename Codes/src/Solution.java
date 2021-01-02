import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/*public class Solution {
    public static void main(String[] args) {
        int[] A = {39, 99, 70, 24, 49, 13, 86, 43, 88, 74, 45, 92, 72, 71, 90, 32, 19, 76, 84, 46, 63, 15, 87, 1, 39, 58, 17, 65, 99, 43, 83, 29, 64, 67, 100, 14, 17, 100, 81, 26, 45, 40, 95, 94, 86, 2, 89, 57, 52, 91, 45};
        int[] B = {1221, 360, 459, 651, 958, 584, 345, 181, 536, 116, 1310, 403, 669, 1044, 1281, 711, 222, 280, 1255, 257, 811, 409, 698, 74, 838};

        System.out.println(Arrays.toString(BinarySearch.simpleQueries(A, B)));
    }
}*/

public class Solution {
    public static void main(String[] args) {
        int[] A = {39, 99, 70, 24, 49, 13, 86, 43, 88, 74, 45, 92, 72, 71, 90, 32, 19, 76, 84, 46, 63, 15, 87, 1, 39, 58, 17, 65, 99, 43, 83, 29, 64, 67, 100, 14, 17, 100, 81, 26, 45, 40, 95, 94, 86, 2, 89, 57, 52, 91, 45};
        int[] B = {1221, 360, 459, 651, 958, 584, 345, 181, 536, 116, 1310, 403, 669, 1044, 1281, 711, 222, 280, 1255, 257, 811, 409, 698, 74, 838};

        System.out.println(Arrays.toString(solve(A, B)));

        System.out.println(Arrays.toString(BinarySearch.simpleQueries(A, B)));
    }

    public static int[] solve(int[] A, int[] B) {
        int n = A.length;
        int[] next = nextG(A, n);
        int[] prev = nextGRev(A, n);

        //System.out.println("sol next = " + Arrays.toString(next));
        System.out.println("sol nextPrev = " + Arrays.toString(prev));

        Pair[] mat = new Pair[n];
        long mod = (int)1e9+7;
        for(int i=0; i<n; i++){
            int a = A[i];
            long k = a;
            for(int j=2; j<=Math.sqrt(a); j++){
                if(a%j == 0){
                    k = ((k%mod)*(j%mod))%mod;
                    if(a/j != j)
                        k = ((k%mod)*((a/j)%mod))%mod;
                }
            }
            mat[i] = new Pair(k, (1L*(i-prev[i]))*(next[i]-i));
        }

        Arrays.sort(mat, Collections.reverseOrder());

        //System.out.println("solve G array = " + Arrays.toString(mat));

        long[] arr = new long[n];
        arr[0] = mat[0].n;
        for(int i=1; i<n; i++){
            arr[i] = arr[i-1] + mat[i].n;
        }

        int[] ans = new int[B.length];
        int m = B.length;
        for(int i=0; i<m; i++){
            int a = Arrays.binarySearch(arr, B[i]);
            if(a<0){
                a = -a;
                a -= 1;
            }

            ans[i] = (int)mat[a].pro;
        }
        return ans;

    }
    public static class Pair implements Comparable<Pair>{
        long pro;
        long n;
        Pair(long a, long b){
            pro = a;
            n = b;
        }
        public int compareTo(Pair p){
            return (int)(this.pro - p.pro);
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "pro=" + pro +
                    ", n=" + n +
                    '}';
        }
    }
    public static int[] nextG(int[] arr, int n) {
        int[] ans = new int[n];
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);
        for(int i=1; i<n; i++) {
            while(!stack.isEmpty() && arr[stack.peek()]<=arr[i])
                ans[stack.pop()] = i;
            stack.push(i);
        }
        while(!stack.isEmpty())
            ans[stack.pop()] = n;
        return ans;
    }

    public static int[] nextGRev(int[] arr, int n) {
        int[] ans = new int[n];
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(n-1);
        for(int i=n-2; i>=0; i--) {
            while(!stack.isEmpty() && arr[stack.peek()]<arr[i])
                ans[stack.pop()] = i;
            stack.push(i);
        }
        while(!stack.isEmpty())
            ans[stack.pop()] = -1;
        return ans;
    }
}