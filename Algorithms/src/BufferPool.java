/*
 * BUFFER POOL PAGE REPLACEMENT ALGORITHMS IMPLEMENTATION
 * 
 * Type: Standard Java Implementation
 * Algorithms: FIFO, LRU, and LFU page replacement strategies
 * How it works: Simulates buffer pool management with different replacement policies
 *               when page faults occur in limited buffer space
 * 
 * To run: Execute main() method - interactive menu prompts for algorithm choice,
 *         buffer size, page count, and page sequence input
 */

import java.util.*;

public class BufferPool {

	/* Main method */
	public static void main(String args[]) {
        // Variables for tracking page faults and buffer management
		boolean isPageFault;
        int bufferSize, currentPage = 0, choice, pageFaultCount = 0, totalPages, processedPages = 0, bufferIndex, pointer;    
        int[] pageSequence;         
        
        // Display menu options for different algorithms
        System.out.println("Menu\n1.FIFO 2.LRU 3.LFU");
        System.out.print("ENTER YOUR CHOICE: ");

        Scanner scanner = new Scanner(System.in); 
        choice = scanner.nextInt();
        
        try {
            switch(choice) {
                case 1: // FIFO implementation
                    pointer = 0;  // Tracks the next position to replace in FIFO buffer
                    
                    // Get and validate buffer size
                    System.out.print("enter the number of buffers (available buffers in the pool): ");
                    bufferSize = scanner.nextInt();
                    if (bufferSize <= 0) {
                        System.out.println("Buffer size must be positive!");
                        return;
                    }
                    
                    // Initialize FIFO buffer with -1 (empty slots)
                    int[] fifoBuffer = new int[bufferSize];
                    for (int i = 0; i < bufferSize; i++) {
                        fifoBuffer[i] = -1;
                    }
                    
                    // Get and validate number of pages
                    System.out.print("enter the number of pages (items to be stored): ");
                    totalPages = scanner.nextInt();
                    if (totalPages <= 0) {
                        System.out.println("Number of pages must be positive!");
                        return;
                    }

                    // Input the sequence of pages to process
                    pageSequence = new int[totalPages];
                    System.out.println("enter the page value (an item to place in a buffer): ");
                    for (int j = 0; j < totalPages; j++)
                        pageSequence[j] = scanner.nextInt();

                    do {
                        int pg = 0;
                        // Process each page in the sequence
                        for (pg = 0; pg < totalPages; pg++) { 
                            currentPage = pageSequence[pg];
                            isPageFault = true;
                            
                            // Check if page already exists in buffer (page hit)
                            for (int j = 0; j < bufferSize; j++) {
                                if (currentPage == fifoBuffer[j]) {
                                    isPageFault = false;  // Page found, no replacement needed
                                    break;
                                }
                            }
                            
                            if (isPageFault) {
                                // Page fault occurred - need to replace a page
                                fifoBuffer[pointer] = currentPage;  // Place new page at current pointer position
                                pointer = (pointer + 1) % bufferSize;  // Move pointer to next position (circular)
                                printBuffer(fifoBuffer);
                                pageFaultCount++;  // Increment page fault counter
                            } else {
                                // Page hit - no replacement needed
                                printBuffer(fifoBuffer);
                            }
                            processedPages++;  // Track total pages processed
                        }
                    } while (processedPages != totalPages);
                    System.out.println("Total page faults: " + pageFaultCount);
                    break;
                    
                case 2: // LRU implementation
                    bufferIndex = 0;
                    
                    // Get and validate buffer size
                    System.out.print("enter the number of buffers (available buffers in the pool): ");
                    bufferSize = scanner.nextInt();
                    if (bufferSize <= 0) {
                        System.out.println("Buffer size must be positive!");
                        return;
                    }

                    // Initialize arrays for LRU tracking
                    int[] lruBuffer = new int[bufferSize];        // Main buffer to store pages
                    int[] recentlyUsed = new int[bufferSize];     // Tracks order of page usage (most recent to least)
                    int[] tempBuffer = new int[bufferSize];       // Temporary buffer for reordering
                    
                    // Initialize all arrays with -1 (empty state)
                    for (int i = 0; i < bufferSize; i++) {
                        lruBuffer[i] = -1;
                        recentlyUsed[i] = -1;
                        tempBuffer[i] = -1;
                    }

                    // Get and validate number of pages
                    System.out.print("enter the number of pages (items to be stored): ");
                    totalPages = scanner.nextInt();
                    if (totalPages <= 0) {
                        System.out.println("Number of pages must be positive!");
                        return;
                    }

                    // Input page sequence
                    pageSequence = new int[totalPages];
                    System.out.println("enter the page value (an item to place in a buffer): ");
                    for (int j = 0; j < totalPages; j++)
                        pageSequence[j] = scanner.nextInt();

                    do {
                        int pg = 0;
                        for (pg = 0; pg < totalPages; pg++) {
                            currentPage = pageSequence[pg];
                            isPageFault = true;
                            
                            // Check if page already exists in buffer
                            for (int j = 0; j < bufferSize; j++) {
                                if (currentPage == lruBuffer[j]) {
                                    isPageFault = false;
                                    break;
                                }
                            }
                            
                            // Find the least recently used page's position
                            for (int j = 0; j < bufferSize && isPageFault; j++) {
                                if (lruBuffer[j] == recentlyUsed[bufferSize-1]) {
                                    bufferIndex = j;
                                    break;
                                }
                            }
                            
                            // Handle page fault or hit
                            if (isPageFault) {
                                lruBuffer[bufferIndex] = currentPage;  // Replace least recently used page
                                printBuffer(lruBuffer);
                                pageFaultCount++;
                            } else {
                                printBuffer(lruBuffer);
                            }
                            
                            // Update the recently used order
                            int p = 1;
                            tempBuffer[0] = currentPage;  // Most recently used page goes to front
                            // Copy other pages while maintaining their relative order
                            for (int j = 0; j < recentlyUsed.length; j++) {
                                if (currentPage != recentlyUsed[j] && p < bufferSize) {
                                    tempBuffer[p] = recentlyUsed[j];
                                    p++;
                                }
                            }
                            // Update the recently used array with new order
                            for (int j = 0; j < bufferSize; j++) {
                                recentlyUsed[j] = tempBuffer[j];
                            }
                            processedPages++;
                        }
                    } while (processedPages != totalPages);
                    System.out.println("Total page faults: " + pageFaultCount);
                    break;
        
                case 3: // LFU implementation
                    bufferIndex = 0;
                    pageFaultCount = 0;
                    int minFrequency;  // Tracks the minimum frequency count
                    
                    // Get and validate buffer size
                    System.out.print("enter the number of buffers (available buffers in the pool): ");
                    bufferSize = scanner.nextInt();
                    if (bufferSize <= 0) {
                        System.out.println("Buffer size must be positive!");
                        return;
                    }

                    // Initialize arrays for LFU tracking
                    int[] lfuBuffer = new int[bufferSize];        // Main buffer to store pages
                    int[] frequencyCount = new int[bufferSize];   // Tracks access frequency of each page
                    
                    // Initialize buffer with -1 (empty) and frequency counts with 0
                    for (int i = 0; i < bufferSize; i++) {
                        lfuBuffer[i] = -1;
                        frequencyCount[i] = 0;
                    }

                    // Get and validate number of pages
                    System.out.print("enter the number of pages (items to be stored): ");
                    totalPages = scanner.nextInt();
                    if (totalPages <= 0) {
                        System.out.println("Number of pages must be positive!");
                        return;
                    }

                    // Input page sequence
                    pageSequence = new int[totalPages];
                    System.out.println("enter the page value (an item to place in a buffer): ");
                    for (int j = 0; j < totalPages; j++)
                        pageSequence[j] = scanner.nextInt();

                    do {
                        int pg = 0;
                        for (pg = 0; pg < totalPages; pg++) {
                            currentPage = pageSequence[pg];
                            isPageFault = true;
                            
                            // Check if page already exists in buffer
                            for (int j = 0; j < bufferSize; j++) {
                                if (currentPage == lfuBuffer[j]) {
                                    isPageFault = false;
                                    frequencyCount[j]++;  // Increment frequency count for hit
                                    break;
                                }
                            }
                            
                            if (isPageFault) {
                                // Find the least frequently used page
                                minFrequency = frequencyCount[0];
                                for (int j = 0; j < bufferSize; j++) {
                                    if (frequencyCount[j] < minFrequency) {
                                        minFrequency = frequencyCount[j];
                                        bufferIndex = j;
                                    }
                                }
                                
                                // Replace the first page with minimum frequency
                                for (int j = 0; j < bufferSize; j++) {
                                    if (minFrequency == frequencyCount[j]) {
                                        lfuBuffer[j] = currentPage;
                                        bufferIndex = j;
                                        break;
                                    }
                                }
                                
                                frequencyCount[bufferIndex] = 1;  // Reset frequency count for new page
                                printBuffer(lfuBuffer);
                                pageFaultCount++;
                            } else {
                                printBuffer(lfuBuffer);
                            }
                            processedPages++;
                        }
                    } while (processedPages != totalPages);
                    System.out.println("Total page faults: " + pageFaultCount);
                    break;
                
                default:
                    System.out.println("Invalid choice! Please select 1, 2, or 3");
                    return;
            }
        } finally {
            scanner.close();
        }
    }


    /* Utility method to print current state of buffer */
    private static void printBuffer(int[] buffer) {
        System.out.print("buffer: ");
        for (int j = 0; j < buffer.length; j++) {
            System.out.print(buffer[j] + "  ");
        }
        System.out.println();
    }

}