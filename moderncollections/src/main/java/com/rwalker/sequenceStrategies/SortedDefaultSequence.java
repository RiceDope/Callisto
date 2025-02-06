package com.rwalker.sequenceStrategies;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import com.rwalker.BinarySearch;
import com.rwalker.ModernCollections;
import com.rwalker.UserNull;
import com.rwalker.UserNullSort;

/**
 * The same as the default strategy but exclusively for sorted Sequence.
 * Nulls in this implementation are placed at the end. Any insertions of a null will be tracked
 * 
 * @author Rhys Walker
 * @since 06-02-2025
 */

 @SuppressWarnings("unchecked")
public class SortedDefaultSequence<E> {
    
    private int endPointer;
    private int startPointer;
    private int initialSize;
    private Object[] array;
    private double growthRate;
    private Comparator<E> defaultComparator;
    private int minumumExpansion;
    private SequenceStrategies name = SequenceStrategies.SORTED_DEFAULT;

    private int expansionAdjustmentValue = 1;
    private int nullsInserted = 0;

    public SortedDefaultSequence(SequenceContext<E> context) {
        this.endPointer = context.endPointer;
        this.startPointer = context.startPointer;
        this.initialSize = context.initialSize;
        this.growthRate = context.growthRate;
        this.defaultComparator = context.comparator;
        this.minumumExpansion = context.minimumExpansion;

        array = new Object[initialSize];

        if (this.defaultComparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null for sorted Strategy");
        }
    }

    public SequenceStrategies getname(){
        return name;
    }

    /**
     * Method to insert an element into the array (Unsupported when sorting)
     * @param index The index to insert into
     * @param value The value to insert
     */
    public void insert(int index, E value) {

        throw new UnsupportedOperationException("Insert not supported for this strategy");

    }

    /**
     * Adds an element into the array
     * @param element
     */
    public void add(E element) {

        manipulateArray();

        // If we are null just add it to the end
        if (element == null) {
            nullsInserted++;
            element = (E) new UserNull<E>();
            insertAtLocation(endPointer, element);
            return;
        }

        if (endPointer == startPointer) {
            insertAtLocation(endPointer, element);
            return;
        }

        // Adjusted so that we don't search the null space pointlessly
        int index = BinarySearch.findInsertionIndex((E[]) array, startPointer, endPointer-nullsInserted, element, defaultComparator, endPointer-startPointer-nullsInserted);
        System.out.println(index);
        // int appendIndex = index-startPointer; // Convert from subarray index
        System.out.println(index);

        insertAtLocation(index, element);
    }

    /**
     * Adds all terms to the collection
     * @param collection The collection to add from
     */
    public void addAll(ModernCollections<E> collection) {
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }

    /**
     * Adds all terms to the collection
     * @param collection The collection to add from
     */
    public void addAll(Collection<E> collection) {
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }

    /**
     * Replaces the element at the index with the value (Unsupported for this strategy)
     * @param index
     * @param value
     */
    public void replace(int index, E value) {

        throw new UnsupportedOperationException("Replace not supported for this strategy");

    }

    public void sort() {

        // We are already sorted do nothing here. If we provide a new comparator we will sort differently

    }

    /**
     * Will change the default comparator and sort the array another way
     * @param comparator The comparator to sort by
     */
    public void sort(Comparator<E> comparator) {
        defaultComparator = comparator;

        int newEp = endPointer - startPointer;

        Object[] sortedArr = UserNullSort.sort(array, defaultComparator, startPointer, endPointer, true);
        Arrays.fill(array, null); // Null out original to maintain terms
        System.arraycopy(sortedArr, 0, array, 0, endPointer-startPointer); // Copy over

        startPointer = 0;
        endPointer = newEp;
    }

    /**
     * Get the item held at the given index
     * @param index The index to get from
     * @return The item at the index
     */
    public E get(int index) {
        index = index+startPointer; // Convert into the subArray index

        if (index < startPointer || index >= endPointer) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        return ((E) array[index] instanceof UserNull) ? null : (E) array[index];
    }

