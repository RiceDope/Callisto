package com.rwalker;

public class ManualTesting {
    public static void main(String[] args) throws NoSuchMethodException{

        Sequence<Integer> test = new Sequence<Integer>();
        test.append(10);
        test.append(15);
        test.append(20);
        test.setFunctionality(HowToFunction.QUEUE);
        test.setEnforce(true);
        test.enqueue(10);

        System.out.println(test.toString());
    }
}

