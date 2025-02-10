package com.rwalker.sequenceStrategies;

import java.util.Comparator;

/**
 * Extends the default strategy but contains methods important for manipulation and swapping strategies as well as sorting
 */

public interface SequenceManipulatorInterface<E> extends SequenceStrategy<E>{
    
    void sortOnwards();
    void sortOnwards(Comparator<E> comp);

}
