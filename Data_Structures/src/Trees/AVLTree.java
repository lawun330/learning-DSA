/**
 * AVL Tree Implementation (Array-Based)
 * 
 * Type: Standard Java implementation
 * Algorithm: Self-balancing binary search tree with automatic rebalancing
 * 
 * How it works:
 * - Uses fixed array-based approach for tree storage
 * - Maintains AVL property: height difference between left and right subtrees ≤ 1
 * - Automatically performs rotations (left, right, left-right, right-left) to maintain balance
 * 
 * Advantages:
 * - Guaranteed O(log n) search, insert, and delete operations
 * - Always balanced, preventing worst-case O(n) performance
 * - Efficient memory usage with array-based storage
 * 
 * Disadvantages:
 * - Fixed capacity (cannot grow beyond initial size)
 * - Complex rotation logic for maintaining balance
 * - More overhead than simple binary search trees
 * 
 * To run: Execute main() method - enter integers to insert (0 to stop), then search for a value.
 */
 
package Trees;

import java.util.Scanner;

public class AVLTree {
	
    // arrays to store tree data
    private int[] elements;     // stores the values of nodes
    private int[] heights;      // stores the height of each node
    private int[] leftChildren; // stores indices of left children
    private int[] rightChildren;// stores indices of right children
    private int size;           // number of elements in the tree
    private int capacity;       // maximum capacity of the tree

    /* Constructor */
    public AVLTree() {
        capacity = 100; // set an initial capacity
        elements = new int[capacity];
        heights = new int[capacity];
        leftChildren = new int[capacity];
        rightChildren = new int[capacity];
        size = 0;
    }


    /* Insert method */
    public void insert(int element) {
        if (size == 0) {
            // if tree is empty, insert at root
            elements[0] = element;
            heights[0] = 1;
            leftChildren[0] = -1;
            rightChildren[0] = -1;
            size++;
        } else {
            // otherwise, insert and balance the tree
            int rootIndex = insert(0, element);
            if (rootIndex != 0) {
                swapNodes(0, rootIndex);
            }
            size++;
        }
    }

 
    /* Recursive method to insert an element */
    private int insert(int nodeIndex, int element) {
        if (nodeIndex == -1 || nodeIndex >= size) {
            int newIndex = size;
            elements[newIndex] = element;
            heights[newIndex] = 1;
            leftChildren[newIndex] = -1;
            rightChildren[newIndex] = -1;
            return newIndex;
        }

        if (element < elements[nodeIndex]) {
            int leftIndex = insert(leftChildren[nodeIndex], element);
            leftChildren[nodeIndex] = leftIndex;
        } else if (element > elements[nodeIndex]) {
            int rightIndex = insert(rightChildren[nodeIndex], element);
            rightChildren[nodeIndex] = rightIndex;
        } else {
            return nodeIndex; // element already exists
        }

        updateHeight(nodeIndex);
        return balance(nodeIndex);
    }


    /* Helper method to update the height of a node */
    private void updateHeight(int nodeIndex) {
        if (nodeIndex == -1 || nodeIndex >= size) return;
        int leftHeight = (leftChildren[nodeIndex] != -1) ? heights[leftChildren[nodeIndex]] : 0;
        int rightHeight = (rightChildren[nodeIndex] != -1) ? heights[rightChildren[nodeIndex]] : 0;
        heights[nodeIndex] = 1 + Math.max(leftHeight, rightHeight);
    }


    /* Helper method to balance the tree */
    private int balance(int nodeIndex) {
        int balance = getBalance(nodeIndex);

        if (balance > 1) {
            if (getBalance(leftChildren[nodeIndex]) < 0) {
                int leftIndex = rotateLeft(leftChildren[nodeIndex]);
                leftChildren[nodeIndex] = leftIndex;
            }
            return rotateRight(nodeIndex);
        }

        if (balance < -1) {
            if (getBalance(rightChildren[nodeIndex]) > 0) {
                int rightIndex = rotateRight(rightChildren[nodeIndex]);
                rightChildren[nodeIndex] = rightIndex;
            }
            return rotateLeft(nodeIndex);
        }

        return nodeIndex;
    }


