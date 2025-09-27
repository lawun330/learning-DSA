/*
 * INSERTION SORT ALGORITHM IMPLEMENTATION
 * 
 * Type: Standard Java Implementation
 * Algorithm: Insertion Sort - builds sorted array one element at a time
 * How it works: Iterates through array, inserting each element into its correct position
 *               in the already sorted portion of the array
 * 
 * To run: Execute main() method - no input required, uses predefined array
 */

package Sorting;

public class InsertionSort {

	/* Method to repeatedly insert a new element into the sorted portion of the array */
	public static int insertionSort(int array[]) {  
		int exchanges = 0; // variable to keep track of exchanges
		int n = array.length;
		for (int j = 1; j < n; j++) {
			int key = array[j];
			int i = j - 1;
			while ( (i > -1) && ( array [i] > key ) ) { 
				array [i + 1] = array [i];
				exchanges++; // increment counter when we make an exchange
				i--;
			}
			array[i + 1] = key;
		}
		return exchanges;
	}

	
	/* Main method */
	public static void main(String a[]) {
		// create an unsorted array
		int[] arr1 = {12, 9, 4, 99, 120, 1, 3, 10, 23, 45, 75, 69, 31, 88, 101, 14, 29, 91, 2, 0, 77};
		
		// before sorting
	    System.out.print("Unsorted array with insertion sort: ");
	    for(int i : arr1) {
	    	System.out.print(i + " ");
	    }
	    System.out.println();
	    
	    // sorting
	    int exchanges = insertionSort(arr1);
	    
	    // after sorting
	    System.out.print("Sorted array with insertion sort: ");
	    for(int i : arr1) { 
	    	System.out.print(i+" ");
	    }
	    System.out.println("\n" + exchanges + " exchanges were made");
	 }

}