package com.rwalker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.rwalker.Sequence;
import com.rwalker.Set;
import com.rwalker.sequenceStrategies.SequenceContext;
import com.rwalker.sequenceStrategies.SequenceState;
import com.rwalker.sequenceStrategies.SequenceStrategies;
import com.rwalker.sequenceStrategies.RingBufferStrategy.RingBufferSequenceStrategy;
import com.rwalker.sequenceStrategies.RingBufferStrategy.UnsortedRingBufferSequence;

public class ManualTest {

    public static void main(String[] args){

        // Sequence<Integer> seq = new Sequence<> (3, SequenceStrategies.RINGBUFFER);
        // seq.enqueue(10);
        // seq.enqueue(15);
        // seq.dequeue();
        // seq.enqueue(10);
        // seq.dequeue();
        // seq.enqueue(20);
        // seq.dequeue();
        // seq.peek(HowToFunction.QUEUE); // 20


        Map<String, Integer> map = new Map<>((a, b) -> a.compareTo(b));
        map.put("X", 1);
        map.put("B", 2);
        map.put("Y", 3);
        map.put("D", 4);
        System.out.println(map.sortedKeySet()); // {B=2, D=4, X=1, Y=3}
        System.out.println(map); // {X=1, B=2, Y=3, D=4}

        Set<Integer> set = new Set<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        System.out.println(set); // [1, 2, 3, 4]

        Set<Integer> set2 = new Set<>();
        set2.add(3);
        set2.add(4);
        set2.add(5);
        set2.add(6);
        System.out.println(set2); // [3, 4, 5, 6]

        set.removeAll(set2);
        System.out.println(set); // [1, 2]

        set.retainAll(set2);
        System.out.println(set); // [3, 4]

        System.out.println(set.addAll(set2)); // true
        System.out.println(set); // [1, 2, 3, 4, 5, 6]


        // Sequence<Integer> seq = new Sequence<>(4, SequenceStrategies.RINGBUFFER);
        // seq.enqueue(10);
        // seq.enqueue(20);
        // seq.dequeue();
        // seq.enqueue(30);
        // seq.dequeue();
        // seq.enqueue(40);
        // seq.enqueue(50);
        // System.out.println(seq.rawString()); // [50, null, 30, 40]
        // seq.swapStrategies(SequenceStrategies.DEFAULT);
        // System.out.println(seq.rawString()); // [30, 40, 50, null]
        // System.out.println(seq); // [30, 40, 50]


        ArrayList<Integer> arr = new ArrayList<>(); // [10, 20, 30, 40]
        Sequence<Integer> seq = new Sequence<>();
        seq.addAll(arr);
        System.out.println(seq); // [10, 20, 30, 40]

    }
}   