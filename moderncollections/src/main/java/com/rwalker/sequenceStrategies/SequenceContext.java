package com.rwalker.sequenceStrategies;

import java.util.Comparator;

/**
 * This class wraps all information that is required to setup and swap between strategies of the Sequence class.
 * 
 * @author Rhys Walker
 * @since 06/01/2025
 */

public class SequenceContext <E> {
    
    // Default values are left public for ease
    public int endPointer = 0;
    public int startPointer = 0;
    public int initialSize = 10; // Average declared array is not larger than 10 elements (76.8%)
    public double growthRate = 3.0; // Because same as ArrayList
    // public boolean enforceSort = false;
    public Comparator<E> comparator;
    public int minimumExpansion = 1; // This must never be set below 1
    public SequenceState currentState = SequenceState.UNSORTED; // Default state will be unsorted

    public SequenceContext(){
        // Default values are used
    }

    public SequenceContext(int startPointer, int endPointer, int initialSize, double growthRate, Comparator<E> comparator, int minimumExpansion, SequenceState currentState){
        this.startPointer = startPointer;
        this.endPointer = endPointer;
        this.initialSize = initialSize;
        this.growthRate = growthRate;
        // this.enforceSort = enforceSort;
        this.comparator = comparator;
        this.minimumExpansion = minimumExpansion;
        this.currentState = currentState;
    }

}
