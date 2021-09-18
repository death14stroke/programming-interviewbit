import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        String[] A = {
                "010",
                "100",
                "001"
        };

        System.out.println(Arrays.deepToString(DP.queenAttack(A)));
    }
}