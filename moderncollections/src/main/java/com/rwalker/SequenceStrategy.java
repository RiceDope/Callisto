package com.rwalker;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Contract that all strategies must adhere to
 * 
 * enforceSort
 * addToEnd
 * reformat
 */

public interface SequenceStrategy<E>{

    // Used in order to change between strategies
    Object[] exportArray();
    void importArray(Object[] array);
    SequenceContext<E> exportContect();
    void importContext(SequenceContext<E> context);
    
    void insert(int index, E element);
    void append(E item);
    void appendAll(Sequence<E> toApp);
    void replace(int index, E element);
    void remove(int index);
    E get (int index);
    void sort();
    void sortOnwards();
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
