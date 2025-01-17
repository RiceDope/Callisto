package com.rwalker;

/**
 * Each collection in the library must use this so they can all be used in methods such as addAll
 * As well as other methods that each collection should have
 */

import java.util.Iterator;

public interface ModernCollections <E> {
    
    Iterator<E> iterator();
    boolean contains(E e);

}
