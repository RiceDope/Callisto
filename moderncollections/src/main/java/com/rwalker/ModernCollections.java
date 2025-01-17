package com.rwalker;

/**
 * Each collection in the library must use this so they can all be used in methods such as addAll
 */

import java.util.Iterator;

public interface ModernCollections <E> {
    
    Iterator<E> iterator();

}
