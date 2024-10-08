package com.rwalker;

import java.util.Collections;

public class ManualTesting {
    public static void main(String[] args){
        Sequence<Integer> test = new Sequence<Integer>();
        test.append(500);
        test.append(20);
        test.append(250);
        test.append(55);
        System.out.println(test.toString());
        test.sort();
        System.out.println(test.toString());
    }
}
