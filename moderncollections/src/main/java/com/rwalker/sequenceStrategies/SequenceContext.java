package com.rwalker.sequenceStrategies;

import java.util.Comparator;

/**
 * This class contains useful information about the array the user has declared
 * It will be passed between strategies upon switching
 * 
 * @author Rhys Walker
 * @since 22/01/2025
 */

public class SequenceContext <E> {
    
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
