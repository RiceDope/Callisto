package com.rwalker;

import com.rwalker.sequenceStrategies.RingBufferSequenceStrategy;
import com.rwalker.sequenceStrategies.SequenceContext;

public class ManualTest {
    public static void main(String[] args){

        SequenceContext<Integer> context = new SequenceContext<>(0, 0, 5, 1.5, false, (a, b) -> a - b, 1);
        RingBufferSequenceStrategy<Integer> seq = new RingBufferSequenceStrategy<Integer>(context);

        seq.sortOnwards();

        System.out.println("Appending" + 1);
        seq.append(1);
        System.out.println(seq.rawString());
        System.out.println("Appending" + 2);
        seq.append(2);
        System.out.println(seq.rawString());
        System.out.println("Appending" + 3);
        seq.append(3);
        System.out.println(seq.rawString());
        System.out.println("Appending" + 4);
        seq.append(4);
        System.out.println(seq.rawString());
        System.out.println("Removing" + 0);
        seq.remove(0);
        System.out.println(seq.rawString());
        System.out.println("Removing" + 0);
        seq.remove(0);
        System.out.println(seq.rawString());
        System.out.println("Removing" + 0);
        seq.remove(0);
        System.out.println(seq.rawString());
        // System.out.println("Appending" + 5);
        // seq.append(5);
        // System.out.println(seq.rawString());
        System.out.println("Appending" + 9);
        seq.append(9);
        System.out.println("Appending" + 3);
        seq.append(3);
        System.out.println(seq.rawString());
        System.out.println("Appending" + 6);
        seq.append(6);
        System.out.println(seq.rawString());
        System.out.println(seq);
        seq.append(12);
        seq.append(3);
        System.out.println(seq.rawString());
        System.out.println(seq);
        seq.stopSorting();
        System.out.println(seq.contains(3));

        // System.out.println(seq);

        // Sequence<Integer> seq = new Sequence<>();
        // seq.append(1);
        // seq.append(2);
        // seq.append(3);
        // seq.append(4);
        // Set<Integer> set = new Set<>();
        // set.add(5);
        // set.add(6);
        // set.add(7);
        // set.add(8);
        // Set<Integer> testSet = new Set<>();
        // testSet.addAll(seq);
        // testSet.addAll(set);
        // System.out.println(testSet);

        // Sequence<Integer> test = new Sequence<>(5, (a, b) -> a - b);
        // test.sortOnwards();
        // test.append(10);
        // test.append(20);
        // test.append(30);
        // test.append(40);
        // test.remove(0);
        // test.remove(0);
        // test.remove(0);
        // System.out.println(test.size());
        // System.out.println(test.rawString());
        // test.append(50);
        // System.out.println(test);
    }
}   