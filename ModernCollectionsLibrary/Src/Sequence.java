/**
 * Script that implements the basic functionality needed for Sequence
 * 
 * @Author Rhys Walker
 * @Since 30/09/2024
 * @Version 0.1
 */

package ModernCollectionsLibrary.Src;

import java.lang.reflect.Array;
import java.lang.StringBuilder;

public class Sequence<E> {

    private E[] defaultList;
    private int index = 0; // Track our current final index

    /* 
        ====================================================
                    OVERLOADED JAVA CONSTRUCTOR             
        ====================================================
    */

    // Used when no default size was used
    @SuppressWarnings("unchecked")
    public Sequence(){
        
        // Instantiate the list with some idea of size
        defaultList = (E[]) new Object[100];

    }

    // Allows for user to specify a starting size of the Array
    @SuppressWarnings("unchecked")
    public Sequence(int size){
        // Instantiate the list with some idea of size
        defaultList = (E[]) new Object[size];
    }

    /* 
        ====================================================
                                END                                      
        ====================================================
    */

    public void append(E item){
        
        defaultList[index] = item;
        index++;

    }

    /**
     * Returns the length of a given list
     * 
     * @return Int length of list
     */
    public int length(){
    
        // For now index points towards the next free index
        return index;
    }

    /**
     * Custom toString method for generics.
     * 
     * @return String form of the array [term1, term2, term3]
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();

        // Add the beginning bracket
        sb.append("[");

        // Add all terms to the StringBuilder
        for (int i = 0; i < index; i++){
            sb.append(defaultList[i] + ", ");
        }
        // Find and remove last comma and space
        int lastComma = sb.lastIndexOf(",");
        sb.delete(lastComma, lastComma+2);

        // Add the final bracket
        sb.append("]");

        // Return the string
        return sb.toString();
    }
} 