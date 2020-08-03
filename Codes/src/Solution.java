public class Solution {
    public static void main(String[] args) {
        int[] A = {6, 10, 5, 4, 9, 120};

        Array.printArray(A);
        System.out.printf("first repeating = %d\n", Hashing.firstRepeating(A));
    }
}