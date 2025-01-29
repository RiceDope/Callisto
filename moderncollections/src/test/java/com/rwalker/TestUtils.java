package com.rwalker;

/**
 * General utilities to aid in testing
 * 
 * @author Rhys Walker
 */

public class TestUtils {
    
    /**
     * Create an empty Integer Sequence
     * @return An empty Integer Sequence
     */
    public static Sequence<Integer> generateEmptySequence(){

        return new Sequence<Integer>();

    }

    /**
     * Adds ten integers to given list
     * 
     * Order = [100, 52, 250, 5, 112, 1052, 9, 100, 3, 52]
     * 
     * @param mySeq Sequence to add Integers to
     * @return Sequence with integers added
     */
    public static Sequence<Integer> addTenInts(Sequence<Integer> mySeq) {

        mySeq.add(100);
        mySeq.add(52);
        mySeq.add(250);
        mySeq.add(5);
        mySeq.add(112);
        mySeq.add(1052);
        mySeq.add(9);
        mySeq.add(100);
        mySeq.add(3);
        mySeq.add(52);

        return mySeq;
    }

    /**
     * Generates a small full sequence of initial size 4
     * 
     * order = [1, 2, 3, 4]
     * @return Full array of size 4
     */
    public static Sequence<Integer> generateFullSmallSequence(){

        Sequence<Integer> testing = new Sequence<Integer>(4);

        testing.add(1);
        testing.add(2);
        testing.add(3);
        testing.add(4);

        return testing;
    }

    /**
     * Generate a sequence and add ten items to the queue
     * order = [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]
     * @return The queue with ten items added
     */
    public static Sequence<Integer> generateEnqueueTenItems() {
        Sequence<Integer> testing = TestUtils.generateEmptySequence();
        testing.enqueue(5);
        testing.enqueue(10);
        testing.enqueue(15);
        testing.enqueue(20);
        testing.enqueue(25);
        testing.enqueue(30);
        testing.enqueue(35);
        testing.enqueue(40);
        testing.enqueue(45);
        testing.enqueue(50);
        return testing;
    }

    /**
     * Generate a sequence with four numbers in a "random" order
     * 
     * order = [3, 5, 10, 20] ASC
     * order = [20, 10, 5, 3] DESC
     * @return A sequence with four integers
     */
    public static Sequence<Integer> generateSequenceFourRandomNumbers(){
        Sequence<Integer> test = TestUtils.generateEmptySequence();
        test.add(5);
        test.add(3);
        test.add(10);
        test.add(20);
        return test;
    }
}
