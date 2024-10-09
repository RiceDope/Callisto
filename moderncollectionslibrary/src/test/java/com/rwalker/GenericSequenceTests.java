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
        test.sort();
        assertEquals("[3, 5, 10, 20]", test.toString());

        // Test descending
        test.setAscending(false);
        test.sort();
        assertEquals("[20, 10, 5, 3]", test.toString());
    }

    /**
     * Test enforce sort
     */
    @Test
    public void testEnforceSort() throws NoSuchMethodException{
        Sequence<Integer> test = TestUtils.generateSequenceFourRandomNumbers();

        // Test automatic sort after enforceSort becomes true
        test.setEnforceSort(true);
        assertEquals("[3, 5, 10, 20]", test.toString());

        // Test automatic sort on insertion
        test.append(4);
        assertEquals("[3, 4, 5, 10, 20]", test.toString());

        // Test automatic sort in descending mode
        test.setAscending(false);
        test.append(15);
        assertEquals("[20, 15, 10, 5, 4, 3]", test.toString());
    }

}
