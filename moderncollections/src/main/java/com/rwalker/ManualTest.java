package com.rwalker;

import com.rwalker.sequenceStrategies.RingBufferSequenceStrategy;
import com.rwalker.sequenceStrategies.SequenceContext;

public class ManualTest {
    public static void main(String[] args){

        SequenceContext<Integer> context = new SequenceContext<>(0, 0, 5, 1.5, false, null, 1);
        RingBufferSequenceStrategy<Integer> seq = new RingBufferSequenceStrategy<Integer>(context);

        seq.append(1);
        seq.append(2);
        seq.append(3);
        seq.append(4);
        seq.insert(1, 10);
        
        seq.remove(0);
        seq.remove(0);
        
        seq.append(10);
        seq.append(10);
        seq.append(10);

        seq.insert(6, 5);
        seq.insert(7, 100);

        System.out.println(seq.rawString());
        System.out.println(seq);
    }
}   