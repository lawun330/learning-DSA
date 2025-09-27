/**
 * LINKED BINARY SEARCH TREE IMPLEMENTATION
 * 
 * Type: Standard Java implementation
 * Algorithm: Binary search tree with linked node structure
 * 
 * How it works:
 * - Uses linked node-based approach for tree storage.
 * - Maintains binary search tree property: left < parent < right.
 * - Insertion: Recursively compares new value with current node, goes left if smaller, right if larger, 
 *   inserts at null position.
 * 	 -	No helper methods required
 * 
 * - Remove: Recursively finds node to delete, handles 4 deletion cases (leaf, single child, two children).
 *   For two children case, finds inorder successor and replaces node value.
 * 	 -	findMin() method is required for finding successor in two-children case
 * 
 * - Search: Binary search algorithm eliminates half the search space each step.
 *   -	Returns comparison count (0 if not found, >0 if found)
 * 	 -	No helper methods required
 * 
 * - Traversal: In-order traversal (left → root → right) visits nodes in sorted order.
 *   Recursive implementation that processes left subtree, prints current node, then processes right subtree
 *   -	No helper methods required
 * 
 * Advantages:
 * - Dynamic size (tree can grow as needed)
 * - Does not store null values
 * - No array resizing overhead
 * 
 * Disadvantages:
 * - No automatic balancing mechanism
 * - Can become unbalanced, leading to O(n) worst-case performance for all operations
 * - Extra memory per node for pointer/reference
 * - Non-contiguous memory can reduce cache locality
 * 
 * To run: Execute main() method - no input required, uses predefined values
 */

package Trees.BinarySearchTree;

class Node {
	Node left;
    Node right;
    int value;

    /* Constructor */
    public Node(int value) {
      this.value = value;
    }
}


public class LinkedBST {

	/* Insert method */
	public static void insert(Node node, int value) {
		if (value < node.value) {
			if (node.left != null) {
				insert(node.left, value);
			} else {
				System.out.println("Inserted " + value + " to left of " + node.value);
				node.left = new Node(value);
			}
		} else if (value > node.value) {
			if (node.right != null) {
				insert(node.right, value);
			} else {
				System.out.println("Inserted " + value + " to right of " + node.value);
				node.right = new Node(value);
			}
		}
	}


	/* Method to find the minimum value in a subtree */
	public static Node findMin(Node node) {
		while (node != null && node.left != null) {
			node = node.left;
		}
		return node;
	}


	/* Remove method */
	public static Node remove(Node node, int value) {
		if (node == null) {
			return null;
		}
		
		if (value < node.value) {
			node.left = remove(node.left, value);
		} else if (value > node.value) {
			node.right = remove(node.right, value);
		} else {
			// Node to be deleted found
			
			// Case 1: Node with no children (leaf node)
			if (node.left == null && node.right == null) {
				return null;
			}
			// Case 2: Node with only right child
			else if (node.left == null) {
				return node.right;
			}
			// Case 3: Node with only left child
			else if (node.right == null) {
				return node.left;
			}
			// Case 4: Node with two children
			else {
				// Find the inorder successor (smallest in right subtree)
				Node successor = findMin(node.right);
				node.value = successor.value;
				node.right = remove(node.right, successor.value);
			}
		}
		return node;
	}


	/* Binary search method to find a value in the BST with total comparisons made */
	public static int binarySearch(Node node, int value) {
		if (node != null) {					// null node (what does not exist) cannot be searched
			if (value == node.value) {
				return 1;					// if found, return total comparisons made
			}
			else if (value < node.value) {	// left branch is chosen, right branch is not required
				int leftResult = binarySearch(node.left, value);	// go further down with a recursive call
				return leftResult == 0 ? 0 : leftResult + 1;		// if not found, return 0 // if found, return total comparisons made
			}
			else {							// right branch is chosen, left branch is not required
				int rightResult = binarySearch(node.right, value);	// go further down with a recursive call
				return rightResult == 0 ? 0 : rightResult + 1;		// if not found, return 0 // if found, return total comparisons made
			}
		}
		return 0;	// if not found (search is exhausted) // if null node search (search has not even begun)
	}


	/* Traverse method for in-order traversal */
	public static void traverse(Node node) {
		if (node != null) {
			traverse(node.left);
			System.out.println(node.value);
			traverse(node.right);
		}
	}


	/* Main method */
	public static void main(String[] args) {
		// 1. Print what values will be inserted
		System.out.println("Values to be inserted: 5, 1, 8, 6, 3, 0");
		
		// 2. Build tree
		Node root = new Node(5);
	    System.out.println("Start building tree with root value " + root.value);
	    insert(root, 1);
	    insert(root, 8);
	    insert(root, 6);
	    insert(root, 3);
	    insert(root, 0);
	    
	    // 3. Add search functionality with comparison count
	    System.out.println("\nSearching for value 6:");
	    int comparisons_made = binarySearch(root, 6);
	    if (comparisons_made == 0) {
	        System.out.println(">> Not found");
	    } else {
	        System.out.println(">> Found in " + comparisons_made + " comparisons");
	    }

	    System.out.println("Searching for value 4 (not in tree):");
	    comparisons_made = binarySearch(root, 4);
	    if (comparisons_made == 0) {
	        System.out.println(">> Not found");
	    } else {
	        System.out.println(">> Found in " + comparisons_made + " comparisons");
	    }
	    
	    // 4. Remove an element
	    System.out.println("\n>> Removing value 6");
	    root = remove(root, 6);
	    
	    // 5. Traverse tree
	    System.out.println("\nTraversing tree:");
	    traverse(root);
    }

}