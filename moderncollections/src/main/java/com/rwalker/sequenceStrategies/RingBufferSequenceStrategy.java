package com.rwalker.sequenceStrategies;

import java.util.Comparator;

/**
 * This is a ring buffer like strategy the difference from the default strategy
 * is that the endPointer can be smaller than the startPointer. This is optimal for
 * a queue/stack.
 */

 // TODO: implements SequenceStrategy<E>
public class RingBufferSequenceStrategy<E> {
    
    private int endPointer;
    private int startPointer;
    private int initialSize;
    private Object[] array;
    private double growthRate;
    private boolean enforceSort;
    private Comparator<E> defaultComparator;
    private int minumumExpansion;
    
    public RingBufferSequenceStrategy(SequenceContext<E> context) {
        this.endPointer = context.endPointer;
        this.startPointer = context.startPointer;
        this.initialSize = context.initialSize;
        this.growthRate = context.growthRate;
        this.enforceSort = context.enforceSort;
        this.defaultComparator = context.comparator;
        this.minumumExpansion = context.minimumExpansion;

        array = new Object[initialSize];
    }

    private void addToEnd(E element) {

        // NORMAL:

        // OPT1 - Can we just append as normal
        // OPT2 - Can we wrap around (Enter inversion)
        // OPT3 - Do we have to expand (Enter expansion)

        // INVERSION:

        // OPT1 - Can we append as normal
        // OPT2 - Do we need to expand (Enter expansion, Exit Inversion)

        if (!(endPointer < startPointer)){ // We are working in normal space
            if (endPointer < array.length){ // If we are smaller than the array then just add
                addAtEndPointer(element);
            } else { // We are at the array limit. What can we do
                if (startPointer > 0) { // There is space at the beginning to add the element
                    addAtEndPointer(element);
                    endPointer = 0;
                } else { // Just expand do not enter an inversion
                    expandArray();
                    addAtEndPointer(element);
                }
            }
        } else { // We are working in inversion space
            if (endPointer == startPointer) { // Then we must expand and exit the inversion
                expandArray();
                addAtEndPointer(element);
            } else { // We can just add the element
                addAtEndPointer(element);
            }
        }
    }

    /**
     * Adds an element at endPointer regardless of the endPointers current position
     * @param element
     */
    private void addAtEndPointer(E element) {
        array[endPointer] = element;
        endPointer++;
    }

    // TODO: Implement
    private void expandArray() {
        System.out.println("EXPANDING ARRAY");
    }
}
