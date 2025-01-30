package com.rwalker;

import com.rwalker.sequenceStrategies.SequenceStrategies;

public class ManualTest {
    public static void main(String[] args){
        Sequence<Integer> test = new Sequence<>(4);
        
        test.add(10);
        System.out.println(test.rawString());

        test.add(20);
        System.out.println(test.rawString());

        test.add(30);
        System.out.println(test.rawString());
        test.add(40);
        System.out.println(test.rawString());
        test.add(50);
        System.out.println(test.rawString());
        test.add(60);
    }
}   