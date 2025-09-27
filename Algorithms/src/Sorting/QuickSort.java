/*
 * QUICK SORT ALGORITHM IMPLEMENTATION
 * 
 * Type: Standard Java Implementation
 * Algorithm: Quick Sort - divide and conquer using pivot element
 * How it works: Selects pivot, partitions array around pivot, recursively sorts subarrays
 * 
 * To run: Execute main() method - no input required, uses predefined array
 *         (3 predefined arrays: unsorted, sorted, reverse sorted)
 */

package Sorting;

public class QuickSort {
	
	private int arr[];
    private int len;
    public static int c = 0; // a static variable to keep track of exchanges

    /* Method to initiate the sorting process */
    public void sort(int[] inArr) {
    	if (inArr == null || inArr.length == 0) {
            return;
        }
        this.arr = inArr;
        len = inArr.length;
        qSort(0, len - 1);	// call qSort with lower and upper index
    }


    /* Method to perform quick sort with the pivot */
    private void qSort(int bottomIdx, int topIdx) {
        int b = bottomIdx;
        int t = topIdx;
        
        // compute the pivot point as the middle index
        int pvt = arr[bottomIdx + (topIdx - bottomIdx) / 2];
        
        // divide into two arrays using pivot point
        while (b <= t) {
        	/* Find the number in left that is greater than the pivot value and the number in right that is less than pivot value. 
             * When two values are found, swap them. */
            
        	while (arr[b] < pvt) {
                b++;				// when index less than pivot point, increment by b
            }
            while (arr[t] > pvt) {
                t--;				// when index is greater than pivot point, decrement by 1
            }
            if (b <= t) {
                exchElements(b, t);	// if b <= t, increment or decrement index 
                b++;
                t--;
            }
        }
        
        // call qSort() recursively using current value of t
        if (bottomIdx < t){
            qSort(bottomIdx, t);
        }
        
        // call qSort() recursively using current value of b
        if (b < topIdx){
            qSort(b, topIdx);
        }
    }


    /* Method to swap the two values */
    private void exchElements(int b, int t) {
    	if (b != t) {
            // System.out.println("Exchange " + arr[b] + " and " + arr[t]); 
            c++; // increment exchange count when values are not equal & exchange values
            int temp = arr[b];
            arr[b] = arr[t];
            arr[t] = temp;
        }
    }


    /* Main method */
    public static void main(String a[]){
    	// create one array from three options
    	int[] input = {12, 9, 4, 99, 120, 1, 3, 10, 23, 45, 75, 69, 31, 88, 101, 14, 29, 91, 2, 0, 77};	// unsorted
    	//int[] input = {0, 1, 2, 3, 4, 9, 10, 12, 14, 23, 29, 31, 45, 69, 75, 77, 88, 91, 99, 101, 120};	// sorted
        //int[] input = {120, 101, 99, 91, 88, 77, 75, 69, 45, 31, 29, 23, 14, 12, 10, 9, 4, 3, 2, 1, 0};   // reverse sorted
        
        // before sorting
        System.out.print("Unsorted array with quick sort: ");
        for(int i = 0; i < input.length; i++){
            System.out.print(input[i]);
            System.out.print(" ");
        }
        System.out.println();

        // sorting
    	QuickSort sorter = new QuickSort();
        sorter.sort(input);
        
        // after sorting
        System.out.print("Sorted array with quick sort: ");
        for(int i = 0; i < input.length; i++){
            System.out.print(input[i]);
            System.out.print(" ");
        }
        System.out.println("\n" + c + " exchanges were made");
    }

}