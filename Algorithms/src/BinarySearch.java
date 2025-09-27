
/*
 * BINARY SEARCH ALGORITHM IMPLEMENTATION
 * 
 * Type: Standard Java Implementation
 * Algorithm: Binary Search - divide and conquer search on pre-sorted arrays
 * How it works: Recursively eliminates half the search space by comparing target with middle element
 *               of current subarray until element is found or search space is exhausted
 * 
 * To run: Execute main() method - prompts for integer to search in predefined sorted array
 */

import java.util.*;

public class BinarySearch {

	/* Method to perform binary search recursively on sorted array */
	public static void binarySearch(int[] array, int currentIndex, int searchWindowEnd, int target, int comparisons) {
		// if target is larger, search right portion
		if (array[currentIndex] < target) {
			currentIndex = (searchWindowEnd - currentIndex) / 2 + currentIndex;
			comparisons++;
			binarySearch(array, currentIndex, searchWindowEnd, target, comparisons);            
		}
		
		// if target is smaller, search left portion
		else if (array[currentIndex] > target) {
			searchWindowEnd = currentIndex;
			currentIndex = currentIndex / 2;
			comparisons++;
			binarySearch(array, currentIndex, searchWindowEnd, target, comparisons);
		}
		
		// if target is found, print comparisons
		else {
			System.out.println("Found the value in " + comparisons + " iterations");
			return;      
		}
	}


	/* Main method */
	public static void main (String[] args) {
		int comparisons;		// counts search comparisons
		int currentIndex;		// current search position in array
		int searchWindowEnd;	// end boundary of current search window
		int target;				// target value to search for
		
		// initialize sorted array
		int[] sortedArray = {1, 2, 3, 4, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 23, 24, 25, 26, 27, 28, 29, 30, 33, 34, 35, 36, 37, 38, 39, 40};

	    // prompt user for input
	    System.out.print("Enter an integer between 1 and 40 to search for: ");

	    // read input
	    Scanner input = new Scanner(System.in);
	    target = input.nextInt();
	    input.close();
	    
	    comparisons = 1;

	    // initialize search position to middle of array
	    currentIndex = sortedArray.length / 2;
	    searchWindowEnd = sortedArray.length;
	 
	    // perform binary search
	    binarySearch(sortedArray, currentIndex, searchWindowEnd, target, comparisons);       
	}

}