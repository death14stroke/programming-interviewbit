import java.util.*;

class Graphs {
    // https://www.interviewbit.com/problems/path-in-directed-graph/
    @SuppressWarnings("unchecked")
    static int isPath(int A, int[][] B) {
        // create adjacency list for the graph
        List<Integer>[] adj = new List[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new LinkedList<>();
        for (int[] edge : B)
            adj[edge[0]].add(edge[1]);
        // perform DFS till the last node is reached starting from 1
        return isPathUtil(1, A, adj, new boolean[A + 1]) ? 1 : 0;
    }

    // util to check if node A is reached or not using DFS
    private static boolean isPathUtil(int u, int A, List<Integer>[] adj, boolean[] visited) {
        // reached end
        if (u == A)
            return true;

        // mark current node as visited
        visited[u] = true;
        // for each neighbour
        for (int v : adj[u]) {
            // if unvisited and has a path to node A, return true
            if (!visited[v] && isPathUtil(v, A, adj, visited))
                return true;
        }
        // no path found to node A
        return false;
    }

    // https://www.interviewbit.com/problems/water-flow/
    static int waterFlow(int[][] A) {
        int m = A.length, n = A[0].length;
        // visited array for both rivers: 0 - not visited, 1 - river1, 2 - river2, 3 - both rivers
        // perform reverse traversal from sink node to start node to get all nodes which will flow into either river
        int[][] vis = new int[m][n];
        // top row is river 1
        for (int j = 0; j < n; j++) {
            // perform BFS if cell not visited already
            if (vis[0][j] == 0)
                waterFlowUtil(0, j, A, vis, 1);
        }
        // left row is river 1
        for (int i = 0; i < m; i++) {
            // perform BFS if cell not visited already
            if (vis[i][0] == 0)
                waterFlowUtil(i, 0, A, vis, 1);
        }
        // bottom row is river 2
        for (int j = 0; j < n; j++) {
            // perform BFS if cell not visited or only river 1 visited
            if (vis[m - 1][j] <= 1)
                waterFlowUtil(m - 1, j, A, vis, 2);
        }
        // right row is river 2
        for (int i = 0; i < m; i++) {
            // perform BFS if cell not visited or only river 1 visited
            if (vis[i][n - 1] <= 1)
                waterFlowUtil(i, n - 1, A, vis, 2);
        }

        int res = 0;
        // for each cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // if visited by both rivers, update result count
                if (vis[i][j] == 3)
                    res++;
            }
        }

