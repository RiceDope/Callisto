package com.rwalker;

import static org.junit.Assert.assertEquals;

/**
 * Added tests for the Set data structure
 */

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SetTest {
    
    /**
     * Test adding a value to the set
     */
    @Test
    public void testAdd() {
        Set<Integer> testing = new Set<Integer>(4);
        testing.add(0);
        testing.add(4);
        testing.add(8);
        testing.add(12);
        testing.add(16);
        assertTrue(testing.contains(8));
    }

    /**
     * Test that removing a value works
     */
    @Test
    public void testRemove() {
        Set<Integer> testing = new Set<Integer>(4);
        testing.add(0);
        testing.add(4);
        testing.add(8);
        testing.add(12);
        testing.add(16);
        testing.remove(8);
        assertTrue(!testing.contains(8));
    }

    /**
     * Test the rawString method. Shows the underlying representation of the set
     */
    @Test
    public void testRawString() {
        Set<Integer> testing = new Set<Integer>(4);
        testing.add(0);
        testing.add(4);
        testing.add(8);
        testing.add(12);
        assertEquals("[ 0 , 4 , 8 , 12 | | | ]", testing.rawString());}
}
