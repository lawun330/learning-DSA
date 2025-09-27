/*
 * KNAPSACK DYNAMIC PROGRAMMING ALGORITHM IMPLEMENTATION
 * 
 * Type: Standard Java Implementation
 * Algorithm: 0/1 Knapsack Problem - dynamic programming solution
 * How it works: Fills a 2D DP table with optimal solutions for subproblems (each cell stores the maximum value achievable),
 *               then uses backtracking to trace back through the DP table to identify which items were selected
 * 
 * To run: Execute main() method - no input required, uses predefined array
 */

package DynamicProgramming;

public class Knapsack {
    private static int validKnapsacks = 0;  // counter for intermediate improvements
    
    /* Method to solve knapsack problem using dynamic programming */
    public static int[] solveKnapsack(int[] values, int[] weights, int knapsackCapacity, int[][] dp) {

        validKnapsacks = 0;              // reset counter
        int bestValue = 0;               // highest value found during DP table construction
        int numItems = values.length;    // number of available items

        // fill the dp table iteratively
        // i is the number of items considered
        // w is the remaining capacity
        for (int i = 0; i <= numItems; i++) {
            for (int w = 0; w <= knapsackCapacity; w++) {
                if (i == 0 || w == 0) {  // base case: no items or no weight capacity
                    dp[i][w] = 0;
                }
                else if (weights[i-1] <= w) {   // if the item fits the remaining capacity
                    int valueWithItem = values[i-1] + dp[i-1][w-weights[i-1]];  // consider adding the item
                    int valueWithoutItem = dp[i-1][w];                          // consider not adding the item
                    dp[i][w] = Math.max(valueWithItem, valueWithoutItem);       // chose the better option
                    
                    // update the best value if the current value is better
                    if (dp[i][w] > bestValue) {
                        bestValue = dp[i][w];
                        validKnapsacks++;
                    }
                }
                else {
                    dp[i][w] = dp[i-1][w];      // skip if the item doesn't fit the remaining capacity
                }
            }
        }
        return new int[] {dp[numItems][knapsackCapacity], bestValue};
    }


    /* Method to print selected items using backtracking */
    public static void printSelectedItems(int[] values, int[] weights, int knapsackCapacity, int[][] dp) {

        int numItems = values.length;
        int remainingCapacity = knapsackCapacity;
        int maxValueFound = dp[numItems][remainingCapacity];

        System.out.println("\nSelected items:");
        
        // iterate backwards through items
        for (int itemIndex = numItems; itemIndex > 0 && maxValueFound > 0; itemIndex--) {
            // if there's a difference between current optimal value and value without this item, 
            // then the current item was included in the optimal solution
            if (maxValueFound != dp[itemIndex-1][remainingCapacity]) {
                System.out.println("Item " + itemIndex + ": value = " + values[itemIndex-1] + 
                                 ", weight = " + weights[itemIndex-1]);
                maxValueFound -= values[itemIndex-1];
                remainingCapacity -= weights[itemIndex-1];
            }
        }
    }


    /* Main method */
    public static void main(String[] args) {

        int knapsackCapacity = 20;
        int numItems = 16;
        
        int[] values = {10, 5, 30, 8, 12, 30, 50, 10, 2, 10, 40, 80, 100, 25, 10, 5};
        int[] weights = {1, 4, 6, 2, 5, 10, 8, 3, 9, 1, 4, 2, 5, 8, 9, 1};
        
        // create the dp table: rows = items, columns = weights
        int[][] dp = new int[numItems + 1][knapsackCapacity + 1];

        // solve the knapsack problem
        int[] results = solveKnapsack(values, weights, knapsackCapacity, dp);
        int maxValue = results[0];
        
        System.out.println("Weight: " + knapsackCapacity);
        System.out.println("Value: " + maxValue);
        System.out.println("Number of valid knapsack's: " + validKnapsacks);
        
        printSelectedItems(values, weights, knapsackCapacity, dp);
    }

}