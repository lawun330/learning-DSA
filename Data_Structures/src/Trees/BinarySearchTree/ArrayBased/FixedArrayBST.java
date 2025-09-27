/**
 * FIXED ARRAY-BASED BINARY SEARCH TREE IMPLEMENTATION
 * 
 * Type: Standard Java implementation
 * Algorithm: Binary search tree with fixed array storage
 * 
 * How it works:
 * - Uses fixed array-based approach for tree storage.
 * - Physical array starts at index 0, which is unused because the logical BST's root starts at index 1.
 * - Maintains binary search tree property: left < parent < right.
 * - Insertion: Iteratively compares new value with current node, goes left if smaller, right if larger, 
 *   inserts at null position. Checks capacity limit before insertion.
 * 	 -	size() method is required to track tree size and check capacity limits
 *   -	Two failure cases:
 *   CASE 1: Logical BST capacity (or) physical array capacity is reached.
 * 			 BST formula produces an index beyond physical array bounds.
 *   CASE 2: Logical BST capacity (or) physical array capacity is not reached.
 * 			 BST formula produces an index within physical array bounds.
 * 		     Available empty slots cannot be used due to BST structural requirements.
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
 * - Less memory usage per node
 * - Contiguous memory layout
 * - No array resizing overhead
 * 
 * Disadvantages:
 * - Fixed size (tree can be full)
 * - No automatic balancing mechanism
 * - Can become unbalanced, leading to O(n) worst-case performance for all operations
 * - Stores null values in sparse array
 * 
 * To run: Execute main() method - no input required, uses predefined values
 */

package Trees.BinarySearchTree.ArrayBased;

public class FixedArrayBST {
	
    private int[] elements;
    private int size;
    private static int root_index = 1;	// logical BST's root starts at index 1 for mathematical convenience
	private static final int logical_capacity = 6;	// fixed capacity of logical BST
	private static final int physical_capacity = logical_capacity + root_index;	// fixed capacity of physical array

    /* Constructor */
    public FixedArrayBST() {
        this.elements = new int[physical_capacity];  // initialize physical array (capacity 7 with index 0 unused)
        this.size = 0;  // track actual BST nodes
        
        // Initialize all elements to -1 (indicating empty)
        for (int i = 0; i < elements.length; i++) {
            elements[i] = -1;
        }
    }


    /* Method to get the size of the tree */
    public int size() {
        return size;
    }


    /* Method to check if tree is full */
    public boolean isFull() {
        return size >= logical_capacity;
    }


    /* Insert method */
    public boolean insert(int element) {
        if (isFull()) {
            System.out.println("Cannot insert " + element + " - tree is at its capacity (" + logical_capacity + ")");
            return false;
        }
        
        int index = 1;

        while (elements[index] != -1) {
            if (element < elements[index]) {
                System.out.println("Inserting " + element + " to left of " + elements[index]);
                index = 2 * index;		// formula
            } else if (element > elements[index]) {
                System.out.println("Inserting " + element + " to right of " + elements[index]);
                index = 2 * index + 1;	// formula
            } else {
                // duplicate not allowed
                System.out.println("Cannot insert " + element + " - duplicate value");
                return false;
            }
            
            // Check if index exceeds bounds or would require dense spacing  
            if (index >= elements.length) {
                System.out.println("Cannot insert " + element + " - would exceed BST traversal indices (capacity remains but unused due to BST structural requirements)");
                return false;
            }
        }

        elements[index] = element;
        size++;
        return true;
    }


    /* Method to find the minimum value in a subtree */
    private int findMin(int index) {
        while (index < elements.length && elements[index] != -1) {
            if (2 * index >= elements.length || elements[2 * index] == -1) {
                return index;
            }
            index = 2 * index;
        }
        return -1;
    }


    /* Helper method to remove a node */
    private void removeHelper(int index) {
        if (index >= elements.length || elements[index] == -1) {
            return;
        }
    
        int left = (2 * index < elements.length) ? elements[2 * index] : -1;
        int right = (2 * index + 1 < elements.length) ? elements[2 * index + 1] : -1;
    
        // Check if leaf node
        if (left == -1 && right == -1) {
            elements[index] = -1;
        }
        // Only right child exists
        else if (left == -1) {
            elements[index] = right;
            removeHelper(2 * index + 1);
        }
        // Only left child exists
        else if (right == -1) {
            elements[index] = left;
            removeHelper(2 * index);
        }
        // Two children
        else {
            int successorIndex = findMin(2 * index + 1);
            if (successorIndex != -1) {
                elements[index] = elements[successorIndex];
                removeHelper(successorIndex);
            }
        }
    }


    /* Remove method */
    public boolean remove(int element) {
        int[] result = binarySearch(root_index, element);
        int index = result[0];
        if (index != -1 && index < elements.length) {
            removeHelper(index);
            size--;
            return true;
        }
        return false;
    }


    /* Binary search method to find a value in the BST with index and total comparisons made */
    public int[] binarySearch(int index, int value) {
        int comparisonsMade = 0;
    
        while (index < elements.length && elements[index] != -1) {
            comparisonsMade++;
    
            int current = elements[index];
            if (value == current) {
                return new int[]{index, comparisonsMade};
            } else if (value < current) {
                index = 2 * index;
            } else {
                index = 2 * index + 1;
            }
        }
    
        return new int[]{-1, comparisonsMade};
    }


    /* Traverse method for in-order traversal */
    public void traverse(int index) {
        if (index >= elements.length || elements[index] == -1) {
            return;
        }
        traverse(2 * index);
        System.out.println(elements[index]);
        traverse(2 * index + 1);
    }


    /* Main method */
    public static void main(String[] args) {
        // 1. Print what values will be inserted
        int[] values = {5, 1, 8, 6, 3, 0};
        System.out.println("Values to be inserted: 5, 1, 8, 6, 3, 0");
        System.out.println("BST's fixed capacity: " + logical_capacity);
        
        // 2. Build tree
        FixedArrayBST bst = new FixedArrayBST();
        int root = values[0];
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
		System.out.println("\nCurrent size: " + bst.size() + "/" + logical_capacity);
        System.out.println(">> Removing value 6");
        bst.remove(6);
		System.out.println("Current size: " + bst.size() + "/" + logical_capacity);

        // 5. Traverse tree
        System.out.println("\nTraversing tree:");
        bst.traverse(root_index);

		// 6. Reinsert the removed element
		System.out.println("\n>> Reinserting 6 (should be approved <- free slot)");
        bst.insert(6);
		System.out.println("Current size: " + bst.size() + "/" + logical_capacity);

        // 7. Test failure cases
		// 7.1. Test capacity limit
        System.out.println("\n>> Testing failure case 1: Trying to insert 4 (should be rejected <- capacity reached)");
        bst.insert(4);
		// 7.2. Reinsert the new element instead of the removed element
		System.out.println("Current size: " + bst.size() + "/" + logical_capacity);
		System.out.println(">> Removing value 6 again");
		bst.remove(6);
		System.out.println("Current size: " + bst.size() + "/" + logical_capacity);
		System.out.println(">> Testing failure case 2: Trying to insert 2 (should be rejected <- newly calculated index does not fall within that free slot)");
        bst.insert(2);
		System.out.println("Current size: " + bst.size() + "/" + logical_capacity);
    }

}