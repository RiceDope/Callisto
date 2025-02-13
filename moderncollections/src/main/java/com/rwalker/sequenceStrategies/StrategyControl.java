package com.rwalker.sequenceStrategies;

public interface StrategyControl<E> extends SortControl<E> {
    
    // Used in order to change between strategies
    Object[] exportArray();
    void importArray(Object[] array);
    SequenceContext<E> exportContext();
    void importContext(SequenceContext<E> context);
    SequenceStrategies getname();

}
