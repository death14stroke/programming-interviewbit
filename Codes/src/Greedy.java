import java.util.Arrays;

class Greedy {
    // https://www.interviewbit.com/problems/highest-product/
    static int highestProduct(int[] A) {
        int n = A.length;
        // sort the array
        Arrays.sort(A);
        // maximum product is the max of product of largest three numbers and 
        // product of largest number and smallest two numbers (when both are negative) 
        return Math.max(A[n - 1] * A[n - 2] * A[n - 3], A[0] * A[1] * A[n - 1]);
    }
}