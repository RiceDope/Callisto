package com.rwalker;

import com.rwalker.sequenceStrategies.RingBufferSequenceStrategy;
import com.rwalker.sequenceStrategies.SequenceContext;

public class ManualTest {
    public static void main(String[] args){

        SequenceContext<Integer> context = new SequenceContext<>(0, 0, 5, 1.5, false, (a, b) -> a - b, 1);
        RingBufferSequenceStrategy<Integer> seq = new RingBufferSequenceStrategy<Integer>(context);

        seq.append(100);
        seq.append(2);
        seq.append(7);
        seq.append(10);
        seq.remove(3);
        seq.append(55);
        seq.append(13);

        

        System.out.println(seq.rawString());
        System.out.println(seq);

        seq.sort();

        System.out.println(seq.rawString());
        System.out.println(seq);

    }
}   