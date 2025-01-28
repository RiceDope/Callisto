package com.rwalker;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A modified form of binary search for use with my custom Sequence data type
 * @author Rhys Walker
 * @version 21/01/2025
 */

public class BinarySearch {
    
    /**
     * Return the correct index to insert a term into
     * @param <E>
     * @param array The array that is to be searched through
     * @param startPointer The start pointer of the array
     * @param endPointer The end pointer of the array
     * @param term The term that we want to find the correct position of
     * @param comparator A comparator to be used when sorting
     * @return The index that is to be used
     */
    public static <E> int findInsertionIndex(E[] array, int startPointer, int endPointer, E term, Comparator<E> comparator, int size){

        if (size == 1) { // We either insert at 0 or 1
            if (comparator.compare(array[startPointer], term) < 0) {
                return endPointer;
            } else {
                return startPointer;
            }
        }

        // Allows us to exit loop once term has been found
        boolean found = false;

        // Pointers for start and end
        int curTop = endPointer;
        int curBottom = startPointer;
        
        // The maximum size of the array
        int maxSize = curTop - curBottom;

        while(!found){
            int midPoint = ((curTop - curBottom)/2)+curBottom; // Will calculate our midpoint

            // If the midpoint is 0 then it must be the same as that index or one on either side
            if (midPoint == 0){
                if (comparator.compare(array[midPoint], term) == 0){ // if they are equal
                    return midPoint;
                } else if(comparator.compare(term, array[midPoint]) < 0) {
                    // if we are strictly less than our point at index 0 then insert there
                    return midPoint;
                } else if (comparator.compare(term, array[midPoint]) > 0) {
                    // If we are strictly one above our point then insert there
                    return ++midPoint;
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            }

            // If the midpoint is the max then it must be the same as that inedex or greater
            if (midPoint == maxSize-1){ // Minus one because curTop is the next free index
                if (comparator.compare(array[midPoint], term) == 0){ // if they are equal
                    return midPoint;
                } else if(comparator.compare(term, array[midPoint]) > 0) {
                    // if we are strictly more than our point at index max then insert infront of there
                    return midPoint+1;
                } else if (comparator.compare(term, array[midPoint]) < 0) {
                    // If we are strictly one below our point then insert there
                    return --midPoint;
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            }

            // Generic loop control
            if (comparator.compare(array[midPoint], term) == 0){ // If they are equal
                return midPoint;
            } else if(comparator.compare(array[midPoint], term) > 0 && comparator.compare(term, array[midPoint-1]) > 0){
                // We have our position (smaller than midPoint larger than below)
                return midPoint;
            } else if(comparator.compare(term, array[midPoint]) > 0 && comparator.compare(array[midPoint+1], term) > 0) {
                // We have our position (bigger than midPoint smaller than above)
                return midPoint+1;
            } else if(comparator.compare(term, array[midPoint]) > 0){ // greater than
                // We have to shift our bounds up
                curBottom = midPoint;
            } else if(comparator.compare(array[midPoint], term) > 0){ // less than
                curTop = midPoint;
            }

        }

        // Unreachable code
        return 0;

    }

    /**
     * Return the correct index to insert a term into (For use with a ring buffer) It converts into a pure array (Sequential) and then returns that
     * @param <E>
     * @param array The array that is to be searched through
     * @param startPointer The start pointer of the array
     * @param endPointer The end pointer of the array
     * @param term The term that we want to find the correct position of
     * @param comparator A comparator to be used when sorting
     * @return The index that is to be used (In subArray form)
     */
    public static <E> int findInsertionIndexBufferLinearSearch(E[] array, int startPointer, int endPointer, E term, Comparator<E> comparator, int size){

        // Allows us to exit loop once term has been foun

        if (startPointer == endPointer) {
            // Must be our first element just insert at startPointer
            return startPointer;
        } // else if (size == 1) {
        //     if (comparator.compare(array[startPointer], term) < 0) {
        //         return endPointer;
        //     } else {
        //         return startPointer;
        //     }
        // }

        int count = startPointer;
        for (int i = 0; i != size+1; i++) {
            if (count == array.length) {
                count = 0;
            }
            if (count == endPointer) { // We have reached end with no insertion so just return and insert at end
                return count;
            }

            // Now perform checks at our current position
            System.out.println(array[count]);
            if (comparator.compare(array[count], term )== 0){
                return count; // We are equal so just insert there
            } else if (comparator.compare(array[count], term) > 0) {
                // TODO: Potential bug here where count == 0 so doing -- does not wrap around but gives an incorrect index
                return count; // We are less than so insert before
            }

            count++;

        }

        // Unreachable code
        return 0;

    }

    private static int convertToBuffer(int index, int size, int sp, int ep, int rawLen) {
        if (index > size-sp) { // We are in the buffer segment
            return index - (rawLen-(sp-1));
        } else {
            return index + sp;
        }
    }
}
