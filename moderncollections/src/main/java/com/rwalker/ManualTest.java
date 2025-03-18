package com.rwalker;

import java.util.ArrayList;
import java.util.Arrays;

import com.rwalker.Sequence;
import com.rwalker.Set;
import com.rwalker.sequenceStrategies.SequenceContext;
import com.rwalker.sequenceStrategies.SequenceState;
import com.rwalker.sequenceStrategies.SequenceStrategies;
import com.rwalker.sequenceStrategies.RingBufferStrategy.RingBufferSequenceStrategy;
import com.rwalker.sequenceStrategies.RingBufferStrategy.UnsortedRingBufferSequence;

public class ManualTest {

    public static void main(String[] args){

        Sequence<Integer> seq = new Sequence<>(SequenceStrategies.RINGBUFFER);
        seq.add(1);
        seq.add(2);
        seq.add(3);
        seq.add(4);
        seq.add(5);
        seq.sortOnwards((a, b) -> a - b);
        System.out.println(seq.getstate());
    }
}   