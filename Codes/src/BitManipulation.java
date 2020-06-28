import java.util.List;

public class BitManipulation {
    // https://www.interviewbit.com/problems/single-number/
    static int singleNumber(final List<Integer> A) {
        int ans = 0;
        // x ^ x = 0, y ^ 0 = y
        // all duplicates will XOR to 0 and only unique will remain
        for (int num : A)
            ans ^= num;

        return ans;
    }
}