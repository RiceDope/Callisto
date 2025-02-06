package com.rwalker;

/**
 * Tests for the ArrayList functionality of Sequence
 * 
 * @author Rhys Walker
 */

// Junit tings
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Iterator;

import org.junit.Test;
import org.junit.jupiter.params.provider.ValueSource;

import com.rwalker.sequenceStrategies.SequenceStrategies;

/**
 * Unit test for ArrayList functionality of Sequence
 */
public class ArrayListTest {

    /**
     * Test that adding the 10 items has worked and length function
     */
    @Test
    public void testPopulation() throws NoSuchMethodException{
        Sequence<Integer> testing = new Sequence<>();
        testing = TestUtils.addTenInts(testing);
        assertEquals(10, testing.length());
    }

    /**
     * Test removal of two items from the list
     */
    @Test
    public void testRandomRemoval() {
        Sequence<Integer> testing = TestUtils.addTenInts(TestUtils.generateEmptySequence());
        testing.remove(4);
        testing.remove(7);
        assertEquals("[100, 52, 250, 5, 1052, 9, 100, 52]", testing.toString());
    }

    /**
     * Test getting the 1st integer
     */
    @Test
    public void testGet() {
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();
        int out = testing.get(1);
        assertEquals(2, out);
    }

    /**
     * Test get with negative index
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testGetNegative() {
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();
        testing.get(-1);
    }

    /**
     * Test get with out of bounds index
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testGetOutOfBounds() {
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();
        testing.get(100);
    }

    /**
     * Test clearing the array
     */
    @Test
    public void testClear(){
        Sequence<Integer> testing = TestUtils.addTenInts(TestUtils.generateEmptySequence());
        testing.clear();
        assertEquals("[]", testing.toString());
    }

    /**
     * Test insertion at multiple points in the array
     * @throws NoSuchMethodException
     */
    @Test
    public void testInsert() throws NoSuchMethodException{
        Sequence<Integer> testing = new Sequence<Integer>();
        testing.add(5);
        testing.add(10);
        testing.add(15);
        testing.add(20);
        testing.insert(0, 100);
        assertEquals("[100, 5, 10, 15, 20]", testing.toString());
        testing.insert(2, 50);
        assertEquals("[100, 5, 50, 10, 15, 20]", testing.toString());
        testing.insert(6, 50);
        assertEquals("[100, 5, 50, 10, 15, 20, 50]", testing.toString());
    }

    /**
     * Test insertion into the array where it is out of bounds
     * @throws NoSuchMethodException
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsInsertion() throws NoSuchMethodException{
        Sequence<Integer> testing = new Sequence<Integer>();
        testing.add(5);
        testing.add(10);
        testing.add(15);
        testing.add(20);
        testing.insert(10, 100);
    }

    /**
     * Test that the sub-array expands when inserting into a full array
     * @throws NoSuchMethodException
     */
    @Test
    public void testArrayExpansionWithInsert() throws NoSuchMethodException {
        Sequence<Integer> testing = new Sequence<Integer>(4);
        assertEquals(4, testing.rawLength());
        testing.add(5);
        testing.add(10);
        testing.add(15);
        // RingBuffer would expand here
        testing.add(20);
        // Default would expand here
        testing.insert(2, 100);
        assertEquals(6, testing.rawLength());
        assertEquals("[5, 10, 100, 15, 20]", testing.toString());
    }

    /**
     * Test that we can insert into position 0 of the array
     */
    @Test
    public void testInsertingIntoZeroEmptyArray(){
        Sequence<Integer> testing = new Sequence<Integer>(4);
        testing.insert(0, 10);
        assertEquals("[10]", testing.toString());
    }

    /**
     * Test attempting to insert into a negative index
     */
    @Test(expected=IndexOutOfBoundsException.class)
    public void testInsertingIntoNegativePosition(){
        Sequence<Integer> testing = new Sequence<Integer>();
        testing.insert(-1, 100);
    }

    /**
     * Test that the replace method works
     */
    @Test
    public void testReplace(){
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(20);
        testing.replace(0, 50);
        assertEquals("[50, 20]", testing.toString());
    }

    /**
     * Test that inserting negative throws an error
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testReplaceNegativeIndex(){
        Sequence<Integer> testing = new Sequence<>();
        testing.replace(-1, 10);
    }

    /**
     * Test that inserting out of bounds throws an error
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testReplaceOutOdBounds(){
        Sequence<Integer> testing = new Sequence<>();
        testing.replace(10, 10);
    }

    /**
     * Test remove function normally
     */
    @Test
    public void testRemove(){
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(20);
        testing.remove(0);
    }

