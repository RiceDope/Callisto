package com.rwalker;

/**
 * Tests for the queue functionality of Sequence.java
 * 
 * @author Rhys Walker
 */

// Junit tings
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QueueTest {
    
    /**
     * Test adding 10 items to a queue
     * order = [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]
     */
    @Test
    public void testEnqueue() {
        Sequence<Integer> testing = TestUtils.generateEnqueueTenItems();
        assertEquals("[5, 10, 15, 20, 25, 30, 35, 40, 45, 50]", testing.toString());
    }

    /**
     * Test dequeuing and check the output
     */
    @Test
    public void testDequeue(){
        Sequence<Integer> testing = TestUtils.generateEnqueueTenItems();
        int out = testing.dequeue();
        assertEquals(5, out);
        out = testing.dequeue();
        assertEquals(10, out);
    }

    /**
     * Test dequeuing on an empty queue
     */
    @Test(expected = NullPointerException.class)
    public void testEmptyDequeue(){
        Sequence<Integer> testing = TestUtils.generateEmptySequence();
        assertEquals(null, testing.dequeue());
    }

    /**
     * Test peek twice in order to check it doesnt remove and stays consistent
     */
    @Test
    public void testPeek() throws NoSuchMethodException{
        Sequence<Integer> testing = TestUtils.generateEnqueueTenItems();
        int out = testing.peek(HowToFunction.QUEUE);
        assertEquals(5, out);
        out = testing.peek(HowToFunction.QUEUE);
        assertEquals(5, out);
    }

    /**
     * Test peek on an empty queue
     */
    @Test(expected = NullPointerException.class)
    public void testPeekEmpty() {
        Sequence<Integer> testing = TestUtils.generateEmptySequence();
        testing.peek(HowToFunction.QUEUE);
    }

    /**
     * Test isEmpty when queue is empty
     */
    @Test
    public void testIsEmptyEmpty(){
        Sequence<Integer> testing = new Sequence<>();
        assertTrue(testing.isEmpty());
    }

    /**
     * Test isEmpty when queue is empty
     */
    @Test
    public void testIsEmptyNotEmpty(){
        Sequence<Integer> testing = new Sequence<>();
        testing.enqueue(10);
        assertFalse(testing.isEmpty());
    }

    /**
     * Test enqueue null
     */
    @Test
    public void testEnqueueNull() {
        Sequence<Integer> testing = new Sequence<>();
        testing.enqueue(null);
        assertEquals("[null]", testing.toString());
    }

    /**
     * Test dequeue null
     */
    @Test
    public void testDequeueNull() {
        Sequence<Integer> testing = new Sequence<>();
        testing.enqueue(null);
        assertEquals(null, testing.dequeue());
    }
}
