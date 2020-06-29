import java.util.ArrayList;
import java.util.List;

public class BitManipulation {
    // https://www.interviewbit.com/problems/number-of-1-bits/
    static int numSetBits(long a) {
        int cnt = 0;

        // a & (a - 1) : clears the lowest set bit
        // hence this loop will run exactly # set bits times
        while (a > 0) {
            cnt++;
            a = a & (a - 1);
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/reverse-bits/
    static long reverseBits(long a) {
        // swap group of 16 bits with its next group
        a = ((a & 0xFFFF0000) >> 16 | (a & 0x0000FFFF) << 16);
        // swap group of 8 bits with its next group
        a = ((a & 0xFF00FF00) >> 8 | (a & 0x00FF00FF) << 8);
        // swap group of 4 bits with its next group
        a = ((a & 0xF0F0F0F0) >> 4 | (a & 0x0F0F0F0F) << 4);
        // swap group of 2 bits with its next group
        a = ((a & 0xCCCCCCCC) >> 2 | (a & 0x33333333) << 2);
        // swap group of 1 bits with its next group
        a = ((a & 0xAAAAAAAA) >> 1 | (a & 0x55555555) << 1);

        return a;
    }

    // https://www.interviewbit.com/problems/different-bits-sum-pairwise/
    static int cntBits(ArrayList<Integer> A) {
        int p = 1000000007;
        long sum = 0;

        for (int i = 0; i < 32; i++) {
            int cnt = 0;
            // count numbers with set bit at pos i
            for (int num : A)
                cnt += (num & (1 << i)) == 0 ? 0 : 1;

            // make all possible pairs from set and unset bits at position i
            sum = (sum + (2L * (long) cnt * (long) (A.size() - cnt)) % p) % p;
        }

        return (int) sum;
    }

    // https://www.interviewbit.com/problems/single-number/
    static int singleNumber(final List<Integer> A) {
        int ans = 0;
        // x ^ x = 0, y ^ 0 = y
        // all duplicates will XOR to 0 and only unique will remain
        for (int num : A)
            ans ^= num;

        return ans;
    }

    // https://www.interviewbit.com/problems/single-number-ii/
    static int singleNumber2(final List<Integer> A) {
        int ans = 0;

        // check for every bit position if # of set bits form a 3-cluster or not
        for (int i = 0; i < 32; i++) {
            int cnt = 0;

            for (int num : A) {
                // found set bit at position i
                if ((num & (1 << i)) != 0)
                    cnt++;
            }

            // if set bits don't form a 3-cluster, set the bit at that position in result
            if (cnt % 3 != 0)
                ans = ans | (1 << i);
        }

        return ans;
    }
}