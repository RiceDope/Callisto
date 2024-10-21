package com.rwalker;

import java.util.Comparator;

/**
 * A modified form of binary search for use with my custom Sequence data type
 * @author Rhys Walker
 * @since 21/10/2024
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
    public static <E> int findIndex(E[] array, int startPointer, int endPointer, E term, Comparator<E> comparator){

        // Allows us to exit loop once term has been found
        boolean found = false;

        int curTop = endPointer;
        int curBottom = startPointer;
        while(!found){
            int midPoint = ((curTop - curBottom)/2)+curBottom; // Will calculate our midpoint

            if (array[midPoint] == term){
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
}
