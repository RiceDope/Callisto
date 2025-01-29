package com.rwalker;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Entry class for the Sequence data structure. Manages swapping between all strategies of the class
 * 
 * @author Rhys Walker
 * @version 13/01/2025
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import com.rwalker.sequenceStrategies.DefaultSequenceStrategy;
import com.rwalker.sequenceStrategies.RingBufferSequenceStrategy;
import com.rwalker.sequenceStrategies.SequenceContext;
import com.rwalker.sequenceStrategies.SequenceHeuristic;
import com.rwalker.sequenceStrategies.SequenceStrategies;
import com.rwalker.sequenceStrategies.SequenceStrategy;

@SuppressWarnings("unchecked")
public class Sequence<E> implements Iterable<E>, ModernCollections<E> {
    
    private SequenceContext<E> seqCon = new SequenceContext<E>();
    private SequenceStrategy<E> strat;
    private SequenceStrategies currentStrat = SequenceStrategies.DEFAULT;
    private Map<SequenceStrategies, Class<? extends SequenceStrategy<E>>> strategies = new Map<>();
    private SequenceHeuristic heuristic = new SequenceHeuristic();

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

    // FROM HERE

    /**
     * Default constructor using default growth rate and size values with a selected strategy
     */
    public Sequence(SequenceStrategies strategy){
        
        // Default values are used
        currentStrat = strategy;

        setupStrategies();

    }

    /**
     * Allows for specification of just the initial size of the array
     * @param size Int initial size of the array
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
     * Allows for the specification of a comparator
     * @param comparator Default comparator to be used in sort
     * @param strategy The strategy to use
     */
    public Sequence(Comparator<E> comparator, SequenceStrategies strategy){
        seqCon.comparator = comparator;
        currentStrat = strategy;
        setupStrategies();
    }

    /**
     * Allows for specification of initial array size and a comparator
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
     * Allows for specification of just the growth rate
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
     * Allows for specification of growth rate and default comparator
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
     * Allows for specification of both starting size and growth rate
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
     * Allows for specification of starting size, growth rate and a comparator
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


    // method signatures

    /**
     * Sets up the strategies for the sequence
     * 
     * The starting strategy is based on what the user specifies in the constructor or the default strategy
     */
    private void setupStrategies(){

        // Put all of the strategies into the map
        strategies.put(SequenceStrategies.DEFAULT, (Class<? extends SequenceStrategy<E>>) (Class<?>) DefaultSequenceStrategy.class);
        strategies.put(SequenceStrategies.RINGBUFFER, (Class<? extends SequenceStrategy<E>>) (Class<?>) RingBufferSequenceStrategy.class);

        // Get the default strategy for now
        try {
            strat = (SequenceStrategy<E>) strategies.get(currentStrat).getDeclaredConstructor(SequenceContext.class).newInstance(seqCon);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize strategy", e);
        }
    }

    /**
     * Allows for the swapping of strategies
     * @param newStrat
     */
    private void swapStrategies(SequenceStrategies newStrat){
        Object[] array = strat.exportArray();
        SequenceContext<E> context = strat.exportContext();
        try {
            strat = (SequenceStrategy<E>) strategies.get(newStrat).getDeclaredConstructor(SequenceContext.class).newInstance(context);
            strat.importArray(array);
            strat.importContext(context);
            currentStrat = newStrat;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize strategy", e);
        }

    }

    /**
     * Force the sequence to check if it is optimal to swap
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

    public boolean add(E item){
        heuristic.incrementArrayOps();
        strat.add(item);
        return true;
    }

    public void addAll(ModernCollections<E> toApp){
        heuristic.incrementArrayOps();
        strat.addAll(toApp);
    }

    public void addAll(Collection<E> toApp){
        heuristic.incrementArrayOps();
        strat.addAll(toApp);
    }

    public void replace(int index, E element){
        heuristic.incrementArrayOps();
        strat.replace(index, element);
    }

    public void remove(int index){
        heuristic.incrementArrayOps();
        strat.remove(index);
    }

    public E get(int index){
        heuristic.incrementArrayOps();
        return strat.get(index);
    }

    public void sort(){
        strat.sort();
    }

    public void sort(Comparator<E> comparator){
        strat.sort(comparator);
    }

    public void sortOnwards(){
        strat.sortOnwards();
    }

    public void sortOnwards(Comparator<E> comparator){
        strat.sortOnwards(comparator);
    }

    public Sequence<E> sortCopy(Comparator<E> comparator){
        return strat.sortCopy(comparator);
    }

    public void stopSorting(){
        strat.stopSorting();
    }

    public void setComparator(Comparator<E> comparator){
        strat.setComparator(comparator);
    }

    public boolean isEmpty(){
        heuristic.incrementQueueOps();
        return strat.isEmpty();
    }

    public E dequeue(){
        heuristic.incrementQueueOps();
        return strat.dequeue();
    }

    public void enqueue(E element){
        heuristic.incrementQueueOps();
        strat.enqueue(element);
    }

    public void push(E element){
        heuristic.incrementStackOps();
        strat.push(element);
    }

    public E pop(){
        heuristic.incrementStackOps();
        return strat.pop();
    }

    public E peek(HowToFunction acting){
        if (acting == HowToFunction.QUEUE){
            heuristic.incrementQueueOps();
        } else if (acting == HowToFunction.STACK){
            heuristic.incrementStackOps();
        }
        return strat.peek(acting);
    }

    public int size(){
        return strat.size();
    }

    public int length(){
        return strat.length();
    }

    public void clear(){
        strat.clear();
    }

    public void empty(){
        strat.empty();
    }

    public boolean equals(Object seq){
        return strat.equals(seq);
    }

    public String toString(){
        return strat.toString();
    }

    public boolean contains(E value){
        return strat.contains(value);
    }

    public Integer firstIndexOf(E value){
        return strat.firstIndexOf(value);
    }

    public int[] allIndexesOf(E value){
        return strat.allIndexesOf(value);
    }

    public int rawLength(){
        return strat.rawLength();
    }

    public String rawString(){
        return strat.rawString();
    }

    public void setGrowthRate(double growthRate){
        strat.setGrowthRate(growthRate);
    }

    public double getGrowthRate(){
        return seqCon.growthRate;
    }

    public Object[] getSubArray(){
        return strat.getSubArray();
    }

    public void setSubArray(int startPointer, int endPointer, Object[] array){
        strat.setSubArray(startPointer, endPointer, array);
    }

    public Iterator<E> iterator(){
        return strat.iterator();
    }
}
