package com.rwalker;

// Junit tings
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * General tests for Sequence
 */


public class GenericSequenceTests {
    
    /**
     * Test that a sequence can be made
     */
    @Test
    public void testCreation() {
        assertNotNull(new Sequence<Integer>());
    }

    @Test
    public void testCreationSize() {
        assertNotNull(new Sequence<Integer>(10));
    }

    @Test
    public void testCreationComparator() {
        assertNotNull(new Sequence<Integer>((a, b) -> a - b));
    }

    @Test
    public void testCreationCustomGrowth() {
        assertNotNull(new Sequence<Integer>(2.0));
    }

    @Test
    public void testCreationSizeComparator() {
        assertNotNull(new Sequence<Integer>(10, (a, b) -> a - b));
    }

    @Test 
    public void testCreationSizeCustomGrowth() {
        assertNotNull(new Sequence<Integer>(10, 2.0));
    }

    @Test
    public void testCreationCustomGrowthComparator() {
        assertNotNull(new Sequence<Integer>(2.0, (a, b) -> a - b));
    }

    @Test
    public void testCreationSizeCustomGrowthComparator() {
        assertNotNull(new Sequence<Integer>(10, 2.0, (a, b) -> a - b));
    }


    /**
     * Test the expansion of the base array as we add terms
     */
    @Test
    public void testExpansion() throws NoSuchMethodException{
        // Generate a small sequence for testing
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();

        // Expansion occurs here (size 6 rate x1.5 default) (RingBuffer expansion occurs before this)
        testing.append(5); 
        assertEquals(6, testing.rawLength());
    }

    /**
     * Test the retraction of an array
     */
    @Test
    public void testBaseArrayShrink() throws NoSuchMethodException{

        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();
        // Expansion occurs here (size 6 rate x1.5 default)
        testing.append(5);
        assertEquals(6, testing.rawLength());
        // Test contraction back to 4 after clear
        testing.clear();
        assertEquals(4, testing.rawLength());
    }

    /**
     * Test custom growth rate
     */
    @Test
    public void testCustomGrowthRate() throws NoSuchMethodException{
        Sequence<Integer> testing = new Sequence<>(4);
        testing.setGrowthRate(2.0);
        testing.append(1);
        testing.append(2);
        testing.append(3);
        // RingBuffer expands here
        testing.append(4);
        // Default expands here
        testing.append(10);
        assertEquals(8, testing.rawLength());
    }

    /**
     * Test that growth rate is handled correctly when given an erronous growth input
     */
    @Test(expected = IllegalArgumentException.class)
    public void instantiateWithNegativeGrowth(){
        Sequence<Integer> testing = new Sequence<Integer>(3, -2.0);
        // Use non deprecated method with "error range"
        assertEquals(1.5, testing.getGrowthRate(), 0.001);
    }

    /**
     * Test that default size is applied when given erronous size input
     */
    @Test(expected = NegativeArraySizeException.class)
    public void instantiateWithNegativeSize(){
        @SuppressWarnings("unused")
        Sequence<Integer> testing = new Sequence<Integer>(-10);
    }

    /**
     * Test ascending and descending sort function
     * 
     * order = [3, 5, 10, 20]
     * order = [20, 10, 5, 3]
     */
    @Test
    public void testSort() throws NoSuchMethodException{
        Sequence<Integer> test = TestUtils.generateSequenceFourRandomNumbers();

        // Test regular sort
        test = test.sortCopy((a, b) -> a - b); // default is ascending
        assertEquals("[3, 5, 10, 20]", test.toString());

        // Test descending
        test = test.sortCopy((a, b) -> b - a);
        assertEquals("[20, 10, 5, 3]", test.toString());
    }

    /**
     * Test that when we try to sort without a comparator set it will throw an error
     */
    @Test(expected = IllegalStateException.class)
    public void testEnforceSortWithoutComparatorThrowsError(){
        Sequence<Integer> test = TestUtils.generateSequenceFourRandomNumbers();

        // Test automatic sort after enforceSort becomes true
        test.sortOnwards();
    }

