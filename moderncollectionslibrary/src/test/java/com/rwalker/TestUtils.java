package com.rwalker;

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
    public static Sequence<Integer> addTenInts(Sequence<Integer> mySeq){

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
    public static Sequence<Integer> generateFullSmallSequence(){

        Sequence<Integer> testing = new Sequence<Integer>(4);

        testing.append(1);
        testing.append(2);
        testing.append(3);
        testing.append(4);

        return testing;
    }
}
