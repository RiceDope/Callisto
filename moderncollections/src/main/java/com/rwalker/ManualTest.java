package com.rwalker;

import java.util.ArrayList;
import java.util.Arrays;

import com.rwalker.Sequence;
import com.rwalker.Set;
import com.rwalker.sequenceStrategies.SequenceState;
import com.rwalker.sequenceStrategies.SequenceStrategies;

public class ManualTest {

    public static void main(String[] args){

        // Sequence<String> seq = new Sequence<>((a, b) -> a.compareTo(b), SequenceState.SORTED);
        // seq.add("a");
        // seq.add("x");
        // seq.add("h");
        // seq.add("p");
        // System.out.println(seq);

        Map<String, Integer> map = new Map<>((a, b) -> a.compareTo(b));
        map.put("a", 1);
        map.put("x", 2);
        map.put("h", 3);
        map.put("p", 4);
        for (String key : map.sortedKeySet()){
            System.out.println(key + " " + map.get(key));
        }


    }
}   