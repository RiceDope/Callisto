package com.rwalker.sequenceStrategies;

import com.rwalker.SequenceHeuristic;

/**
 * This class tracks information about the sequence and can give an indication as to when it is best to change strategies
 * The rule is change late. Be sure as to what we are doing when we change.
 * 
 * @author Rhys Walker
 * @version 26/01/2025
 */

public class DefaultSequenceHeuristic implements SequenceHeuristic{
    
    private int queueOps;
    private int stackOps;
    private int arrayOps;
    private int totalOps;

    public DefaultSequenceHeuristic(){
        queueOps = 0;
        stackOps = 0;
        arrayOps = 0;
        totalOps = 0;
    }

    public void incrementQueueOps(){
        queueOps++;
        totalOps++;
    }

    public void incrementStackOps(){
        stackOps++;
        totalOps++;
    }

    public void incrementArrayOps(){
        arrayOps++;
        totalOps++;
    }

    /**
     * This method will return the best strategy to use based on the current operations
     * 
     * If we are heavily queue or stack based then we will switch to a ringBuffer (80% of 100 or more operations)
     * 
     * @return SequenceStrategies for the best implementation to use
     */
    public SequenceStrategies getBestStrategy() {
        if ((queueOps + stackOps)/totalOps > 0.8 && totalOps >= 100){
            return SequenceStrategies.RINGBUFFER;
        } else if (arrayOps/totalOps > 0.8){
            return SequenceStrategies.DEFAULT;
        } else {
            return SequenceStrategies.DEFAULT;
        }
    }

}
