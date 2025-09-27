/*
 * LINKED STACK IMPLEMENTATION (SIMPLE INT DATA TYPE VERSION)
 * 
 * Type: Standard Java Implementation
 * Algorithm: Linked Stack - LIFO data structure using linked nodes
 * How it works: Implements stack operations (clear, length, push, pop) using linked nodes,
 *              maintains LIFO order
 *
 * Advantages:
 * - O(1) push/pop
 * - Dynamic capacity: stack is never full
 * - No need to choose a size up front
 * - One file implementation
 *
 * Disadvantages:
 * - Extra memory per node for pointer/reference
 * - Non-contiguous memory can reduce cache locality
 * - Integer-only data type
 * 
 * To run: Execute main() method - no input required, uses predefined values
 */

package Stack.Linked;

class StackNode {
    StackNode ptr;
    int value;

    /* Constructor */
    public StackNode(int value) {
        this.value = value;
    }
}


public class SimpleLinkedStack {

    /* Method to clear the entire stack */
    public static StackNode clear(StackNode node) {
        System.out.println("Stack cleared");
        return null;                                // return null to clear the stack
    }


    /* Method to get the number of elements in the stack */
    public static int length(StackNode node) {
        int count = 0;
        StackNode current = node;
        while (current != null) {
            count++;
            current = current.ptr;
        }
        return count;
    }


    /* Method to push items onto the stack */
    public static StackNode push(StackNode node, int value) {
        StackNode nnode;
        
        if (node != null) {
            nnode = new StackNode(value);
            nnode.ptr = node;
        } else {
            nnode = new StackNode(value);
            nnode.ptr = null;
        }
        return(nnode);
    }


    /* Method to pop items from the stack */
    public static StackNode pop(StackNode node) {
        if (node == null) {                         // check if stack is empty
            System.out.println("Stack is empty");
            return(null);                           // return null for empty stack
        }
        return(node.ptr);                           // return the popped element
    }


    /* Main method */
    public static void main(String[] args) {
        StackNode link;
    
        link = null;

        // 1. Push/Pop with Integer stack
        link = push(link, 0);
        System.out.println("Pushed " + link.value + " on stack");

        link = push(link, 1);
        System.out.println("Pushed " + link.value + " on stack");

        link = push(link, 2);
        System.out.println("Pushed " + link.value + " on stack");

        System.out.println("Popped " + link.value + " from stack");
        link = pop(link);
            
        System.out.println("Popped " + link.value + " from stack");
        link = pop(link);

        // 2. Push/Pop with String stack (will show compilation errors)
        // link = push(link, "Zero");  // Uncomment this to see the compilation error
        // link = push(link, "One");   // Uncomment this to see the compilation error
        // link = push(link, "Two");   // Uncomment this to see the compilation error
        
        // 3. Clear operation with Integer stack
        System.out.println("Remaining Integer stack length: " + length(link));
        link = clear(link);
        System.out.println("Remaining Integer stack length: " + length(link));
    }

}