/*
 * ARRAY-BASED STACK IMPLEMENTATION (SIMPLE INT DATA TYPE VERSION)
 * 
 * Type: Standard Java Implementation
 * Algorithm: Array-Based Stack - LIFO data structure using fixed-size array
 * How it works: Implements stack operations (clear, length, push, pop) using array with pointer,
 *              maintains LIFO order
 * 
 * Advantages:
 * - O(1) push/pop
 * - Less memory usage
 * - Contiguous memory layout (better cache performance)
 * - One file implementation
 * 
 * Disadvantages:
 * - Fixed capacity: stack can become full
 * - Requires choosing a size up front
 * - Integer-only data type
 * 
 * To run: Execute main() method - no input required, uses predefined values
 */

package Stack.ArrayBased;

public class SimpleArrayStack {

	private static final int defaultSize = 8; // default stack size

	private int maxSize; 		// maximum stack size
	private int pointer;		// index for the top element
	private int [] arrayStack;	// array holding stack

	/* Constructor for default stack size */
	SimpleArrayStack() {
		this(defaultSize);	// call the following constructor with the default size
	}


	/* Constructor for user-defined stack size */
	SimpleArrayStack(int size) {
		maxSize = size;						// set maximum stack size for operations
		pointer = 0;						// index always starts at 0
		this.arrayStack = new int[size];	// create a stack // elements are integer type
	}


	/* Method to clear the stack */
	public void clear() {
		pointer = 0;						// reset pointer to index 0
		System.out.println("Stack cleared");
	}


	/* Method to get the number of elements in the stack */
	public int length() {
		return pointer;
	}


	/* Method to add an element onto the top of the stack */
	public Integer push(int element) {
		if (pointer == maxSize) {					// check if stack is full
			System.out.println("Stack is full");
			return null;}							// return null for full stack
		arrayStack[pointer++] = element;			// else, increment pointer to add a new element
		return element;								// return the pushed element
	}


	/* Method to get the element at the top of the stack */
	public Integer pop() {
		if (pointer == 0) {							// check if stack is empty
			System.out.println("Stack is empty");
			return null;}							// return null for empty stack
		return arrayStack[--pointer]; 				// else, decrement pointer to remove the top element // return the popped element
	}


	/* Main method */
	public static void main(String[] args) {
		
		// 1. Push/Pop with Integer stack
		SimpleArrayStack intStack = new SimpleArrayStack(3);
		System.out.println("Pushed " + intStack.push(0) + " on stack");
		System.out.println("Pushed " + intStack.push(1) + " on stack");
		System.out.println("Pushed " + intStack.push(2) + " on stack");
		System.out.println("Popped " + intStack.pop() + " from stack");
		System.out.println("Popped " + intStack.pop() + " from stack");
		
		// 2. Push/Pop with String stack (will show compilation errors)
		SimpleArrayStack stringStack = new SimpleArrayStack(3);
		// System.out.println("Pushed " + stringStack.push("First") + " on stack");		// Uncomment this to see the compilation error
		// System.out.println("Pushed " + stringStack.push("Second") + " on stack");	// Uncomment this to see the compilation error
		// System.out.println("Pushed " + stringStack.push("Third") + " on stack");		// Uncomment this to see the compilation error
		
		// 3. Clear operation for Integer stack
		System.out.println("Remaining Integer stack length: " + intStack.length());
		intStack.clear();
		System.out.println("Remaining Integer stack length: " + intStack.length());
	}

}