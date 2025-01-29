package com.rwalker;

import java.util.Collection;
import java.util.Iterator;

/**
 * Implementation of a Set using hashCode to determine location of objects
 * 
 * @author Rhys Walker
 * @version 10-01-2025
 */

 //TODO: Specialisation for small sets

@SuppressWarnings("unchecked")
public class Set<E> implements Iterable<E>, ModernCollections<E> {
    
    private SetEntry<E>[] items;
    private SetEntry<E> firstInserted; // Beginning of the doubly linked list
    private SetEntry<E> lastInserted; // Most recently inserted item
    private int buckets = 16;
    private int totalItems = 0;
    private double overloadFactor = 1.25;
    private double expansionFactor = 1.5;

    public Set() {
        items = new SetEntry[buckets];
    }

    public Set(int buckets) {
        this.buckets = buckets;
        items = new SetEntry[buckets];
    }

    /**
     * Do we need to rehash the set
     */
    private void doRehash() {
        if (buckets * overloadFactor < totalItems) {
            rehash();
        }
    }

    /**
     * Add an item to the set
     * @param value Value to be added to the set
     * @return boolean True if the set has been modified
     */
    public boolean add(E value) {

        // Check how overloaded the set is
        doRehash();

        if (addInternal(value)){
            totalItems++;
            return true;
        }

        return false;
        
    }

    /**
     * Add an item to the set
     * @param value Value to be added to the set
     * @return boolean True if the set has been modified
     */
    private boolean addInternal(E value){
        int index = getHashCode(value);

        // If we have a completely empty bucket
        if (items[index] == null){
            items[index] = new SetEntry<E>(value);
            addInsetionOrder(items[index]);
            return true;
        }
        else {
            SetEntry<E> current = items[index]; // Set our location in linked list
            while (current.getNext() != null){ // Loop over until we find the next empty spot

                // If we have a duplicate term
                if (current.getValue().equals(value)){
                    return false;
                }

                current = current.getNext();
            }
            if (!current.getValue().equals(value)){ // Only add if item does not exist
                current.setNext(new SetEntry<E>(value));
                addInsetionOrder(current.getNext());
                return true;
            }
        }
        
        // Nothing was added exit with false
        return false;
    }

    /**
     * Add all items in the other collection to the set
     * @param other Other collections items to be added
     * @return boolean True if the set has been modified
     */
    public boolean addAll(ModernCollections<E> other) {

        boolean modified = false;
        Iterator<E> iterator = other.iterator();

        while(iterator.hasNext()){
            if (add(iterator.next())){
                modified = true;
            }
        }

        return modified;
    }

    /**
     * Add all of the terms in the default java collection to the set
     * @param other The Java Collection Framework Collection to add
     * @return
     */
    public boolean addAll(Collection<E> other) {
        boolean modified = false;
        for (E value : other){
            if (add(value)){
                modified = true;
            }
        }
        return modified;
    }    

    /**
     * Remove all operation to be used with other ModernCollections
     * @param other
     * @return
     */
    public boolean removeAll(ModernCollections<E> other) {
        boolean modified = false;
        Iterator<E> iterator = other.iterator();

        while(iterator.hasNext()){
            if (remove(iterator.next())){
                modified = true;
            }
        }

        return modified;
    }

