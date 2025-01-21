package com.rwalker.sequenceStrategies;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import com.rwalker.BinarySearch;
import com.rwalker.HowToFunction;
import com.rwalker.Sequence;
import com.rwalker.UserNull;
import com.rwalker.UserNullSort;
/**
 * This is a ring buffer like strategy the difference from the default strategy
 * is that the endPointer can be smaller than the startPointer. This is optimal for
 * a queue/stack.
 */

 // TODO: implements SequenceStrategy<E>
@SuppressWarnings({"unchecked", "unused"})
public class RingBufferSequenceStrategy<E> implements Iterable<E> {
    
    private int endPointer;
    private int startPointer;
    private int initialSize;
    private Object[] array;
    private double growthRate;
    private boolean enforceSort;
    private Comparator<E> defaultComparator;
    private int minumumExpansion;
    
    public RingBufferSequenceStrategy(SequenceContext<E> context) {
        this.endPointer = context.endPointer;
        this.startPointer = context.startPointer;
        this.initialSize = context.initialSize;
        this.growthRate = context.growthRate;
        this.enforceSort = context.enforceSort;
        this.defaultComparator = context.comparator;
        this.minumumExpansion = context.minimumExpansion;

        array = new Object[initialSize];
    }

    /**
     * Returns the size of the array
     * @return The size of the array
     */
    public int size() {
        if (endPointer < startPointer) { // Inversion calculation
            return (array.length - startPointer) + endPointer;
        } else { // Regular calculation
            return endPointer - startPointer;
        }
    }

    /**
     * Alias for size
     * @return The length of the array
     */
    public int length() {
        return size();
    }

    /**
     * Adds an element to the array
     * @param element
     */
    public void append(E element) {
        if (!enforceSort) {
            addToEnd(element);
        } else if (endPointer > startPointer) { // We are just a regular array
            int index = BinarySearch.findInsertionIndex((E[]) array, startPointer, endPointer, element, defaultComparator, size());
            int appendIndex = index-startPointer; // Convert from subarray index
            insert(appendIndex, element);
        } else { // We are using a ringBuffer
            int insertionIndex = BinarySearch.findInsertionIndexBufferLinearSearch((E[]) array, startPointer, endPointer, element, defaultComparator, size());
            insertionIndex = convertPureArrToBuffer(insertionIndex);
            insert(insertionIndex, element);
        }
    }

    /**
     * Convert an index from a regular sequential array into a ringBuffer implementation
     * @param index
     * @return
     */
    private int convertPureArrToBuffer(int index) {
        if (endPointer < startPointer) { // Inversion
            if (index > startPointer && index > endPointer) { // We are in the regular part of the array
                return index-startPointer;
            } else if (index < startPointer && index <= endPointer) {
                return (array.length-startPointer)+index;
            }
        } else {
            return index-startPointer;
        }
        
        // Unreachable code
        return 0;
    }

    /**
     * Clears the array
     */
    public void clear() {
        array = new Object[initialSize];
        startPointer = 0;
        endPointer = 0;
    }

    /**
     * Empties the array
     */
    public void empty() {
        clear();
    }



