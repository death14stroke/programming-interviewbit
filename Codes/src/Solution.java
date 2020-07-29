public class Solution {
    public static void main(String[] args) {
        int[] A = {1, 2};

        Array.printArray(A);
        System.out.printf("trapped water = %d\n", StackQueue.rainWaterTrapped(A));
    }
}