package com.rwalker;

import com.rwalker.sequenceStrategies.SequenceStrategies;

/**
 * Defines the rules for any heuristic that is to be used with the Sequence
 * @author Rhys Walker
 * @since 06/03/2025
 */

public interface SequenceHeuristic {

    /**
     * Increment the internal counter for array operations
     */
    void incrementArrayOps();

    /**
     * Increment the internal counter for queue operations
     */
    void incrementQueueOps();

    /**
     * Increment the internal counter for stack operations
     */
    void incrementStackOps();

    /**
     * Calculate and return the best strategy to use based on the specific heuristic
     * @return Enum being the name of the best strategy to use
     */
    SequenceStrategies getBestStrategy();
    
}
