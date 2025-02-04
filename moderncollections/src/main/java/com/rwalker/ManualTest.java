package com.rwalker;

import com.rwalker.sequenceStrategies.SequenceStrategies;

public class ManualTest {
    public static void main(String[] args){
        
        Sequence<Integer> seq = new Sequence<>((a, b) -> a - b);
        seq.sortOnwards();

        
    }
}   