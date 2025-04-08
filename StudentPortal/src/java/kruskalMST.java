import java.util.Arrays;
import java.util.Comparator;

class GfG {
    public static int kruskalsMST(int V, int[][] edges) {
        
        // Sort all edges based on weight
        Arrays.sort(edges, Comparator.comparingInt(e -> e[2]));
        
        // Traverse edges in sorted order
        DSU dsu = new DSU(V);
        int cost = 0, count = 0;
        
        for (int[] e : edges) {
            int x = e[0], y = e[1], w = e[2];
            
            // Make sure that there is no cycle
            if (dsu.find(x) != dsu.find(y)) {
                dsu.union(x, y);
                cost += w;
                if (++count == V - 1) break;
            }
        }
        return cost;
    }

    public static void main(String[] args) {
        
        // An edge contains, weight, source and destination
        int[][] edges = {
            {0, 1, 10}, {1, 3, 15}, {2, 3, 4}, {2, 0, 6}, {0, 3, 5}
        };
        
       System.out.println(kruskalsMST(4, edges));
    }
}

// Disjoint set data structure
class DSU {
    private int[] parent, rank;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int i) {
        if (parent[i] != i) {
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }

    public void union(int x, int y) {
        int s1 = find(x);
        int s2 = find(y);
        if (s1 != s2) {
            if (rank[s1] < rank[s2]) {
                parent[s1] = s2;
            } else if (rank[s1] > rank[s2]) {
                parent[s2] = s1;
            } else {
                parent[s2] = s1;
                rank[s1]++;
            }
        }
    }
}