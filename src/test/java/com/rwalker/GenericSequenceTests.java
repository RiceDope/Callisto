package com.rwalker;

import static org.junit.Assert.assertEquals;

/**
 * General tests for Sequence
 */

// Junit tings
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class GenericSequenceTests {
    
    /**
     * Test that a sequence can be made
     */
    @Test
    public void testCreation() {
        assertNotNull(TestUtils.generateEmptySequence());
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
}
