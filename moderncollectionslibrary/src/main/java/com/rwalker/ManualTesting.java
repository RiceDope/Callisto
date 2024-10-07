package com.rwalker;

public class ManualTesting {
    public static void main(String[] args){
        Sequence<String> test = new Sequence<String>(10, 1.1);
        test.setGrowthRate(0.5);
    }
}
