/*
 * ARRAY-BASED STACK APPLICATION (GENERIC ABSTRACT DATA TYPE VERSION)
 * 
 * Type: Standard Java Application
 * Algorithm: Stack Application - demonstrates generic stack operations
 * How it works: Shows the use of basic stack operations (push, pop, clear, length) with
 *              different data types to showcase generic ADT functionality
 * 
 * Three files work together to implement a generic array-based stack similar to a simple array stack:
 * - Interface: Abstract Data Type "Stack" interface
 * - Implementation: Implementation of Stack ADT
 * - Demo: Application of Stack Implementation
 * 
 * To run: Execute main() method - no input required, uses predefined values
 */

package Stack.ArrayBased.ADTGenericArrayStack;

public class Demo {

	/* Main method */
	public static void main(String[] args) {	
		
		// 1. Push/Pop with Integer stack
		Implementation<Integer> intStack = new Implementation<Integer>(5);
		System.out.println("Pushed " + intStack.push(0) + " on stack");
		System.out.println("Pushed " + intStack.push(1) + " on stack");
		System.out.println("Pushed " + intStack.push(2) + " on stack");
		System.out.println("Popped " + intStack.pop() + " from stack");
		System.out.println("Popped " + intStack.pop() + " from stack");
		
		// 2. Push/Pop with String stack
		Implementation<String> stringStack = new Implementation<String>(3);
		System.out.println("Pushed " + stringStack.push("Zero") + " on stack");
		System.out.println("Pushed " + stringStack.push("One") + " on stack");
		System.out.println("Pushed " + stringStack.push("Two") + " on stack");
		System.out.println("Popped " + stringStack.pop() + " from stack");
		System.out.println("Popped " + stringStack.pop() + " from stack");
		
		// 3. Clear operation for both stacks
		System.out.println("Remaining Integer stack length: " + intStack.length());
		System.out.println("Remaining String stack length: " + stringStack.length());
		intStack.clear();
		stringStack.clear();
		System.out.println("Remaining Integer stack length: " + intStack.length());
		System.out.println("Remaining String stack length: " + stringStack.length());		
	}

}