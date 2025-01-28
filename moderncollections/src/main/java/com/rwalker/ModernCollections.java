package com.rwalker;

/**
 * Each collection in the library must use this so they can all be used in methods such as addAll
 * As well as other methods that each collection should have
 * 
 * @author Rhys Walker
 * @since 28/01/2025
 */

import java.util.Iterator;

public interface ModernCollections <E> {
    
    Iterator<E> iterator();
    boolean contains(E e);
    // isEmpty
    // addAll
    // removeAll
    // removeIf
    // retainAll
    // clear
    // size

}
