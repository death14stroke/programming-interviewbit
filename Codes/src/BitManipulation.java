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

    // https://www.interviewbit.com/problems/divide-integers/
    static int divide(int A, int B) {
        // save the sign of result
        int sign = (A < 0) ^ (B < 0) ? -1 : 1;
        // long to check for overflow
        long a = Math.abs((long) A), b = Math.abs((long) B);
        long quotient = 0, temp = 0;

        // check for each bit in quotient if it is set
        for (int i = 31; i >= 0; i--) {
            if (temp + (b << i) <= a) {
                temp += (b << i);
                quotient = quotient | (1L << i);
            }
        }
        // make the quotient negative if required
        quotient *= sign;

        // overflow
        if (quotient >= Integer.MAX_VALUE || quotient < Integer.MIN_VALUE)
            return Integer.MAX_VALUE;
        return (int) quotient;
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

    // https://www.interviewbit.com/problems/min-xor-value/
    private static Trie trie;

    static int minXor(ArrayList<Integer> A) {
        int minXor = Integer.MAX_VALUE;

        // insert first element into trie
        trie = new Trie();
        trie.insert(A.get(0));

        // search for the number with largest number of similar bits with current number till now
        // take xor and compare with minimum. Insert current number in trie
        for (int i = 1; i < A.size(); i++) {
            minXor = Math.min(minXor, minXorUtil(A.get(i)));
            trie.insert(A.get(i));
        }

        return minXor;
    }

    // util to find the number with largest number of bits similar to key in trie
    // and take xor with it as the result xor will be minimum
    private static int minXorUtil(int key) {
        Trie.TrieNode curr = trie.root;

        // check from MSB to LSB
        for (int i = 31; i >= 0; i--) {
            int bit = (key & 1 << i) == 0 ? 0 : 1;

            // if there is number with current bit same
            if (curr.child[bit] != null)
                curr = curr.child[bit];
                // else bit at this position will be different for all
            else if (curr.child[1 - bit] != null)
                curr = curr.child[1 - bit];
            else
                break;
        }

        // minimum xor value including key with any number from trie
        return key ^ curr.data;
    }

    static class Trie {
        static class TrieNode {
            int data;
            TrieNode[] child;

            // binary trie with two child for each bit
            // trie runs from MSB to LSB
            TrieNode() {
                child = new TrieNode[2];
            }
        }

        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        public void insert(int key) {
            TrieNode curr = root;

            // check from MSB to LSB
            for (int i = 31; i >= 0; i--) {
                int bit = (key & (1 << i)) == 0 ? 0 : 1;

                if (curr.child[bit] == null)
                    curr.child[bit] = new TrieNode();

                curr = curr.child[bit];
            }

            // leaf node will store the value
            curr.data = key;
        }
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