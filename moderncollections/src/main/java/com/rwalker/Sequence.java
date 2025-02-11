package com.rwalker;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Entry class for the Sequence data structure. Manages swapping between all strategies of the class. It can maintain both sorted and unsorted data.
 * 
 * Implements the LinearSequence interface
 * 
 * @author Rhys Walker
 * @since 30-09-2024
 */

import java.util.Comparator;
import java.util.Iterator;

import com.rwalker.sequenceStrategies.DefaultSequence;
import com.rwalker.sequenceStrategies.RingBufferSequenceStrategy;
import com.rwalker.sequenceStrategies.SequenceContext;
import com.rwalker.sequenceStrategies.SequenceHeuristic;
import com.rwalker.sequenceStrategies.SortControl;
import com.rwalker.sequenceStrategies.SubArrayManipulation;
import com.rwalker.sequenceStrategies.SequenceState;
import com.rwalker.sequenceStrategies.SequenceStrategies;
import com.rwalker.sequenceStrategies.SequenceStrategy;
import com.rwalker.sequenceStrategies.SequenceStrategyControl;
import com.rwalker.sequenceStrategies.DefaultStrategy.UnsortedDefaultSequence;

@SuppressWarnings("unchecked")
public class Sequence<E> implements Iterable<E>, SequenceStrategy<E> {
    
    private SequenceContext<E> seqCon = new SequenceContext<E>(); // Current context of the sequence
    private SequenceStrategyControl<E> strat; // The specific strategy that is being ran at the time
    private SequenceStrategies currentStrat = SequenceStrategies.DEFAULT; // The "name" of the current strategy
    private Map<SequenceStrategies, Class<? extends SequenceStrategyControl<E>>> strategies = new Map<>(); // A Map mapping the "names" to their respective classes
    private SequenceHeuristic heuristic = new SequenceHeuristic(); // The heuristic for the sequence

    /**
     * Default constructor using default growth rate and size values
     */
    public Sequence(){
        
        // Default values are used

        setupStrategies();

    }

    /**
     * Allows for specification of just the initial size of the array
     * @param size Int initial size of the array
     */
    public Sequence(int size){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }

