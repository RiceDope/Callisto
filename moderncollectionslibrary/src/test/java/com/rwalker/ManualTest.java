package com.rwalker;

import org.junit.Test;

import com.rwalker.Sequence;

public class ManualTest {
    
    @Test
    public void manTest(){

        Sequence<Integer> test = new Sequence<Integer>(2);

        test.append(5);
        test.append(6);
        test.append(7);
        test.append(8);
        test.append(9);
        System.out.println(test.rawString());
        test.clear();
        System.out.println(test.rawString());
        test.append(10);
        test.append(11);
        test.append(12);
        System.out.println(test.rawString());

    }

}
