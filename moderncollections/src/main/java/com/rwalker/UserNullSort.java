package com.rwalker;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Sorting algorithm that takes in a sortedArray and a comparator and returns a sortedArray
 */

 @SuppressWarnings("unchecked")
public class UserNullSort {
    
    /**
     * Takes in an Object array and deals with sorting taking into account any null values
     * @param <E>
     * @param array
     * @param comparator
     * @param startPointer
     * @param endPointer
     * @return
     */
    public static <E> Object[] sort(Object[] array, Comparator<E> comparator, int startPointer, int endPointer, boolean nullTail){
        // First loop over the array removing all null values
        // Track the amount of userNulls
        int userNulls = 0;
        Object[] sortingArray = new Object[endPointer-startPointer];
        int count = 0;
        for (int i = startPointer; i < endPointer; i++){
            if (array[i] instanceof UserNull){
                userNulls++;
            } else {
                sortingArray[count] = array[i];
                count++;
            }
        }

        // Remove what nulls are left from adding userNulls
        Object[] tempArr = new Object[sortingArray.length-userNulls];
        System.arraycopy(sortingArray, 0, tempArr, 0, sortingArray.length-userNulls);
        
        // Sort the array
        Arrays.sort((E[]) tempArr, comparator);

        Object[] finalArray = new Object[array.length];
        if (nullTail){ // Are nulls due to be at the end
            for (int i = 0; i < tempArr.length; i++) {
                finalArray[i] = tempArr[i];
            }
            for (int i = 0; i < userNulls; i++){
                finalArray[tempArr.length+i] = new UserNull<E>();
            }
            return finalArray;
        } else { // Are nulls due to be at the beginning
            for (int i = 0; i < userNulls; i++){
                finalArray[i] = new UserNull<E>();
            }
            for (int i = userNulls; i < tempArr.length+userNulls; i++){
                finalArray[i] = tempArr[i-userNulls];
            }
            return finalArray;
        }

    }

}
