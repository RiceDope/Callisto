package com.rwalker;

import com.rwalker.sequenceStrategies.SequenceStrategies;

public class ManualTest {
    public static void main(String[] args){
        Sequence<Integer> test = new Sequence<>(5, com.rwalker.sequenceStrategies.SequenceStrategies.RINGBUFFER);
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.remove(0);
        test.remove(0);
        test.remove(0);
        test.append(35);
        test.append(40);
        System.out.println(test.getname());
        System.out.println(test.rawString());
        test.swapStrategies(SequenceStrategies.DEFAULT);
        System.out.println(test.getname());

        System.out.println(test.rawString());
    }
}   