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
        test.put("Hello", 100);
        System.out.println(test.toString());


    }
}   