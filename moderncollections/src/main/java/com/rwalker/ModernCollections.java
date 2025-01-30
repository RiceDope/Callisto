package com.rwalker;

/**
 * Interface for all the ModernCollections classes.
 * 
 * Implementors:
 *  - Sequence
 *  - Set
 *  - Map
 * 
 * Sub-Interfaces:
 *  - LinearCollection
 * 
 * @author Rhys Walker
 * @since 17-01-2025
 */

import java.util.Iterator;

public interface ModernCollections <E> {
    
    /**
     * Get the iterator from the specific collection
     * @return Iterator<E> the iterator for the collection
     */
    Iterator<E> iterator();

    /**
     * Return the String representation of the collection
     * @return String representation
     */
    String toString();

    /**
     * Check if the object is equal to the collection
     * @param o Object to compare
     * @return boolean if the object is equal to the collection
     */
    boolean equals(Object o);

    /**
     * Check if the collection contains the element
     * @param e Element to check
     * @return boolean if the collection contains the element
     */
    boolean contains(E e);

    /**
     * Check if the collection is empty
     * @return boolean if the collection is empty
     */
    boolean isEmpty();

    /**
     * Clear the collection
     */
    void clear();

    /**
     * Get the size of the collection
     * @return int size of the collection
     */
    int size();

}