    /**
     * Insert an item into the array at the specific index
     * @param index The index to insert the element at
     * @param element The element to insert
     */
    public void insert(int index, E element){
        if (element == null) {
            element = (E) new UserNull<E>();
        }

        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size());
        }

        int realIndex = calculateIndex(index);

        if (realIndex == endPointer) { // Inserting at end
            addToEnd(element);

        } else if (realIndex == startPointer) { // Inserting at start

            if (startPointer != 0){ // If we have space before

                if (array[startPointer-1] == null) { // We can decrement startPointer
                    startPointer--;
                    array[startPointer] = element;
                    if (endPointer == startPointer) { // We have decremented with space but not have no space left
                        expandArray();
                    }
                } else { // We must expand if no space before
                    expandArray();
                    shift(startPointer);
                }
            }
        } else { // We are inserting into the array in a central location
            if (length() < array.length - 1) { // We have enough space to insert
                shift(realIndex);
                array[realIndex] = element;
            } else { // We must expand the array
                expandArray();
                shift(realIndex);
                array[realIndex] = element;
            }
        }
    }

    /**
     * Shift all terms from the indexFrom by one to the right(Ensuring there is space)
     * @param indexFrom
     */
    //TODO: Potential edge case where we shift and fill the array but we don't detect the need to expand
    private void shift(int indexFrom) {

        if (!(length() < array.length - 1)) { // We have enough space to shift
            expandArray();
        }

        int currentIndex = endPointer;

        while(currentIndex != indexFrom) {
            if (currentIndex == 0){
                array[currentIndex] = array[array.length-1];
                currentIndex = array.length-1;
            } else {
                array[currentIndex] = array[currentIndex-1];
                currentIndex--;
            }  
        }

        endPointer++;
    }

    /**
     * Appends all elements from the given sequence to the array
     * @param toApp The sequence to append
     */
    // TODO: Potential speed increase by finding the size of toApp and if expansion is necessary do it once
    // Or can avoid regular append each time by pre allocating the space
    public void appendAll(Sequence<E> toApp) {
        for (E element : toApp) {
            append(element);
        }
    }

    /**
     * Replaces the element at the given index with the given element
     * @param index The index to replace
     * @param element The element to replace with
     */
    public void replace(int index, E element){
        if (element == null) {
            element = (E) new UserNull<E>();
        }
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size());
        }
        array[calculateIndex(index)] = element;
    }



    /**
     * Gets the element at the given index
     * @param index The index to get the element from
     * @return The element at the given index
     */
    public E get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size());
        }
        return (E) array[calculateIndex(index)];
    }

    /**
     * Dequeues an item from the array
     * @return
     */
    public E dequeue() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Cannot dequeue from an empty sequence");
        }
        E element = (E) array[startPointer];
        array[startPointer] = null;
        startPointer++;
        return element;
    }

    /**
     * Enqueues an item to the array
     * @param element
     */
    public void enqueue(E element) {
        append(element);
    }

    /**
     * Checks if the array is empty
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Pushes an element to the array
     * @param element
     */
    public void push(E element) {
        append(element);
    }

    /**
     * Pops an element from the array
     * @return
     */
    public E pop() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Cannot pop from an empty sequence");
        }
        E element = (E) array[endPointer-1];
        array[endPointer-1] = null;
        endPointer--;
        return element;
    }

    /**
     * Peeks at the element at the given index
     * @param acting
     * @return
     */
    public E peek(HowToFunction acting) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Cannot peek from an empty sequence");
        }

        if (acting == HowToFunction.QUEUE) { // NO EXTRA CODE FOR QUEUE
            return (E) array[startPointer];
        } else { // INVERSION FOR STACK
            if (endPointer > 0) {
                return (E) array[endPointer-1];
            } else {
                return (E) array[array.length-1];
            }
        }
    }

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
     * Sort the array acording to the given comparator
     */
    public void sortOnwards() {
        if (!(length() < 0) && !(length() == 1)){ // Only sort when we have something to not nothing or 1 item
            sort();
        }
        setEnforceSort(true);
    }

    /**
     * Sort the array and then enforce sort using the given comparator
     * @param comp A comparator that will then become the default comparator used
     */
    public void sortOnwards(Comparator<E> comp) {
        this.defaultComparator = comp;
        sortOnwards();
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
            Sequence<E> sortedSeq = new Sequence<E>(array.length, growthRate, comparator); // Instantiate sequence with same settings
            sortedSeq.setSubArray(startPointer, endPointer, sortedArr); // Set the sub array
            return sortedSeq;
        } catch (Exception e){ // Throw error for either not implementing Comparable or smth else
            System.err.println(e);
            throw new UnknownError("Comparator either not valid or cannot be compared");
        }
    }

    /**
     * Stop sorting the array automatically
     */
    public void stopSorting() {
        setEnforceSort(false);
    }

    /**
     * Set the comparator to be used
     * @param comparator
     */
    public void setComparator (Comparator<E> comparator){
        defaultComparator = comparator;
    }

    /**
     * Calculates the correct index for the array based on the pointers
     * @param index
     * @return
     */
    private int calculateIndex(int index) {
        // Are we in an inversion
        if (endPointer < startPointer) {
            if (index < (array.length - startPointer)) {
                return startPointer + index;
            } else {
                return index - (array.length - startPointer);
            }
        } else {
            return startPointer + index;
        }
    }

    /**
     * Removes an element from the array. Handles buffer representation
     * @param index
     */
    public void remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size());
        }
        int realIndex = calculateIndex(index);
        array[realIndex] = null;
        
        if (realIndex == startPointer) {
            startPointer++;
        } else if (realIndex == endPointer-1) {
            endPointer--;
        } else {
            fillGap(realIndex);
        }
    }

    /**
     * Fill the gap that is present after removal
     * @param realIndex
     */
    private void fillGap(int realIndex) {
        // Go from realIndex to endPointer
        // Make sure to loop around if we reach the end of the array
        int currentIndex = realIndex;
        while (currentIndex != endPointer) {
            if (currentIndex == array.length) {
                currentIndex = 0;
            }
            if (currentIndex == endPointer) {
                break;
            }
            array[currentIndex] = array[currentIndex+1];
            currentIndex++;
        }
    }

    /**
     * Adds an element to the array
     * Correctly handles insertion in an inversion
     * @param element
     */
    private void addToEnd(E element) {

        // NORMAL:

        // OPT1 - Can we just append as normal
        // OPT2 - Can we wrap around (Enter inversion)
        // OPT3 - Do we have to expand (Enter expansion)

        // INVERSION:

        // OPT1 - Can we append as normal
        // OPT2 - Do we need to expand (Enter expansion, Exit Inversion)

        // Edge case: When we have inverted and then fill the array endPointer is no longer less than startPointer
        // Try catch deals with this edge case correctly. Discovers the inversion fill before it can occur.

        // Deal with expansion edge case

        if (!(endPointer < startPointer)){ // We are working in normal space
            if (endPointer < array.length-1){ // If we are smaller than the array then just add
                addAtEndPointer(element);
            } else { // We are at the array limit. What can we do
                if (startPointer > 0) { // There is space at the beginning to add the element
                    addAtEndPointer(element);
                    endPointer = 0;
                } else { // Just expand do not enter an inversion
                    expandArray();
                    addAtEndPointer(element);
                }
            }
        } else { // We are working in inversion space
            if (endPointer == startPointer) { // Then we must expand and exit the inversion
                expandArray();
                addAtEndPointer(element);
            } else { // We can just add the element
                addAtEndPointer(element);
            }
        }

        try {
            if (endPointer == startPointer && array[startPointer-1] != null) {
                expandArray();
            }
        } catch (Exception e) {
            // Do nothing
        }
    }

    /**
     * Adds an element at endPointer regardless of the endPointers current position
     * @param element
     */
    private void addAtEndPointer(E element) {
        if (element == null){
            element = (E) new UserNull<E>();
        }
        array[endPointer] = element;
        endPointer++;
    }

    /**
     * Expand the array by the given factor and refactor in order to maintain
     */
    private void expandArray() {
        Object[] newArr = new Object[(int) Math.ceil(array.length*growthRate)];

        int index = startPointer;
        int newIndex = 0;

        // Array is full need to save before continuing
        if (startPointer == endPointer) {
            newArr[newIndex] = array[index];
            index++;
            newIndex++;
        }

        while (index != endPointer) {
            if (index == array.length) {
                index = 0;
            }
            newArr[newIndex] = array[index];
            index++;
            newIndex++;
        }

        array = newArr;
        startPointer = 0;
        endPointer = newIndex;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (endPointer < startPointer) {
            // We are in an inversion
            int currentIndex = startPointer;
            for (int i = 0; i < size(); i++){
                sb.append(" ");
                sb.append(array[currentIndex]);
                currentIndex++;
                if (currentIndex == array.length) {
                    currentIndex = 0;
                }
                sb.append(", ");
            }
        } else {
            // We are in normal space
            for (int i = startPointer; i < endPointer; i++){
                sb.append(array[i]);
                sb.append(", ");
            }
        }

        sb.replace(sb.length()-2, sb.length(), ""); // Remove the last comma
        sb.append("]");
        return sb.toString();
    }

    private void setEnforceSort(boolean enforceSort) {
        this.enforceSort = enforceSort;
    }

    @Override
    public boolean equals(Object seq) {
        if (seq instanceof Sequence) {
            Sequence<E> sequence = (Sequence<E>) seq;
            if (sequence.size() != size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (!sequence.get(i).equals(get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public String rawString() {
        return Arrays.toString(array);
    }

    public String getPointers() {
        return "Start: " + startPointer + " End: " + endPointer;
    }

    class SequenceIterator implements Iterator<E> {

        private int currentIndex;

        public SequenceIterator() {
            currentIndex = startPointer;
        }

        @Override
        public boolean hasNext() {
            return currentIndex != endPointer;
        }

        @Override
        public E next() {
            E element = (E) array[currentIndex];
            currentIndex++;
            if (currentIndex == array.length) {
                currentIndex = 0;
            }
            return element;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new SequenceIterator();
    }
}
