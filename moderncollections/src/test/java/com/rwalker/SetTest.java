package com.rwalker;

import org.junit.Test;

public class SetTest {
    
    @Test
    public void testAdd() {
        Set<Integer> testing = new Set<Integer>(4);
        testing.add(0);
        testing.add(4);
        testing.add(8);
        testing.add(12);
        testing.add(16);
        System.out.println(testing);
    }
}
