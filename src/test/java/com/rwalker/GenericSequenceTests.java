package com.rwalker;

// Junit tings
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
        assertNotNull(TestUtils.generateEmptySequence());
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
        test.sort((a, b) -> a - b); // default is ascending
        assertEquals("[3, 5, 10, 20]", test.toString());

        // Test descending
        test.sort((a, b) -> b - a);
        assertEquals("[20, 10, 5, 3]", test.toString());
    }

    /**
     * Test that when we try to sort without a comparator set it will throw an error
     */
    @Test(expected = IllegalStateException.class)
    public void testEnforceSortWithoutComparatorThrowsError(){
        Sequence<Integer> test = TestUtils.generateSequenceFourRandomNumbers();

        // Test automatic sort after enforceSort becomes true
        test.setEnforceSort(true);
        test.sort();
    }

    /**
     * Test enforce sort
     */
    @Test
    public void testEnforceSort() throws NoSuchMethodException{
        Sequence<Integer> test = TestUtils.generateSequenceFourRandomNumbers();

        // Test automatic sort after enforceSort becomes true
        test.setComparator((a, b) -> a - b);
        test.setEnforceSort(true);
        test.sort();
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
        test.setEnforceSort(true);
        test.sort();
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
}
