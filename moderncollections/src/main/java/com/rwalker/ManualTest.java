package com.rwalker;

import java.util.Iterator;

import com.rwalker.sequenceStrategies.SequenceContext;
import com.rwalker.sequenceStrategies.SequenceStrategies;
import com.rwalker.sequenceStrategies.SortedDefaultSequence;

public class ManualTest {
    public static void main(String[] args){

        SequenceContext<Integer> context = new SequenceContext<>();
        context.comparator = (a, b) -> a - b;
        context.initialSize = 4;
        
        SortedDefaultSequence<Integer> sortedDefaultSequence = new SortedDefaultSequence<>(context);

        sortedDefaultSequence.add(1);
        sortedDefaultSequence.add(2);
        sortedDefaultSequence.add(6);
        sortedDefaultSequence.add(0);
        sortedDefaultSequence.add(7);
        sortedDefaultSequence.add(3);
        sortedDefaultSequence.add(null);
        sortedDefaultSequence.add(null);
        sortedDefaultSequence.add(4);
        sortedDefaultSequence.add(5);
        sortedDefaultSequence.add(6);
        sortedDefaultSequence.add(null);
        sortedDefaultSequence.add(8);
        sortedDefaultSequence.add(9);
        sortedDefaultSequence.add(10);

        System.out.println(sortedDefaultSequence.rawString());
        System.out.println(sortedDefaultSequence);

        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);
        sortedDefaultSequence.remove(0);

        System.out.println(sortedDefaultSequence.rawString());
        System.out.println(sortedDefaultSequence);

        sortedDefaultSequence.add(1);

        System.out.println(sortedDefaultSequence.rawString());
        System.out.println(sortedDefaultSequence);

        sortedDefaultSequence.sort((a, b) -> b - a);
        System.out.println(sortedDefaultSequence);


    }
}   