    /**
     * Removes an element from the array
     * @param element
     */
    public void remove(int index) {

        // If we are removing the first then just increase the start
        System.out.println(index);
        if (index == 0) {

            if (array[startPointer] instanceof UserNull) {
                nullsInserted--;
            }

            array[startPointer] = null;
            startPointer++;
            return;
        } else if (index == endPointer-1) {

            if (array[endPointer-1] instanceof UserNull) {
                nullsInserted--;
            }

            endPointer--;
            return;
        }

        index = index+startPointer; // Convert into the subArray index

        if (index < startPointer || index >= endPointer) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        E element = (E) array[index];

        closeGap(index);

        if (element instanceof UserNull){
            nullsInserted--;
        }
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
        if (endPointer-startPointer != 0){
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

    public String rawString() {
        return Arrays.toString(array) + " Start: " + startPointer + " End: " + endPointer;
    }

    /*
     * System functions
     */

    /**
     * Insertes the element at the desired location. As is dealt with by the add method
     * @param insertionIndex Index to insert at
     * @param element Element to insert
     */
    private void insertAtLocation(int insertionIndex, E element) {
        makeGap(insertionIndex);
        array[insertionIndex] = element;
    }

    /**
     * Makes a gap at the gapIndex given
     * @param gapIndex The index of the null element to make
     */
    private void makeGap(int gapIndex) {

        for (int i = endPointer; i > gapIndex; i--) {
            array[i] = array[i-1];
        }
        endPointer++;
        array[gapIndex] = null;
    }

    /**
     * Closes the array around the gapIndex given
     * @param gapIndex The index of the null element to remove
     */
    private void closeGap(int gapIndex) {
        for (int i = gapIndex; i < endPointer; i++) {
            array[i] = array[i+1];
        }
        endPointer--;
    }

    /**
     * Correctly deals with the check to expand or contract the array
     * There are two steps to this process:
     *      - Do we need to expand the array?
     *      YES:
     *          - Create a new array with the expansion factor
     *      NO:
     *          - Do we need to contract.
     *      MAYBE:
     *          - This is for the case where we have a lot of empty space but we have
     *            have a lot of space free at the beginning of the array.
     *              DO WE SHIFT:
     *              OR DO WE EXPAND
     *              RINGBUFFER?
     * @return
     */
    private void manipulateArray() {
        
        if (array.length - expansionAdjustmentValue == endPointer-startPointer) {
            // We have reached a maximum

            expandArray();

        } else if (array.length - expansionAdjustmentValue == endPointer){
            // We have reached the end maybe there is space at the beginning

            if (startPointer > array.length * 0.5) {
                // StartPointer is now more than halfway through the array so we should just shift the array

                System.out.println("Shifting array");

                shiftArray();

            } else {

                expandArray();

            }
        } else {
            // We have more space to add elements so do nothing
            return;
        }
    }

    /** 
     * Shift the array to start at [0]
     */
    private void shiftArray() {
        Object[] newArray = new Object[array.length];

        int newEp = endPointer - startPointer;

        int count = 0;
        for (int i = startPointer; i < endPointer; i++) {
            newArray[count] = array[i];
            count++;
        }

        array = newArray;

        startPointer = 0;
        endPointer = newEp;
    }

    /**
     * Expands the array by the growth rate
     */
    private void expandArray() {
        int newSize = (int) (array.length * growthRate);

        int newEp = endPointer - startPointer;

        // Are we expanding enough
        if (newSize < array.length + minumumExpansion){
            newSize = array.length + minumumExpansion;
        }
        Object[] newArray = new Object[newSize];

        // Copy the elements from the old array to the new array
        int count = 0;
        for (int i = startPointer; i<endPointer; i++) {
            newArray[count] = array[i];
            count++;
        }
 
        array = newArray;

        startPointer = 0;
        endPointer = newEp;
    }
}
