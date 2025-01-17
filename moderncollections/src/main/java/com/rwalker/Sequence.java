package com.rwalker;

/**
 * Entry class for the Sequence data structure. Manages swapping between all strategies of the class
 * 
 * @author Rhys Walker
 * @version 13/01/2025
 */

import java.util.Comparator;
import java.util.Iterator;

import com.rwalker.sequenceStrategies.DefaultSequenceStrategy;
import com.rwalker.sequenceStrategies.SequenceContext;
import com.rwalker.sequenceStrategies.SequenceStrategy;

public class Sequence<E> implements Iterable<E>, ModernCollections<E> {
    
    private SequenceContext<E> seqCon = new SequenceContext<E>();
    private SequenceStrategy<E> strat;

    /**
     * Default constructor using default growth rate and size values
     */
    public Sequence(){
        
        // Default values are used

        postConstructor();

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

        postConstructor();
    }

    /**
     * Allows for the specification of a comparator
     * @param comparator Default comparator to be used in sort
     */
    public Sequence(Comparator<E> comparator){
        seqCon.comparator = comparator;

        postConstructor();
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

        postConstructor();
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

        postConstructor();
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

        postConstructor();
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

        postConstructor();
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

        postConstructor();
    }


    // method signatures

    private void postConstructor(){
        strat = new DefaultSequenceStrategy<>(seqCon);
    }

    public void insert(int index, E element){
        strat.insert(index, element);
    }

    public void append(E item){
        strat.append(item);
    }

    public void appendAll(Sequence<E> toApp){
        strat.appendAll(toApp);
    }

    public void replace(int index, E element){
        strat.replace(index, element);
    }

    public void remove(int index){
        strat.remove(index);
    }

    public E get(int index){
        return strat.get(index);
    }

    public void sort(){
        strat.sort();
    }

    public void sortOnwards(){
        strat.sortOnwards();
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
        return strat.isEmpty();
    }

    public E dequeue(){
        return strat.dequeue();
    }

    public void enqueue(E element){
        strat.enqueue(element);
    }

    public void push(E element){
        strat.push(element);
    }

    public E pop(){
        return strat.pop();
    }

    public E peek(HowToFunction acting){
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

    // TODO: IMPLEMENT ITERATOR IN THIS CLASS
}