        setupStrategies();
    }

    /**
     * Allows for the specification of a comparator
     * @param comparator Default comparator to be used in sort
     */
    public Sequence(Comparator<E> comparator){
        seqCon.comparator = comparator;

        setupStrategies();
    }

    /**
     * Allows for specification of initial array size and a comparator
     * @param size Int initial size of the array
     * @param comparator Default comparator to be used in sort
     */
    public Sequence(int size, Comparator<E> comparator){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        seqCon.comparator = comparator;

        setupStrategies();
    }

    /**
     * Allows for specification of just the growth rate
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(double customGrowthRate){
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }

        setupStrategies();
    }

    /**
     * Allows for specification of growth rate and default comparator
     * @param growthRate Double initial growth rate of the array
     * @param comparator Default comparator to be used in sort
     */
    public Sequence(double customGrowthRate, Comparator<E> comparator){
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        seqCon.comparator = comparator;

        setupStrategies();
    }

    /**
     * Allows for specification of both starting size and growth rate
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(int size, double customGrowthRate){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }

        setupStrategies();
    }

    /**
     * Allows for specification of starting size, growth rate and a comparator
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     * @param comparator Default comparator to be used in sort
     */
    public Sequence(int size, double customGrowthRate, Comparator<E> comparator){

        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        seqCon.comparator = comparator;

        setupStrategies();
    }

    /**
     * Constructor that allows specification of the strategy being used
     * @param strategy The strategy to use from SequenceStrategies.STRAT
     */
    public Sequence(SequenceStrategies strategy){
        
        // Default values are used
        currentStrat = strategy;

        setupStrategies();

    }

    /**
     * Allows for specification of the initial size and the strategy to start with
     * @param size Int initial size of the array
     * @param strategy The strategy to use from SequenceStrategies.STRAT
     */
    public Sequence(int size, SequenceStrategies strategy){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        currentStrat = strategy;
        setupStrategies();
    }

    /**
     * Allows for the specification of a comparator and the strategy to use
     * @param comparator Default comparator to be used in sort
     * @param strategy The strategy to use
     */
    public Sequence(Comparator<E> comparator, SequenceStrategies strategy){
        seqCon.comparator = comparator;
        currentStrat = strategy;
        setupStrategies();
    }

    /**
     * Allows for specification of initial array size, Comparator and the strategy to use
     * @param size Int initial size of the array
     * @param comparator Default comparator to be used in sort
     * @param strategy The strategy to use
     */
    public Sequence(int size, Comparator<E> comparator, SequenceStrategies strategy){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        seqCon.comparator = comparator;
        currentStrat = strategy;
        setupStrategies();
    }

    /**
     * Allows for specification of the growth rate and the strategy to use
     * @param growthRate Double initial growth rate of the array
     * @param strategy The strategy to use
     */
    public Sequence(double customGrowthRate, SequenceStrategies strategy){
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        currentStrat = strategy;
        setupStrategies();
    }

    /**
     * Allows for specification of growth rate, default comparator and the strategy to use
     * @param growthRate Double initial growth rate of the array
     * @param comparator Default comparator to be used in sort
     * @param strategy The strategy to use
     */
    public Sequence(double customGrowthRate, Comparator<E> comparator, SequenceStrategies strategy){
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        seqCon.comparator = comparator;
        currentStrat = strategy;
        setupStrategies();
    }

    /**
     * Allows for specification of starting size, growth rate and the strategy to use
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     * @param strategy The strategy to use
     */
    public Sequence(int size, double customGrowthRate, SequenceStrategies strategy){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        currentStrat = strategy;
        setupStrategies();
    }

    /**
     * Allows for specification of starting size, growth rate, default comparator and the strategy to use
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     * @param comparator Default comparator to be used in sort
     * @param strategy The strategy to use
     */
    public Sequence(int size, double customGrowthRate, Comparator<E> comparator, SequenceStrategies strategy){

        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        seqCon.comparator = comparator;
        currentStrat = strategy;
        setupStrategies();
    }

     /**
     * Allows for specification of just the initial size of the array
     * @param size Int initial size of the array
     */
    public Sequence(int size, Comparator<E> comparator, SequenceState state){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }

        seqCon.currentState = state;

        setupStrategies();
    }

    /**
     * Allows for the specification of a comparator
     * @param comparator Default comparator to be used in sort
     */
    public Sequence(Comparator<E> comparator, SequenceState state){
        seqCon.comparator = comparator;

        seqCon.currentState = state;

        setupStrategies();
    }

    /**
     * Allows for specification of just the growth rate
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(double customGrowthRate, Comparator<E> comparator, SequenceState state){
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }

        seqCon.comparator = comparator;
        seqCon.currentState = state;

        setupStrategies();
    }

    /**
     * Allows for specification of both starting size and growth rate
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     */
    public Sequence(int size, double customGrowthRate, Comparator<E> comparator, SequenceState state){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }

        seqCon.comparator = comparator;
        seqCon.currentState = state;

        setupStrategies();
    }

    /**
     * Constructor that allows specification of the strategy being used
     * @param strategy The strategy to use from SequenceStrategies.STRAT
     */
    public Sequence(SequenceStrategies strategy, Comparator<E> comparator, SequenceState state){
        
        // Default values are used
        currentStrat = strategy;

        seqCon.comparator = comparator;
        seqCon.currentState = state;

        setupStrategies();

    }

    /**
     * Allows for specification of the initial size and the strategy to start with
     * @param size Int initial size of the array
     * @param strategy The strategy to use from SequenceStrategies.STRAT
     */
    public Sequence(int size, SequenceStrategies strategy, Comparator<E> comparator, SequenceState state){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        currentStrat = strategy;

        seqCon.comparator = comparator;
        seqCon.currentState = state;

        setupStrategies();
    }

    /**
     * Allows for the specification of a comparator and the strategy to use
     * @param comparator Default comparator to be used in sort
     * @param strategy The strategy to use
     */
    public Sequence(Comparator<E> comparator, SequenceStrategies strategy, SequenceState state){
        seqCon.comparator = comparator;
        seqCon.currentState = state;
        currentStrat = strategy;
        setupStrategies();
    }

    /**
     * Allows for specification of initial array size, Comparator and the strategy to use
     * @param size Int initial size of the array
     * @param comparator Default comparator to be used in sort
     * @param strategy The strategy to use
     */
    public Sequence(int size, Comparator<E> comparator, SequenceStrategies strategy, SequenceState state){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        seqCon.comparator = comparator;
        seqCon.currentState = state;
        currentStrat = strategy;
        setupStrategies();
    }

    /**
     * Allows for specification of the growth rate and the strategy to use
     * @param growthRate Double initial growth rate of the array
     * @param strategy The strategy to use
     */
    public Sequence(double customGrowthRate, SequenceStrategies strategy, Comparator<E> comparator, SequenceState state){
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        currentStrat = strategy;
        seqCon.comparator = comparator;
        seqCon.currentState = state;
        setupStrategies();
    }

    /**
     * Allows for specification of growth rate, default comparator and the strategy to use
     * @param growthRate Double initial growth rate of the array
     * @param comparator Default comparator to be used in sort
     * @param strategy The strategy to use
     */
    public Sequence(double customGrowthRate, Comparator<E> comparator, SequenceStrategies strategy, SequenceState state){
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        seqCon.comparator = comparator;
        seqCon.currentState = state;
        currentStrat = strategy;
        setupStrategies();
    }

    /**
     * Allows for specification of starting size, growth rate and the strategy to use
     * @param size Int starting size of the array
     * @param growthRate Double initial growth rate of the array
     * @param strategy The strategy to use
     */
    public Sequence(int size, double customGrowthRate, SequenceStrategies strategy, Comparator<E> comparator, SequenceState state){
        if (size  > 0){
            seqCon.initialSize = size;
        } else {
            throw new NegativeArraySizeException("Cannot use size of 0 or less");
        }
        if (customGrowthRate >  1.0){
            seqCon.growthRate = customGrowthRate;
        } else {
            throw new IllegalArgumentException("Cannot have a growth rate of 1 or less than 1");
        }
        seqCon.comparator = comparator;
        seqCon.currentState = state;
        currentStrat = strategy;
        setupStrategies();
    }

    // method signatures

    /**
     * Sets up the strategies for the sequence (Runs right after all constructors)
     * 
     * The starting strategy is based on what the user specifies in the constructor or the default strategy
     */
    private void setupStrategies(){

        // Put all of the strategies into the map
        strategies.put(SequenceStrategies.DEFAULT, (Class<? extends SequenceStrategyControl<E>>) (Class<?>) DefaultSequence.class);
        strategies.put(SequenceStrategies.RINGBUFFER, (Class<? extends SequenceStrategyControl<E>>) (Class<?>) RingBufferSequenceStrategy.class);

        // Get the default strategy for now
        try {
            strat = (SequenceStrategyControl<E>) strategies.get(currentStrat).getDeclaredConstructor(SequenceContext.class).newInstance(seqCon);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize strategy", e);
        }
    }

    /**
     * Correctly deals with swapping between strategies using a SequenceContext object and exporting the subArray into a common form
     * @param newStrat The strategy to swap over to
     */
    private void swapStrategies(SequenceStrategies newStrat){
        Object[] array = strat.exportArray();
        SequenceContext<E> context = strat.exportContext();
        try {
            strat = (SequenceStrategyControl<E>) strategies.get(newStrat).getDeclaredConstructor(SequenceContext.class).newInstance(context);
            strat.importArray(array);
            strat.importContext(context);
            currentStrat = newStrat;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize strategy", e);
        }

    }

    /**
     * Force the sequence to check if it is optimal to swap. If it is optimal it will swap.
     * @return boolean True if we have swapped, false if we have not
     */
    public boolean checkSwap() {
        if (heuristic.getBestStrategy() != currentStrat){
            swapStrategies(heuristic.getBestStrategy());
            return true; // We have swapped so return true
        }
        return false; // We have not swapped so return false
    }

    /**
     * Insert an item into the sequence at a given position
     * @param index The position to insert into
     * @param element The element to insert
     */
    public void insert(int index, E element){
        heuristic.incrementArrayOps();
        strat.insert(index, element);
    }

    /**
     * Get the name of the strategy from SequenceStrategies
     * @return Enum of the current strategies name
     */
    public SequenceStrategies getname(){
        return strat.getname();
    }

    public SequenceState getstate(){
        return strat.getstate();
    }

    /**
     * Add an item to the sequence
     * @param item The item to add
     * @return boolean This context will always be true (For support of LinearSequence Interface)
     */
    public boolean add(E item){
        heuristic.incrementArrayOps();
        strat.add(item);
        return true;
    }

    /**
     * Add all items from a ModernCollections object to the sequence
     * @param toApp The ModernCollections object to add from
     * @return boolean This context will always be true (For support of LinearSequence Interface)
     */
    public boolean addAll(ModernCollections<E> toApp){
        heuristic.incrementArrayOps();
        strat.addAll(toApp);
        return true; // So that it is compatible with the interface (Functionally pointless)
    }

    /**
     * Add all items from a Collection object to the sequence
     * @param toApp The Collection object to add from
     * @return boolean This context will always be true (For support of LinearSequence Interface)
     */
    public boolean addAll(Collection<E> toApp){
        heuristic.incrementArrayOps();
        strat.addAll(toApp);
        return true; // So that it is compatible with the interface (Functionally pointless)
    }

    /**
     * Replace an item in the Sequence
     * @param index The index to replace
     * @param element The element to replace with
     */
    public void replace(int index, E element){
        heuristic.incrementArrayOps();
        strat.replace(index, element);
    }

    /**
     * Remove an item from the sequence
     * @param index The index to remove
     */
    public void remove(int index){
        heuristic.incrementArrayOps();
        strat.remove(index);
    }

    /**
     * Get an item from the sequence
     * @param index The index to get from
     * @return E The item at the index
     */
    public E get(int index){
        heuristic.incrementArrayOps();
        return strat.get(index);
    }

    /**
     * Sort the Sequence based on the default Comparator given
     */
    public void sort(){
        strat.sort();
    }

    /**
     * Sort the Sequence based on a custom Comparator. The Comparator will then become the defaultComparator
     * @param comparator The Comparator to sort by
     */
    public void sort(Comparator<E> comparator){
        strat.sort(comparator);
    }

    /**
     * Keep the array in sorted order based on the default Comparator.
     * Calling this will sort the array. There is no need to call sort first.
     */
    public void sortOnwards(){
        strat.sortOnwards();
    }

    /**
     * Keep the array in sorted order based on the given Comparator.
     * @param comparator Comparator to become new default Comparator
     */
    public void sortOnwards(Comparator<E> comparator){
        strat.sortOnwards(comparator);
    }

    /**
     * Get a sorted copy of the Sequence based on the Comparator specified
     * @param comparator The comparator to use
     * @return Sequence<E> A new Sequence with the sorted data
     */
    public Sequence<E> sortCopy(Comparator<E> comparator){
        return strat.sortCopy(comparator);
    }

    /**
     * Get a sorted copy of the Sequence based on the default Comparator
     * @return Sequence<E> A new Sequence with the sorted data
     */
    public Sequence<E> sortCopy() {
        return strat.sortCopy();
    }

    /**
     * Stop keeping the array in sorted order and allow for unsorted data to be added
     * This will not revert the array to previous state, it will just stop inserting data into the correct location
     */
    public void stopSorting(){
        strat.stopSorting();
    }

    /**
     * Sets the default comparator for the Sequence
     * @param comparator The Comparator to use
     */
    public void setComparator(Comparator<E> comparator){
        strat.setComparator(comparator);
    }

    /**
     * Is the current Sequence empty
     * @return boolean True if empty, false if not
     */
    public boolean isEmpty(){
        heuristic.incrementQueueOps();
        return strat.isEmpty();
    }

    /**
     * Dequeue an item from the queue
     * @return E The item dequeued
     */
    public E dequeue(){
        heuristic.incrementQueueOps();
        return strat.dequeue();
    }

    /**
     * Enqueue an item into the queue
     * @param element The item to enqueue
     */
    public void enqueue(E element){
        heuristic.incrementQueueOps();
        strat.enqueue(element);
    }

    /**
     * Push an item onto the stack
     * @param element The item to push
     */
    public void push(E element){
        heuristic.incrementStackOps();
        strat.push(element);
    }

    /**
     * Pop an item from the stack
     * @return E The item popped
     */
    public E pop(){
        heuristic.incrementStackOps();
        return strat.pop();
    }

    /**
     * Peek at the top of the stack or the front of the queue
     * @param acting HowToFunction.(QUEUE or STACK)
     * @return E The item at the top of the stack or the front of the queue
     */
    public E peek(HowToFunction acting){
        if (acting == HowToFunction.QUEUE){
            heuristic.incrementQueueOps();
        } else if (acting == HowToFunction.STACK){
            heuristic.incrementStackOps();
        }
        return strat.peek(acting);
    }

    /**
     * Get the size of the Sequence
     * @return int The size of the Sequence
     */
    public int size(){
        return strat.size();
    }

    /**
     * Get the length of the Sequence
     * @return int The length of the Sequence
     */
    public int length(){
        return strat.length();
    }

    /**
     * Clear the Sequence
     */
    public void clear(){
        strat.clear();
    }

    /**
     * Empty the Sequence
     */
    public void empty(){
        strat.empty();
    }

    /**
     * Does this Sequence equal another Sequence
     * Checks if we are the same object first, if not the same object then:
     * - Checks if the other object is a Sequence
     * - Checks if the other Sequence is the same length
     * - Checks if the other Sequence has the same elements in the same order
     * 
     * @param seq The Sequence to compare to
     */
    public boolean equals(Object seq){
        return strat.equals(seq);
    }

    /**
     * Get the string representation of the Sequence
     * 
     * example = [10, 20, 30, 40, 50]
     * 
     * @return String The string representation of the Sequence
     */
    public String toString(){
        return strat.toString();
    }

    /**
     * Does the Sequence contain a value
     * Maintaining a sorted sequence will allow for quicker lookup via binary search
     * @param value The value to check for
     * @return boolean True if the value is in the Sequence, false if not
     */
    public boolean contains(E value){
        return strat.contains(value);
    }

    /**
     * Get the first index of a value in the Sequence
     * Maintaining a sorted sequence will allow for quicker lookup via binary search
     * @param value The value to find
     * @return Integer The index of the first occurrence of the value
     */
    public Integer firstIndexOf(E value){
        return strat.firstIndexOf(value);
    }

    /**
     * Get the last index of a value in the Sequence
     * No performance increase for using a sorted sequence
     * @param value The value to find
     * @return Integer The index of the last occurrence of the value
     */
    public int[] allIndexesOf(E value){
        return strat.allIndexesOf(value);
    }

    /**
     * Get the raw length of the array. This is the length of the underlying subArray
     * @return int length of the sub array
     */
    public int rawLength(){
        return strat.rawLength();
    }

    /**
     * Get the raw string of the array. This is the string representation of the underlying subArray
     * @return String string representation of the sub array
     */
    public String rawString(){
        return strat.rawString();
    }

    /**
     * Set the growth rate of the Sequence
     * @param growthRate The growth rate to set
     */
    public void setGrowthRate(double growthRate){
        strat.setGrowthRate(growthRate);
    }

    /**
     * Get the growth rate of the Sequence
     * @return double The growth rate of the Sequence
     */
    public double getGrowthRate(){
        return seqCon.growthRate;
    }

    /**
     * Get the subarray of the Sequence in exactly the same form as the underlying array
     * @return Object[] array
     */
    public Object[] getSubArray(){
        return strat.getSubArray();
    }

    /**
     * Set the subarray of the Sequence in exactly the same form as the underlying array
     * 
     * array = [10, 20, 30, null, null, null]
     * sp = 0
     * ep = 3 // ep must always point to the null after the last element
     * 
     * IMPORTANT:
     *      Check the form your Sequence is in with strategy when using this.
     *      If you attempt to set a RingBuffer sub array in a Default strategy it will break the Sequence
     *      If you are in the correct form then use make sure it is in the form
     *      
     *      array = [40, 50, null, null, null, 10, 20, 30]
     *      sp = 5
     *      ep = 2
     * 
     * @param startPointer The start pointer of elements in the array
     * @param endPointer The end pointer of elements in the array
     * @param array The array to be set
     */
    public void setSubArray(int startPointer, int endPointer, Object[] array){
        strat.setSubArray(startPointer, endPointer, array);
    }

    /**
     * The iterator for the Sequence
     * @return Iterator<E> for the Sequence
     */
    public Iterator<E> iterator(){
        return strat.iterator();
    }
}
