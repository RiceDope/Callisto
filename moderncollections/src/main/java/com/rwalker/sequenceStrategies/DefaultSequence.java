package com.rwalker.sequenceStrategies;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import com.rwalker.HowToFunction;
import com.rwalker.Map;
import com.rwalker.ModernCollections;
import com.rwalker.Sequence;
import com.rwalker.sequenceStrategies.DefaultStrategy.DefaultSequenceStrategy;
import com.rwalker.sequenceStrategies.DefaultStrategy.SortedDefaultSequence;
import com.rwalker.sequenceStrategies.DefaultStrategy.UnsortedDefaultSequence;

/**
 * This class is the entry point for the DefaultSequence strategy
 * Will maintain the two different strategies (Sorted Unsorted)
 */

public class DefaultSequence<E> implements SortControl<E>, SequenceStrategyControl<E>{

    private SequenceContext<E> seqCon = new SequenceContext<E>(); // Current context of the sequence
    private DefaultSequenceStrategy<E> strat; // The specific strategy that is being ran at the time
    private SequenceState currentState = SequenceState.UNSORTED; // The "name" of the current strategy
    private SequenceStrategies name = SequenceStrategies.DEFAULT; // The "name" of the strategy
    private Map<SequenceState, Class<? extends DefaultSequenceStrategy<E>>> strategies = new Map<>(); // A Map mapping the "names" to their respective classes
    
    /**
     * Use default values
     */
    public DefaultSequence() {

        postConstructor();

    }

    public DefaultSequence(SequenceContext<E> seqCon) {
        this.seqCon = seqCon;

        currentState = seqCon.currentState;

        postConstructor();
    }

    public void postConstructor() {
        strategies.put(SequenceState.UNSORTED, (Class<? extends DefaultSequenceStrategy<E>>) (Class<?>) UnsortedDefaultSequence.class);
        strategies.put(SequenceState.SORTED, (Class<? extends DefaultSequenceStrategy<E>>) (Class<?>) SortedDefaultSequence.class);

        // Get the default strategy for now
        try {
            strat = (DefaultSequenceStrategy<E>) strategies.get(currentState).getDeclaredConstructor(SequenceContext.class).newInstance(seqCon);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize strategy", e);
        }
    }

    public SequenceStrategies getname() {
        return name;
    }

    public SequenceState getstate() {
        return currentState;
    }

    public void insert(int index, E value) {
        strat.insert(index, value);
    }

    public boolean add(E value) {
        strat.add(value);
        return true;
    }

    public boolean addAll(ModernCollections<E> collection) {
        strat.addAll(collection);
        return true;
    }

    public boolean addAll(Collection<E> collection) {
        strat.addAll(collection);
        return true;
    }

    public void replace(int index, E value) {
        strat.replace(index, value);
    }

    public void sort() {
        strat.sort();
    }

    public void sort(Comparator<E> comparator) {
        strat.sort(comparator);
    }

    public void sortOnwards() {
        
        switch(currentState) {
            case UNSORTED:
                // Must now swap over to sorted
                swapToState(SequenceState.SORTED);
            case SORTED:
                // Already sorted do nothing
        }

    }

    private void swapToState(SequenceState state) {
        // Swap to the new state

        // Ensure sorting has occured before becoming sorted
        if (state == SequenceState.SORTED) {
            strat.sort();
        }

        try {
            SequenceContext<E> curCon = strat.exportContext();
            Object[] curArray = strat.exportArray();
            strat = strategies.get(state).getDeclaredConstructor(SequenceContext.class).newInstance(strat.exportContext());
            strat.importArray(curArray);
            strat.importContext(curCon);
            currentState = state;
        } catch (IllegalStateException e) {
            System.out.println("THIS BIT RUNNING");
            throw new IllegalStateException("Cannot swap to sorted when we have no comparator set");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize strategy", e);
        }
    }

    private void swapToState(SequenceState state, Comparator<E> comparator) {
        // Swap to the new state

        // Ensure sorting has occured before becoming sorted
        if (state == SequenceState.SORTED) {
            strat.sort();
        }
        
        try {
            SequenceContext<E> curCon = strat.exportContext();
            curCon.comparator = comparator;
            Object[] curArray = strat.exportArray();

            strat = strategies.get(state).getDeclaredConstructor(SequenceContext.class).newInstance(strat.exportContext());
            strat.importArray(curArray);
            strat.importContext(curCon);
            currentState = state;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize strategy", e);
        }
    }

    public void sortOnwards(Comparator<E> comparator) {

        switch(currentState) {
            case UNSORTED:
                // Must now swap over to sorted
                swapToState(SequenceState.SORTED, comparator);
            case SORTED:
                // Already sorted do nothing
        }

    }

    public Sequence<E> sortCopy() {
        return strat.sortCopy();
    }

    public Sequence<E> sortCopy(Comparator<E> comparator) {
        return strat.sortCopy(comparator);
    }

    public void stopSorting() {
        
        switch (currentState) {
            case UNSORTED:
                // Already unsorted do nothing
            case SORTED:
                swapToState(SequenceState.UNSORTED);
        }

    }

    public void setComparator(Comparator<E> comparator) {
        strat.setComparator(comparator);
    }

    public boolean isEmpty() {
        return strat.isEmpty();
    }

    public E dequeue() {
        return strat.dequeue();
    }

    public void enqueue(E value) {
        strat.enqueue(value);
    }

    public void push(E value) {
        strat.push(value);
    }

    public E pop() {
        return strat.pop();
    }

    public E peek(HowToFunction acting) {
        return strat.peek(acting);
    }

    public int size() {
        return strat.size();
    }

    public int length() {
        return strat.length();
    }

    public void clear() {
        strat.clear();
    }

    public void empty() {
        strat.empty();
    }

    public boolean equals(Object obj) {
        return strat.equals(obj);
    }

    public boolean contains(E element) {
        return strat.contains(element);
    }

    public Integer firstIndexOf(E element) {
        return strat.firstIndexOf(element);
    }

    public int[] allIndexesOf(E element) {
        return strat.allIndexesOf(element);
    }

    public int rawLength() {
        return strat.rawLength();
    }

    public void setGrowthRate(double growthRate) {
        strat.setGrowthRate(growthRate);
    }

    public double getGrowthRate() {
        return strat.getGrowthRate();
    }

    public Object[] getSubArray() {
        return strat.getSubArray();
    }

    public void setSubArray(int startPointer, int endPointer, Object[] subArray) {
        strat.setSubArray(startPointer, endPointer, subArray);
    }

    public Object[] exportArray() {
        return strat.exportArray();
    }

    public void importArray(Object[] array) {
        strat.importArray(array);
    }

    public SequenceContext<E> exportContext() {
        return strat.exportContext();
    }

    public void importContext(SequenceContext<E> seqCon) {

        //TODO: Check conflicts

        strat.importContext(seqCon);
    }

    public E get(int index) {
        return strat.get(index);
    }

    public void remove(int index) {
        strat.remove(index);
    }

    public String toString() {
        return strat.toString();
    }

    public String rawString() {
        return strat.rawString();
    }

    public Iterator<E> iterator() {
        return strat.iterator();
    }
}