    /**
     * Remove all operation to be used with other Java Collection Framework Collections
     * @param other
     * @return
     */
    public boolean removeAll(Collection<E> other) {
        boolean modified = false;
        for (E value : other){
            if (remove(value)){
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Retain all operation to be used with other ModernCollections
     * @param other
     * @return
     */
    public boolean retainAll(ModernCollections<E> other) {
        boolean modified = false;
        Iterator<E> iterator = iterator();

        while(iterator.hasNext()){
            E value = iterator.next();
            if (!other.contains(value)){
                remove(value);
                modified = true;
            }
        }

        return modified;
    }

    /**
     * Retain all operation to be used with other Java Collection Framework Collections
     * @param other
     * @return
     */
    public boolean retainAll(Collection<E> other) {
        boolean modified = false;
        Iterator<E> iterator = iterator();

        while(iterator.hasNext()){
            E value = iterator.next();
            if (!other.contains(value)){
                remove(value);
                modified = true;
            }
        }

        return modified;
    }

    /**
     * Get the size of the set
     * @return
     */
    public int size() {
        return totalItems;
    }

    /**
     * Remove an item from the set
     * @param value
     */
    public boolean remove(E value) {
        int index = getHashCode(value);
        if (items[index] == null){
            return false;
        }
        if (items[index].getValue().equals(value)){
            removeInsertionOrder(items[index]);
            items[index] = items[index].getNext();
            totalItems--;
            return true;
        }
        SetEntry<E> current = items[index];
        while (current.getNext() != null){
            if (current.getNext().getValue().equals(value)){
                removeInsertionOrder(items[index]);
                current.setNext(current.getNext().getNext());
                totalItems--;
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    /**
     * Is a specific item in the set
     * @param value
     * @return
     */
    public boolean contains(E value) {
        int index = getHashCode(value);
        SetEntry<E> current = items[index];
        while (current != null){
            if (current.getValue().equals(value)){
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Remove all items from the set
     */
    public void clear() {
        items = new SetEntry[buckets];
        totalItems = 0;
    }

    // TODO: ContainsAll
    // TODO: RemoveAll
    // TODO: RetainAll

    public boolean isEmpty() {
        return totalItems == 0;
    }

    private boolean addInsetionOrder(SetEntry<E> entry) {
        if (firstInserted == null) {
            firstInserted = entry;
            lastInserted = firstInserted;
            return true;
        }
        lastInserted.setNextInsertion(entry);
        entry.setPreviousInsertion(lastInserted);
        lastInserted = entry;
        return true;
    }

    private boolean removeInsertionOrder(SetEntry<E> entry) {
        if (entry == firstInserted){
            firstInserted = entry.getNextInsertion();
        }
        if (entry == lastInserted){
            lastInserted = entry.getPreviousInsertion();
        }
        if (entry.getPreviousInsertion() != null){
            entry.getPreviousInsertion().setNextInsertion(entry.getNextInsertion());
        }
        if (entry.getNextInsertion() != null){
            entry.getNextInsertion().setPreviousInsertion(entry.getPreviousInsertion());
        }
        return true;
    }

    /**
     * Get the hashCode within certain bounds for object location
     * @param value
     * @return
     */
    private int getHashCode(E value) {
        int code = value.hashCode()%buckets;
        if (code < 0){
            code = code * -1;
        }
        return code;
    }

    /**
     * Expand the subArray that handles the sets implementation. This is called whenever the set is overloaded
     */
    private void rehash() {
        SetEntry<E>[] oldItems = items;
        buckets = (int) Math.ceil((buckets * expansionFactor));
        items = new SetEntry[buckets];
        for (SetEntry<E> entry : oldItems){
            while (entry != null){
                addInternal(entry.getValue());
                entry = entry.getNext();
            }
        }
    }

    /**
     * Convert the set to a sequence
     * @return Sequence
     */
    public Sequence<E> toSequence() {
        Sequence<E> sequence = new Sequence<E>();
        for (SetEntry<E> entry : items){
            while (entry != null){
                sequence.add(entry.getValue());
                entry = entry.getNext();
            }
        }
        return sequence;
    }

    /**
     * Convert the set to an array
     * @return Array
     */
    public E[] toArray() {
        E[] array = (E[]) new Object[totalItems];
        int index = 0;
        Iterator<E> it = iterator();
        while (it.hasNext()){
            array[index] = it.next();
            index++;
        }
        return array;
    }

    /**
     * Get the string representation of the set with a "|" separator between buckets
     * @return
     */
    public String rawString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (SetEntry<E> entry : items){

            boolean hadItemInBucket = false;

            while (entry != null){
                sb.append(entry.getValue() + " ");
                sb.append(", ");
                entry = entry.getNext();
                hadItemInBucket = true;
            }
            // Tidy up the string
            if (hadItemInBucket){
                sb.replace(sb.length()-2, sb.length(), "");
            }
            sb.append("| ");
        }
        sb.replace(sb.length()-2, sb.length(), "");
        sb.append("]");
        return sb.toString();
    }

    /**
     * Overriden toString method to print out the set
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<E> it = iterator();
        while (it.hasNext()){
            sb.append(it.next());
            sb.append(", ");
        }
        if (size() != 0){
            sb.replace(sb.length()-2, sb.length(), "");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Overriden equals method to compare two sets
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Set<E> other = (Set<E>) obj;
        if (other.size() != totalItems) {
            return false;
        }
        for (SetEntry<E> entry : items) {
            while (entry != null) {
                if (!other.contains(entry.getValue())) {
                    return false;
                }
                entry = entry.getNext();
            }
        }
        return true;
    }

    public Iterator<E> iterator() {
        return new SetIterator();
    }

    class SetIterator implements Iterator<E> {

        private int index = 0;
        private SetEntry<E> current = firstInserted;

        @Override
        public boolean hasNext() {
            if (current != null) {
                return true;
            }
            return false;
        }

        @Override
        public E next() {
            if (current == null){
                for (int i = index+1; i < buckets; i++){
                    if (items[i] != null){
                        index = i;
                        current = items[i];
                        return current.getValue();
                    }
                }
            }
            E value = current.getValue();
            current = current.getNextInsertion();
            return value;
        }
    }
}