    /**
     * Test sorting works
     */
    @Test
    public void testSortOnwards() throws NoSuchMethodException{
        Sequence<Integer> test = TestUtils.generateSequenceFourRandomNumbers();

        // Test automatic sort after enforceSort becomes true
        test.setComparator((a, b) -> a - b);
        test.sortOnwards();
        assertEquals("[3, 5, 10, 20]", test.toString());

        // Test automatic sort on insertion
        test.append(4);
        assertEquals("[3, 4, 5, 10, 20]", test.toString());

        // Test automatic sort in descending mode
        test.setComparator((a, b) -> b - a);
        test.sort();
        test.append(15);
        assertEquals("[20, 15, 10, 5, 4, 3]", test.toString());
    }

    /**
     * Test enforce sort when inserting at index 0 and index max
     */
    @Test
    public void testEnforceSortBounds() throws NoSuchMethodException{
        Sequence<Integer> test = TestUtils.generateSequenceFourRandomNumbers();

        // Test automatic sort after enforceSort becomes true
        test.setComparator((a, b) -> a - b);
        test.sortOnwards();
        assertEquals("[3, 5, 10, 20]", test.toString());

        test.append(2); // Should insert into the first position
        assertEquals("[2, 3, 5, 10, 20]", test.toString());

        test.append(25); // Should insert into the last position
        assertEquals("[2, 3, 5, 10, 20, 25]", test.toString());
    }

    /**
     * Test equals works for when they are the same and when they aren't
     */
    @Test
    public void testEquals(){
        // Create two identical sequences
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        Sequence<Integer> test1 = new Sequence<>();
        test1.append(10);

        // Test they are the same
        assertTrue(test.equals(test1));

        // Make one not identical
        test1.append(20);

        // Test they are not the same
        assertFalse(test.equals(test1));
    }

