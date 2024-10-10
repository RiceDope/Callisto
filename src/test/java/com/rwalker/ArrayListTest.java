package com.rwalker;

/**
 * Tests for the ArrayList functionality of Sequence
 * 
 * @author Rhys Walker
 */

// Junit tings
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit test for ArrayList functionality of Sequence
 */
public class ArrayListTest {

    /**
     * Test that adding the 10 items has worked and length function
     */
    @Test
    public void testPopulation() throws NoSuchMethodException{
        Sequence<Integer> testing = TestUtils.addTenInts(TestUtils.generateEmptySequence());
        assertEquals(10, testing.length());
    }

    /**
     * Test removal of two items from the list
     */
    @Test
    public void testRandomRemoval() throws NoSuchMethodException{
        Sequence<Integer> testing = TestUtils.addTenInts(TestUtils.generateEmptySequence());
        testing.remove(4);
        testing.remove(7);
        assertEquals("[100, 52, 250, 5, 1052, 9, 100, 52]", testing.toString());
    }

    /**
     * Test getting the 1st integer
     */
    @Test
    public void testGet() throws NoSuchMethodException{
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();
        int out = testing.get(1);
        assertEquals(2, out);
    }

    /**
     * Test clearing the array
     */
    @Test
    public void testClear() throws NoSuchMethodException{
        Sequence<Integer> testing = TestUtils.addTenInts(TestUtils.generateEmptySequence());
        testing.clear();
        assertEquals("[]", testing.toString());
    }

    /**
     * Test the expansion of the base array as we add terms
     */
    @Test
    public void testExpansion() throws NoSuchMethodException{
        // Generate a small sequence for testing
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();

        assertEquals(4, testing.rawLength());

        // Expansion occurs here (size 6 rate x1.5 default)
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
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();
        testing.setGrowthRate(2.0);
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
    @Test(expected = IllegalArgumentException.class)
    public void instantiateWithNegativeSize(){
        @SuppressWarnings("unused")
        Sequence<Integer> testing = new Sequence<Integer>(-10);
    }

    /**
     * Test insertion at multiple points in the array
     * @throws NoSuchMethodException
     */
    @Test
    public void testInsert() throws NoSuchMethodException{
        Sequence<Integer> testing = new Sequence<Integer>();
        testing.append(5);
        testing.append(10);
        testing.append(15);
        testing.append(20);
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
    public void testOutOfBoundsExpansion() throws NoSuchMethodException{
        Sequence<Integer> testing = new Sequence<Integer>();
        testing.append(5);
        testing.append(10);
        testing.append(15);
        testing.append(20);
        testing.insert(10, 100);
    }

    /**
     * Test that the sub-array expands when inserting into a full array
     * @throws NoSuchMethodException
     */
    @Test
    public void testArrayExpansionWithInsert() throws NoSuchMethodException {
        Sequence<Integer> testing = new Sequence<Integer>(4);
        testing.append(5);
        testing.append(10);
        testing.append(15);
        testing.append(20);
        assertEquals(4, testing.rawLength());
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
    
}
