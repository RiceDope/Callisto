package com.rwalker;

import java.util.ArrayList;
import java.util.Arrays;

import com.rwalker.sequenceStrategies.SequenceState;
import com.rwalker.Set;

public class ManualTest {

    private static final int appendIterations = 1000;
    private static final String key1 = "key10";
    private static final String key2 = "key500";
    private static final String key3 = "key100";
    public static void main(String[] args){

        Map<String, Integer> map = new Map<>();

        for (int i = 0; i < appendIterations; i++) {
            map.put("key" + i, i);
        }

        System.out.println(map.get(key1));
        map.get(key2);
        map.get(key3);
    }
}   