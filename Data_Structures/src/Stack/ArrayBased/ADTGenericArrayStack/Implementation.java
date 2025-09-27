/*
 * ARRAY-BASED STACK IMPLEMENTATION (GENERIC ABSTRACT DATA TYPE VERSION)
 * 
 * Type: Standard Java Implementation
 * Algorithm: Array-Based Stack - LIFO data structure using generic array
 * How it works: Implements stack operations (clear, length, push, pop),
 * 				maintains LIFO order
 *
 * Advantages:
 * - O(1) push/pop
 * - Less memory usage
 * - Contiguous memory layout (better cache performance)
 * - Generic type support (E) for flexible data types
 *
 * Disadvantages:
 * - Fixed capacity: stack can become full
 * - Requires choosing a size up front
 * - Many files implementation
 * 
 * Three files work together to implement a generic array-based stack similar to a simple array stack:
 * - Interface: Abstract Data Type "Stack" interface
 * - Implementation: Implementation of Stack ADT
 * - Demo: Application of Stack Implementation
 * 
 * To run: This is an implementation class - use with Demo.java
 *         for complete stack functionality
 */

package Stack.ArrayBased.ADTGenericArrayStack;

class Implementation<E> implements Interface<E> {	// elements are type <E>

	private static final int defaultSize = 8;		// default stack size

	private int maxSize; 		// maximum stack size
	private int pointer;		// index for the top element
	private E [] arrayStack;	// array holding stack

	/* Constructor for default stack size */
	Implementation() {
		this(defaultSize);		// call the following constructor with the default size
	}


	/* Constructor for user-defined stack size */
	@SuppressWarnings("unchecked")				// generic array allocation
	Implementation(int size) {
		maxSize = size;							// set maximum stack size for operations
		pointer = 0;							// index always starts at 0
		arrayStack = (E[]) new Object[size];	// create a stack // elements are type <E>
	}


	/* Implementation of an abstract method to clear the stack (reclaim resources) */
	public void clear() {
		pointer = 0;							// reset pointer to index 0
		System.out.println("Stack cleared");
	}

	
	/* Implementation of an abstract method to get the number of elements in the stack */
	public int length() {
		return pointer;
	}


	/* Implementation of an abstract method to add an element onto the top of the stack */
	public E push(E element) {
		if (pointer == maxSize) {					// check if stack is full
			System.out.println("Stack is full");
			return null;}							// return null for full stack
		arrayStack[pointer++] = element;			// else, increment pointer to add a new element
		return element;								// return the pushed element
	}


	/* Implementation of an abstract method to get the element at the top of the stack */
	public E pop() {
		if (pointer == 0) {							// check if stack is empty
			System.out.println("Stack is empty");
			return null;}							// return null for empty stack
		return arrayStack[--pointer]; 				// else, decrement pointer to remove the top element // return the popped element
	}

}