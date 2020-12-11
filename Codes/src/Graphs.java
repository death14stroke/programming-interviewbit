import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Graphs {
    // https://www.interviewbit.com/problems/region-in-binarymatrix/
    static int largestRegion(int[][] A) {
        int m = A.length, n = A[0].length;
        // length of largest connected component
        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // unvisited node - start dfs
                if (A[i][j] == 1)
                    res = Math.max(res, dfs(i, j, A));
            }
        }

        return res;
    }

    // util to count no of nodes traversed in dfs
    private static int dfs(int i, int j, int[][] A) {
        // already visited node
        if (A[i][j] == 0)
            return 0;

        // mark current node as visited
        A[i][j] = 0;

        // 8 directions in which we can move ahead
        int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
        int m = A.length, n = A[0].length;
        // no of nodes traversed in current dfs
        int noOfNodes = 1;

        // for each direction
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];
            // if path does not exist
            if (x < 0 || x >= m || y < 0 || y >= n)
                continue;

            // add no of nodes traversed in this direction to total
            noOfNodes += dfs(x, y, A);
        }

        return noOfNodes;
    }

    // https://www.interviewbit.com/problems/level-order/
    @SuppressWarnings("ConstantConditions")
    static ArrayList<ArrayList<Integer>> levelOrder(Trees.TreeNode root) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        // empty tree
        if (root == null)
            return res;

        // queue for level order traversal
        Queue<Trees.TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int n = q.size();
            ArrayList<Integer> level = new ArrayList<>();

            // traverse current level
            for (int i = 0; i < n; i++) {
                Trees.TreeNode front = q.poll();
                // add node to current level
                level.add(front.val);

                // append next level nodes to queue
                if (front.left != null)
                    q.add(front.left);
                if (front.right != null)
                    q.add(front.right);
            }

            res.add(level);
        }

        return res;
    }
}