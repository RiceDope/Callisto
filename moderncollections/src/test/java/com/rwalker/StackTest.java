/**
 * Tests the stack functionality of sequence
 * @author Rhys Walker
 */
package com.rwalker;

// Junit tings
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StackTest {
    
    /**
     * Test pushing items
     */
    @Test
    public void testPush(){
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.push(20);
        assertEquals("[10, 20]", test.toString());
    }

    /**
     * Test poping items
     * @throws NoSuchMethodException
     */
    @Test
    public void testPop(){
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.push(20);
        assertEquals(20, (int) test.pop());
        assertEquals(10, (int) test.pop());
    }

    /**
     * Test pop on an empty list throws error
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testPopEmptyList() {
        Sequence<Integer> test = new Sequence<Integer>();
        test.pop();
    }

    /**
     * Test peek on the stack
     */
    @Test
    public void testPeek(){
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.push(20);
        assertEquals(20, (int) test.peek(Sequence.HowToFunction.STACK));
    }

    /**
     * Test peek on an empty list
     */
    @Test(expected = NullPointerException.class)
    public void testPeekEmpty() {
        Sequence<Integer> test = new Sequence<Integer>();
        test.peek(Sequence.HowToFunction.STACK);
    }

    /**
     * Test the empty method for stack
     */
    @Test
    public void testEmpty(){
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.push(20);
        test.empty();
        assertEquals("[]", test.toString());
    }

    /**
     * Test the size method for stack
     */
    @Test
    public void testSize(){
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(10);
        test.push(20);
        test.size();
        assertEquals(2, test.size());
    }

    /**
     * Test pushing null
     */
    @Test
    public void testPushNull() {
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(null);
        assertEquals("[null]", test.toString());
    }

    /**
     * Test that popping a null value returns a null not a UserNull
     */
    @Test
    public void testPopNull() {
        Sequence<Integer> test = new Sequence<Integer>();
        test.push(null);
        assertEquals(null, test.pop());
    }
}
