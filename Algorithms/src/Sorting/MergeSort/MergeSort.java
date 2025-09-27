/*
 * MERGE SORT ALGORITHM IMPLEMENTATION
 * 
 * Type: Standard Java Implementation
 * Algorithm: Merge Sort - divide and conquer with stable sorting
 * How it works: Divides array into halves, recursively sorts each half, then merges sorted halves
 * 
 * To run: Execute main() method - no input required, uses predefined array
 */

package Sorting.MergeSort;

import java.util.Arrays;

public class MergeSort {

	// a static variable to keep track of exchanges
	private static int exchangeCount = 0;

	/* Method to initiate the sorting process */
    public static void mergeSort(int[] array) {
        // base case -> if the array is null or has 1 or fewer elements, it's already sorted
        if (array == null || array.length <= 1) {
            return;
        }
        
        // calculate the middle index to divide the array
        int mid = array.length / 2;

        // create left and right subarrays based on the middle index
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        
        // recursively sort the left and right subarrays
        mergeSort(left);
        mergeSort(right);
        
        // merge the sorted subarrays to reconstruct the sorted original array
        merge(array, left, right);
    }
    
    
    /* Method to merge two sorted subarrays into a single sorted array */
    private static void merge(int[] array, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        
        // compare elements from left and right subarrays and place them in the correct order
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
				exchangeCount += (left.length - i);	// increment exchange count when an element from the right array is placed
            }
        }
        
        // copy any remaining elements from the left subarray
        while (i < left.length) {
            array[k++] = left[i++];
        }
        
        // copy any remaining elements from the right subarray
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }


    /* Main method */
    public static void main(String[] args) {
        // create an unsorted array
        int[] arr1 = {12, 9, 4, 99, 120, 1, 3, 10, 23, 45, 75, 69, 31, 88, 101, 14, 29, 91, 2, 0, 77};

        // before sorting
        System.out.print("Unsorted array with merge sort: ");
        for (int element : arr1) {
            System.out.print(element + " ");
        }
        System.out.println();
		
        // reset exchange count
		exchangeCount = 0;
		
        // sorting
        mergeSort(arr1);

        // after sorting
        System.out.print("Sorted array with merge sort: ");
        for (int element : arr1) {
            System.out.print(element + " ");
        }
		System.out.println("\n" + exchangeCount + " exchanges were made");
    }
    
}