    /**
     * Test remove function with negative index
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testRemoveNegative(){
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(20);
        testing.remove(-1);
    }

    /**
     * Test remove function with out of bounds index
     */
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testRemoveOutOfBounds(){
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(20);
        testing.remove(10);
    }

    /**
     * Test the append all method
     */
    @Test
    public void testAppendAll() {
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(20);
        testing.add(30);
        Sequence<Integer> toAdd = new Sequence<>();
        toAdd.add(40);
        toAdd.add(50);
        toAdd.add(60);
        testing.addAll(toAdd);
        assertEquals("[10, 20, 30, 40, 50, 60]", testing.toString());
    }

    /**
     * Test the append method with a UserNull object
     */
    @Test
    public void testAppendUserNull() {
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(20);
        testing.add(30);
        testing.add(null);
        assertEquals("[10, 20, 30, null]", testing.toString());
    }

    /**
     * Test sorting with nulls when a comparator is provided
     */
    @Test
    public void testSortingUserNull() {
        Sequence<Integer> testing = new Sequence<>((a, b) -> a - b);
        testing.add(10);
        testing.add(20);
        testing.add(30);
        testing.add(null);
        testing.sort();
        assertEquals("[null, 10, 20, 30]", testing.toString());
    }

    /* 
     * Test sorting with nulls when a comparator is provided (sortCopy)
    */
    @Test
    public void testSortingUserNullComparator() {
        Sequence<Integer> testing = new Sequence<>((a, b) -> a - b);
        testing.add(10);
        testing.add(20);
        testing.add(30);
        testing.add(null);
        testing = testing.sortCopy((a, b) -> b - a);
        assertEquals("[null, 30, 20, 10]", testing.toString());
    }

    /**
     * Test inserting a null into the Sequence
     */
    @Test
    public void testInsertNull() {
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(20);
        testing.add(30);
        testing.insert(1, null);
        assertEquals("[10, null, 20, 30]", testing.toString());
    }

    /**
     * Test replacing a term with a user null
     */
    @Test
    public void testReplaceNull() {
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(20);
        testing.add(30);
        testing.replace(1, null);
        assertEquals("[10, null, 30]", testing.toString());
    }

    /**
     * Test using get for a null
     */
    @Test
    public void testGetNull() {
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(20);
        testing.add(30);
        testing.add(null);
        assertEquals(null, testing.get(3));
    }

    /**
     * Test that we don't crash when Iterating on nothing
     */
    @Test
    public void testIteratorNull() {
        Sequence<Integer> testing = new Sequence<>();
        for (Integer i : testing) {
            assertNull(i);
        }
    }

    /**
     * Test that removing an item from the middle removes correctly (With a gap at the end)
     */
    @Test
    public void testIteratorRemove() {
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(10);
        testing.add(20);
        testing.add(30);
        testing.dequeue();
        
        Iterator<Integer> it = testing.iterator();
        while(it.hasNext()) {
            int next = it.next();
            if (next == 20) {
                it.remove();
            }
        }
        assertEquals("[10, 30]", testing.toString());
    }

    /**
     * Test that removing all items from the list works
     */
    @Test
    public void testIteratorRemoveAll() {
        Sequence<Integer> testing = new Sequence<>();
        testing.add(10);
        testing.add(20);
        testing.add(30);
        
        Iterator<Integer> it = testing.iterator();
        while(it.hasNext()) {
            it.next();
            it.remove();
        }
        assertEquals("[]", testing.toString());
    }

    /**
     * Test that we correctly can remove all terms from a Sequence while holding the subArray in an inversion
     */
    @Test
    public void testIteratorRemoveAllInInversion() {
        Sequence<Integer> testing = new Sequence<>(8, SequenceStrategies.RINGBUFFER);
        testing.add(10);
        testing.add(20);
        testing.add(30);
        testing.add(40);
        testing.add(50);
        testing.add(60);
        testing.add(70);
        testing.dequeue();
        testing.dequeue();
        testing.dequeue();
        testing.add(80);
        testing.add(90);
        testing.add(100);

        assertEquals("[40, 50, 60, 70, 80, 90, 100]", testing.toString());

        Iterator<Integer> it = testing.iterator();
        while(it.hasNext()) {
            it.next();
            it.remove();
        }

    }
}
