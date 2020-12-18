public class Solution {
    public static void main(String[] args) {
        int A = 12;
        int[][] B = {
                {1, 11},
                {2, 12},
                {2, 10},
                {2, 5},
                {2, 4},
                {2, 6},
                {8, 6},
                {9, 10},
                {7, 12},
                {9, 12},
                {1, 10},
                {8, 5},
                {8, 4},
                {3, 6},
                {1, 3},
                {12, 4},
                {8, 3},
                {9, 7}
        };

        System.out.print(Graphs.twoTeams(A, B));
    }
}