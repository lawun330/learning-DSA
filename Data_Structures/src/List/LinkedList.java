/*
 * LINKED LIST IMPLEMENTATION
 * 
 * Type: Standard Java Implementation
 * Algorithm: Linked List - linear data structure using linked nodes
 * How it works: Creates a list of nodes where each node points to the next,
 *              uses recursive traversal to print node values
 * 
 * Advantages:
 * - O(1) insert/delete at head
 * - Dynamic capacity: list is never full
 * - No need to choose a size up front
 * 
 * Disadvantages:
 * - Extra memory per node for pointer/reference
 * - Non-contiguous memory can reduce cache locality
 * - O(n) access by index
 * 
 * To run: Execute main() method - no input required, uses predefined values
 */

package List;

class Node {
	Node ptr;
	int value;

	/* Constructor */
	public Node(int value) {
		this.value = value;
	}
}


public class LinkedList {
	
	private Node head;							// head of the linked list
	private int size;							// number of elements in the list

	/* Constructor */
	public LinkedList() {
		head = null;							// initialize empty list
		size = 0;								// start with 0 elements
	}


	/* Method to clear the entire list */
	public void clear() {
		head = null;							// remove all references
		size = 0;								// reset size to 0
		System.out.println("List cleared");
	}


	/* Method to get the number of elements in the list */
	public int length() {
		return size;							// return current number of elements
	}


	/* Method to add an element to the end of the list */
	public boolean add(int element) {
		Node newNode = new Node(element);		// create new node
		if (head == null) {						// if list is empty
			head = newNode;						// make new node the head
		} else {								// if list has elements
			Node current = head;				// start from head
			while (current.ptr != null) {		// find the last node
				current = current.ptr;
			}
			current.ptr = newNode;				// link new node to end
		}
		size++;									// increment size
		return true;							// return true for successful addition
	}


	/* Method to get an element at a specific index */
	public Integer get(int index) {
		if (index < 0 || index >= size) {		// check if index is valid
			System.out.println("Index out of bounds");
			return null;						// return null for invalid index
		}
		Node current = head;					// start from head
		for (int i = 0; i < index; i++) {		// traverse to the index
			current = current.ptr;
		}
		return current.value;					// return the element at index
	}


	/* Method to remove an element at a specific index */
	public Integer remove(int index) {
		Integer removedElement = get(index);		// use get function to retrieve element
		if (removedElement == null) {				// if get returned null (invalid index)
			return null;							// return null for invalid index
		}
		if (index == 0) {							// if removing first element
			head = head.ptr;						// update head to next node
		} else {									// if removing from middle or end
			Node current = head;					// start from head
			for (int i = 0; i < index - 1; i++) {	// traverse to node before target
				current = current.ptr;
			}
			current.ptr = current.ptr.ptr;			// skip the target node
		}
		size--;										// decrement size
		System.out.println("Removed " + removedElement + " from list");	// show what was removed
		return removedElement;						// return the removed element
	}


	/* Utility method to print the list */
	public void printList() {
		System.out.println("Linked List:");
		printListRecursive(head);				// use recursive helper method
	}


	/* Recursive helper method to print the list */
	private void printListRecursive(Node node) {
		if (node != null) {
			System.out.println(node.value);
			printListRecursive(node.ptr);		// recursively call itself
		}
	}


	/* Main method */
	public static void main(String[] args) {
		LinkedList list = new LinkedList();

		// Add operation
		list.add(2);
		list.add(1);
		list.add(0);

		// Print operation
		list.printList();

		// Get operation
		System.out.println("Element at index 1: " + list.get(1));

		// Remove operation
		list.remove(1);

		// Length operation
		System.out.println("List length: " + list.length());

		// Clear operation
		list.clear();
		System.out.println("List length after clear: " + list.length());
	}

}