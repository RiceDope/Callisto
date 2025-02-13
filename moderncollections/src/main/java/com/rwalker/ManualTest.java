package com.rwalker;

import java.util.ArrayList;
import java.util.Arrays;

import com.rwalker.sequenceStrategies.SequenceState;

public class ManualTest {
    public static void main(String[] args){

        System.out.println(new Integer(10).compareTo(new Integer(2)));
        System.out.println(new Integer(2) instanceof Comparable);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

    }
}   