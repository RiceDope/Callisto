package com.rwalker;

import java.util.Comparator;

/**
 * This class contains useful information about the array the user has declared
 * It will be passed between strategies upon switching
 */

public class SequenceContext <E> {
    
    public int endPointer = 0;
    public int startPointer = 0;
    
    public int initialSize = 100;
    public double growthRate = 1.5;

    public boolean enforceSort = false;
    public Comparator<E> comparator;

    public int minimumExpansion = 1;

}
