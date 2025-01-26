package com.rwalker.sequenceStrategies;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import com.rwalker.HowToFunction;
import com.rwalker.ModernCollections;
import com.rwalker.Sequence;

/**
 * Contract that all strategies must adhere to
 * 
 * @author Rhys Walker
 * @version 13/01/2025
 */

public interface SequenceStrategy<E>{

    // Used in order to change between strategies
    Object[] exportArray();
    void importArray(Object[] array);
    SequenceContext<E> exportContext();
    void importContext(SequenceContext<E> context);
    SequenceStrategies getname();
    
    // General Sequence interaction methods
    void insert(int index, E element);
    void append(E item);
    void appendAll(ModernCollections<E> toApp);
    void appendAll(Collection<E> toApp);
    void replace(int index, E element);
    void remove(int index);
    E get (int index);
    void sort();
    void sort(Comparator<E> comparator);
    void sortOnwards();
    void sortOnwards(Comparator<E> comp);
    Sequence<E> sortCopy();
    Sequence<E> sortCopy(Comparator<E> comparator);
    void stopSorting();
    void setComparator(Comparator<E> comparator);
    boolean isEmpty();
    E dequeue();
    void enqueue(E element);
    void push(E element);
    E pop();
    E peek(HowToFunction acting);
    int size();
    int length();
    void clear();
    void empty();
    boolean equals(Object seq);
    String toString();
    boolean contains(E value);
    Integer firstIndexOf(E value);
    int[] allIndexesOf(E value);
    int rawLength();
    String rawString();
    void setGrowthRate(double growthRate);
    double getGrowthRate();
    Object[] getSubArray();
    void setSubArray(int startPointer, int endPointer, Object[] array);
    Iterator<E> iterator();

}
