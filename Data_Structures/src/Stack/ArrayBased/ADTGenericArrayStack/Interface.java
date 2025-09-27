/*
 * ARRAY-BASED STACK INTERFACE (GENERIC ABSTRACT DATA TYPE VERSION)
 * 
 * Type: Standard Java Interface
 * Algorithm: Stack ADT - defines contract for LIFO data structure operations
 * How it works: Declares abstract methods for stack operations (push, pop, clear, length)
 *              that must be implemented by concrete stack classes
 * 
 * Three files work together to implement a generic array-based stack similar to a simple array stack:
 * - Interface: Abstract Data Type "Stack" interface
 * - Implementation: Implementation of Stack ADT
 * - Demo: Application of Stack Implementation
 * 
 * To run: This is an interface - implement this in Implementation.java and use in
 *         Demo.java for complete stack functionality
 */

package Stack.ArrayBased.ADTGenericArrayStack;

public interface Interface<E> {	// elements are type <E>
	
	/* Abstract method to clear the stack (reclaim resources) */
	public void clear();
	
	
	/* Abstract method to get the number of elements in the stack */
	public int length();
	
	
	/* Abstract method to add an element onto the top of the stack */
	public E push(E element);
	
	
	/* Abstract method to pop the top element of the stack (print popped element) */
	public E pop();
	
}