    /**
     * Test the contains method does and doesn't contain different values
     */
    @Test
    public void testContains() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);

        assertTrue(test.contains(10));
        assertFalse(test.contains(20));
    }

    /**
     * Test getting the first index of a value and when the value doesn't exist
     */
    @Test
    public void testFirstIndex() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.append(45);
        test.append(20);

        assertEquals(1, (int) test.firstIndexOf(20));
        assertNull(test.firstIndexOf(50));
    }

    /**
     * Test that stop sorting stops sorting the method
     */
    @Test
    public void testStopSorting() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.append(45);
        test.append(20);

        test.setComparator((a, b) -> a - b);
        test.sortOnwards();
        assertEquals("[10, 15, 20, 20, 30, 45]", test.toString());

        test.stopSorting();
        test.append(5);
        assertEquals("[10, 15, 20, 20, 30, 45, 5]", test.toString());
    }

    /**
     * Test that the enqueue and push methods work with sorting
     */
    @Test
    public void testSortingEnqueuePush() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.append(45);
        test.append(20);

        test.setComparator((a, b) -> a - b);
        test.sortOnwards();
        assertEquals("[10, 15, 20, 20, 30, 45]", test.toString());

        test.enqueue(5);
        assertEquals("[5, 10, 15, 20, 20, 30, 45]", test.toString());

        test.push(25);
        assertEquals("[5, 10, 15, 20, 20, 25, 30, 45]", test.toString());
    }

    /**
     * Test that we get all indexes of a value
     */
    @Test
    public void testallIndexOf() {
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

        assertArrayEquals(testArry, test.allIndexesOf(20));
        assertEquals(null, test.allIndexesOf(50));
    }

    /**
     * Test the raw string method (Prints nulls)
     */
    @Test
    public void testRawString() {
        Sequence<Integer> test = new Sequence<>(10);
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.append(45);
        test.append(20);

        assertEquals("[10, 20, 15, 30, 45, 20, null, null, null, null]", test.rawString());
    }

    /**
     * Test that we can get the custom growth rate
     */
    @Test
    public void testGetGrowthRate() {
        Sequence<Integer> test = new Sequence<>();
        assertEquals(1.5, test.getGrowthRate(), 0.001);
    }

    /**
     * Test getting the sub array from the sequence
     */
    @Test
    public void testGetSubArray() {
        Sequence<Integer> test = new Sequence<>(7);
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.append(45);
        test.append(20);

        Integer[] subArray = new Integer[7];
        subArray[0] = 10;
        subArray[1] = 20;
        subArray[2] = 15;
        subArray[3] = 30;
        subArray[4] = 45;
        subArray[5] = 20;
        assertArrayEquals(subArray, test.getSubArray());
    }

    /**
     * Test that we can iterate using a forEach loop
     */
    @Test
    public void testIteratorForEach() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.append(45);
        test.append(20);

        Integer[] arr = new Integer[6];
        arr[0] = 10;
        arr[1] = 20;
        arr[2] = 15;
        arr[3] = 30;
        arr[4] = 45;
        arr[5] = 20;

        int i = 0;
        for (Integer val : test) {
            assertEquals(arr[i], val);
            i++;
        }
    }

    /**
     * Test iterator with hasNext method
     */
    @Test
    public void testIteratorHasNext() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.append(45);
        test.append(20);

        Integer[] arr = new Integer[6];
        arr[0] = 10;
        arr[1] = 20;
        arr[2] = 15;
        arr[3] = 30;
        arr[4] = 45;
        arr[5] = 20;

        Iterator<Integer> iter = test.iterator();

        int i = 0;
        while (iter.hasNext()) {
            assertEquals(arr[i], iter.next());
            i++;
        }
    }

    /**
     * Check we expand correctly with UserNulls in the array
     */
    @Test
    public void testExpandingUserNulls() {
        Sequence<Integer> test = new Sequence<>(4, 1.5);
        test.append(10);
        test.append(20);
        test.append(null);
        assertEquals(4, test.rawLength()); // RingBuffer expands here
        test.append(30);
        // Default expands here
        test.append(45);

        assertEquals(6, test.rawLength());
        assertEquals("[10, 20, null, 30, 45]", test.toString());
    }

    /**
     * Test that we can check contains when looking for null
     */
    @Test
    public void testContainsNull() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(null);
        test.append(30);

        assertTrue(test.contains(null));
    }

    /**
     * Test that we can find firstIndex of null
     */
    @Test
    public void testFindFirstIndexNull() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(null);
        test.append(30);

        assertEquals(2, (int) test.firstIndexOf(null));
    }

    /**
     * Test we can find all indexes of null
     */
    @Test
    public void testFindAllIndexesOf() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(null);
        test.append(30);
        test.append(null);
        test.append(20);

        int[] testArry = new int[2];
        testArry[0] = 2;
        testArry[1] = 4;

        assertArrayEquals(testArry, test.allIndexesOf(null));
    }

    @Test
    public void testAppendNullWhileSorting() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.append(45);
        test.append(20);

        test.setComparator((a, b) -> a - b);
        test.sortOnwards();
        assertEquals("[10, 15, 20, 20, 30, 45]", test.toString());

        test.append(null);
        assertEquals("[10, 15, 20, 20, 30, 45, null]", test.toString());
    }

    @Test
    public void testContainsWhileSorting() {
        Sequence<Integer> test = new Sequence<>();
        test.append(10);
        test.append(20);
        test.append(15);
        test.append(30);
        test.append(45);
        test.append(20);

        test.setComparator((a, b) -> a - b);
        test.sortOnwards();
        assertEquals("[10, 15, 20, 20, 30, 45]", test.toString());

        test.append(null);
        assertTrue(test.contains(null));
    }
}
