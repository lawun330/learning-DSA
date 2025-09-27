/*
 * ARRAY-BASED LIST IMPLEMENTATION
 * 
 * Type: Standard Java Implementation
 * Algorithm: Array-Based List - linear data structure using array
 * How it works: Creates a list using array with pointer to track elements,
 *              uses iterative traversal to print array values
 * 
 * Advantages:
 * - O(1) access by index
 * - Less memory usage
 * - Contiguous memory layout
 * 
 * Disadvantages:
 * - Fixed capacity: list can become full
 * - Requires choosing a size up front
 * - O(n) insert/delete in middle
 * 
 * To run: Execute main() method - no input required, uses predefined values
 */

package List;

public class ArrayBasedList {
	
	private static final int defaultSize = 10;		// default list size
	private int maxSize; 							// maximum list size
	private int pointer;							// index for the next element
	private int[] arrayList;						// array holding list

	/* Constructor for default list size */
	ArrayBasedList() {
		this(defaultSize);							// call the following constructor with the default size
	}


	/* Constructor for user-defined list size */
	ArrayBasedList(int size) {
		maxSize = size;							// set maximum list size for operations
		pointer = 0;							// index always starts at 0
		this.arrayList = new int[size];			// create a list // elements are integer type
	}


	/* Method to clear the entire list */
	public void clear() {
		pointer = 0;							// reset pointer to index 0
		System.out.println("List cleared");
	}


	/* Method to get the number of elements in the list */
	public int length() {
		return pointer;							// return current number of elements
	}


	/* Method to add an element to the end of the list */
	public void add(int element) {
		if (pointer == maxSize) {					// check if list is full
			System.out.println("List is full");
		}
		arrayList[pointer++] = element;				// else, increment pointer to add a new element
	}


	/* Method to get an element at a specific index */
	public Integer get(int index) {
		if (index < 0 || index >= pointer) {		// check if index is valid
			System.out.println("Index out of bounds");
			return null;							// return null for invalid index
		}
		return arrayList[index];					// return the element at index
	}


	/* Method to remove an element at a specific index */
	public Integer remove(int index) {
		Integer removedElement = get(index);		// use get function to retrieve element
		if (removedElement == null) {				// if get returned null (invalid index)
			return null;							// return null for invalid index
		}
		for (int i = index; i < pointer - 1; i++) {	// shift elements left
			arrayList[i] = arrayList[i + 1];
		}
		pointer--;								// decrement pointer
		System.out.println("Removed " + removedElement + " from list");	// show what was removed
		return removedElement;					// return the removed element
	}


	/* Utility method to print the list */
	public void printList() {
		System.out.println("Array-Based List:");
		for (int i = 0; i < pointer; i++) {
			System.out.println(arrayList[i]);
		}
	}


	/* Main method */
	public static void main(String[] args) {
		ArrayBasedList list = new ArrayBasedList(5);

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
