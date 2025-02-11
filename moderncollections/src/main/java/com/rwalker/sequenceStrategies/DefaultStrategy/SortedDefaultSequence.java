package com.rwalker.sequenceStrategies.DefaultStrategy;

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

/**
 * The same as the default strategy but exclusively for sorted Sequence.
 * Nulls in this implementation are placed at the end. Any insertions of a null will be tracked
 * 
 * @author Rhys Walker
 * @since 06-02-2025
 */

 @SuppressWarnings("unchecked")
public class SortedDefaultSequence<E> implements DefaultSequenceStrategy<E>{
    
    private int endPointer;
    private int startPointer;
    private int initialSize;
    private Object[] array;
    private double growthRate;
    private Comparator<E> defaultComparator;
    private int minumumExpansion;
    private SequenceStrategies name = SequenceStrategies.DEFAULT;
    private SequenceState state = SequenceState.SORTED;

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
            throw new IllegalStateException("Comparator cannot be null for sorted Strategy");
        }
    }

    public SequenceStrategies getname(){
        return name;
    }

    public SequenceState getstate(){
        return state;
    }

    /**
     * Method to insert an element into the array
     * @param index The index to insert into
     * @param value The value to insert
     */
    public void insert(int index, E value) {

        manipulateArray();

        int subIndex = index+startPointer;

        // First are we inside the array bounds
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (value == null) {
            // Are we within the null section of the array
            if (subIndex > endPointer-nullsInserted) {
                // We are in the right location
                nullsInserted++;
                makeGap(subIndex);
                array[subIndex] = (E) new UserNull<E>();
                return;
            } else if (subIndex == endPointer-nullsInserted) { // Last not null point
                // We are in the right location
                nullsInserted++;
                makeGap(subIndex);
                array[subIndex] = (E) new UserNull<E>();
                return;
            } else {
                // We are in the wrong location
                throw new IllegalArgumentException("Cannot insert null in the middle of the array");
            }
        }

        // We can insert here as its the end but we must check if we are correct
        if (subIndex == endPointer-nullsInserted) {
            if (defaultComparator.compare((E)array[subIndex-1], value) < 0) {
                insertAtLocation(subIndex, value);
                return;
            } else {
                throw new IllegalArgumentException("Attempting to insert at end but not in the correct order");
            }
        }

        // We are not null and are inside of array bounds
        if (subIndex < endPointer - nullsInserted) {
            // We are not in the null section

            // If we are at position 0 just check that index
            if (subIndex == startPointer) {

                // If we are less than or equal to
                if (defaultComparator.compare((E) array[subIndex], value) > 0 || defaultComparator.compare((E) array[subIndex], value) == 0) {
                    // We are in the right location
                    insertAtLocation(subIndex, value);
                    return;
                } else {
                    // We are in the wrong location
                    throw new IllegalArgumentException("Inserting at the beginning of array not consistent with Compartor");
                }
            }

            // If we are greater than lower smaller than index or equal to the index then insert
            if (defaultComparator.compare((E) array[subIndex] , value) > 0 && defaultComparator.compare((E) array[subIndex-1], value) < 0 || defaultComparator.compare((E) array[subIndex], value) == 0) {
                // We are in the right location
                insertAtLocation(subIndex, value);
                return;
            } else {
                // We are in the wrong location
                throw new IllegalArgumentException("Insertion index not consistent with sorting requirement");

            }
        }

        throw new IllegalArgumentException("Index not allowed");

    }

    /**
     * Adds an element into the array
     * @param element
     */
    public boolean add(E element) {

        manipulateArray();

        // If we are null just add it to the end
        if (element == null) {
            nullsInserted++;
            element = (E) new UserNull<E>();
            insertAtLocation(endPointer, element);
            return true;
        }

        if (endPointer == startPointer) {
            insertAtLocation(endPointer, element);
            return true;
        }

        // Adjusted so that we don't search the null space pointlessly
        int index = BinarySearch.findInsertionIndex((E[]) array, startPointer, endPointer-nullsInserted, element, defaultComparator, endPointer-startPointer-nullsInserted);

        insertAtLocation(index, element);

        return true;
    }

    /**
     * Adds all terms to the collection
     * @param collection The collection to add from
     */
    public boolean addAll(ModernCollections<E> collection) {
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            add(it.next());
        }

        return true;
    }

    /**
     * Adds all terms to the collection
     * @param collection The collection to add from
     */
    public boolean addAll(Collection<E> collection) {
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            add(it.next());
        }

        return true;
    }

    /**
     * Replaces the element at the index with the value (Unsupported for this strategy)
     * The value cannot be null
     * The index must be within the non null section anything that is within the null section will throw an error
     * @param index
     * @param value
     */
    public void replace(int index, E value) {

        int subIndex = index+startPointer;

        // If we are replacing a value with a null then we should throw an error
        if (value == null) {
            throw new IllegalArgumentException("Cannot replace a value with a null while sorting");
        }

        // Are we in the right bounds
        if (subIndex < startPointer || subIndex >= endPointer-nullsInserted) {
            throw new IndexOutOfBoundsException("Index out of bounds (May be within null section)");
        }

        // If replacing top end or low end then we need an exception (We must be smaller than the first)
        if (subIndex == startPointer) {

            if (size() == 1) {
                array[subIndex] = value;
                return;
            }

            if (defaultComparator.compare((E) array[subIndex+1], value) > 0) {
                array[subIndex] = value;
                return;
            } else {
                throw new IllegalArgumentException("Replacement index not consistent with sorting requirement");
            }
        }

        // Same as above but for endPointer-1
        if (subIndex == endPointer-1-nullsInserted) {
            if (defaultComparator.compare((E) array[subIndex-1], value) < 0) {
                array[subIndex] = value;
                return;
            } else {
                throw new IllegalArgumentException("Replacement index not consistent with sorting requirement");
            }
        }

        // If we are replacing we need to check it is in the right location
        if (defaultComparator.compare((E) array[subIndex-1], value) < 0 && defaultComparator.compare((E) array[subIndex+1], value) > 0) {
            array[subIndex] = value;
        } else {
            throw new IllegalArgumentException("Replacement index not consistent with sorting requirement");
        }

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


    public void sortOnwards() {

        // We are already sorted do nothing here. If we provide a new comparator we will sort differently

    }

    /**
     * Will change the default comparator and sort the array another way
     * @param comparator The comparator to sort by
     */
    public void sortOnwards(Comparator<E> comparator) {
        sort(comparator);
    }

    /**
     * Returns a copy of the current array
     * @return
     */
    public Sequence<E> sortCopy() {
        Sequence<E> copy = new Sequence<>(array.length, growthRate, SequenceStrategies.DEFAULT, defaultComparator, SequenceState.SORTED);
        copy.setSubArray(startPointer, endPointer, array);
        return copy;
    }

    public Sequence<E> sortCopy(Comparator<E> comparator) {
        Sequence<E> copy = new Sequence<>(array.length, growthRate, SequenceStrategies.DEFAULT, defaultComparator, SequenceState.SORTED);
        copy.setSubArray(startPointer, endPointer, array);
        copy.sort(comparator);
        return copy;
    }

    public void stopSorting() {
        throw new UnsupportedOperationException("Stop sorting not implemented yet. Will be once sorted strategies finalised");
    }

    public void setComparator(Comparator<E> comparator) {
        sort(comparator); // Sort deals with comparator setting
    }

    public boolean isEmpty() {
        if (length() == 0){
            return true;
        } else {
            return false;
        }
    }

    public E dequeue() {

        if (length() > 0) {
            E element = get(0);
            remove(0);

            if (element instanceof UserNull) {
                nullsInserted--;
                element = null;
            }

            return element;
        } else {
            throw new NullPointerException("No items to dequeue");
        }
    }

    public void enqueue(E item) {
        add(item);
    }

    public void push (E item) {
        add(item);
    }

    public E pop() {
        if (length() > 0) {
            E element = get(length()-1);
            remove(length()-1);

            if (element instanceof UserNull) {
                nullsInserted--;
                element = null;
            }

            return element;
        } else {
            throw new NullPointerException("No items to pop");
        }
    }

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

    public int size() {
        return length();
    }

    public int length() {
        return endPointer-startPointer;
    }

    public void clear() {
        array = new Object[initialSize];
        startPointer = 0;
        endPointer = 0;
    }

    public void empty() {
        clear();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sequence) {
            Sequence<E> seq = (Sequence<E>) obj;
            if (seq.length() != length()) {
                return false;
            }

            for (int i = 0; i < length(); i++) {
                if (!seq.get(i).equals(get(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean contains (E element) {

        if (element == null && nullsInserted > 0) {
            return true;
        }
        
        // Search for its location using binary search
        int index = BinarySearch.findInsertionIndex((E[]) array, startPointer, endPointer-nullsInserted, element, defaultComparator, endPointer-startPointer-nullsInserted);

        if (array[index].equals(element)){
            return true;
        }

        return false;
    }

    public Integer firstIndexOf(E element) {
        int index = BinarySearch.findInsertionIndex((E[]) array, startPointer, endPointer-nullsInserted, element, defaultComparator, endPointer-startPointer-nullsInserted);

        // We may have multiple elements of the same value check that below is different

        if (index != startPointer) {

            if (!array[index].equals(element)){
                return null;
            }

            while(array[index-1].equals(element)) {
                index--;
                if (index == startPointer) {
                    break;
                }
            }

            return index-startPointer;
        } else {
            if (array[index].equals(element)) {
                return index-startPointer;
            }
        }

        return null;
    }

    public int[] allIndexesOf(E element) {

        // Locate the first Index of the element
        Integer firstIndex = firstIndexOf(element);

        if (firstIndex != null) {
            int[] indexes = new int[length()];

            indexes[0] = firstIndex;

            int count = 1;

            for (int i = firstIndex+1; i < length(); i++) {
                if (array[i].equals(element)) {
                    indexes[count] = i;
                    count++;
                }
            }

            return Arrays.copyOf(indexes, count);
        }

        return null;
    }

    public int rawLength() {
        return array.length;
    }

    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public Object[] getSubArray() {
        return array;
    }


    public void setSubArray(int startPointer, int endPointer, Object[] array) {
        this.array = array;
        this.startPointer = startPointer;
        this.endPointer = endPointer;
    }

    public Object[] exportArray() {
        return array;
    }

    public void importArray(Object[] array) {
        this.array = array;
    }

    public SequenceContext<E> exportContext() {
        return new SequenceContext<>(startPointer, endPointer, initialSize, growthRate, true, defaultComparator, minumumExpansion, SequenceState.SORTED);
    }

    public void importContext(SequenceContext<E> context) {
        this.startPointer = context.startPointer;
        this.endPointer = context.endPointer;
        this.initialSize = context.initialSize;
        this.growthRate = context.growthRate;
        this.defaultComparator = context.comparator;
        this.minumumExpansion = context.minimumExpansion;
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

    public Iterator<E> iterator() {
        return new SortedSequenceIterator();
    }

    class SortedSequenceIterator implements Iterator<E> {
            
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
            int removeIndex = index-startPointer;
            SortedDefaultSequence.this.remove(--removeIndex);
        }
    }
}
