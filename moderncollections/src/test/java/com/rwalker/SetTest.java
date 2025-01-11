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
        assertEquals("[ 0 , 4 , 8 , 12 | | | ]", testing.rawString());
    }

    /**
     * Test if adding a whole set to another set works
     */
    @Test
    public void testAddAll() {
        Set<Integer> testing = new Set<Integer>(4);
        testing.add(0);
        testing.add(4);
        testing.add(8);
        testing.add(12);
        Set<Integer> other = new Set<Integer>(4);
        other.add(16);
        other.add(20);
        other.add(24);
        other.add(28);
        testing.addAll(other);
        assertTrue(testing.contains(16));
        assertTrue(testing.contains(20));
        assertTrue(testing.contains(24));
        assertTrue(testing.contains(28));
    }

    
}