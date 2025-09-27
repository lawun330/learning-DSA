/**
 * DYNAMIC ARRAY-BASED BINARY SEARCH TREE IMPLEMENTATION
 * 
 * Type: Standard Java implementation
 * Algorithm: Binary search tree with dynamic array storage
 * 
 * How it works:
 * - Uses dynamic array-based approach for tree storage.
 * - Physical array starts at index 0, which is unused because the logical BST (root) starts at index 1.
 * - Maintains binary search tree property: left < parent < right.
 * - Insertion: Iteratively compares new value with current node, goes left if smaller, right if larger, 
 *   inserts at null position.
 * 	 -	size() method is required to track tree size and dynamic array expansion
 * 
 * - Remove: Iteratively finds node to delete, handles 4 deletion cases (leaf, single child, two children).
 *   For two children case, finds inorder successor and replaces node value.
 * 	 -	findMin() method is required for finding successor in two-children case
 * 	 -	removeHelper() method is required for deletion operations
 * 
 * - Search: Binary search algorithm eliminates half the search space each step.
 *   -	Returns array [index, comparison_count]
 * 	 -	No helper methods required
 * 
 * - Traversal: In-order traversal (left → root → right) visits nodes in sorted order.
 *   Recursive implementation using array index calculations (2*index for left, 2*index+1 for right).
 *   -	No helper methods required
 * 
 * Advantages:
 * - Dynamic size (tree can grow as needed)
 * - Less memory usage per node
 * - Contiguous memory layout
 * 
 * Disadvantages:
 * - No automatic balancing mechanism
 * - Can become unbalanced, leading to O(n) worst-case performance for all operations
 * - Stores null values in sparse array
 * - Array resizing overhead when tree grows
 * 
 * To run: Execute main() method - no input required, uses predefined values
 */

package Trees.BinarySearchTree.ArrayBased;

import java.util.ArrayList;
import java.util.List;

public class DynamicArrayBST {
	
    private List<Integer> elements;
    private int size;
    private static int root_index = 1;  // root of BST starts at index 1 for mathematical convenience

    /* Constructor */
    public DynamicArrayBST() {
        elements = new ArrayList<>();
        elements.add(null);           // add a null root to simplify index calculations
        size = 0;
    }


    /* Method to get the size of the tree */
    public int size() {
        return size;
    }


    /* Insert method */
    public void insert(int element) {
        int index = 1;

        while (index >= elements.size()) {
            elements.add(null);
        }

        while (elements.get(index) != null) {
            if (element < elements.get(index)) {
                System.out.println("Inserting " + element + " to left of " + elements.get(index));
                index = 2 * index;
            } else if (element > elements.get(index)) {
                System.out.println("Inserting " + element + " to right of " + elements.get(index));
                index = 2 * index + 1;
            } else {
                // duplicate not allowed // nothing is inserted
                return;
            }

            while (index >= elements.size()) {
                elements.add(null);
            }
        }

        elements.set(index, element);
        size++;
    }


    /* Method to find the minimum value in a subtree */
    private int findMin(int index) {
        while (index < elements.size() && elements.get(index) != null) {
            if (2 * index >= elements.size() || elements.get(2 * index) == null) {
                return index;
            }
            index = 2 * index + 1;
        }
        return -1;
    }


    /* Helper method to remove a node */
    private void removeHelper(int index) {
        if (index >= elements.size() || elements.get(index) == null) {
            return;
        }
    
        Integer left = (2 * index < elements.size()) ? elements.get(2 * index) : null;
        Integer right = (2 * index + 1 < elements.size()) ? elements.get(2 * index + 1) : null;
    
        // Check if leaf node
        if (left == null && right == null) {
            elements.set(index, null);
        }
        // Only right child exists
        else if (left == null) {
            elements.set(index, right);
            removeHelper(2 * index + 1);
        }
        // Only left child exists
        else if (right == null) {
            elements.set(index, left);
            removeHelper(2 * index);
        }
        // Two children
        else {
            int successorIndex = findMin(2 * index + 1);
            if (successorIndex != -1) {
                elements.set(index, elements.get(successorIndex));
                removeHelper(successorIndex);
            }
        }
    }


    /* Remove method */
    public void remove(int element) {
        int[] result = binarySearch(root_index, element);   // binarySearch returns [index, total comparisons made]
        int index = result[0];
        if (index != -1 && index < elements.size()) {
            removeHelper(index);
            size--;
        }
    }


    /* Binary search method to find a value in the BST with index and total comparisons made */
    public int[] binarySearch(int index, int value) {
        int comparisonsMade = 0;
    
        while (index < elements.size() && elements.get(index) != null) {    // null node (what does not exist) cannot be searched // index < elements.size() because we are checking if the index is within the bounds of the array
            comparisonsMade++;
    
            int current = elements.get(index);
            if (value == current) {
                return new int[]{index, comparisonsMade};   // if found, return [index, total comparisons made]
            } else if (value < current) {   // left branch is chosen, right branch is not required
                index = 2 * index;                          // go further down
            } else {                        // right branch is chosen, left branch is not required
                index = 2 * index + 1;                      // go further down
            }
        }
        // if not found (search is exhausted) // if null node search (search has not even begun)
        return new int[]{-1, comparisonsMade};  // return [-1, total comparisons made]
    }    


    /* Traverse method for in-order traversal */
    public void traverse(int index) {
        if (index >= elements.size() || elements.get(index) == null) {
            return;
        }
        traverse(2 * index);                      // left
        System.out.println(elements.get(index));  // root
        traverse(2 * index + 1);                  // right
    }


    /* Main method */
    public static void main(String[] args) {
        // 1. Print what values will be inserted
        int[] values = {5, 1, 8, 6, 3, 0};
        System.out.println("Values to be inserted: 5, 1, 8, 6, 3, 0");
        
        // 2. Build tree
        DynamicArrayBST bst = new DynamicArrayBST();
        int root = values[0];   // root is the first value in the array
        System.out.println("Start building tree with root value " + root);
        for (int value : values) {
            bst.insert(value);
        }
        
        // 3. Add search functionality with both comparisons and index
        System.out.println("\nSearching for value 6:");
        int[] result = bst.binarySearch(root_index, 6);
        int index = result[0];
        int comparisons = result[1];
        if (index == -1) {
            System.out.println(">> Not found");
        } else {
            System.out.println(">> Found in " + comparisons + " comparisons at index " + index);
        }
        
        System.out.println("Searching for value 4 (not in tree):");
        result = bst.binarySearch(root_index, 15);
        index = result[0];
        comparisons = result[1];
        if (index == -1) {
            System.out.println(">> Not found");
        } else {
            System.out.println(">> Found in " + comparisons + " comparisons at index " + index);
        }
        
        // 4. Remove an element
        System.out.println("\nBST size before removing: " + bst.size());
        System.out.println(">> Removing value 6");
        bst.remove(6);
        System.out.println("BST size after removing: " + bst.size());
        
        // 5. Traverse tree
        System.out.println("\nTraversing tree:");
        bst.traverse(root_index);
    }

}