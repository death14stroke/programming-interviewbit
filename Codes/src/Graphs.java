import java.util.*;

class Graphs {
    // https://www.interviewbit.com/problems/valid-path/
    // problem statement wrong: (0, 0) is top left and (x, y) is bottom right
    static String validPath(int x, int y, int N, int R, int[] A, int[] B) {
        // visited array for BFS
        boolean[][] visited = new boolean[x + 1][y + 1];

        // for each point (i, j)
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                // for each circle
                for (int k = 0; k < N; k++) {
                    int x0 = A[k], y0 = B[k];

                    // if (i, j) is in circle, mark (i, j) as visited
                    if (isInCircle(i, j, x0, y0, R))
                        visited[i][j] = true;
                }
            }
        }

        // queue for BFS
        Queue<MatrixNode> q = new LinkedList<>();
        // start with (0, 0)
        q.add(new MatrixNode(0, 0));
        visited[0][0] = true;

        // 8 directions
        int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        // perform BFS
        while (!q.isEmpty()) {
            MatrixNode front = q.poll();

            // reached destination
            if (front.x == x && front.y == y)
                return "YES";

            // for each direction
            for (int[] dir : dirs) {
                int x1 = front.x + dir[0], y1 = front.y + dir[1];

                // if out of bounds or already visited
                if (x1 < 0 || x1 > x || y1 < 0 || y1 > y || visited[x1][y1])
                    continue;

                // add to queue for BFS
                q.add(new MatrixNode(x1, y1));
                // mark as visited
                visited[x1][y1] = true;
            }
        }

        // cannot reach destination
        return "NO";
    }

    // util to check if (x, y) is in or on circle with center (x0, y0) and radius R
    static boolean isInCircle(int x, int y, int x0, int y0, int R) {
        return Math.pow(x - x0, 2) + Math.pow(y - y0, 2) <= R * R;
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

    // https://www.interviewbit.com/problems/snake-ladder-problem/
    static int snakesAndLadders(int[][] A, int[][] B) {
        // create edge map for all the ladders and snakes
        int[] edges = new int[101];
        for (int[] ladder : A)
            edges[ladder[0]] = ladder[1];
        for (int[] snake : B)
            edges[snake[0]] = snake[1];

        // visited array for BFS
        boolean[] visited = new boolean[101];
        // queue for BFS
        Queue<GameNode> q = new LinkedList<>();
        // flag to check whether solution was found or not
        boolean isValid = false;

        // start from 1
        GameNode front = new GameNode();
        front.v = 1;
        front.dist = 0;

        visited[1] = true;
        q.add(front);

        // since in the board game all edges will have equal weight, BFS will give us the shortest path
        while (!q.isEmpty()) {
            front = q.poll();
            int v = front.v;

            // reached end. Mark valid flag as true and return
            if (v == 100) {
                isValid = true;
                break;
            }

            // try all 6 possible numbers on dice
            for (int j = v + 1; j <= (v + 6) && j <= 100; j++) {
                if (visited[j])
                    continue;

                // visit new node
                GameNode node = new GameNode();
                node.dist = front.dist + 1;
                visited[j] = true;

                // if any ladder or snake present, move up/down along that
                if (edges[j] != 0)
                    node.v = edges[j];
                else
                    node.v = j;

                q.add(node);
            }
        }

        // if solution not found return -1
        return isValid ? front.dist : -1;
    }

    // data class for each position on the board
    private static class GameNode {
        // vertex on the board
        int v;
        // distance currently travelled
        int dist;
    }

    // https://www.interviewbit.com/problems/smallest-multiple-with-0-and-1/
    static String smallestMultiple(int n) {
        // base case
        if (n == 0)
            return "0";

        // start BFS from "1"
        Queue<StringNode> q = new LinkedList<>();
        q.add(new StringNode('1', 1, null));
        // set to check if remainder is seen or not. A smaller string with same remainder will generate
        // all the possible remainders that can be generated by a larger string with same remainder
        boolean[] remainderSet = new boolean[n];
        // last digit in the result string
        StringNode head = null;

        while (!q.isEmpty()) {
            StringNode front = q.poll();

            // found required number
            if (front.remainder % n == 0) {
                head = front;
                break;
            }

            // if remainder not seen before, perform BFS
            if (!remainderSet[front.remainder]) {
                remainderSet[front.remainder] = true;
                q.add(new StringNode('0', (front.remainder * 10) % n, front));
                q.add(new StringNode('1', (front.remainder * 10 + 1) % n, front));
            }
        }

        // build the reverse number
        StringBuilder builder = new StringBuilder();
        while (head != null) {
            builder.append(head.c);
            head = head.prev;
        }

        return builder.reverse().toString();
    }

    /// data class for BFS node
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

    // https://www.interviewbit.com/problems/permutation-swaps/
    static int permutationSwaps(int[] A, int[] B, int[][] C) {
        int n = A.length;

        // subsets array for union find with rank and path compression
        Subset[] subsets = new Subset[n + 1];
        for (int i = 1; i <= n; i++)
            subsets[i] = new Subset(0, i);

        // take union of end points for each edge
        for (int[] edge : C) {
            int i1 = edge[0] - 1, i2 = edge[1] - 1;
            unionRank(subsets, A[i1], A[i2]);
        }

        // for each index
        for (int i = 0; i < n; i++) {
            if (A[i] != B[i]) {
                // if values not equal and don't have same subset, cannot swap them
                if (findRank(subsets, A[i]) != findRank(subsets, B[i]))
                    return 0;
            }
        }

        // can make swaps to get output permutation
        return 1;
    }

    // util for find operation with rank and path compression
    private static int findRank(Subset[] subsets, int i) {
        // if not reached parent
        if (subsets[i].parent != i)
            // recursively find parent of parent and also compress the path for current node
            subsets[i].parent = findRank(subsets, subsets[i].parent);

        // return the compressed path parent for current node
        return subsets[i].parent;
    }

    // util for union operation with rank and path compression
    private static void unionRank(Subset[] subsets, int x, int y) {
        // find parents of both nodes
        int xFind = findRank(subsets, x);
        int yFind = findRank(subsets, y);

        // if parent(y) has higher rank, append parent(x) to parent(y)
        if (subsets[xFind].rank < subsets[yFind].rank)
            subsets[xFind].parent = yFind;
            // else if parent(x) has higher rank, append parent(y) to parent(x)
        else if (subsets[yFind].rank < subsets[xFind].rank)
            subsets[yFind].parent = xFind;
            // else equal ranks - append to any and increment its rank
        else {
            subsets[xFind].parent = yFind;
            subsets[yFind].rank++;
        }
    }

    // data class for union find with rank and path compression on disjoint sets
    private static class Subset {
        // rank of the node
        int rank;
        // parent of the node
        int parent;

        Subset(int rank, int parent) {
            this.rank = rank;
            this.parent = parent;
        }
    }

    // https://www.interviewbit.com/problems/min-cost-path/
    static int minCostPath(int A, int B, String[] C) {
        // deque for 0-1 BFS
        Deque<MatrixNode> q = new LinkedList<>();
        // start with (0, 0)
        q.addLast(new MatrixNode(0, 0));

        // distance from (0, 0) to each node
        int[][] distance = new int[A][B];
        // initialize with max distance
        for (int[] d : distance)
            Arrays.fill(d, Integer.MAX_VALUE);
        // source to source distance is 0
        distance[0][0] = 0;

        // visited array for BFS
        boolean[][] visited = new boolean[A][B];

        // perform BFS
        while (!q.isEmpty()) {
            MatrixNode front = q.pollFirst();

            // four directions and their char representations
            int[][] dirs = {{-1, 0}, {0, 1}, {0, -1}, {1, 0}};
            char[] dirChars = {'U', 'R', 'L', 'D'};

            // mark current node as visited
            visited[front.x][front.y] = true;

            // for each direction (edge)
            for (int i = 0; i < 4; i++) {
                int x = front.x + dirs[i][0], y = front.y + dirs[i][1];
                // out of bounds or already visited
                if (x < 0 || x >= A || y < 0 || y >= B || visited[x][y])
                    continue;

                // if direction is same as the entry in matrix, weight of edge is 0 else it is 1
                int w = dirChars[i] == C[front.x].charAt(front.y) ? 0 : 1;

                // relax current edge
                if (distance[x][y] > distance[front.x][front.y] + w)
                    distance[x][y] = distance[front.x][front.y] + w;

                // if weight is 0, add to front of deque
                if (w == 0)
                    q.addFirst(new MatrixNode(x, y));
                    // else add to end of deque
                else
                    q.addLast(new MatrixNode(x, y));
            }
        }

        // distance from (0, 0) to (A - 1, B - 1)
        return distance[A - 1][B - 1];
    }

    // data class for each position in the matrix
    private static class MatrixNode {
        int x, y;

        MatrixNode(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // https://www.interviewbit.com/problems/commutable-islands/
    static int minCostBridges(int A, int[][] B) {
        int res = 0;
        // no of edges required for MST
        int reqNoOfEdges = A - 1;

        // union-find parent array
        int[] parent = new int[A + 1];
        Arrays.fill(parent, -1);

        // sort the edges in increasing order of weight
        Arrays.sort(B, Comparator.comparingInt(e -> e[2]));

        // for each edge
        for (int[] edge : B) {
            int u = edge[0], v = edge[1], weight = edge[2];

            // if both points are in same set, adding edge will form cycle
            if (find(parent, u) == find(parent, v))
                continue;

            // update minimum cost
            res += weight;
            // update no of edges remaining
            reqNoOfEdges--;
            // MST is complete
            if (reqNoOfEdges == 0)
                break;

            // add both endpoints to the same set
            union(parent, u, v);
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
            // perform DFS for each connected component. If cycle, cannot complete courses
            if (!visited[i] && isDirectedCyclicUtil(i, adj, visited, recStack))
                return 0;
        }

        // no cycles. Can complete all courses in a topological order
        return 1;
    }

    // util to check for cycle in directed graph using DFS
    private static boolean isDirectedCyclicUtil(int u, List<Integer>[] adj, boolean[] visited, boolean[] recStack) {
        // mark current node as visited
        visited[u] = true;
        // mark current node as part of stack
        recStack[u] = true;

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
            if (!visited[i] && isUndirectedCycleUtil(i, adj, visited, -1))
                return 1;
        }

        // no cycles found
        return 0;
    }

    // util to check cycle in undirected graph using DFS
    private static boolean isUndirectedCycleUtil(int u, List<Integer>[] adj, boolean[] visited, int parent) {
        // mark current node as visited
        visited[u] = true;

        // for each neighbour
        for (int v : adj[u]) {
            // if visited node and is not the parent of the current node, cycle found
            if (visited[v] && v != parent)
                return true;

            // if unvisited node and forms cycle
            if (!visited[v] && isUndirectedCycleUtil(v, adj, visited, u))
                return true;
        }

        // no cycles formed by any neighbours
        return false;
    }

    // https://www.interviewbit.com/problems/cycle-in-undirected-graph/
    // Approach 2 - using union find (T.C: O(E log V))
    static int isUndirectedCycleUnionFind(int A, int[][] B) {
        // initialize parent array for all nodes
        int[] parent = new int[A + 1];
        Arrays.fill(parent, -1);

        // for each edge
        for (int[] edge : B) {
            // find parents of both end points
            int x = find(parent, edge[0]);
            int y = find(parent, edge[1]);

            // if in same subset, cycle exists
            if (x == y)
                return 1;

            // perform union of both parents
            union(parent, x, y);
        }

        // no cycles found
        return 0;
    }

    // util to perform find operation in disjoint set
    private static int find(int[] parent, int i) {
        // find the root node of the set
        while (parent[i] != -1)
            i = parent[i];

        return i;
    }

    // util to perform union operation in disjoint set
    private static void union(int[] parent, int x, int y) {
        // find parents of both nodes
        x = find(parent, x);
        y = find(parent, y);

        // make either node as parent of other node
        parent[x] = y;
    }

    // https://www.interviewbit.com/problems/black-shapes/
    static int blackShapes(String[] A) {
        int m = A.length, n = A[0].length();
        // no of connected components
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

    // https://www.interviewbit.com/problems/largest-distance-between-nodes-of-a-tree/
    @SuppressWarnings("unchecked")
    static int largestDistance(int[] A) {
        int n = A.length;

        // create adjacency list
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++)
            adj[i] = new LinkedList<>();

        // root of the tree
        int root = 0;

        for (int i = 0; i < n; i++) {
            // add undirected edge to the graph
            if (A[i] != -1) {
                adj[i].add(A[i]);
                adj[A[i]].add(i);
            }
            // mark root node
            else {
                root = i;
            }
        }

        // find the last visited node in BFS starting from root node
        int lastNode = findLastNode(root, adj);

        // calculate the distance of last visited node in BFS starting from the previous result
        return farthestNode(lastNode, adj);
    }

    // util to find the last visited node in BFS
    private static int findLastNode(int root, List<Integer>[] adj) {
        // last visited node
        int lastNode = root;

        // queue for BFS
        Queue<Integer> q = new LinkedList<>();
        q.add(root);

        // visited array
        boolean[] visited = new boolean[adj.length];
        visited[root] = true;

        // perform BFS
        while (!q.isEmpty()) {
            lastNode = q.poll();

            // for each neighbour
            for (int v : adj[lastNode]) {
                // if not visited, mark visited and add to queue
                if (!visited[v]) {
                    visited[v] = true;
                    q.add(v);
                }
            }
        }

        return lastNode;
    }

    // util to find the distance to the farthest node in BFS
    private static int farthestNode(int start, List<Integer>[] adj) {
        // farthest node
        BFSNode front = new BFSNode(start, 0);

        // queue for BFS
        Queue<BFSNode> queue = new LinkedList<>();
        queue.add(front);

        // visited array
        boolean[] visited = new boolean[adj.length];
        visited[start] = true;

        // perform BFS
        while (!queue.isEmpty()) {
            front = queue.poll();

            // for each neighbour
            for (int v : adj[front.label]) {
                // if not visited, mark visited, update distance and add to queue
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(new BFSNode(v, front.dist + 1));
                }
            }
        }

        // distance to the last node
        return front.dist;
    }

    // data class for BFS node
    private static class BFSNode {
        // label of the node
        int label;
        // distance from the root node
        int dist;

        BFSNode(int label, int dist) {
            this.label = label;
            this.dist = dist;
        }
    }

    // https://www.interviewbit.com/problems/delete-edge/
    private static int maxProduct;
    private static final int p = 1000000007;

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
        long sum = 0;
        for (int w : A)
            sum += w;

        // maximum product of sum of two subtrees
        maxProduct = 0;

        // DFS util to calculate subtree sum at each node
        deleteEdgeUtil(1, -1, adj, sum, A);

        return maxProduct;
    }

    // util to calculate subtree sum at each node and maximize result
    private static void deleteEdgeUtil(int u, int parent, List<Integer>[] adj, long sum, int[] A) {
        // initialize current subtree sum
        int x = A[u - 1];

        // for each neighbour
        for (int v : adj[u]) {
            // if not parent node, recursively calculate subtree sum and append the sum to current node
            if (v != parent) {
                deleteEdgeUtil(v, u, adj, sum, A);
                x += A[v - 1];
            }
        }

        // update subtree sum for current node
        A[u - 1] = x;

        // if not root node, update result
        if (u != 1)
            maxProduct = (int) Math.max(maxProduct, (x * (sum - x)) % p);
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

    // https://www.interviewbit.com/problems/two-teams/
    @SuppressWarnings("unchecked")
    static int twoTeams(int A, int[][] B) {
        // adjacency list for the undirected graph
        List<Integer>[] adj = new List[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new LinkedList<>();

        for (int[] edge : B) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
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

        // graph is bipartite
        return true;
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
    @SuppressWarnings("ForLoopReplaceableByForEach")
    static void captureRegions(ArrayList<ArrayList<Character>> A) {
        int m = A.size(), n = A.get(0).size();

        // mark each 'O' as '-'
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (A.get(i).get(j) == 'O')
                    A.get(i).set(j, '-');
            }
        }

        // start BFS from each '-' on the four boundaries and mark them as 'O' again
        for (int i = 0; i < m; i++) {
            if (A.get(i).get(0) == '-')
                captureRegionsUtil(i, 0, A, m, n);
            if (A.get(i).get(n - 1) == '-')
                captureRegionsUtil(i, n - 1, A, m, n);
        }

        for (int j = 0; j < n; j++) {
            if (A.get(0).get(j) == '-')
                captureRegionsUtil(0, j, A, m, n);
            if (A.get(m - 1).get(j) == '-')
                captureRegionsUtil(m - 1, j, A, m, n);
        }

        // mark all remaining '-' in the middle as 'X' - captured regions
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (A.get(i).get(j) == '-')
                    A.get(i).set(j, 'X');
            }
        }
    }

    // util to mark '-' as 'O' on board using BFS
    private static void captureRegionsUtil(int i, int j, ArrayList<ArrayList<Character>> A, int m, int n) {
        // mark current position as 'O'
        A.get(i).set(j, 'O');

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // for each direction
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];

            // if out of bounds or not '-'
            if (x < 0 || x >= m || y < 0 || y >= n || A.get(x).get(y) != '-')
                continue;

            // recursively mark as 'O'
            captureRegionsUtil(x, y, A, m, n);
        }
    }

    // https://www.interviewbit.com/problems/word-search-board/
    static int wordSearch(String[] board, String word) {
        int m = board.length, n = board[0].length();

        // for each cell on board
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // if word can start with current cell, check if word found
                if (board[i].charAt(j) == word.charAt(0) && wordSearchUtil(i, j, 0, word, board))
                    return 1;
            }
        }

        // word not found on board
        return 0;
    }

    // util to check for word on board using DFS with repeated cells in path
    private static boolean wordSearchUtil(int i, int j, int pos, String word, String[] board) {
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int m = board.length, n = board[0].length();

        // reached last character in the word
        if (pos == word.length() - 1)
            return true;

        // move to next character
        pos++;

        // for each direction
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];

            // if out of bounds or character doesn't word
            if (x < 0 || x >= m || y < 0 || y >= n || word.charAt(pos) != board[x].charAt(y))
                continue;

            // recursively check if remaining word found in this direction
            if (wordSearchUtil(x, y, pos, word, board))
                return true;
        }

        // word not found
        return false;
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

        // create new tree node
        Trees.TreeNode node = new Trees.TreeNode(slow.val);

        // if left half is null
        if (slow == head)
            node.left = null;
            // else recursively build left subtree
        else
            node.left = sortedListToBST(head);

        // recursively build right subtree
        node.right = sortedListToBST(head2);

        return node;
    }

    // https://www.interviewbit.com/problems/sum-of-fibonacci-numbers/
    static int sumOfFibonacci(int n) {
        // base case
        if (n == 1)
            return 1;

        // get all fibonacci numbers <= n
        List<Integer> fib = fibNumbersLessThanEqual(n);

        // minimum fibonacci numbers required
        int cnt = 0;
        // greedily start from largest possible fib number
        int pos = fib.size() - 1;

        // while the sum is not satisfied
        while (n > 0) {
            // update count
            cnt += (n / fib.get(pos));
            // update remaining sum
            n %= fib.get(pos);
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
    static int minMovesForKnight(int A, int B, int C, int D, int E, int F) {
        // 8 directions for the knight
        int[][] dirs = {{2, 1}, {-2, 1}, {2, -1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};

        // queue for BFS
        Queue<ChessNode> q = new LinkedList<>();
        q.add(new ChessNode(C, D, 0));

        // visited array for BFS
        boolean[][] visited = new boolean[A + 1][B + 1];
        visited[C][D] = true;

        // perform BFS
        while (!q.isEmpty()) {
            ChessNode front = q.poll();

            // reached destination - return the distance to reach here
            if (front.x == E && front.y == F)
                return front.dist;

            // for each direction
            for (int[] dir : dirs) {
                int x = front.x + dir[0], y = front.y + dir[1];

                // if out of bounds or already visited
                if (x < 1 || x > A || y < 1 || y > B || visited[x][y])
                    continue;

                // add new point to queue with incremented distance and mark as visited
                q.add(new ChessNode(x, y, front.dist + 1));
                visited[x][y] = true;
            }
        }

        // cannot reach destination
        return -1;
    }

    // data class for Node on chess board
    private static class ChessNode {
        // x, y coordinates on the board
        int x, y;
        // distance to reach this node from source node
        int dist;

        ChessNode(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    // https://www.interviewbit.com/problems/useful-extra-edges/
    @SuppressWarnings("unchecked")
    static int usefulExtraEdges(int A, int[][] B, int C, int D, int[][] E) {
        // create adjacency list for directed graph
        LinkedList<Node>[] adj = new LinkedList[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new LinkedList<>();

        for (int[] edge : B) {
            int u = edge[0], v = edge[1], w = edge[2];
            adj[u].add(new Node(v, w));
        }

        // calculate shortest distance without any edges from E
        int minDistance = shortestPathDistance(A, C, D, adj);

        // for each edge in E
        for (int[] edge : E) {
            int u = edge[0], v = edge[1], w = edge[2];

            if (u < 1 || u > A || v < 1 || v > A)
                continue;

            // if valid edge, add to graph as undirected edge
            adj[u].add(new Node(v, w));
            adj[v].add(new Node(u, w));

            // recalculate shortest distance and update result
            minDistance = Math.min(minDistance, shortestPathDistance(A, C, D, adj));

            // remove this edge from graph
            adj[u].removeLast();
            adj[v].removeLast();
        }

        // return min distance if found else -1
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

    // util to calculate shortest distance using Dijkstra
    private static int shortestPathDistance(int A, int src, int dest, LinkedList<Node>[] adj) {
        // priority queue for getting nearest node
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.weight));
        pq.add(new Node(src, 0));

        // distance array initialize with infinite
        int[] dist = new int[A + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        // source to source distance is 0
        dist[src] = 0;

        // set to keep track of visited nodes
        Set<Integer> set = new HashSet<>();

        // loop till all nodes not visited and queue is not empty
        while (set.size() != A && !pq.isEmpty()) {
            Node front = pq.poll();
            int u = front.label;

            // add current node to visited set
            set.add(u);

            // for each neighbour
            for (Node node : adj[u]) {
                int v = node.label;
                // if visited already
                if (set.contains(v))
                    continue;

                // relax current edge
                if (dist[v] > dist[u] + node.weight)
                    dist[v] = dist[u] + node.weight;

                // add updated node to priority queue
                pq.add(new Node(v, dist[v]));
            }
        }

        // distance from src to dest
        return dist[dest];
    }

    // util class node for shortest path
    private static class Node {
        // label of the node
        int label;
        // weight of the edge connecting
        int weight;

        Node(int label, int weight) {
            this.label = label;
            this.weight = weight;
        }
    }

    // https://www.interviewbit.com/problems/word-ladder-i/
    static int wordLadder1(String A, String B, String[] C) {
        // same source and destination (initial distance is 1 - question requirement)
        if (A.equals(B))
            return 1;

        // word length
        int n = A.length();
        // map for storing each intermediate string and
        // list of original strings from which it can be reached in one step
        Map<String, List<String>> map = new HashMap<>();

        // for source string
        mapIntermediateStrings(A, map);
        // for destination string
        mapIntermediateStrings(B, map);
        // for each string in dictionary
        for (String str : C)
            mapIntermediateStrings(str, map);

        // visited set for string
        Set<String> visited = new HashSet<>();
        // mark source as visited
        visited.add(A);

        // queue for BFS. Take initial distance as 1 (question requirement)
        Queue<WordNode> q = new LinkedList<>();
        q.add(new WordNode(A, 1));

        // perform BFS
        while (!q.isEmpty()) {
            WordNode front = q.poll();
            // reached destination
            if (front.word.equals(B))
                return front.dist;

            StringBuilder builder = new StringBuilder(front.word);
            // for the current string
            for (int i = 0; i < n; i++) {
                // generate intermediate string at position i
                builder.setCharAt(i, '*');

                String key = builder.toString();
                // for each original string mapped to this intermediate string
                for (String str : map.getOrDefault(key, new LinkedList<>())) {
                    // if not visited, mark visited and add to queue for BFS
                    if (!visited.contains(str)) {
                        q.add(new WordNode(str, front.dist + 1));
                        visited.add(str);
                    }
                }

                builder.setCharAt(i, front.word.charAt(i));
            }
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

            String key = builder.toString();
            // add to map of intermediate string and its list of original strings
            map.putIfAbsent(key, new LinkedList<>());
            map.get(key).add(str);

            builder.setCharAt(i, str.charAt(i));
        }
    }

    // util class for storing word and its distance
    private static class WordNode {
        String word;
        int dist;

        WordNode(String word, int dist) {
            this.word = word;
            this.dist = dist;
        }
    }

    // https://www.interviewbit.com/problems/word-ladder-ii/
    static ArrayList<ArrayList<String>> wordLadder2(String start, String end, ArrayList<String> dict) {
        // length of each string
        int n = start.length();
        // map for storing each intermediate string and
        // list of original strings from which it can be reached in one step
        Map<String, List<String>> map = new HashMap<>();

        // dictionary set as words can repeat in dictionary
        Set<String> dictSet = new HashSet<>(dict);
        for (String str : dictSet)
            mapIntermediateStrings(str, map);

        // visited strings set
        Set<String> visited = new HashSet<>();
        visited.add(start);

        // shortest path distance to end
        int distance = -1;

        // queue for BFS
        Queue<WordNode> q = new LinkedList<>();
        q.add(new WordNode(start, 0));

        while (!q.isEmpty()) {
            WordNode front = q.poll();
            // reached end
            if (front.word.equals(end)) {
                distance = front.dist;
                break;
            }

            StringBuilder builder = new StringBuilder(front.word);
            // for each intermediate string formed from current word
            for (int i = 0; i < n; i++) {
                builder.setCharAt(i, '*');

                String key = builder.toString();
                // for each original string that can form this intermediate string
                for (String str : map.getOrDefault(key, new LinkedList<>())) {
                    // if not visited, mark visited and add to queue for BFS
                    if (!visited.contains(str)) {
                        visited.add(str);
                        q.add(new WordNode(str, front.dist + 1));
                    }
                }

                builder.setCharAt(i, front.word.charAt(i));
            }
        }

        ArrayList<ArrayList<String>> res = new ArrayList<>();
        // if cannot transform start to end
        if (distance == -1)
            return res;

        // clear visited set
        visited.clear();
        // perform DFS till depth = distance to get all possible shortest paths
        wordLadder2Util(start, end, distance, map, visited, new ArrayList<>(), res);

        return res;
    }

    // util to find all paths to end upto depth = distance using DFS
    private static void wordLadder2Util(String word, String dest, int distance,
                                        Map<String, List<String>> map, Set<String> visited,
                                        ArrayList<String> curr, ArrayList<ArrayList<String>> res) {
        // add current word to path
        curr.add(word);

        // reached end - add current path to result and backtrack
        if (word.equals(dest)) {
            res.add(new ArrayList<>(curr));
            curr.remove(curr.size() - 1);
            return;
        }

        // update distance
        distance--;
        // if depth exhausted, backtrack
        if (distance < 0) {
            curr.remove(curr.size() - 1);
            return;
        }

        // mark current word as visited
        visited.add(word);

        StringBuilder builder = new StringBuilder(word);
        // for each intermediate string formed from current word
        for (int i = 0; i < word.length(); i++) {
            builder.setCharAt(i, '*');

            String key = builder.toString();
            // for each original string that can form this intermediate string
            for (String str : map.getOrDefault(key, new LinkedList<>())) {
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

    static UndirectedGraphNode cloneGraph(UndirectedGraphNode src) {
        // map for mapping original nodes to cloned nodes
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
        map.put(src, new UndirectedGraphNode(src.label));

        // queue for BFS
        Queue<UndirectedGraphNode> q = new LinkedList<>();
        q.add(src);

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

        // clone of the src node
        return map.get(src);
    }

    // https://www.interviewbit.com/problems/path-in-directed-graph/
    @SuppressWarnings("unchecked")
    static int isPath(int A, int[][] B) {
        // create adjacency list for the graph
        List<Integer>[] adj = new List[A + 1];
        for (int i = 1; i <= A; i++)
            adj[i] = new ArrayList<>();

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

    // https://www.interviewbit.com/problems/path-with-good-nodes/
    private static int goodPaths;

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

        // number of good paths found
        goodPaths = 0;

        // start DFS from root (1) to count good paths
        pathWithGoodNodesUtil(1, -1, adj, A, C);

        return goodPaths;
    }

    // util to check for good root to leaf paths using DFS
    private static void pathWithGoodNodesUtil(int u, int parent, List<Integer>[] adj, int[] A, int goodNodes) {
        // good node - update count
        if (A[u - 1] == 1)
            goodNodes--;

        // exhausted all good nodes
        if (goodNodes < 0)
            return;

        // if leaf node, update good paths count
        if (adj[u].size() == 1 && adj[u].get(0) == parent) {
            goodPaths++;
            return;
        }

        // for each neighbour
        for (int v : adj[u]) {
            // if not parent, recursively perform DFS to find good paths
            if (v != parent)
                pathWithGoodNodesUtil(v, u, adj, A, goodNodes);
        }
    }

    // https://www.interviewbit.com/problems/water-flow/
    // wrong problem statement - river flows to another cell in opposite direction,
    // i.e if value >= current in new cell
    static int waterFlow(int[][] A) {
        int m = A.length, n = A[0].length;
        // visited array for both rivers:
        // 0 - not visited, 1 - river1, 2 - river2, 3 - both rivers
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

    // util to visit cells by river using BFS
    private static void waterFlowUtil(int i, int j, int[][] A, int[][] vis, int river) {
        int m = A.length, n = A[0].length;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // queue for BFS
        Queue<MatrixNode> q = new LinkedList<>();
        q.add(new MatrixNode(i, j));

        // mark current cell as visited by river
        vis[i][j] += river;

        while (!q.isEmpty()) {
            MatrixNode front = q.poll();

            // for each direction
            for (int[] dir : dirs) {
                int x = front.x + dir[0], y = front.y + dir[1];

                // if out of bounds or flow is less than current flow or already visited by current river
                if (x < 0 || x >= m || y < 0 || y >= n || A[x][y] < A[front.x][front.y] || vis[x][y] >= river)
                    continue;

                // add to queue for BFS
                q.add(new MatrixNode(x, y));
                // mark as visited by river
                vis[x][y] += river;
            }
        }
    }
}