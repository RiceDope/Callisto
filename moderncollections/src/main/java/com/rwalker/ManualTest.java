package com.rwalker;

import java.util.ArrayList;

// import com.rwalker.sequenceStrategies.RingBufferSequenceStrategy;
import com.rwalker.sequenceStrategies.SequenceContext;

public class ManualTest {
    public static void main(String[] args){

        // SequenceContext<Integer> context = new SequenceContext<>(0, 0, 5, 1.5, false, (a, b) -> a - b, 1);
        // RingBufferSequenceStrategy<Integer> seq = new RingBufferSequenceStrategy<Integer>(context);

        // seq.sortOnwards();
        // seq.append(1);
        // seq.append(2);
        // seq.append(3);
        // seq.append(4);
        // seq.remove(0);
        // seq.remove(0);
        // seq.remove(0);
        // seq.append(5);
        // seq.append(6);
        

        // System.out.println(seq.rawString());
        // System.out.println(seq);

        Sequence<Integer> seq = new Sequence<>();
        seq.append(1);
        seq.append(2);
        seq.append(3);
        seq.append(4);
        Set<Integer> set = new Set<>();
        set.add(5);
        set.add(6);
        set.add(7);
        set.add(8);
        ArrayList<Integer> ar = new ArrayList<>();
        ar.add(9);
        ar.add(10);
        ar.add(11);
        ar.add(12);
        Set<Integer> testSet = new Set<>();
        testSet.addAll(seq);
        testSet.addAll(set);
        testSet.addAll(ar);
        System.out.println(testSet);

    }
}   