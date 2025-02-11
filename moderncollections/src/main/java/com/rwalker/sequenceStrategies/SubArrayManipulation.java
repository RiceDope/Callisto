package com.rwalker.sequenceStrategies;

public interface SubArrayManipulation<E> extends SequenceStrategy<E> {

    // Used in order to change between strategies
    Object[] exportArray();
    void importArray(Object[] array);
    SequenceContext<E> exportContext();
    void importContext(SequenceContext<E> context);
    SequenceStrategies getname();

}
