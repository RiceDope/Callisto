package com.rwalker;

/**
 * Interface for all the ModernCollections classes.
 * 
 * Set and Sequence have another interface they both implement as well as this.
 * 
 * @author Rhys Walker
 * @since 17/01/2025
 */

import java.util.Iterator;

public interface ModernCollections <E> {
    
    Iterator<E> iterator();
    boolean contains(E e);
    boolean isEmpty();
    void clear();
    int size();

}
