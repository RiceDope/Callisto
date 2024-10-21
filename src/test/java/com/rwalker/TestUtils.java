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
    public static Sequence<Integer> addTenInts(Sequence<Integer> mySeq) throws NoSuchMethodException{

        mySeq.append(100);
        mySeq.append(52);
        mySeq.append(250);
        mySeq.append(5);
        mySeq.append(112);
        mySeq.append(1052);
        mySeq.append(9);
        mySeq.append(100);
        mySeq.append(3);
        mySeq.append(52);

        return mySeq;
    }

    /**
     * Generates a small full sequence of initial size 4
     * 
     * order = [1, 2, 3, 4]
     * @return Full array of size 4
     */
    public static Sequence<Integer> generateFullSmallSequence() throws NoSuchMethodException{

        Sequence<Integer> testing = new Sequence<Integer>(4);

        testing.append(1);
        testing.append(2);
        testing.append(3);
        testing.append(4);

        return testing;
    }

    /**
     * Generate a sequence and add ten items to the queue
     * order = [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]
     * @return The queue with ten items added
     */
    public static Sequence<Integer> generateEnqueueTenItems() throws NoSuchMethodException{
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
        test.append(5);
        test.append(3);
        test.append(10);
        test.append(20);
        return test;
    }
}