    /* Helper method to get the balance factor of a node */
    private int getBalance(int nodeIndex) {
        if (nodeIndex == -1 || nodeIndex >= size) return 0;
        int leftHeight = (leftChildren[nodeIndex] != -1) ? heights[leftChildren[nodeIndex]] : 0;
        int rightHeight = (rightChildren[nodeIndex] != -1) ? heights[rightChildren[nodeIndex]] : 0;
        return leftHeight - rightHeight;
    }

    
    /* Helper method to perform a right rotation */
    private int rotateRight(int nodeIndex) {
        int leftChildIndex = leftChildren[nodeIndex];
        int leftRightChildIndex = rightChildren[leftChildIndex];

        rightChildren[leftChildIndex] = nodeIndex;
        leftChildren[nodeIndex] = leftRightChildIndex;

        updateHeight(nodeIndex);
        updateHeight(leftChildIndex);

        return leftChildIndex;
    }


    /* Helper method to perform a left rotation */
    private int rotateLeft(int nodeIndex) {
        int rightChildIndex = rightChildren[nodeIndex];
        int rightLeftChildIndex = leftChildren[rightChildIndex];

        leftChildren[rightChildIndex] = nodeIndex;
        rightChildren[nodeIndex] = rightLeftChildIndex;

        updateHeight(nodeIndex);
        updateHeight(rightChildIndex);

        return rightChildIndex;
    }


    /* Helper method to swap two nodes */
    private void swapNodes(int index1, int index2) {
        int temp;
        
        temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;

        temp = heights[index1];
        heights[index1] = heights[index2];
        heights[index2] = temp;

        temp = leftChildren[index1];
        leftChildren[index1] = leftChildren[index2];
        leftChildren[index2] = temp;

        temp = rightChildren[index1];
        rightChildren[index1] = rightChildren[index2];
        rightChildren[index2] = temp;

        updateParentReferences(index1, index2);
    }


    /* Helper method to update parent references after a swap */
    private void updateParentReferences(int oldIndex, int newIndex) {
        for (int i = 0; i < size; i++) {
            if (leftChildren[i] == oldIndex) {
                leftChildren[i] = newIndex;
            }
            if (rightChildren[i] == oldIndex) {
                rightChildren[i] = newIndex;
            }
        }
    }

    
    /* Method to get the size of the tree */
    public int size() {
        return size;
    }

    
    /* Method for binary search */
    public boolean binarySearch(int value) {
        return binarySearch(0, value, 1);
    }

    
    /* Recursive method for binary search */
    private boolean binarySearch(int nodeIndex, int value, int iterations) {
        if (nodeIndex == -1 || nodeIndex >= size) {
            System.out.println("The value not found after " + iterations + " iterations.");
            return false;
        }

        int currentValue = elements[nodeIndex];
        if (value == currentValue) {
            System.out.println("Found the value " + value + " in " + iterations + " iterations.");
            return true;
        } else if (value < currentValue) {
            return binarySearch(leftChildren[nodeIndex], value, iterations + 1);
        } else {
            return binarySearch(rightChildren[nodeIndex], value, iterations + 1);
        }
    }

    
    /* Main method */
    public static void main(String[] args) {
        AVLTree avl = new AVLTree();
        Scanner scanner = new Scanner(System.in);

        // input loop for inserting elements
        System.out.println("Enter integers to add to the AVL tree (enter 0 to stop):");
        while (true) {
            int value = scanner.nextInt();
            if (value == 0) break;
            avl.insert(value);
            System.out.println("Inserted: " + value);
        }

        System.out.println("AVL Tree size: " + avl.size());

        // search for a value
        System.out.println("Enter a value to search for:");
        int searchValue = scanner.nextInt();
        avl.binarySearch(searchValue);

        scanner.close();
    }
    
}