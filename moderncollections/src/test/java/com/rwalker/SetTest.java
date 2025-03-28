package com.rwalker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Added tests for the Set data structure
 */

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

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
        assertTrue(testing.remove(8));
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

    /**
     * Test the add all method
     */
    @Test
    public void testAddAllEmpty() {
        Set<Integer> testing = new Set<Integer>(4);
        testing.add(0);
        testing.add(4);
        testing.add(8);
        testing.add(12);
        Set<Integer> other = new Set<Integer>(4);
        testing.addAll(other);
        assertTrue(!testing.contains(16));
        assertTrue(!testing.contains(20));
        assertTrue(!testing.contains(24));
        assertTrue(!testing.contains(28));
    }

    /**
     * Test the addAll method with a set that has overlapping values
     */
    @Test
    public void testAddAllOverlap() {
        Set<Integer> testing = new Set<Integer>(4);
        testing.add(0);
        testing.add(4);
        testing.add(8);
        testing.add(12);
        Set<Integer> other = new Set<Integer>(4);
        other.add(8);
        other.add(12);
        other.add(16);
        other.add(20);
        assertTrue(testing.addAll(other)); // We modify so should be true
        assertFalse(testing.addAll(other)); // Set is not modified so should be false
        assertTrue(testing.contains(16)); // Should exist
        assertTrue(testing.contains(20)); // Should exist
        assertEquals(6, testing.size()); // Should be 6 items in the set
    }

    /**
     * Test iterating over a set to remove items from another set
     */
    @Test
    public void testIterator() {
        Set<Integer> testing = new Set<Integer>(4);
        testing.add(100);
        testing.add(65);
        testing.add(12);

        Set<Integer> test2 = new Set<Integer>(4);
        test2.addAll(testing);

        for (Integer i : test2) {
            testing.remove(i);
        }

        assertTrue(!testing.contains(100));
        assertTrue(!testing.contains(65));
        assertTrue(!testing.contains(12));
        assertTrue(test2.contains(100));
        assertTrue(test2.contains(65));
        assertTrue(test2.contains(12));
    }

    /**
     * Test that inserting multiple duplicated entries only adds one
     */
    @Test
    public void testNoDuplicates() {
        Set<Integer> test = new Set<Integer>();
        test.add(2);
        assertFalse(test.add(2));
        assertEquals(1, test.size());    
    }

    /**
     * Test that the equals method works correctly for the set
     */
    @Test
    public void testEqualsMethod() {
        Set<Integer> test = new Set<Integer>();
        test.add(2);
        Set<Integer> test2 = new Set<Integer>();
        test2.add(2);

        assertTrue(test.equals(test2)); // Test with same values

        Set<Boolean> test3 = new Set<Boolean>();
        test3.add(true);

        assertFalse(test.equals(test3)); // Test with different types

        test.add(3);

        assertFalse(test2.equals(test)); // Test with additional values
    }

    /**
     * Test the overload of the addAll method
     */
    @Test
    public void testAddAllMethod() {
        // Testing for purely set elements
        Set<Integer> test = new Set<Integer>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);

        Set<Integer> test2 = new Set<Integer>();
        test2.add(3);
        test2.add(4);
        test2.add(5);
        test2.add(6);

        test.addAll(test2);

        assertTrue(test.contains(1));
        assertTrue(test.contains(2));
        assertTrue(test.contains(3));
        assertTrue(test.contains(4));
        assertTrue(test.contains(5));
        assertTrue(test.contains(6));

        // Test using a Sequence
        Sequence<Integer> seq = new Sequence<>();
        seq.add(1);
        seq.add(2);
        seq.add(3);
        seq.add(4);

        test.clear();
        test.addAll(seq);

        assertTrue(test.contains(1));
        assertTrue(test.contains(2));
        assertTrue(test.contains(3));
        assertTrue(test.contains(4));

        // Test using an ArrayList
        ArrayList<Integer> ar = new ArrayList<>();
        ar.add(1);
        ar.add(2);
        ar.add(3);
        ar.add(4);

        test.clear();
        test.addAll(ar);

        assertTrue(test.contains(1));
        assertTrue(test.contains(2));
        assertTrue(test.contains(3));
        assertTrue(test.contains(4));
    }

    /**
     * Test the overload of the retainAll method
     */
    @Test
    public void testRetainAll() {
        // Test with ModernCollections
        Set<Integer> test = new Set<Integer>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);

        Set<Integer> test2 = new Set<Integer>();
        test2.add(3);
        test2.add(4);
        test2.add(5);
        test2.add(6);

        test.retainAll(test2);

        assertFalse(test.contains(1));
        assertFalse(test.contains(2));
        assertTrue(test.contains(3));
        assertTrue(test.contains(4));
        assertFalse(test.contains(5));
        assertFalse(test.contains(6));

        // Test with Java Collection
        ArrayList<Integer> ar = new ArrayList<>();
        ar.add(3);
        ar.add(4);

        test.clear();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);

        test.retainAll(ar);

        assertFalse(test.contains(1));
        assertFalse(test.contains(2));
        assertTrue(test.contains(3));
        assertTrue(test.contains(4));
    }

    /**
     * Test the overload of the removeAll method
     */
    /**
     * Test the overload of the removeAll method
     */
    @Test
    public void testRemoveAll() {
        // Test with ModernCollections
        Set<Integer> test = new Set<Integer>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);

        Set<Integer> test2 = new Set<Integer>();
        test2.add(3);
        test2.add(4);
        test2.add(5);
        test2.add(6);

        test.removeAll(test2);

        assertTrue(test.contains(1));
        assertTrue(test.contains(2));
        assertFalse(test.contains(3));
        assertFalse(test.contains(4));
        assertFalse(test.contains(5));
        assertFalse(test.contains(6));

        // Test with Java Collection
        ArrayList<Integer> ar = new ArrayList<>();
        ar.add(3);
        ar.add(4);

        test.clear();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);

        test.removeAll(ar);

        assertTrue(test.contains(1));
        assertTrue(test.contains(2));
        assertFalse(test.contains(3));
        assertFalse(test.contains(4));
    }

    /**
     * Test that the iterator works in insertion order
     */
    @Test
    public void testIteratorOrder() {
        Set<Integer> test = new Set<Integer>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);

        int i = 1;
        for (Integer val : test) {
            assertEquals(i, val.intValue());
            i++;
        }
    }
}