        return res;
    }

    // util to visit cells by river using BFS.
    private static void waterFlowUtil(int i, int j, int[][] A, int[][] vis, int river) {
        int m = A.length, n = A[0].length;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        // queue for BFS
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{i, j});
        // mark current cell as visited by river
        vis[i][j] += river;

        while (!q.isEmpty()) {
            int[] front = q.poll();
            // for each direction
            for (int[] dir : dirs) {
                int x = front[0] + dir[0], y = front[1] + dir[1];
                // if out of bounds or flow is less than current flow or already visited by current river
                if (x < 0 || x >= m || y < 0 || y >= n || A[x][y] < A[front[0]][front[1]] || vis[x][y] >= river)
                    continue;
                // add to queue for BFS
                q.add(new int[]{x, y});
                // mark as visited by river
                vis[x][y] += river;
            }
        }
    }

    // https://www.interviewbit.com/problems/stepping-numbers/
    static ArrayList<Integer> steppingNumbers(int A, int B) {
        ArrayList<Integer> res = new ArrayList<>();
        // queue for BFS
        Queue<Integer> q = new LinkedList<>();
        // 0 to 9 will be the first 10 stepping numbers
        for (int i = 0; i <= 9; i++)
            q.add(i);
        // for each number in queue
        while (!q.isEmpty()) {
            int num = q.poll();
            // if in range, add to result
            if (num >= A && num <= B)
                res.add(num);
            // if 0, cannot form new numbers or if out of range, no use in forming new numbers
            if (num == 0 || num > B)
                continue;

            // last digit in current number
            int digit = num % 10;
            // new number with unit's digit one less than current number
            int num1 = num * 10 + (digit - 1);
            // new number with unit's digit one more than current number
            int num2 = num * 10 + (digit + 1);
            // for 0, can only add one
            if (digit == 0)
                q.add(num2);
                // for 9, can only subtract one
            else if (digit == 9)
                q.add(num1);
                // subtract one and add one to the last digit
            else {
                q.add(num1);
                q.add(num2);
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/capture-regions-on-board/
    static void captureRegions(ArrayList<ArrayList<Character>> board) {
        int m = board.size(), n = board.get(0).size();
        // start DFS from each 'O' on the four boundaries and mark them as '.'
        for (int i = 0; i < m; i++) {
            if (board.get(i).get(0) == 'O')
                captureRegionsUtil(i, 0, board);
            if (board.get(i).get(n - 1) == 'O')
                captureRegionsUtil(i, n - 1, board);
        }

        for (int j = 0; j < n; j++) {
            if (board.get(0).get(j) == 'O')
                captureRegionsUtil(0, j, board);
            if (board.get(m - 1).get(j) == 'O')
                captureRegionsUtil(m - 1, j, board);
        }

        // mark each '.' as 'O' (un-captured) and each 'O' as 'X' (captured)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board.get(i).get(j) == '.')
                    board.get(i).set(j, 'O');
                else if (board.get(i).get(j) == 'O')
                    board.get(i).set(j, 'X');
            }
        }
    }

    // util to mark 'O' as '.' on board using DFS
    private static void captureRegionsUtil(int i, int j, ArrayList<ArrayList<Character>> board) {
        // mark current position as 'O'
        board.get(i).set(j, '.');

        int m = board.size(), n = board.get(0).size();
        // for each direction if there is 'O' move and mark it as '.'
        if (i + 1 < m && board.get(i + 1).get(j) == 'O')
            captureRegionsUtil(i + 1, j, board);
        if (i > 0 && board.get(i - 1).get(j) == 'O')
            captureRegionsUtil(i - 1, j, board);
        if (j + 1 < n && board.get(i).get(j + 1) == 'O')
            captureRegionsUtil(i, j + 1, board);
        if (j > 0 && board.get(i).get(j - 1) == 'O')
            captureRegionsUtil(i, j - 1, board);
    }

    // https://www.interviewbit.com/problems/word-search-board/
    static int wordSearch(String[] A, String B) {
        int m = A.length, n = A[0].length();
        // for each cell on board
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // if word can start with current cell, check if word found
                if (A[i].charAt(j) == B.charAt(0) && wordSearchUtil(i, j, 0, B, A))
                    return 1;
            }
        }
        // word not found on board
        return 0;
    }

    // util to check for word on board using DFS with repeated cells in path
    private static boolean wordSearchUtil(int i, int j, int pos, String B, String[] A) {
        // reached last character in the word
        if (pos == B.length() - 1)
            return true;

        // move to next character
        pos++;

        int m = A.length, n = A[0].length();
        // check if word found in each direction else word not found
        if (i + 1 < m && B.charAt(pos) == A[i + 1].charAt(j) && wordSearchUtil(i + 1, j, pos, B, A))
            return true;
        if (i > 0 && B.charAt(pos) == A[i - 1].charAt(j) && wordSearchUtil(i - 1, j, pos, B, A))
            return true;
        if (j + 1 < n && B.charAt(pos) == A[i].charAt(j + 1) && wordSearchUtil(i, j + 1, pos, B, A))
            return true;
        return j > 0 && B.charAt(pos) == A[i].charAt(j - 1) && wordSearchUtil(i, j - 1, pos, B, A);
    }

    // https://www.interviewbit.com/problems/path-with-good-nodes/
    @SuppressWarnings("unchecked")
    static int pathWithGoodNodes(int[] A, int[][] B, int C) {
        int n = A.length;
        // create adjacency list for undirected tree
        List<Integer>[] adj = new List[n + 1];
        for (int i = 1; i <= n; i++)
            adj[i] = new LinkedList<>();
        for (int[] edge : B) {
            int u = edge[0], v = edge[1];
            adj[u].add(v);
            adj[v].add(u);
        }
        // start DFS from root (1) to count good paths
        return goodPaths(1, -1, C, A, adj);
    }

    // util to count for good root to leaf paths using DFS
    private static int goodPaths(int u, int parent, int C, int[] A, List<Integer>[] adj) {
        // good node - update count
        if (A[u - 1] == 1)
            C--;
        // exhausted all good nodes
        if (C < 0)
            return 0;
        // if leaf node, update good paths count
        if (adj[u].size() == 1 && adj[u].get(0) == parent)
            return 1;

        int cnt = 0;
        // for each neighbour
        for (int v : adj[u]) {
            // if not parent, recursively perform DFS to count good paths
            if (v != parent)
                cnt += goodPaths(v, u, C, A, adj);
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/largest-distance-between-nodes-of-a-tree/
    private static int res;

    @SuppressWarnings("unchecked")
    public static int largestDistance(int[] A) {
        int n = A.length;
        // create adjacency list
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++)
            adj[i] = new LinkedList<>();
        // root of the tree
        int root = 0;
        for (int i = 0; i < n; i++) {
            // add edge from root to child
            if (A[i] != -1)
                adj[A[i]].add(i);
            else
                root = i;
        }

        res = 0;
        // perform dfs to compute height of each subtree
        largestDistanceUtil(root, adj);

        return res;
    }

    private static int largestDistanceUtil(int u, List<Integer>[] adj) {
        // leaf node
        if (adj[u].isEmpty())
            return 1;

        // find the two subtrees with maximum height
        int max1 = 0, max2 = 0;
        for (int v : adj[u]) {
            int height = largestDistanceUtil(v, adj);
            // found subtree with maximum height
            if (height >= max1) {
                max2 = max1;
                max1 = height;
            }
            // found subtree with 2nd maximum height
            else if (height > max2) {
                max2 = height;
            }
        }
        // largest distance = max(the largest distance so far, sum of heights of max 2 subtrees)
        res = Math.max(res, max1 + max2);
        // return height of current subtree
        return max1 + 1;
    }

    // https://www.interviewbit.com/problems/cycle-in-directed-graph/
    @SuppressWarnings("unchecked")
    static int isDirectedCycle(int A, int[][] B) {
        // create adjacency list
        List<Integer>[] adj = new List[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new LinkedList<>();
        for (int[] edge : B)
            adj[edge[0]].add(edge[1]);

        // boolean array to keep track of visited nodes
        boolean[] visited = new boolean[A + 1];
        // boolean array to keep track of nodes currently in DFS path stack
        boolean[] recStack = new boolean[A + 1];

        for (int i = 1; i <= A; i++) {
            // perform DFS for each connected component to check for cycle
            if (!visited[i] && isDirectedCyclicUtil(i, adj, visited, recStack))
                return 1;
        }
        // no cycles found
        return 0;
    }

    // util to check for cycle in directed graph using DFS
    private static boolean isDirectedCyclicUtil(int u, List<Integer>[] adj, boolean[] visited, boolean[] recStack) {
        // mark current node as visited and part of stack
        visited[u] = recStack[u] = true;
        // for each neighbour
        for (int v : adj[u]) {
            // if node already in stack, there is cycle
            if (recStack[v])
                return true;
            // if node is unvisited and it forms cycle
            if (!visited[v] && isDirectedCyclicUtil(v, adj, visited, recStack))
                return true;
        }
        // pop current node from stack
        recStack[u] = false;
        // no cycles starting from current node
        return false;
    }

    // https://www.interviewbit.com/problems/delete-edge/
    private static final int MOD = 1_000_000_007;
    private static long totalSum, maxProduct;

    @SuppressWarnings("unchecked")
    static int deleteEdge(int[] A, int[][] B) {
        int n = A.length;
        // create adjacency list for undirected tree
        List<Integer>[] adj = new List[n + 1];
        for (int i = 1; i <= n; i++)
            adj[i] = new LinkedList<>();
        for (int[] edge : B) {
            int u = edge[0], v = edge[1];
            adj[u].add(v);
            adj[v].add(u);
        }

        // calculate total sum of all node weights
        totalSum = 0;
        for (int val : A)
            totalSum += val;
        // maximum product of sum of two subtrees
        maxProduct = 0;
        // DFS util to calculate subtree sum at each node
        deleteEdgeUtil(1, -1, A, adj);

        return (int) maxProduct;
    }

    // util to calculate subtree sum at each node and maximize result
    private static long deleteEdgeUtil(int u, int parent, int[] A, List<Integer>[] adj) {
        long sum = A[u - 1];
        // for each neighbour
        for (int v : adj[u]) {
            if (v != parent) {
                // if not parent node, recursively calculate subtree sum, update result and append to current sum
                long subtreeSum = deleteEdgeUtil(v, u, A, adj);
                maxProduct = Math.max(maxProduct, (subtreeSum * (totalSum - subtreeSum)) % MOD);
                sum += subtreeSum;
            }
        }

        return sum;
    }

    // https://www.interviewbit.com/problems/two-teams/
    @SuppressWarnings("unchecked")
    static int twoTeams(int A, int[][] B) {
        // adjacency list for the undirected graph
        List<Integer>[] adj = new List[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new LinkedList<>();
        for (int[] edge : B) {
            int u = edge[0], v = edge[1];
            adj[u].add(v);
            adj[v].add(u);
        }

        // color array for bipartite partitioning - (0, 1) are the colors
        int[] color = new int[A + 1];
        Arrays.fill(color, -1);

        for (int i = 1; i <= A; i++) {
            // if any connected component is not bipartite, cannot form 2 teams
            if (color[i] == -1 && !isBipartiteUtil(i, adj, color))
                return 0;
        }
        // graph is bipartite - can form 2 teams
        return 1;
    }

    // util to check if connected component is bipartite or not using BFS
    private static boolean isBipartiteUtil(int src, List<Integer>[] adj, int[] color) {
        // queue for BFS
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        // color source vertex as 0
        color[src] = 0;

        while (!q.isEmpty()) {
            int u = q.poll();
            // for each neighbour of u
            for (int v : adj[u]) {
                // if same color, graph is not bipartite
                if (color[v] == color[u])
                    return false;
                // if not colored, color with opposite color and add to queue for BFS
                if (color[v] == -1) {
                    color[v] = 1 - color[u];
                    q.add(v);
                }
            }
        }
        // subgraph is bipartite
        return true;
    }

    // https://www.interviewbit.com/problems/valid-path/
    private static final int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

    // problem statement wrong: (0, 0) is top left and (x, y) is bottom right
    static String validPath(int x, int y, int N, int R, int[] A, int[] B) {
        // visited array for BFS
        boolean[][] visited = new boolean[x + 1][y + 1];
        // for each point (i, j)
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                // for each circle
                for (int k = 0; k < N; k++) {
                    // if (i, j) is in circle, mark (i, j) as visited
                    if (isInCircle(i, j, A[k], B[k], R)) {
                        visited[i][j] = true;
                        break;
                    }
                }
            }
        }
        // source or destination touches any circle
        if (visited[0][0] || visited[x][y])
            return "NO";

        // queue for BFS
        Queue<int[]> q = new LinkedList<>();
        // start with (0, 0)
        q.add(new int[]{0, 0});
        visited[0][0] = true;
        // perform BFS in 8 directions
        while (!q.isEmpty()) {
            int[] front = q.poll();
            // reached destination
            if (front[0] == x && front[1] == y)
                return "YES";

            // for each direction
            for (int[] dir : dirs) {
                int x1 = front[0] + dir[0], y1 = front[1] + dir[1];
                // if out of bounds or already visited
                if (x1 < 0 || x1 > x || y1 < 0 || y1 > y || visited[x1][y1])
                    continue;
                // add to queue for BFS
                q.add(new int[]{x1, y1});
                // mark as visited
                visited[x1][y1] = true;
            }
        }
        // cannot reach destination
        return "NO";
    }

    // util to check if (x, y) is in or on circle with center (x0, y0) and radius R
    private static boolean isInCircle(int x, int y, int x0, int y0, int R) {
        return (x - x0) * (x - x0) + (y - y0) * (y - y0) <= R * R;
    }

    // https://www.interviewbit.com/problems/snake-ladder-problem/
    @SuppressWarnings("ConstantConditions")
    static int snakesAndLadders(int[][] A, int[][] B) {
        // create edge map for all the ladders and snakes
        int[] graph = new int[101];
        for (int[] ladder : A)
            graph[ladder[0]] = ladder[1];
        for (int[] snake : B)
            graph[snake[0]] = snake[1];

        // #dice rolls till now
        int dist = 0;
        // queue for BFS
        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        // visited array for BFS
        boolean[] visited = new boolean[101];
        visited[1] = true;
        // since in the board game all edges will have equal weight, BFS will give us the shortest path
        while (!q.isEmpty()) {
            int n = q.size();
            for (int i = 0; i < n; i++) {
                int front = q.poll();
                // reached end
                if (front == 100)
                    return dist;

                // try all 6 possible numbers on dice
                for (int j = 1; j <= 6; j++) {
                    int x = front + j;
                    // not valid roll
                    if (x > 100)
                        break;
                    // if any ladder or snake present, move up/down along that
                    if (graph[x] != 0)
                        x = graph[x];
                    // mark as visited and add to queue
                    if (!visited[x]) {
                        visited[x] = true;
                        q.add(x);
                    }
                }
            }
            // do next dice roll
            dist++;
        }
        // solution not found
        return -1;
    }

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
        // mark current node as visited
        A[i][j] = 0;

        int m = A.length, n = A[0].length;
        // #nodes traversed in current dfs
        int cnt = 1;
        // for each direction
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];
            // if path does not exist
            if (x < 0 || x >= m || y < 0 || y >= n || A[x][y] == 0)
                continue;
            // add no of nodes traversed in this direction to total
            cnt += dfs(x, y, A);
        }

        return cnt;
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

    // https://www.interviewbit.com/problems/smallest-multiple-with-0-and-1/
    static String smallestMultiple(int A) {
        // base cases
        if (A == 0)
            return "0";
        if (A == 1)
            return "1";

        // start BFS from "1"
        Queue<StringNode> q = new LinkedList<>();
        q.add(new StringNode('1', 1, null));
        // set to check if remainder is seen or not. A smaller string with same remainder will generate
        // all the possible remainders that can be generated by a larger string with same remainder
        boolean[] remainderSet = new boolean[A];
        remainderSet[1] = true;
        // last digit in the result string
        StringNode front = null;

        while (!q.isEmpty()) {
            front = q.poll();
            // found required number
            if (front.remainder == 0)
                break;

            // if remainder not seen before, perform BFS
            int rem1 = (front.remainder * 10) % A, rem2 = (front.remainder * 10 + 1) % A;
            if (!remainderSet[rem1]) {
                remainderSet[rem1] = true;
                q.add(new StringNode('0', rem1, front));
            }
            if (!remainderSet[rem2]) {
                remainderSet[rem2] = true;
                q.add(new StringNode('1', rem2, front));
            }
        }

        // build the reverse number
        StringBuilder builder = new StringBuilder();
        while (front != null) {
            builder.append(front.c);
            front = front.prev;
        }
        return builder.reverse().toString();
    }

    // data class for BFS node
    private static class StringNode {
        // current character in the result string
        char c;
        // remainder of the string formed till now
        int remainder;
        // pointer to the previous char's node in the string
        StringNode prev;

        StringNode(char c, int remainder, StringNode prev) {
            this.c = c;
            this.remainder = remainder;
            this.prev = prev;
        }
    }

    // https://www.interviewbit.com/problems/min-cost-path/
    static int minCostPath(int A, int B, String[] C) {
        // deque for 0-1 BFS
        Deque<int[]> q = new LinkedList<>();
        // start with (0, 0)
        q.addLast(new int[]{0, 0, 0});
        // visited array for BFS
        boolean[][] visited = new boolean[A][B];

        // perform BFS
        while (!q.isEmpty()) {
            int[] front = q.pollFirst();
            int x = front[0], y = front[1], dist = front[2];
            // reached destination
            if (x == A - 1 && y == B - 1)
                return dist;

            // mark current node as visited
            visited[x][y] = true;
            // check down
            if (x + 1 < A && !visited[x + 1][y]) {
                // if current node says to go down, no cost else add 1 cost
                if (C[x].charAt(y) == 'D')
                    q.addFirst(new int[]{x + 1, y, dist});
                else
                    q.addLast(new int[]{x + 1, y, dist + 1});
            }
            // check up
            if (x > 0 && !visited[x - 1][y]) {
                // if current node says to go up, no cost else add 1 cost
                if (C[x].charAt(y) == 'U')
                    q.addFirst(new int[]{x - 1, y, dist});
                else
                    q.addLast(new int[]{x - 1, y, dist + 1});
            }
            // check right
            if (y + 1 < B && !visited[x][y + 1]) {
                // if current node says to go right, no cost else add 1 cost
                if (C[x].charAt(y) == 'R')
                    q.addFirst(new int[]{x, y + 1, dist});
                else
                    q.addLast(new int[]{x, y + 1, dist + 1});
            }
            // check left
            if (y > 0 && !visited[x][y - 1]) {
                // if current node says to go left, no cost else add 1 cost
                if (C[x].charAt(y) == 'L')
                    q.addFirst(new int[]{x, y - 1, dist});
                else
                    q.addLast(new int[]{x, y - 1, dist + 1});
            }
        }
        // path not found
        return -1;
    }

    // https://www.interviewbit.com/problems/permutation-swaps/
    static int permutationSwaps(int[] A, int[] B, int[][] C) {
        int n = A.length;
        // union find with rank and path compression
        UnionFindRank uf = new UnionFindRank(n + 1);
        // take union of end points for each edge
        for (int[] edge : C) {
            int i1 = edge[0] - 1, i2 = edge[1] - 1;
            uf.union(A[i1], A[i2]);
        }

        // for each index
        for (int i = 0; i < n; i++) {
            // if values not equal and don't have same subset, cannot swap them
            if (A[i] != B[i] && uf.find(A[i]) != uf.find(B[i]))
                return 0;
        }
        // can make swaps to get output permutation
        return 1;
    }

    // union find data structure with rank and path compression
    static class UnionFindRank {
        private final int[] parent, rank;

        UnionFindRank(int n) {
            parent = new int[n];
            rank = new int[n];
        }

        // util to find parent
        public int find(int i) {
            // base-case
            if (parent[i] == 0)
                return i;
            // path compression
            parent[i] = find(parent[i]);

            return parent[i];
        }

        // util to union two nodes
        public void union(int x, int y) {
            x = find(x);
            y = find(y);
            // already in same set - no need to union
            if (x == y)
                return;
            // rank is same - update rank of any node
            if (rank[x] == rank[y])
                rank[x]++;
            // mark higher rank node as parent
            if (rank[x] < rank[y])
                parent[x] = y;
            else
                parent[y] = x;
        }
    }

    // https://www.interviewbit.com/problems/commutable-islands/
    static int minCostBridges(int A, int[][] B) {
        int res = 0;
        // #edges required for MST
        int reqNoOfEdges = A - 1;
        // union find with rank and path compression
        UnionFindRank uf = new UnionFindRank(A + 1);
        // sort the edges in increasing order of weight
        Arrays.sort(B, Comparator.comparingInt(e -> e[2]));
        // for each edge
        for (int[] edge : B) {
            int u = edge[0], v = edge[1], weight = edge[2];
            // if both points are in same set, adding edge will form cycle
            if (uf.find(u) == uf.find(v))
                continue;

            // update minimum cost
            res += weight;
            // update #edges remaining
            reqNoOfEdges--;
            // MST is complete
            if (reqNoOfEdges == 0)
                break;
            // add both endpoints to the same set
            uf.union(u, v);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/possibility-of-finishing-all-courses-given-prerequisites/
    @SuppressWarnings("unchecked")
    static int canFinishCourses(int A, int[] B, int[] C) {
        // create adjacency list for the graph
        List<Integer>[] adj = new List[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new LinkedList<>();
        for (int i = 0; i < B.length; i++)
            adj[B[i]].add(C[i]);

        // boolean array to keep track of visited nodes
        boolean[] visited = new boolean[A + 1];
        // boolean array to keep track of nodes currently in DFS path stack
        boolean[] recStack = new boolean[A + 1];

        for (int i = 1; i <= A; i++) {
            // perform DFS for each connected component. If found cycle, cannot complete courses
            if (!visited[i] && isDirectedCyclicUtil(i, adj, visited, recStack))
                return 0;
        }
        // no cycles. Can complete all courses in a topological order
        return 1;
    }

    // https://www.interviewbit.com/problems/cycle-in-undirected-graph/
    // Approach 1 - using DFS (T.C O(V + E))
    @SuppressWarnings("unchecked")
    static int isUndirectedCycle(int A, int[][] B) {
        // create adjacency list for graph
        List<Integer>[] adj = new List[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new LinkedList<>();
        for (int[] edge : B) {
            int u = edge[0], v = edge[1];
            adj[u].add(v);
            adj[v].add(u);
        }

        // visited array for DFS
        boolean[] visited = new boolean[A + 1];
        // perform DFS for each connected component and check if cycle found
        for (int i = 1; i <= A; i++) {
            if (!visited[i] && isUndirectedCycleUtil(i, -1, adj, visited))
                return 1;
        }
        // no cycles found
        return 0;
    }

    // util to check cycle in undirected graph using DFS
    private static boolean isUndirectedCycleUtil(int u, int parent, List<Integer>[] adj, boolean[] visited) {
        // mark current node as visited
        visited[u] = true;
        // for each neighbour
        for (int v : adj[u]) {
            // if visited node and is not the parent of the current node, cycle found
            if (visited[v] && v != parent)
                return true;
            // if unvisited node and forms cycle
            if (!visited[v] && isUndirectedCycleUtil(v, u, adj, visited))
                return true;
        }
        // no cycles formed by any neighbours
        return false;
    }

    // Approach 2 - using union find (T.C: O(E log V))
    static int isUndirectedCycleUnionFind(int A, int[][] B) {
        // union-find rank with path compression
        UnionFindRank uf = new UnionFindRank(A + 1);
        // for each edge
        for (int[] edge : B) {
            int u = edge[0], v = edge[1];
            // if in same subset, cycle exists
            if (uf.find(u) == uf.find(v))
                return 1;
            // perform union of both parents
            uf.union(u, v);
        }
        // no cycles found
        return 0;
    }

    // https://www.interviewbit.com/problems/black-shapes/
    static int blackShapes(String[] A) {
        int m = A.length, n = A[0].length();
        // #connected components
        int res = 0;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // if character is 'X' and not visited
                if (A[i].charAt(j) == 'X' && !visited[i][j]) {
                    // update count
                    res++;
                    // perform DFS
                    blackShapesUtil(i, j, m, n, A, visited);
                }
            }
        }

        return res;
    }

    // util to perform DFS for each connected component
    private static void blackShapesUtil(int i, int j, int m, int n, String[] A, boolean[][] visited) {
        // mark current position as visited
        visited[i][j] = true;

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        // for all 4 directions
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];
            // if no path or character is 'O' or already visited
            if (x < 0 || x >= m || y < 0 || y >= n || A[x].charAt(y) == 'O' || visited[x][y])
                continue;
            // perform DFS
            blackShapesUtil(x, y, m, n, A, visited);
        }
    }

    // https://www.interviewbit.com/problems/convert-sorted-list-to-binary-search-tree/
    static Trees.TreeNode sortedListToBST(LinkedLists.ListNode head) {
        // empty linked list
        if (head == null)
            return null;
        // only one node in linked list
        if (head.next == null)
            return new Trees.TreeNode(head.val);

        // prev - previous node of the middle node
        LinkedLists.ListNode prev = null, slow = head, fast = head.next;
        // tortoise and hare method to find middle node of linked-list
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // head of the second half of linked list
        LinkedLists.ListNode head2 = slow.next;
        // partition first half
        if (prev != null)
            prev.next = null;

        Trees.TreeNode root = new Trees.TreeNode(slow.val);
        // recursively build left subtree is left half is not null
        root.left = head != slow ? sortedListToBST(head) : null;
        // recursively build right subtree
        root.right = sortedListToBST(head2);

        return root;
    }

    // https://www.interviewbit.com/problems/sum-of-fibonacci-numbers/
    static int sumOfFibonacci(int A) {
        // base case
        if (A == 1)
            return 1;

        // get all fibonacci numbers <= A
        List<Integer> fib = fibNumbersLessThanEqual(A);
        // minimum fibonacci numbers required
        int cnt = 0;
        // greedily start from the largest possible fib number
        int pos = fib.size() - 1;

        // while the sum is not satisfied
        while (A > 0) {
            // update count
            cnt += (A / fib.get(pos));
            // update remaining sum
            A %= fib.get(pos);
            // take next largest fib number
            pos--;
        }

        return cnt;
    }

    // util to get all fibonacci numbers less than equal to n
    private static List<Integer> fibNumbersLessThanEqual(int n) {
        List<Integer> fib = new ArrayList<>();
        // first 2 fib numbers
        fib.add(1);
        fib.add(1);

        int t1, t2 = 1, t3 = 2;
        // keep adding fib numbers till they are <= n
        while (t3 <= n) {
            fib.add(t3);

            t1 = t2;
            t2 = t3;
            t3 = t1 + t2;
        }

        return fib;
    }

    // https://www.interviewbit.com/problems/knight-on-chess-board/
    @SuppressWarnings("ConstantConditions")
    static int minMovesForKnight(int A, int B, int C, int D, int E, int F) {
        // 8 directions for the knight
        int[][] dirs = {{2, 1}, {-2, 1}, {2, -1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        // queue for BFS
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{C, D});
        // visited array for BFS
        boolean[][] visited = new boolean[A + 1][B + 1];
        visited[C][D] = true;
        // moves made by knight
        int dist = 0;

        // perform BFS
        while (!q.isEmpty()) {
            int n = q.size();
            for (int i = 0; i < n; i++) {
                int[] front = q.poll();
                // reached destination - return the distance to reach here
                if (front[0] == E && front[1] == F)
                    return dist;

                // for each direction
                for (int[] dir : dirs) {
                    int x = front[0] + dir[0], y = front[1] + dir[1];
                    // if out of bounds or already visited
                    if (x < 1 || x > A || y < 1 || y > B || visited[x][y])
                        continue;
                    // add new point to queue with incremented distance and mark as visited
                    q.add(new int[]{x, y});
                    visited[x][y] = true;
                }
            }
            // update count
            dist++;
        }
        // cannot reach destination
        return -1;
    }

    // https://www.interviewbit.com/problems/useful-extra-edges/
    @SuppressWarnings("unchecked")
    static int usefulExtraEdges(int A, int[][] B, int C, int D, int[][] E) {
        // create adjacency list for undirected graph
        LinkedList<int[]>[] adj = new LinkedList[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new LinkedList<>();
        for (int[] edge : B) {
            int u = edge[0], v = edge[1], w = edge[2];
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, w});
        }

        // calculate the shortest distance from C to D without any edges from E
        int[] dist1 = shortestPathDistance(C, adj);
        // calculate the shortest distance from D to C without any edges from E
        int[] dist2 = shortestPathDistance(D, adj);
        // shortest distance from C to D
        int minDistance = dist1[D];
        // for each edge in E
        for (int[] edge : E) {
            int u = edge[0], v = edge[1], w = edge[2];
            // skip invalid edges
            if (u < 1 || u > A || v < 1 || v > A)
                continue;
            // if path from C to u and v to D, use it to optimize
            if (dist1[u] != Integer.MAX_VALUE && dist2[v] != Integer.MAX_VALUE)
                minDistance = Math.min(minDistance, dist1[u] + w + dist2[v]);
            // if path from C to v and u to D, use it to optimize
            if (dist1[v] != Integer.MAX_VALUE && dist2[u] != Integer.MAX_VALUE)
                minDistance = Math.min(minDistance, dist1[v] + w + dist2[u]);
        }
        // return min distance if found else -1
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

    // util to calculate shortest distance using Dijkstra
    private static int[] shortestPathDistance(int src, LinkedList<int[]>[] adj) {
        // distance array initialize with infinite
        int[] dist = new int[adj.length + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        // source to source distance is 0
        dist[src] = 0;
        // priority queue for getting nearest node
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(a -> dist[a]));
        pq.add(src);
        // loop till all edges not relaxed
        while (!pq.isEmpty()) {
            int u = pq.poll();
            // for each neighbour
            for (int[] node : adj[u]) {
                int v = node[0], w = node[1];
                // relax current edge
                if (dist[v] > dist[u] + w) {
                    dist[v] = dist[u] + w;
                    // add node to priority queue
                    pq.add(v);
                }
            }
        }

        return dist;
    }

    // https://www.interviewbit.com/problems/word-ladder-i/
    @SuppressWarnings("ConstantConditions")
    static int wordLadder1(String A, String B, String[] C) {
        // same source and destination (initial distance is 1 - question requirement)
        if (A.equals(B))
            return 1;

        // map for storing each intermediate string and list of original strings from which it can be reached in one step
        // (adjacency list for the graph)
        Map<String, List<String>> map = new HashMap<>();
        // for destination string
        mapIntermediateStrings(B, map);
        // for each string in dictionary
        for (String str : C)
            mapIntermediateStrings(str, map);

        // initial distance
        int level = 1;
        // queue for BFS
        Queue<String> q = new LinkedList<>();
        q.add(A);
        // visited set for string
        Set<String> visited = new HashSet<>();
        visited.add(A);
        // perform BFS
        while (!q.isEmpty()) {
            int n = q.size();
            for (int k = 0; k < n; k++) {
                String front = q.poll();
                // reached destination
                if (front.equals(B))
                    return level;

                StringBuilder builder = new StringBuilder(front);
                // for the current string
                for (int i = 0; i < front.length(); i++) {
                    // generate intermediate string at position i
                    builder.setCharAt(i, '*');
                    // for each original string mapped to this intermediate string
                    for (String str : map.getOrDefault(builder.toString(), new LinkedList<>())) {
                        // if not visited, mark visited and add to queue for BFS
                        if (!visited.contains(str)) {
                            q.add(str);
                            visited.add(str);
                        }
                    }

                    builder.setCharAt(i, front.charAt(i));
                }
            }

            level++;
        }
        // could not transform A to B
        return 0;
    }

    // util to map intermediate strings of str to str
    private static void mapIntermediateStrings(String str, Map<String, List<String>> map) {
        StringBuilder builder = new StringBuilder(str);

        for (int i = 0; i < str.length(); i++) {
            // generate intermediate string at position i
            builder.setCharAt(i, '*');
            // add to map of intermediate string and its list of original strings
            map.computeIfAbsent(builder.toString(), k -> new LinkedList<>()).add(str);

            builder.setCharAt(i, str.charAt(i));
        }
    }

    // https://www.interviewbit.com/problems/word-ladder-ii/
    @SuppressWarnings("ConstantConditions")
    static ArrayList<ArrayList<String>> wordLadder2(String start, String end, ArrayList<String> dict) {
        // map for storing each intermediate string and list of original strings from which it can be reached in one step
        // (adjacency list for the graph)
        Map<String, List<String>> map = new HashMap<>();
        // dictionary set as words can repeat in dictionary (dictionary already contains start and end strings)
        Set<String> dictSet = new HashSet<>(dict);
        for (String str : dictSet)
            mapIntermediateStrings(str, map);

        // shortest path distance to end
        int level = 0;
        // flag to check if path found
        boolean found = false;
        // queue for BFS
        Queue<String> q = new LinkedList<>();
        q.add(start);
        // visited strings set
        Set<String> visited = new HashSet<>();
        visited.add(start);

        while (!q.isEmpty() && !found) {
            int n = q.size();
            for (int k = 0; k < n; k++) {
                String front = q.poll();
                // reached end
                if (front.equals(end)) {
                    found = true;
                    break;
                }

                StringBuilder builder = new StringBuilder(front);
                // for each intermediate string formed from current word
                for (int i = 0; i < front.length(); i++) {
                    builder.setCharAt(i, '*');
                    // for each original string that can form this intermediate string
                    for (String str : map.getOrDefault(builder.toString(), new LinkedList<>())) {
                        // if not visited, mark visited and add to queue for BFS
                        if (!visited.contains(str)) {
                            q.add(str);
                            visited.add(str);
                        }
                    }

                    builder.setCharAt(i, front.charAt(i));
                }
            }

            level++;
        }

        ArrayList<ArrayList<String>> res = new ArrayList<>();
        // if cannot transform start to end
        if (!found)
            return res;

        // clear visited set
        visited.clear();
        // perform DFS till depth = distance to get all possible shortest paths
        wordLadder2Util(start, end, level, map, visited, new ArrayList<>(), res);

        return res;
    }

    // util to find all paths to end till depth = distance using DFS
    private static void wordLadder2Util(String word, String dest, int distance, Map<String, List<String>> map,
                                        Set<String> visited, ArrayList<String> curr, ArrayList<ArrayList<String>> res) {
        // update distance
        distance--;
        // if depth exhausted, backtrack
        if (distance < 0)
            return;

        // add current word to path
        curr.add(word);
        // reached end - add current path to result and backtrack
        if (word.equals(dest)) {
            res.add(new ArrayList<>(curr));
            curr.remove(curr.size() - 1);
            return;
        }

        // mark current word as visited
        visited.add(word);

        StringBuilder builder = new StringBuilder(word);
        // for each intermediate string formed from current word
        for (int i = 0; i < word.length(); i++) {
            builder.setCharAt(i, '*');
            // for each original string that can form this intermediate string
            for (String str : map.getOrDefault(builder.toString(), new LinkedList<>())) {
                // if not visited, explore using DFS
                if (!visited.contains(str))
                    wordLadder2Util(str, dest, distance, map, visited, curr, res);
            }

            builder.setCharAt(i, word.charAt(i));
        }
        // path not found, backtrack
        curr.remove(curr.size() - 1);
        visited.remove(word);
    }

    // https://www.interviewbit.com/problems/clone-graph/
    static class UndirectedGraphNode {
        int label;
        List<UndirectedGraphNode> neighbors;

        UndirectedGraphNode(int label) {
            this.label = label;
            neighbors = new LinkedList<>();
        }
    }

    static UndirectedGraphNode cloneGraph(UndirectedGraphNode root) {
        // map for mapping original nodes to cloned nodes
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
        map.put(root, new UndirectedGraphNode(root.label));
        // queue for BFS
        Queue<UndirectedGraphNode> q = new LinkedList<>();
        q.add(root);
        // perform BFS
        while (!q.isEmpty()) {
            UndirectedGraphNode front = q.poll();
            // for each neighbor
            for (UndirectedGraphNode node : front.neighbors) {
                // if node not cloned before, clone it and add original node to queue for BFS
                if (!map.containsKey(node)) {
                    map.put(node, new UndirectedGraphNode(node.label));
                    q.add(node);
                }
                // update neighbors of cloned front node
                map.get(front).neighbors.add(map.get(node));
            }
        }
        // clone of the root node
        return map.get(root);
    }

    // https://www.interviewbit.com/problems/connected-components/
    @SuppressWarnings("unchecked")
    static int connectedComponents(int A, int[][] B) {
        List<Integer>[] adj = new List[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new LinkedList<>();
        // build adjacency list for the graph
        for (int[] edge : B) {
            int u = edge[0], v = edge[1];
            adj[u].add(v);
            adj[v].add(u);
        }

        boolean[] visited = new boolean[A + 1];
        int res = 0;
        for (int i = 1; i <= A; i++) {
            // run dfs for each unvisited node (connected component)
            if (!visited[i]) {
                res++;
                dfs(i, adj, visited);
            }
        }

        return res;
    }

    // util to run dfs for a connected component
    private static void dfs(int u, List<Integer>[] adj, boolean[] visited) {
        visited[u] = true;

        for (int v : adj[u]) {
            // visit the neighbour if unvisited
            if (!visited[v])
                dfs(v, adj, visited);
        }
    }

    // https://www.interviewbit.com/problems/file-search/
    static int breakRecords(int A, int[][] B) {
        // ranked union find to group records together
        UnionFindRank uf = new UnionFindRank(A + 1);
        // initially every record is alone in its own group
        int res = A;

        for (int[] edge : B) {
            int u = edge[0], v = edge[1];
            // if in different groups, union the two groups and update remaining groups count
            if (uf.find(u) != uf.find(v)) {
                uf.union(u, v);
                res--;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/mother-vertex/
    @SuppressWarnings("unchecked")
    static int motherVertex(int A, int[][] B) {
        // create adjacency list for the graph
        Set<Integer>[] adj = new Set[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new HashSet<>();
        for (int[] edge : B) {
            int u = edge[0], v = edge[1];
            // skip self-loops
            if (u != v)
                adj[u].add(v);
        }

        boolean[] visited = new boolean[A + 1];
        // a mother vertex if exists will be the last node from which we start dfs
        int motherVertex = 0;
        for (int i = 1; i <= A; i++) {
            if (!visited[i]) {
                dfs(i, adj, visited);
                // mark as possible mother vertex
                motherVertex = i;
            }
        }

        visited = new boolean[A + 1];
        // dfs to check if current node connects to all nodes
        dfs(motherVertex, adj, visited);

        for (int i = 1; i <= A; i++) {
            // found unvisited node - mother vertex not possible
            if (!visited[i])
                return 0;
        }
        // found 1 mother vertex
        return 1;
    }

    // util to perform dfs
    private static void dfs(int u, Set<Integer>[] adj, boolean[] visited) {
        visited[u] = true;

        for (int v : adj[u]) {
            if (!visited[v])
                dfs(v, adj, visited);
        }
    }

    // https://www.interviewbit.com/problems/path-in-matrix/
    public int checkPath(int[][] A) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // run dfs from source to check if destination reached
                if (A[i][j] == 1)
                    return checkPathUtil(i, j, n, A) ? 1 : 0;
            }
        }
        // source not found
        return 0;
    }

    // util to check path with dfs
    private boolean checkPathUtil(int i, int j, int n, int[][] A) {
        // reached destination
        if (A[i][j] == 2)
            return true;

        // mark as visited
        A[i][j] = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        // check in all 4 directions
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];
            // out of bounds or already visited
            if (x < 0 || x >= n || y < 0 || y >= n || A[x][y] == 0)
                continue;
            // found path at this neighbour
            if (checkPathUtil(x, y, n, A))
                return true;
        }
        // path not found
        return false;
    }
}