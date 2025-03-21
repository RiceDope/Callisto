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

        Map<String, Integer> test = new Map<>();
        System.out.println(test);

        test.put("Hello", 100);
        System.out.println(test);

        test.put("Hello1", 101);
        System.out.println(test);

        test.put("Hello2", 102);
        System.out.println(test);

        test.put("Hello3", 103);
        System.out.println(test);

        test.put("Hello4", 104);
        System.out.println(test);

        test.put("Hello5", 105);
        System.out.println(test);

        test.put("Hello6", 106);
        System.out.println(test);

        test.put("Hello7", 107);
        System.out.println(test);

        test.put("Hello8", 108);
        System.out.println(test);

        test.put("Hello9", 109);
        System.out.println(test);

        // Set<String> set = new Set<>();
        // set.add("Hello");
        // set.add("Hello1");
        // set.add("Hello2");
        // set.add("Hello3");
        // set.add("Hello4");
        // set.add("Hello5");
        // set.add("Hello6");
        // set.add("Hello7");
        // set.add("Hello8");
        // set.add("Hello9");

        // System.out.println(set);


    }
}   