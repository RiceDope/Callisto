package com.rwalker;

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

}
