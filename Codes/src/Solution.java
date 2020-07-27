public class Solution {
    public static void main(String[] args) {
        int[] A = {2, 1, 5, 6, 2, 3};

        Array.printArray(A);
        System.out.printf("largest area = %d\n", StackQueue.largestRectangleArea(A));
    }
}