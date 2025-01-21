package com.rwalker;

import java.util.ArrayList;

import java.util.Arrays;

public class ManualTest {
    public static void main(String[] args){
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.append(45);
        test.append(20);

        int[] testArry = new int[2];
        testArry[0] = 1;
        testArry[1] = 5;

        System.out.println(Arrays.toString(test.allIndexesOf(20)));
        System.out.println(test);
    }
}   