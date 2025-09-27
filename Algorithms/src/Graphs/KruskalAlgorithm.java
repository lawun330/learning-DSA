/*
 * KRUSKAL'S ALGORITHM IMPLEMENTATION
 * 
 * Type: Standard Java Implementation
 * Algorithm: Kruskal's Algorithm - greedy algorithm to find Minimum Spanning Tree
 * How it works: Uses Union-Find data structure to sort all edges by weight
 *              and adds them to MST if they don't create a cycle
 * 
 * Key differences from Prim's:
 * - Edge-based approach (sorts all edges and processes by weight)
 * - Uses Union-Find data structure for cycle detection
 * - Time complexity: O(E log E) for sorting + O(E α(V)) for Union-Find
 *
 * To run: Execute main() method - no input required, uses predefined graph
 */

package Graphs;

import java.util.*;

public class KruskalAlgorithm {
    
    /* Edge class */
    static class Edge implements Comparable<Edge> {
        int source;      // starting vertex of the edge
        int destination; // ending vertex of the edge
        int weight;      // weight/cost of the edge

        /* Constructor */
        Edge(int source, int destination, int weight) {
            // initialize edge with given source, destination, and weight
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }


        /* Method to compare edges by weight for sorting */
        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }


    /* Union-Find data structure class */
    static class UnionFind {
        int[] parent;  // parent array to track root of each vertex
        int[] rank;    // rank array to optimize union operations

        /* Constructor */
        UnionFind(int vertices) {
            // initialize parent and rank arrays
            parent = new int[vertices];
            rank = new int[vertices];
            
            // each vertex is initially its own parent
            for (int i = 0; i < vertices; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }


        /* Method to find root of vertex with path compression */
        int find(int vertex) {
            // path compression: make every node point directly to root
            if (parent[vertex] != vertex) {
                parent[vertex] = find(parent[vertex]);
            }
            return parent[vertex];
        }


        /* Method to union two vertices by rank */
        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            // if already in same set, no union needed
            if (rootX == rootY) {
                return;
            }
            
            // attach smaller rank tree under root of higher rank tree
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                // ranks are equal, make one root and increment its rank
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }


    /* Graph class */
    static class Graph {
        int vertices;                    // number of vertices in the graph
        List<Edge> edges;                // list to store all edges

        /* Constructor */
        Graph(int vertices) {
            // initialize graph with given number of vertices
            this.vertices = vertices;
            edges = new ArrayList<>();
        }


        /* Method to add an edge to the graph */
        void addEdge(int source, int destination, int weight) {
            // create and add edge to the list
            Edge edge = new Edge(source, destination, weight);
            edges.add(edge);
        }


        /* Method to implement Kruskal's algorithm to find Minimum Spanning Tree */
        void kruskalMST() {
            // sort all edges by weight in ascending order
            Collections.sort(edges);
            
            // create Union-Find data structure
            UnionFind uf = new UnionFind(vertices);
            
            // list to store edges that form the MST
            List<Edge> mst = new ArrayList<>();
            
            // process each edge in sorted order
            for (Edge edge : edges) {
                int sourceRoot = uf.find(edge.source);
                int destRoot = uf.find(edge.destination);
                
                // if adding this edge doesn't create a cycle, add it to MST
                if (sourceRoot != destRoot) {
                    mst.add(edge);
                    uf.union(edge.source, edge.destination);
                }
            }

            System.out.println("Minimum Spanning Tree:");
            int totalWeight = 0;
            for (Edge edge : mst) {
                System.out.println(edge.source + " -- " + edge.destination + 
                                 " with weight " + edge.weight);
                totalWeight += edge.weight;
            }
            System.out.println("Total MST weight: " + totalWeight);
        }
    }


    /* Main method */
    public static void main(String[] args) {

        // create graph with 9 vertices (0 to 8)
        Graph graph = new Graph(9);
        
        // add edges to graph with their weights
        graph.addEdge(1, 2, 5);  // edge from vertex 1 to 2 with weight 5
        graph.addEdge(1, 3, 4);  // edge from vertex 1 to 3 with weight 4
        graph.addEdge(2, 3, 2);  // edge from vertex 2 to 3 with weight 2
        graph.addEdge(2, 4, 3);  // and so on...
        graph.addEdge(3, 5, 4);
        graph.addEdge(4, 5, 2);
        graph.addEdge(4, 7, 6);
        graph.addEdge(5, 6, 1);
        graph.addEdge(6, 7, 8);
        graph.addEdge(7, 8, 2);

        graph.kruskalMST();
    }

}