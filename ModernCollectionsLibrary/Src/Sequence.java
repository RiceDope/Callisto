/**
 * Script that implements the basic functionality needed for Sequence
 * 
 * @Author Rhys Walker
 * @Since 30/09/2024
 * @Version 0.1
 */

package ModernCollectionsLibrary.Src;

import java.lang.StringBuilder;

public class Sequence<E> {

    private E[] array = (E[]) new Object[100];
    private int endPointer = 0; // Track our current final index
    private int startPointer = 0; // Track our current first index

    private double growthRate = 1.5;

    /* 
        =================================================
                    OVERLOADED CONSTRUCTOR                          
        =================================================
    */

    /**
     * Default constructor using default growth rate and size values
     */
    @SuppressWarnings("unchecked")
    public Sequence(){
        
        // Default values are used

    }

    /**
     * Allows for specification of just the initial size of the array
     * @param size Int initial size of the array
     */
    @SuppressWarnings("unchecked")
    public Sequence(int size){

        array = (E[]) new Object[size];

    }

    /**
     * Allows for specification of just the growth rate
     * @param growthRate Double initial growth rate of the array
     */
    @SuppressWarnings("unchecked")
    public Sequence(double growthRate){

        this.growthRate = growthRate;

    }

    /**
     * Allows for specification of both starting size and growth rate
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     */
    @SuppressWarnings("unchecked")
    public Sequence(int size, double growthRate){

        array = (E[]) new Object[size];
        this.growthRate = growthRate;

    }

    /* 
        ====================================================
                                END                                      
        ====================================================
    */

    /**
     * Add an item to the array. Automatically deals with expansion of the array
     * @param item The item to add
     */
    public void append(E item){

        if (endPointer == array.length){ // We must expand the array
            // Expand the array
            arrayExpansion();

            // Now add the new item
            array[endPointer] = item;
            endPointer++;

        } else { // No need to expand array
            array[endPointer] = item;
            endPointer++;
        }
    }

    /**
     * Expand the array by the given growth factor
     */
    @SuppressWarnings("unchecked")
    private void arrayExpansion(){
        // New array is created by expanding by growthFactor
        int newLen = (int) Math.round(array.length*growthRate);
        E[] newArray = (E[]) new Object[newLen];

        // Now loop over and add all items to the larger list
        int secondCount = 0; // This counts from index 0 for the new array
        for (int i = startPointer; i < endPointer; i++){ // This counts from index startPointer for the old array
            newArray[secondCount] = array[i];
            secondCount++;
        }

        // Get the current term length of array to set pointers
        int termLength = endPointer-startPointer;

        // Now set array to newArray
        array = newArray;
        endPointer = termLength;
        startPointer = 0;
    }

    /**
     * Returns the length of a given sequence
     * 
     * @return Int length of sequence
     */
    public int length(){
    
        // Length can be worked out via the difference between the startIndex and endIndex
        return endPointer - startPointer;
    }

    /**
     * Custom toString method for generics.
     * 
     * @return String form of the array [term1, term2, term3]
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();

        // Add the beginning bracket
        sb.append("[");

        // Add all terms to the StringBuilder
        for (int i = 0; i < endPointer; i++){
            sb.append(array[i] + ", ");
        }
        // Find and remove last comma and space
        int lastComma = sb.lastIndexOf(",");
        sb.delete(lastComma, lastComma+2);

        // Add the final bracket
        sb.append("]");

        // Return the string
        return sb.toString();
    }

    /**
     * Returns the length of the underlying array not where terms are
     * @return Int being the raw length of the array
     */
    public int rawLength(){
        return array.length;
    }
} 