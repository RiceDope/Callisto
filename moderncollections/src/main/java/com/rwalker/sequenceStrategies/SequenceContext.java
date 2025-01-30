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
    public int initialSize = 100;
    public double growthRate = 1.5;
    public boolean enforceSort = false;
    public Comparator<E> comparator;
    public int minimumExpansion = 1;

    public SequenceContext(){
        // Default values are used
    }

    public SequenceContext(int startPointer, int endPointer, int initialSize, double growthRate, boolean enforceSort, Comparator<E> comparator, int minimumExpansion){
        this.startPointer = startPointer;
        this.endPointer = endPointer;
        this.initialSize = initialSize;
        this.growthRate = growthRate;
        this.enforceSort = enforceSort;
        this.comparator = comparator;
        this.minimumExpansion = minimumExpansion;
        
    }

}
