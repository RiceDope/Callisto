package com.rwalker;

import com.rwalker.sequenceStrategies.SequenceStrategies;

public class ManualTest {
    public static void main(String[] args){
        Sequence<Integer> test = new Sequence<>();
        for (int i = 0; i < 101; i++){
            test.enqueue(i);
        }

        System.out.println(test.checkSwap());
    }
}   