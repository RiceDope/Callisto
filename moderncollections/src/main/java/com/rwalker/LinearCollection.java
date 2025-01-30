package com.rwalker;

/**
 * This interface is specifically for the use of Linear style collections. This extends the ModernCollections interface.
 * 
 * Implementors:
 *  - Sequence
 *  - Set
 * 
 * Note:
 * For collections such as Sequence returning a boolean value is pointless as if the addition is not successful then we would have thrown an error
 * Noting this the decision has been made to always return true for collections that allow duplicates. Or don't fail for any reason other than a
 * internal error.
 * 
 * @author Rhys Walker
 * @since 29-01-2025
 */

 import java.util.Collection;

public interface LinearCollection<E> extends ModernCollections<E> {
    
    /**
     * Add an element to the collection
     * @param element The element to be added
     * @return boolean for use with Set just return True for other Linear types where duplicates are allowed
     */
    boolean add(E element);

    /**
     * Add all elements from the given collection to this collection
     * @param collection ModernCollections<E> any collection that implements this interface
     * @return boolean for use with Set just return True for other Linear types where duplicates are allowed
     */
    boolean addAll(ModernCollections<E> collection);

    /**
     * Add all elements from the given collection to this collection
     * @param collection Collection<E> any collection that implements this interface
     * @return boolean for use with Set just return True for other Linear types where duplicates are allowed
     */
    boolean addAll(Collection<E> collection);

}
