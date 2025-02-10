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
    
    // General Sequence interaction methods
    SequenceStrategies getname();
    SequenceState getstate();
    void insert(int index, E element);
    void add(E item);
    void addAll(ModernCollections<E> toApp);
    void addAll(Collection<E> toApp);
    void replace(int index, E element);
    void remove(int index);
    E get (int index);
    void sort();
    void sort(Comparator<E> comparator);
    Sequence<E> sortCopy();
    Sequence<E> sortCopy(Comparator<E> comparator);
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
    int rawLength(); //TODO: THINKING ABOUT REMOVE
    String rawString();  //TODO: THINKING ABOUT REMOVE
    void setGrowthRate(double growthRate);
    double getGrowthRate();
    Object[] getSubArray(); // TODO: REMOVE
    void setSubArray(int startPointer, int endPointer, Object[] array); // TODO: REMOVE
    Iterator<E> iterator();

}
