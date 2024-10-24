package com.rwalker.Sample;

import com.rwalker.Sequence;

/**
 * This file is intended to define a general ruleset to be used when working with the Sequence class
 * 
 * Despite the class being relatively dynamic there are some rules as to the use of certain features so
 * that you get the correct functionality.
 * 
 * Rules about Objects this can store;
 *      Anything can be stored by Sequence
 *      Any sorting must take a comparator. Either lambda or class types
 *      All objects must override .equals()
 *      All objects must override .toString()
 * 
 *      Check Student.java for an example of a "good" object for Sequence
 * 
 * @author Rhys Walker
 * Updated: 24/10/24
 * Created: 23/10/24
 */

public class SequenceHowTo {

    public static void main(String[] args){

        // Choose what you would like to run
        // sequenceArray();
        // sortingAndSortedArray();
        // sequenceQueue();
        // sequenceStack();
        // constructorsAdvancedFunctions();
        // generalPurposeFunctions();
    }
    
    /**
     * An example of how to use the Sequence as an Array
     */
    public static void sequenceArray(){
        Sequence<String> example = new Sequence<>();

        // Append two items to the array
        example.append("Hello");
        example.append("World!");

        System.out.println(example.toString()); // OUTPUT = [Hello, World!]

        System.out.println(example.length()); // = 2

        /*
         * Insert a term into the position specified
         * 
         * Also works to insert into the next free space. So 2 in this case
         */
        example.insert(1, "Goodbye");

        System.out.println(example.toString()); // OUTPUT = [Hello, Goodbye, World!]

        /*
         * Replace the item at that index
         * 
         * Will throw an error if null used. Don't use this to remove
         */
        example.replace(2, "The fool on the hill");

        System.out.println(example.toString()); // OUTPUT = [Hello, Goodbye, The fool on the hill]

        // Remove an item at the given index
        example.remove(0);

        System.out.println(example.get(1)); // returns "The fool on the hill"

        // Check if a value is stored in the array
        System.out.println(example.contains("Goodbye"));

        // We can also clear the array (Important to note that this does not remove any comparators or enforcing)
        example.clear();

        // Will return Integer containing the first index. Or null if not found
        example.firstIndexOf("The fool on the hill");

        // Will return Integer[] containing all indexes. Or null if not found
        example.allIndexesOf("The fool on the hill");
    }

    /**
     * Shows usage of sorting an array and enforcing a sorted array
     */
    public static void sortingAndSortedArray(){

        Sequence<Integer> example = new Sequence<>();
        example.append(20);
        example.append(15);
        example.append(30);
        example.append(35);

        // Sort using a comparator as no default set
        example.sort((a, b) -> a - b);

        System.out.println(example.toString());

        // Alternatively set the comparator and then use just sort
        // Example now in descending

        example.setComparator((a, b) -> b - a);
        example.sort();

        System.out.println(example.toString());

        // Now sorting will always be done descending unless we
        // change the comparator

        /*
         * Finally we can choose to enforce sorting
         * 
         * This will change only the append method to work alongside
         * sorting. The items will be inserted in the correct place.
         * 
         * Warning when using enforcing sort and specifying a comparator
         * for a sort check README.md for more details.
         */

        example.setEnforceSort(true);
        example.append(50); // This will now get appended in place

        /*
         * Important to note is that setEnforceSort(true) will not
         * automaticallt sort the list you will need to call sort() to get
         * in order. In this example the list was already in order so it was ok
         */

         System.out.println(example.toString());
    }

    /**
     * Shows the Sequence data type being used as a Queue
     */
    public static void sequenceQueue(){

        Sequence<String> example = new Sequence<>();

        // Enqueue some items
        example.enqueue("Rhys");
        example.enqueue("Nathan");
        example.enqueue("Joel");

        // Dequeue an items
        System.out.println(example.dequeue());

        System.out.println(example.size());

        example.isEmpty(); // Will return false here

        // Pass a enum of type HowToFunction as a queue to peek at the front
        System.out.println(example.peek(Sequence.HowToFunction.QUEUE));
    }

    /**
     * Shows the Sequence data type being used as a Stack
     */
    public static void sequenceStack(){
        
        Sequence<String> example = new Sequence<>();

        // Push some items
        example.push("Hello");
        example.push("World");

        // Peek at the top item
        System.out.println(example.peek(Sequence.HowToFunction.STACK));

        // Then pop an item off the stack
        example.pop();

        // Can also empty the stack
        example.empty();
    }

    /**
     * Gives an overview of the overloaded constructor as well
     * as different methods that allow for fine tuning of how
     * the array works.
     */
    public static void constructorsAdvancedFunctions(){

        /*
         * Different fields in the constructor
         * 
         * Comparator - The comparator used in sorting
         * InitialSize - The initial size of the sub-array (Goes back to when cleared)
         * GrowthRate - How much the sub-array grows by each time it needs to expand
         * 
         * You can specify every comparator in any way you would like and any
         * combination of them.
         */

        Sequence<Integer> example = new Sequence<>(10, 1.5, (a, b) -> a - b);

        // returns the boolean flag enforceSort
        example.getEnforceSort();

        // returns the length of the sub array
        example.rawLength();

        // returns the sub-array as a string (including nulls)
        example.rawString();

        // sets a custom growth rate for the Sequence
        example.setGrowthRate(5.0); // x5 growth
        
        // returns the current growth rate
        example.getGrowthRate();
    }

    /**
     * Gives an overview of the general purpose functions available in Sequence
     */
    public static void generalPurposeFunctions() {

        Sequence<Integer> example1 = new Sequence<>();
        Sequence<Integer> example2 = new Sequence<>();

        example1.append(10);
        example2.append(10);

        // Converts the Sequence into a string calling .toString() on each element
        System.out.println(example1.toString());

        // Are the two Sequences equal
        System.out.println(example1.equals(example2));

        example1.replace(0, 15);
        System.out.println(example1.equals(example2));

    }
}
