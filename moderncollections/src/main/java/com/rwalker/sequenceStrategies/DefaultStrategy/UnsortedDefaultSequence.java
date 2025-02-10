package com.rwalker.sequenceStrategies.DefaultStrategy;

/**
 * The default strategy for the Sequence class.
 * 
 * Sub array is managed using a start and end pointer [null, null, null, 4, 7, 3, 2, null, null]
 *                                                                       ^start      ^end
 * There are no storage techniques used here to speed up for certain operations.
 * 
 * @author Rhys Walker
 * @version 13/01/2025
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import com.rwalker.BinarySearch;
import com.rwalker.HowToFunction;
import com.rwalker.ModernCollections;
import com.rwalker.Sequence;
import com.rwalker.UserNull;
import com.rwalker.UserNullSort;
import com.rwalker.sequenceStrategies.SequenceContext;
import com.rwalker.sequenceStrategies.SequenceState;
import com.rwalker.sequenceStrategies.SequenceStrategies;

@SuppressWarnings("unchecked")
public class UnsortedDefaultSequence<E> implements DefaultSequenceStrategy<E> {
    
    private int endPointer;
    private int startPointer;
    private int initialSize;
    private Object[] array;
    private double growthRate;
    private boolean enforceSort;
    private Comparator<E> defaultComparator;
    private int minumumExpansion;
    private SequenceStrategies name = SequenceStrategies.DEFAULT;
    private SequenceState state = SequenceState.UNSORTED;

    private int expansionAdjustmentValue = 1; // Adjustment for when to expand. Number of indexes before .length to expand (Brings in line with RingBuffer at 1)

    public UnsortedDefaultSequence(SequenceContext<E> context){
        this.endPointer = context.endPointer;
        this.startPointer = context.startPointer;
        this.initialSize = context.initialSize;
        this.growthRate = context.growthRate;
        this.enforceSort = context.enforceSort;
        this.defaultComparator = context.comparator;
        this.minumumExpansion = context.minimumExpansion;

        array = new Object[initialSize];
    }

    public SequenceStrategies getname(){
        return name;
    }

    public SequenceState getstate() {
        return state;
    }

    /**
     * Inserts a term at a specific index. Rest of the list gets shuffled
     * @param index index to insert the value at
     * @param value value to be inserted
     */
    public void insert(int index, E value){

        if (value == null){
            value = (E) new UserNull<E>();
        }

        // Calculate index we want to insert at
        int insertionIndex = startPointer + index;

        // Check if index is out of bounds
        if (insertionIndex > endPointer){
            throw new IndexOutOfBoundsException("Cannot insert into index outside of list");
        } else if (insertionIndex < startPointer) {
            throw new IndexOutOfBoundsException("Cannot insert into negative index");
        } else {
            E curTerm;
            E nextTerm = value; // first value to insert is value
            if (rawLength() > endPointer+1-expansionAdjustmentValue){
                for (int i = insertionIndex; i < endPointer+1; i++){
                    // Set our term to be inserted in this run
                    curTerm = nextTerm;
                    // Save the current term in that spot
                    nextTerm = (E) array[i];
                    // Overwrite
                    array[i] = curTerm;
                }
            } else {
                // Expand the array as we are at limit.
                array = expandCopy();
                for (int i = insertionIndex; i < endPointer+1; i++){
                    // Set our term to be inserted in this run
                    curTerm = nextTerm;
                    // Save the current term in that spot
                    nextTerm = (E) array[i];
                    // Overwrite
                    array[i] = curTerm;
                }
            }
            endPointer++; // Adjust as we have expanded by 1
        }
    }

    /**
     * Add an item to the array. Automatically deals with expansion of the array
     * @param item The item to add
     */
    public void add(E item){

        // Just regular append operation when enforcing
        if (item != null){
            addToEnd(item);
        } else {
            addToEnd((E) new UserNull<E>());
        }
    }

    /**
     * Add all terms from the given Sequence to this Sequence
     * @param toApp The Sequence to add from
     */
    public void addAll(ModernCollections<E> toApp) {

        Iterator<E> it = toApp.iterator();
        while (it.hasNext()){
            add(it.next());
        }

    }

    /**
     * Add all terms from the given Collection to this Sequence
     * @param toApp The Collection to add from
     */
    public void addAll(Collection<E> toApp) {

        Iterator<E> it = toApp.iterator();
        while (it.hasNext()){
            add(it.next());
        }

    }

    /**
     * Replace a specifc item in the array
     * @param value The value to insert
     * @param index The index to insert at
     */
    public void replace(int index, E value) {

        // Adjust in case 0 is not the starting point
        int indexToAdjust = startPointer+index;

        if (indexToAdjust > endPointer || index < 0){ // If in unset positions
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        }

        // Force null check
        if (value == null){
            array[indexToAdjust] = new UserNull<E>();
        } else {
             
            array[indexToAdjust] = value;
        }

    }

    /**
     * Remove an element by index
     * @param index Index of the element to remove
     */
    public void remove(int index){

        int indexToAdjust = startPointer+index;
        if (indexToAdjust > endPointer || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        } else {

            array[indexToAdjust] = null;
            closeGap(indexToAdjust);
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
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        } else { // All good
            E term = (E) array[indexToAdjust];
            if (term instanceof UserNull) {
                term = null;
            }
            return term;
        }

    }

    /*
     * OVERLOADED SORT() FUNCTION
     */

    /**
     * Sorts the array based on the field defaultComparator. Must be set
     * This will overwrite the current array with the sorted version
     * Use a comparator for a returnable type
     */
    public void sort(){

        if (defaultComparator != null){
            try {
                // Sort the array
                Object[] sortedArr = UserNullSort.sort(array, defaultComparator, startPointer, endPointer, false);
                Arrays.fill(array, null); // Null out original to maintain terms
                System.arraycopy(sortedArr, 0, array, 0, endPointer-startPointer); // Copy over

            } catch (Exception e){ // Throw error for either not implementing Comparable or smth else
                System.err.println(e);
                throw new UnknownError("Comparator incorrect or not set");
            }
        } else {
            throw new IllegalStateException("Default comparator must be set to use this sort");
        }
    }

    /**
     * Replaces the default comparator and then sorts the array
     * @param comparator
     */
    public void sort(Comparator<E> comparator){

        defaultComparator = comparator;
        try {
            // Sort the array
            Object[] sortedArr = UserNullSort.sort(array, defaultComparator, startPointer, endPointer, false);
            Arrays.fill(array, null); // Null out original to maintain terms
            System.arraycopy(sortedArr, 0, array, 0, endPointer-startPointer); // Copy over

        } catch (Exception e){ // Throw error for either not implementing Comparable or smth else
            System.err.println(e);
            throw new UnknownError("Comparator incorrect or not set");
        }
    }

    public Sequence<E> sortCopy(){

        if (defaultComparator != null) {
            try {
                // Sort the array
                Object[] sortedArr = UserNullSort.sort(array, defaultComparator, startPointer, endPointer, false);
                Sequence<E> sortedSeq = new Sequence<E>(rawLength(), growthRate, defaultComparator); // Instantiate sequence with same settings
                sortedSeq.setSubArray(startPointer, endPointer, sortedArr); // Set the sub array
                return sortedSeq;
            } catch (Exception e){ // Throw error for either not implementing Comparable or smth else
                System.err.println(e);
                throw new UnknownError("Comparator either not valid or cannot be compared");
            }
        } else {
            throw new IllegalStateException("Default comparator must be set to use this sort");
        }
    }

    /**
     * Sorts the array based on the comarator given and returns a new Copy.
     * This method does not overwrite the current array
     * 
     * @param comparator The comparator for comparing the types
     * @return A sorted Sequence
     */
    public Sequence<E> sortCopy(Comparator<E> comparator){
        try {
            // Sort the array
            Object[] sortedArr = UserNullSort.sort(array, comparator, startPointer, endPointer, false);
            Sequence<E> sortedSeq = new Sequence<E>(rawLength(), growthRate, comparator); // Instantiate sequence with same settings
            sortedSeq.setSubArray(startPointer, endPointer, sortedArr); // Set the sub array
            return sortedSeq;
        } catch (Exception e){ // Throw error for either not implementing Comparable or smth else
            System.err.println(e);
            throw new UnknownError("Comparator either not valid or cannot be compared");
        }
    }

    /*
     * END OF OVERLOADED SORT() FUNCTION
     */

    /**
      * Set the comparator to be used
      * @param comparator
      */
    public void setComparator (Comparator<E> comparator){
        defaultComparator = comparator;
    }

    /*
     * ======================================================
     *                  END ARRAY OPERATIONS
     * ======================================================
     */

    /*
     * ======================================================
     *                    QUEUE FUNCTIONS
     * ======================================================
     */

    /**
     * Is the queue empty
     * @return true if queue is empty, false if queue has terms
     */
    public boolean isEmpty(){

        if (length() == 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Dequeue an item
     * @return The item that has  been dequeued
     */
    public E dequeue() {

        if (length() > 0){
            E temp = (E) array[startPointer];
            if (temp instanceof UserNull){
                temp = null;
            }
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
     * This means that when sortOnwards then it will insert into place
     * 
     * @param item The item to enqueue
     */
    public void enqueue(E item){

        add(item);

    }

    /*
     * ======================================================
     *                END OF QUEUE FUNCTIONS
     * ======================================================
     */

    /*
     * ======================================================
     *                   STACK FUNCTIONS
     * ======================================================
     */

    /**
     * Push an item onto the stack
     * Functionally the same as append. Will push into place if
     * sortOnwards is set
     * 
     * @param item The item to be pushed
     */
    public void push (E item) {
        
        add(item);

    }

    /**
     * Pop an item off of the stack
     * @return The item popped off of the stack
     */
    public E pop () {

        if(endPointer != 0){
            E temp = (E) array[endPointer-1];
            if (temp instanceof UserNull){
                temp = null;
            }
            endPointer--;
            array[endPointer] = null;
            return temp;
        } else if (endPointer == 0){
            throw new ArrayIndexOutOfBoundsException("Nothing to pop");
        } else {
            throw new RuntimeException("Cannot pop, check code");
        }
    }

    /*
     * ======================================================
     *                END OF STACK FUNCTIONS
     * ======================================================
     */

    /*
     * ======================================================
     *              MULTI PURPOSE FUNCTIONS
     * ======================================================
     */

    /**
     * Peek at the next item in the queue or stack depending on the enum
     * @param acting Enum of type HowToFunction being either QUEUE or STACK
     * @return The next item in the queue
     */
    public E peek(HowToFunction acting) {

        if (length() > 0){
            switch (acting){
                case STACK:
                    return (E) array[endPointer-1];
                case QUEUE: 
                    return (E) array[startPointer];
                default:
                    throw new IllegalArgumentException("Not an enum allowed with peek. Choose STACK/QUEUE");
            } 
        }
        else {
            throw new NullPointerException("No item to peek");
        }
    }

    /*
     * ======================================================
     *            END OF MULTI PURPOSE FUNCTIONS
     * ======================================================
     */

    /*
     * ============================================
     *          GENERAL PURPOSE FUNCTIONS
     * ============================================
     */

    /**
     * Alias for length method.
     * Size typically used by stack
     * @return The size of the stack
     */
    public int size(){
        return length();
    }

    /**
     * Returns the length of a given sequence
     * 
     * @return Int length of sequence
     */
    public int length(){
        // Length can be worked out via the difference between the startIndex and endIndex with removed taken off to track non existant terms.
        return endPointer - startPointer;
    }

    /**
     * Clears the array. New arrays size is initialSize
     */
    public void clear(){
        array =  new Object[initialSize];
        startPointer = 0;
        endPointer = 0;
    }

    /**
     * Alias for clear method.
     * Empty typically used with stack
     */
    public void empty() {
        clear();
    }

    /**
     * Are these two sequences identical. Objects must be at the same index and same length
     * 
     * !! IMPORTANT
     *      Does not check the "settings" of the Sequence.
     *      Not Checked:
     *          - Comparator
     *          - EnforceSort
     *          - CustomGrowthRate
     *          - InitialSize
     *      Checked:
     *          - Same length
     *          - Same terms at each index
     * 
     * @return boolean true if same / false if not
     */
    @Override
    public boolean equals(Object seq){

        if (this == seq){ // If memory address equal they are the same
            return true;
        } else if (seq instanceof Sequence<?>){

            // Convert to correct type
            Sequence<E> comp = (Sequence<E>) seq;

            // Do they have the same length
            if (length() != comp.length()){
                return false;
            } else { 
                for (int i = 0; i < length(); i++){ // Check each term now
                    if (get(i).equals(comp.get(i))){
                        continue; // Equal so can continue
                    } else { // If one term incorrect then not the same
                        return false;
                    }
                }
            }

            
        } else {
            // If they aren't the same memory location or same type then false
            return false;
        }
        
        // If we make it through they are equal
        return true;
    }

    /**
     * Custom toString method for generics.
     * 
     * @return String form of the array [term1, term2, term3]
     */
    @Override
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
     * Returns true if item is contained and false if not
     * 
     * !!! Objects must override equals to use this function properly
     *     otherwise objects will be compared on object referrence
     * 
     * @param value The value to check for
     * @return true if yes, false if no
     */
    public boolean contains(E value){

        if (value == null){
            value = (E) new UserNull<E>();
        }

        for (int i = startPointer; i < endPointer; i++){
            if (value.equals(array[i])){ // If same value then return true
                return true;
            } else {
                continue;
            }
        }

        // If get here then value not contained
        return false;
    }

    /**
     * Returns the first index of a given value in the array
     * 
     * !!! Objects must override equals to use this function properly
     *     otherwise objects will be compared on object referrence
     * 
     * @param value The value to check for
     * @return The index if item is there or null if it isnt
     */
    public Integer firstIndexOf(E value){

        if (value == null){
            value = (E) new UserNull<E>();
        }

        // Search over all terms and check their equivalence to ours
        for (int i = startPointer; i < endPointer; i++){
            if (value.equals(array[i])){ // If same value then return true
                return i;
            } else {
                continue;
            }
        }

        // If we get here no terms match
        return null;
    }

    /**
     * Returns all indexes of a given element
     * 
     * !!! Objects must override equals to use this function properly
     *     otherwise objects will be compared on object referrence
     * 
     * @param value The value to search for
     * @return A Integer[] containing all the indexes that the value occurs at
     */
    public int[] allIndexesOf(E value){

        if (value == null){
            value = (E) new UserNull<E>();
        }

        // Create an array of length total
        int[] loopList = new int[length()];

        // Each time we add one we increment this
        int count = 0;

        // Search over all terms and check their equivalence to ours
        for (int i = startPointer; i < endPointer; i++){
            if (value.equals(array[i])){ // If same value then add to array and increment
                loopList[count] = i;
                count++;
            } else {
                continue;
            }
        }

        if (count > 0){
            // Create a new array being the size we have found
            int[] returnable = new int[count];
            // Copy over to the new array of correct size
            System.arraycopy(loopList, 0, returnable, 0, count);
            return returnable;
        }

        return null;
    }

    /*
     * ============================================
     *      END OF GENERAL PURPOSE FUNCTIONS
     * ============================================
     */

    /*
     * SYSTEM FUNCTIONS
     */

    /**
     * Add an item to the end of the array correctly
     * @param item
     */
    private void addToEnd(E item){
        if (endPointer == array.length - expansionAdjustmentValue){ // We must expand the array
            // Expand the array
            reformat(true);

            // Now add the new item
            array[endPointer] = item;
            endPointer++;

        } else { // No need to expand array
            array[endPointer] = item;
            endPointer++;
        }
    }

    /**
     * Will expand the array whilst also copying over the existing terms
     * @return
     */
    private Object[] expandCopy(){
        // Check for minimum growth requirement of 1
        int length = array.length;
        int newSize = (int) Math.ceil(length*growthRate);

        if (newSize <= length){
            newSize = newSize + minumumExpansion;
        }

        // New array is created by expanding by growthFactor
        int newLen = (int) Math.round(newSize);
        Object[] newArray =  new Object[newLen];

        // Now loop over and add all items to the larger list
        for (int i = startPointer; i < endPointer; i++){
            newArray[i] = array[i];
        }

        return newArray;
    }

     /**
     * Create and return a blank array of new size
     * @return Array of new size
     */
    private Object[] expand(){
        // Check for minimum growth requirement of 1
        int length = array.length;
        int newSize = (int) Math.ceil(length*growthRate);

        if (newSize <= length){
            newSize = newSize + minumumExpansion;
        }
        // New array is created by expanding by growthFactor
        int newLen = (int) Math.round(newSize);
        Object[] newArray =  new Object[newLen];
        return newArray;
    }

    /**
     * Expand the array by the given growth factor.
     * Check for null items and remove
     * @param expand Boolean telling whether to expand or not
     */
    private void reformat(boolean  expand){

        Object[] newArray;

        if (expand == true){
            // If we choose to expand run the function expand
            newArray = expand();
        } else {
            // If we dont want to expand the array but just re-format
            newArray =  new Object[array.length];
        }
        

        // Now loop over and add all items to the larger list
        int secondCount = 0; // This counts from index 0 for the new array
        for (int i = startPointer; i < endPointer; i++){ // This counts from index startPointer for the old array

            // Get current term from the array
            E curTerm = (E) array[i];

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
    }

    /**
     * If a gap exists in the array close it
     * @param gapIndex The index of the gap
     */
    private void closeGap(int gapIndex) {
        for (int i = gapIndex; i < endPointer; i++){
            array[i] = array[i+1];
        }
        --endPointer; // TODO: Make an exception for when we are shifting all from startPointer to endPointer
    }

    /*
     * END OF SYSTEM FUNCTIONS
     */

    /*
     * ================================================
     *             EXPERT USER FUNCTIONS
     * ================================================
     */

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
    @Override
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
    public void setGrowthRate(double newRate){
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

    /**
     * Retrieve the sub array
     * @return
     */
    public Object[] getSubArray(){
        return array;
    }

    /**
     * Allow the setting of the sub array through passing in parameters
     * @param startPointer
     * @param endPointer
     * @param array
     */
    public void setSubArray(int startPointer, int endPointer, Object[] array){
        this.startPointer = startPointer;
        this.endPointer = endPointer;
        this.array = array;
    }

    /**
     * Export the array
     * @return
     */
    public Object[] exportArray() {
        return array;
    }

    /**
     * Import an array
     * @param array
     */
    public void importArray(Object[] array){
        this.array = array;
    }

    /**
     * Export the context
     * @return
     */
    public SequenceContext<E> exportContext() {
        return new SequenceContext<E>(startPointer, endPointer, initialSize, growthRate, enforceSort, defaultComparator, minumumExpansion, SequenceState.UNSORTED);
    }

    /**
     * Import the context
     * @param context
     */
    public void importContext(SequenceContext<E> context){
        this.startPointer = context.startPointer;
        this.endPointer = context.endPointer;
        this.initialSize = context.initialSize;
        this.growthRate = context.growthRate;
        this.enforceSort = context.enforceSort;
        this.defaultComparator = context.comparator;
        this.minumumExpansion = context.minimumExpansion;
    }

    /**
     * Convert the internal index to an external index.
     * 
     * external index = what user sees
     * internal index = the index in the sub array
     * @param internalIndex
     * @return
     */
    private int convertToExternalIndex(int internalIndex){
        return internalIndex - startPointer;
    }

    /*
     * ================================================
     *          END OF EXPERT USER FUNCTIONS
     * ================================================
     */

    public Iterator<E> iterator() {
        return new SequenceIterator();
    }

    class SequenceIterator implements Iterator<E> {
            
        private int index = startPointer;
        private Integer prev;

        @Override
        public boolean hasNext() {
            return index < endPointer;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new IndexOutOfBoundsException("No more elements to iterate over");
            }
            prev = index;
            return (E) array[index++];
        }

        @Override
        public void remove() {
            if (prev == null) {
                throw new IllegalStateException("Cannot remove element before calling next()");
            }
            UnsortedDefaultSequence.this.remove(convertToExternalIndex(--index));
        }
    }

}
