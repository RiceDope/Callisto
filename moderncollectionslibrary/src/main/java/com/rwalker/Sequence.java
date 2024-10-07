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
    public Sequence(int size) throws IllegalArgumentException {

        if (size  > 0){
            initialSize = size;
            array = (E[]) new Object[size];
        } else {
            throw new IllegalArgumentException("Cannot use size of 0 or less");
        }

    }

    /**
     * Allows for specification of just the growth rate
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(double customGrowthRate) throws IllegalArgumentException{

        if (customGrowthRate >  1.0){
            this.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        

    }

    /**
     * Allows for specification of both starting size and growth rate
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(int size, double customGrowthRate) throws IllegalArgumentException{

        if (size  > 0){
            initialSize = size;
            array = (E[]) new Object[size];
        } else {
            throw new IllegalArgumentException("Cannot use size of 0 or less");
        }
        if (customGrowthRate >  1.0){
            this.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
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
    public E  peek() throws NullPointerException{ // TODO: Is this the right exception
        if (length() > 0){
            return array[startPointer];
        } else {
            throw new NullPointerException("No item to peek");
        }
        
    }

    /**
     * Dequeue an item
     * @return The item that has  been dequeued
     */
    public E dequeue() throws NullPointerException{ // TODO: Is this the right exception
        if (length() > 0){
            E temp = array[startPointer];
            array[startPointer] = null;
            startPointer++;
            return temp;
        } else {
            throw new NullPointerException("No items to dequeue");
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
    public void remove(int index) throws ArrayIndexOutOfBoundsException{
        int indexToAdjust = startPointer+index;
        if (indexToAdjust > endPointer || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
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
    public E get (int index) throws ArrayIndexOutOfBoundsException{

        // Adjust in case 0 is not the starting point
        int indexToAdjust = startPointer+index;

        if (indexToAdjust > endPointer || index < 0){ // If in unset positions
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        } else { // All good
            return array[indexToAdjust];
        }

    }

    /**
     * Replace a specifc item in the array
     * @param value The value to insert
     * @param index The index to insert at
     */
    public void replace(E value, int index) throws ArrayIndexOutOfBoundsException, IllegalArgumentException{

        // Force null check
        if (value == null){
            throw new IllegalArgumentException("null not allowed for replace");
        } else {
            // Adjust in case 0 is not the starting point
            int indexToAdjust = startPointer+index;

            if (indexToAdjust > endPointer || index < 0){ // If in unset positions
                throw new ArrayIndexOutOfBoundsException("Index out of bounds");
            } else { // All good
                array[startPointer+index] = value;
            }
        }

    }

    /**
     * Add an item to the array. Automatically deals with expansion of the array
     * @param item The item to add
     */
    public void append(E item) throws IllegalArgumentException{
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
            throw new IllegalArgumentException("null not allowed for append");
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
    public void setGrowthRate(Double newRate) throws IllegalArgumentException{
        if (newRate >  1.0){
            this.growthRate = newRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
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