/**
 * Script that implements the basic functionality needed for Sequence
 * 
 * Created: 30/09/2024
 * Last Updated 01/10/2024
 * 
 * @Author Rhys Walker
 */

package ModernCollectionsLibrary.Src;

import java.lang.StringBuilder;

@SuppressWarnings("unchecked")
public class Sequence<E> {

    // Runtime variables
    private int endPointer = 0; // Track our current final index
    private int startPointer = 0; // Track our current first index
    private int removed = 0; // Track removed terms

    // Subject to change in constructor
    private E[] array = (E[]) new Object[100]; // Default size of array if one is not chosen
    private double growthRate = 1.5; // Default growth rate if one isnt chosen

    /* 
        =================================================
                    OVERLOADED CONSTRUCTOR                          
        =================================================
    */

    /**
     * Default constructor using default growth rate and size values
     */
    public Sequence(){
        
        // Default values are used

    }

    /**
     * Allows for specification of just the initial size of the array
     * @param size Int initial size of the array
     */
    public Sequence(int size){

        array = (E[]) new Object[size];

    }

    /**
     * Allows for specification of just the growth rate
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(double growthRate){

        this.growthRate = growthRate;

    }

    /**
     * Allows for specification of both starting size and growth rate
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     */
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
     * Gets a specific element from the array
     * @param index The index of the element 
     * @return The element
     */
    public E get (int index){

        // Adjust in case 0 is not the starting point
        int indexToAdjust = startPointer+index;

        if (indexToAdjust > endPointer){ // If in unset positions
            System.err.println("Index out of bounds, Nothing to get");
            return null;
        } else { // All good
            return array[indexToAdjust];
        }

    }

    /**
     * Replace a specifc item in the array
     * @param value The value to insert
     * @param index The index to insert at
     */
    public void replace(E value, int index){

        // Force null check
        if (value == null){
            System.err.println("Null not allowed for replace, Action not taken");
        }  else {
            // Adjust in case 0 is not the starting point
            int indexToAdjust = startPointer+index;

            if (indexToAdjust > endPointer){ // If in unset positions
                System.err.println("Index out of bounds, Nothing to replace");
            } else { // All good
                array[startPointer+index] = value;
            }
        }

    }

    /**
     * Add an item to the array. Automatically deals with expansion of the array
     * @param item The item to add
     */
    public void append(E item){
        // Force null check
        if (item != null){
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
        } else {
            System.err.println("Cannot insert null, Action not taken");
        }
    }

    /**
     * Expand the array by the given growth factor.
     * Check for null items and remove
     */
    private void arrayExpansion(){
        // New array is created by expanding by growthFactor
        int newLen = (int) Math.round(array.length*growthRate);
        E[] newArray = (E[]) new Object[newLen];

        // Now loop over and add all items to the larger list
        int secondCount = 0; // This counts from index 0 for the new array
        for (int i = startPointer; i < endPointer-removed; i++){ // This counts from index startPointer for the old array

            // Get current term from the array
            E curTerm = array[i];

            if (curTerm == null){
                // Skip as term not needed
                continue;
            } else {
                newArray[secondCount] = curTerm;
                secondCount++;
            }
    
        }

        // Now set array to newArray
        array = newArray;
        endPointer = length(); // Set to current length after expansion
        startPointer = 0;
        removed = 0; // No items have been removed from list currently
    }

    /**
     * Returns the length of a given sequence
     * 
     * @return Int length of sequence
     */
    public int length(){
    
        // Length can be worked out via the difference between the startIndex and endIndex with removed taken off to track non existant terms.
        return endPointer - startPointer - removed;
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
            if (array[i] != null){
                sb.append(array[i] + ", ");
            }
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