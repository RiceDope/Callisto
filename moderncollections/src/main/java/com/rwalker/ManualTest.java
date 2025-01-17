package com.rwalker;

import com.rwalker.sequenceStrategies.RingBufferSequenceStrategy;
import com.rwalker.sequenceStrategies.SequenceContext;

public class ManualTest {
    public static void main(String[] args){

        SequenceContext<Integer> context = new SequenceContext<>(0, 0, 5, 1.5, false, (a, b) -> a - b, 1);
        RingBufferSequenceStrategy<Integer> seq = new RingBufferSequenceStrategy<Integer>(context);

        seq.sortOnwards();
        seq.append(1);
        seq.append(2);
        seq.append(3);
        seq.append(4);
        seq.remove(0);
        seq.remove(0);
        seq.remove(0);
        seq.append(5);
        seq.append(6);
        

        System.out.println(seq.rawString());
        System.out.println(seq);

    }
}   