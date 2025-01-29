package com.rwalker.sequenceStrategies;

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
/**
 * This is a ring buffer like strategy the difference from the default strategy
 * is that the endPointer can be smaller than the startPointer. This is optimal for
 * a queue/stack.
 */

// TODO: when startPointer == array.length

@SuppressWarnings({"unchecked"})
public class RingBufferSequenceStrategy<E> implements Iterable<E>, SequenceStrategy<E> {
    
    private int endPointer;
    private int startPointer;
    private int initialSize;
    private Object[] array;
    private double growthRate;
    private boolean enforceSort;
    private Comparator<E> defaultComparator;
    private int minumumExpansion;
    private com.rwalker.sequenceStrategies.SequenceStrategies name = SequenceStrategies.RINGBUFFER;
    
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

    public SequenceStrategies getname() {
        return name;
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
    public void add(E element) {
        if (!enforceSort) {
            addToEnd(element);
        } else if (element != null) {
            if (endPointer > startPointer) { // We are just a regular array
                int index = BinarySearch.findInsertionIndex((E[]) array, startPointer, endPointer, element, defaultComparator, size());
                int appendIndex = index-startPointer; // Convert from subarray index
                insert(appendIndex, element);
            } else { // We are using a ringBuffer
                int insertionIndex = BinarySearch.findInsertionIndexBufferLinearSearch((E[]) array, startPointer, endPointer, element, defaultComparator, size());
                insertionIndex = convertPureArrToBuffer(insertionIndex);
                insert(insertionIndex, element);
            }
        }
        else {
            element = (E) new UserNull<E>();
            addToEnd(element);
        }
    }

    public int rawLength() {
        return array.length;
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
            } else { // Becomes just a normal insert
                if (length() < array.length - 1) { // We have enough space to insert
                    shift(realIndex);
                    array[realIndex] = element;
                } else { // We must expand the array
                    expandArray();
                    shift(realIndex);
                    array[realIndex] = element;
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
    public void addAll(ModernCollections<E> toApp) {
        Iterator<E> iter = toApp.iterator();
        while (iter.hasNext()) {
            add(iter.next());
        }
    }

    /**
     * Appends all elements from the given collection to the array
     * @param toApp The collection to append
     */
    public void addAll(Collection<E> toApp) {
        for (E element : toApp) {
            add(element);
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
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size());
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
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size());
        }
        E term = (E) array[calculateIndex(index)];
        if (term instanceof UserNull) {
            term = null;
        }
        return term;
    }

    /**
     * Dequeues an item from the array
     * @return
     */
    public E dequeue() {
        if (isEmpty()) {
            throw new NullPointerException("Cannot dequeue from an empty sequence");
        }
        E element = (E) array[startPointer];
        if (element instanceof UserNull) {
            element = null;
        }
        array[startPointer] = null;
        startPointer++;
        return element;
    }

    /**
     * Enqueues an item to the array
     * @param element
     */
    public void enqueue(E element) {
        add(element);
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
        add(element);
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
        if (element instanceof UserNull) {
            element = null;
        }
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
            throw new NullPointerException("Cannot peek from an empty sequence");
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

    public void sort(Comparator<E> comparator) {
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

    /**
     * Sort the array acording to the given comparator
     */
    public void sortOnwards() { //TODO: Add check for defaultComp being set
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
    public Sequence<E> sortCopy(){
        if (defaultComparator != null){
            try {
                // Sort the array
                Object[] sortedArr = UserNullSort.sort(array, defaultComparator, startPointer, endPointer, false);
                Sequence<E> sortedSeq = new Sequence<E>(array.length, growthRate, defaultComparator); // Instantiate sequence with same settings
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
     * Does the Sequence contain the term given
     * @return
     */
    public boolean contains(E element) {

        if (element == null) {
            element = (E) new UserNull<E>();
        }

        if (!enforceSort) { // Normal search
            for (int i = startPointer; i != endPointer; i++) {
                if (i == array.length) {
                    i = 0;
                }
                if (array[i].equals(element)) {
                    return true;
                }
            }
        } else if (enforceSort && !(element instanceof UserNull)){ // Binary search as long as not null
            int index = BinarySearch.findInsertionIndex((E[]) array, startPointer, endPointer, element, defaultComparator, size());
            if (index < endPointer && array[index].equals(element)) {
                return true;
            }
        } else { // Nulls are appended to the end of a sorted sequence
            if (array[endPointer-1] instanceof UserNull){
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * Returns the first index of the given element
     * @param element
     * @return
     */
    public Integer firstIndexOf(E element) {
        if (!enforceSort) { // Normal search
            for (int i = startPointer; i != endPointer; i++) {
                if (i == array.length) {
                    i = 0;
                }
                if (array[i].equals(element)) {
                    return i;
                }
            }
        } else { // Binary search
            int index = BinarySearch.findInsertionIndex((E[]) array, startPointer, endPointer, element, defaultComparator, size());
            if (index < endPointer && array[index].equals(element)) {
                return index;
            }
        }

        return null;
    }

    /**
     * Returns all indexes of the given element
     * @param element
     * @return
     */
    public int[] allIndexesOf(E element) {
        int[] indexes = new int[size()];
        int index = 0;
        for (int i = startPointer; i != endPointer; i++) {
            if (i == array.length) {
                i = 0;
            }
            if (array[i].equals(element)) {
                indexes[index] = i;
                index++;
            }
        }

        // Now we must make a new array the size of what we have found
        if (index > 0) {
            int[] newIndexes = new int[index];
            System.arraycopy(indexes, 0, newIndexes, 0, index);
            return newIndexes;
        }
        
        return null;
    }

    /**
     * Get the growth rate Sequence is using
     * @return Double growth rate
     */
    public double getGrowthRate(){
        return growthRate;
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
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size());
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
     * Exports the array in a common format
     * @return The Object array
     */
    public Object[] exportArray() {
        Object[] arr = new Object[size()+1];
        int index = 0;
        for (Object obj : array) {
            if (obj != null) {
                arr[index] = obj;
                index++;
            } 
        }

        return arr;
    }

    /**
     * Imports the array into the sequence
     * @param array
     */
    public void importArray(Object[] array) {
        this.array = array;
    }

    /**
     * Exports the context of the sequence
     * @return The context of the sequence
     */
    public SequenceContext<E> exportContext() {
        // Adjusts the pointers to be in the correct positions
        int returnableEndPointer = size();
        int startPointer = 0;
        return new SequenceContext<E>(startPointer, returnableEndPointer, initialSize, growthRate, enforceSort, defaultComparator, minumumExpansion);
    }

    /**
     * Imports the context of the sequence
     * @param context
     */
    public void importContext(SequenceContext<E> context) {
        this.startPointer = context.startPointer;
        this.endPointer = context.endPointer;
        this.initialSize = context.initialSize;
        this.growthRate = context.growthRate;
        this.enforceSort = context.enforceSort;
        this.defaultComparator = context.comparator;
        this.minumumExpansion = context.minimumExpansion;
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
        endPointer--;
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

        if (length() == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (endPointer < startPointer) {
            // We are in an inversion
            int currentIndex = startPointer;
            for (int i = 0; i < size(); i++){
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
