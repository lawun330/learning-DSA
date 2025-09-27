/*
 * LINKED STACK IMPLEMENTATION (GENERIC ABSTRACT DATA TYPE VERSION)
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
 * - Generic type support (E) for flexible data types
 * - One file implementation
 *
 * Disadvantages:
 * - Extra memory per node for pointer/reference
 * - Non-contiguous memory can reduce cache locality
 * 
 * To run: Execute main() method - no input required, uses predefined values
 */

package Stack.Linked;

class GenericStackNode<E> {
    GenericStackNode<E> ptr;
    E value;

    /* Constructor */
    public GenericStackNode(E value) {
        this.value = value;
    }
}


public class GenericLinkedStack {

    /* Method to clear the entire stack */
    public static <E> GenericStackNode<E> clear(GenericStackNode<E> node) {
        System.out.println("Stack cleared");
        return null;                                // return null to clear the stack
    }


    /* Method to get the number of elements in the stack */
    public static <E> int length(GenericStackNode<E> node) {
        int count = 0;
        GenericStackNode<E> current = node;
        while (current != null) {
            count++;
            current = current.ptr;
        }
        return count;
    }


    /* Method to push items onto the stack */
    public static <E> GenericStackNode<E> push(GenericStackNode<E> node, E value) {
        GenericStackNode<E> nnode;
        
        if (node != null) {
            nnode = new GenericStackNode<E>(value);
            nnode.ptr = node;
        } else {
            nnode = new GenericStackNode<E>(value);
            nnode.ptr = null;
        }
        return(nnode);
    }


    /* Method to pop items from the stack */
    public static <E> GenericStackNode<E> pop(GenericStackNode<E> node) {
        if (node == null) {                         // check if stack is empty
            System.out.println("Stack is empty");
            return(null);                           // return null for empty stack
        }
        return(node.ptr);                           // return the popped element
    }


    /* Main method */
    public static void main(String[] args) {
        GenericStackNode<Integer> intLink = null;
        GenericStackNode<String> stringLink = null;

        // 1. Push/Pop with Integer stack
        intLink = push(intLink, 0);
        System.out.println("Pushed " + intLink.value + " on stack");
        
        intLink = push(intLink, 1);
        System.out.println("Pushed " + intLink.value + " on stack");
        
        intLink = push(intLink, 2);
        System.out.println("Pushed " + intLink.value + " on stack");

        System.out.println("Popped " + intLink.value + " from stack");
        intLink = pop(intLink);
        
        System.out.println("Popped " + intLink.value + " from stack");
        intLink = pop(intLink);

        // 2. Push/Pop with String stack
        stringLink = push(stringLink, "Zero");
        System.out.println("Pushed " + stringLink.value + " on stack");
        
        stringLink = push(stringLink, "One");
        System.out.println("Pushed " + stringLink.value + " on stack");
        
        stringLink = push(stringLink, "Two");
        System.out.println("Pushed " + stringLink.value + " on stack");

        System.out.println("Popped " + stringLink.value + " from stack");
        stringLink = pop(stringLink);
        
        System.out.println("Popped " + stringLink.value + " from stack");
        stringLink = pop(stringLink);

        // 3. Clear operation for both stacks
        System.out.println("Remaining Integer stack length: " + length(intLink));
        System.out.println("Remaining String stack length: " + length(stringLink));
        intLink = clear(intLink);
        stringLink = clear(stringLink);
        System.out.println("Remaining Integer stack length: " + length(intLink));
        System.out.println("Remaining String stack length: " + length(stringLink));
    }

}