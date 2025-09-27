/*
 * PRIM'S ALGORITHM IMPLEMENTATION
 * 
 * Type: Standard Java Implementation
 * Algorithm: Prim's Algorithm - greedy algorithm to find Minimum Spanning Tree
 * How it works: Uses a priority queue to always select the minimum weight edge
 *              and builds the MST one edge at a time
 * 
 * Key differences from Kruskal's:
 * - Vertex-based approach (starts from a vertex and grows outward)
 * - Uses adjacency list representation and priority queue
 * - Time complexity: O(E log V) with binary heap
 *
 * To run: Execute main() method - no input required, uses predefined graph
 */

package Graphs;

import java.util.*;

public class PrimAlgorithm {
    
    /* Edge class */
    static class Edge {
        int source;      // starting vertex of the edge
        int destination; // ending vertex of the edge
        int weight;      // weight or cost of the edge

        /* Constructor */
        Edge(int source, int destination, int weight) {
            // initialize edge with given source, destination, and weight
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }


    /* Graph class */
    static class Graph {
        int vertices;                               // number of vertices in the graph
        ArrayList<ArrayList<Edge>> adjacencyList;   // adjacency list to store edges for each vertex

        /* Constructor */
        Graph(int vertices) {
            // initialize graph with given number of vertices
            this.vertices = vertices;

            // initialize empty adjacency lists for each vertex
            adjacencyList = new ArrayList<>(vertices);
            for (int i = 0; i < vertices; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }


        /* Method to add an edge to the graph */
        void addEdge(int source, int destination, int weight) {
            // create and add forward edge
            Edge edge = new Edge(source, destination, weight);
            adjacencyList.get(source).add(edge);
            // create and add reverse edge (because graph is undirected)
            edge = new Edge(destination, source, weight);
            adjacencyList.get(destination).add(edge);
        }


        /* Method to implement Prim's algorithm to find Minimum Spanning Tree */
        void primMST() {
            // priority queue to store edges, sorted by weight (minimum weight first)
            PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.weight - b.weight);
            
            // array to keep track of visited vertices
            boolean[] visited = new boolean[vertices];
            
            // list to store the edges that form the MST
            ArrayList<Edge> mst = new ArrayList<>();
            
            // start from vertex 1 and mark it as visited
            visited[1] = true;
            
            // add all edges connected to starting vertex to priority queue
            for (Edge edge : adjacencyList.get(1)) {
                pq.offer(edge);
            }

            // continue processing until priority queue is empty
            while (!pq.isEmpty()) {
                // get edge with minimum weight from queue
                Edge edge = pq.poll();
                
                // skip if destination vertex is already visited
                if (visited[edge.destination]) {
                    continue;
                }

                // add current edge to MST and mark destination as visited
                mst.add(edge);
                visited[edge.destination] = true;

                // add all edges from newly visited vertex to priority queue
                // (only if they lead to unvisited vertices)
                for (Edge nextEdge : adjacencyList.get(edge.destination)) {
                    if (!visited[nextEdge.destination]) {
                        pq.offer(nextEdge);
                    }
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

        graph.primMST();
    }

}