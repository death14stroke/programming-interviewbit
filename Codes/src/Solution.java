import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        int A = 3682;
        int[] B = {13511, 9286, 6132, 2958, 21799, 5160, 22244, 5969, 14955, 12808, 3456, 11238, 6511, 4637, 2558, 18808, 15537, 5598, 14022, 4885, 17572, 3775, 23999, 21993, 22203, 24768, 22045, 10785, 11393, 7080, 12218, 16247, 7709};

        System.out.println(Arrays.toString(DP.birthdayBombs(A, B)));
    }
}