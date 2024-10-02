package com.rwalker;

// Junit tings
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

// Test the functionality of sequence
// import com.rwalker.Sequence;

/**
 * Unit test for ArrayList functionality of Sequence
 */
public class ArrayListTest {
    
    @Test
    public void testCreation() {
        Sequence<String> testSequence = new Sequence<String>();
        assertNotNull(testSequence);
    }

}
