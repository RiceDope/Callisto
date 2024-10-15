package com.rwalker;

public class ManualTesting {
    public static void main(String[] args) throws NoSuchMethodException{

        Sequence<Integer> test = new Sequence<Integer>();
        test.append(10);
        test.append(15);
        test.append(20);
        test.setFunctionality(HowToFunction.STACK);
        test.setEnforce(false);
        test.append(13);

        System.out.println(test.peek());
    }
}

