package com.rwalker;

// Junit tings
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit test for ArrayList functionality of Sequence
 */
public class ArrayListTest {
    
    /**
     * Test that a sequence can be made
     */
    @Test
    public void testCreation() {
        assertNotNull(TestUtils.generateEmptySequence());
    }

    /**
     * Test that adding the 10 items has worked and length function
     */
    @Test
    public void testPopulation() {
        Sequence<Integer> testing = TestUtils.addTenInts(TestUtils.generateEmptySequence());
        assertEquals(10, testing.length());
    }

    /**
     * Test removal of two items from the list
     */
    @Test
    public void testRandomRemoval(){
        Sequence<Integer> testing = TestUtils.addTenInts(TestUtils.generateEmptySequence());
        testing.remove(4);
        testing.remove(7);
        assertEquals("[100, 52, 250, 5, 1052, 9, 100, 52]", testing.toString());
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
     * Test the expansion of the base array as we add terms
     */
    @Test
    public void testExpansion(){
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
    public void testBaseArrayShrink(){

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
    public void testCustomGrowthRate(){
        Sequence<Integer> testing = TestUtils.generateFullSmallSequence();
        testing.setGrowthRate(2.0);
        testing.append(10);
        assertEquals(8, testing.rawLength());
    }

    /**
     * Test that growth rate is handled correctly when given an erronous growth input
     */
    @Test
    public void instantiateWithNegativeGrowth(){
        Sequence<Integer> testing = new Sequence<Integer>(3, -2.0);
        // Use non deprecated method with "error range"
        assertEquals(1.5, testing.getGrowthRate(), 0.001);
    }

    /**
     * Test that default size is applied when given erronous size input
     */
    @Test
    public void instantiateWithNegativeSize(){
        Sequence<Integer> testing = new Sequence<Integer>(-10);
        assertEquals(100, testing.rawLength());
    }
}
