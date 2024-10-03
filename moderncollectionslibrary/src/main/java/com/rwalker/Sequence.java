/**
 * Script that implements the basic functionality needed for Sequence
 * 
 * Created: 30/09/2024
 * Last Updated 02/10/2024
 * 
 * Important notes:
 *      Infrastructure to deal with null removes and reduce array refactor is in place partially
 *      Needs implementing in certain methods as when to refactor
 * 
 *      Some fields have been implemented for later use
 * 
 * @Author Rhys Walker
 */

package com.rwalker;

import java.lang.StringBuilder;

@SuppressWarnings("unchecked")
public class Sequence<E> {

    // Runtime variables
    private int endPointer = 0; // Track our current final index
    private int startPointer = 0; // Track our current first index
    private int removed = 0; // Not used infrastructure in place for length and such

    // Subject to change in constructor
    private int initialSize = 100;
    private E[] array = (E[]) new Object[initialSize]; // Default size of array if one is not chosen
    private double growthRate = 1.5; // Default growth rate if one isnt chosen

    // Boolean settings of the Sequence
    private boolean enforceSort = false; // Do we want to enforce a sort
    private boolean ascending = true; // When enforcing sort do we want ascending or descending

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

        if (size  > 0){
            initialSize = size;
            array = (E[]) new Object[size];
        } else {
            System.err.println("Negative size used, Defaults applied");
        }

    }

    /**
     * Allows for specification of just the growth rate
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(double customGrowthRate){

        if (customGrowthRate >  1.0){
            this.growthRate = customGrowthRate;
        } else {
            System.err.println("Invalid growth rate: Default growth rate used");
        }
        

    }

    /**
     * Allows for specification of both starting size and growth rate
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(int size, double customGrowthRate){

        if (size  > 0){
            initialSize = size;
            array = (E[]) new Object[size];
        } else {
            System.err.println("Negative size used, Defaults applied");
        }
        if (customGrowthRate >  1.0){
            this.growthRate = customGrowthRate;
        } else {
            System.err.println("Invalid growth rate: Default growth rate used");
        }

    }

    /* 
        ====================================================
                                END                                      
        ====================================================
    */

    /**
     * Peek at the next item in the queue
     * @return The next item in the queue
     */
    public E  peek(){
        if (length() > 0){
            return array[startPointer];
        } else {
            System.err.println("No items to dequeue: null returned");
            return null;
        }
        
    }

    /**
     * Dequeue an item
     * @return The item that has  been dequeued
     */
    public E dequeue(){
        if (length() > 0){
            E temp = array[startPointer];
            array[startPointer] = null;
            startPointer++;
            return temp;
        } else {
            System.err.println("No items to dequeue: null returned");
            return null;
        }
    }

    /**
     * Enqueue an item at the end of the queue
     * Functionaly the same as append. There is no difference
     * @param item
     */
    public void enqueue(E item){
        append(item);
    }

    /**
     * Clears the array. New arrays size is initialSize
     */
    public void clear(){
        E[] newArray = (E[]) new Object[initialSize];
        array = newArray;
        startPointer = 0;
        endPointer = 0;
    }

    /**
     * Remove an element by index
     * @param index Index of the element to remove
     */
    public void remove(int index){
        int indexToAdjust = startPointer+index;
        if (indexToAdjust > endPointer || index < 0) {
            System.err.println("Index out of bounds, Nothing removed");
        } else {

            array[indexToAdjust] = null;
            reformat(false);
        }
    }

    /**
     * Gets a specific element from the array
     * @param index The index of the element 
     * @return The element
     */
    public E get (int index){

        // Adjust in case 0 is not the starting point
        int indexToAdjust = startPointer+index;

        if (indexToAdjust > endPointer || index < 0){ // If in unset positions
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
        } else {
            // Adjust in case 0 is not the starting point
            int indexToAdjust = startPointer+index;

            if (indexToAdjust > endPointer || index < 0){ // If in unset positions
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
                reformat(true);

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
    private void reformat(boolean  expand){
        E[] newArray;
        if (expand == true){
            // New array is created by expanding by growthFactor
            int newLen = (int) Math.round(array.length*growthRate);
            newArray = (E[]) new Object[newLen];
        } else {
            // If we dont want to expand the array but just re-format
            newArray = (E[]) new Object[array.length];
        }
        

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

        // As long as more than one element
        if (length() != 0){
            // Add all terms to the StringBuilder
            for (int i = startPointer; i < endPointer; i++){
                if (array[i] != null){
                    sb.append(array[i] + ", ");
                }
            }
            // Find and remove last comma and space
            int lastComma = sb.lastIndexOf(",");
            sb.delete(lastComma, lastComma+2);
        }

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

    /**
     * Returns the array including null positions
     * @return String containing full length of the array not just what is being worked on
     */
    public String rawString(){
        StringBuilder sb = new StringBuilder();

        // Add the beginning bracket
        sb.append("[");

        // Add all terms to the StringBuilder
        for (int i = 0; i < array.length; i++){
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
     * Set a custom growth rate for the array
     * @param newRate The new growth rate
     */
    public void setGrowthRate(Double newRate){
        if (newRate >  1.0){
            this.growthRate = newRate;
        } else {
            System.err.println("Invalid growth rate: Growth rate above 1 is required");
        }
    }

    /**
     * Get the growth rate Sequence is using
     * @return Double growth rate
     */
    public double getGrowthRate(){
        return growthRate;
    }
} 