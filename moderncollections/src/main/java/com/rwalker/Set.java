package com.rwalker;

import java.util.Iterator;

/**
 * Implementation of a Set using hashCode to determine location of objects
 * 
 * @author Rhys Walker
 * @version 10-01-2025
 */

@SuppressWarnings("unchecked")
public class Set<E> implements Iterable<E> {
    
    private SetEntry<E>[] items;
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
                return true;
            }
        }
        
        // Nothing was added exit with false
        return false;
    }

    /**
     * Add all items in other set to a given set
     * @param other Other set to add items from
     * @return boolean True if the set has been modified
     */
    public boolean addAll(Set<E> other) {

        // Used to track if we have modified on any additions
        Set<Boolean> modifiedSet = new Set<Boolean>(2);

        // Loop over each item in the other set
        for (SetEntry<E> entry : other.items){
            while (entry != null){
                modifiedSet.add(add(entry.getValue()));
                entry = entry.getNext();
            }
        }

        // If there is 1 or more modifications the set will contain true
        if (modifiedSet.contains(true)) {
            return true;
        }

        return false;
    }

    public int size() {
        return totalItems;
    }

    /**
     * Remove an item from the set
     * @param value
     */
    public void remove(E value) {
        int index = getHashCode(value);
        if (items[index] == null){
            return;
        }
        if (items[index].getValue().equals(value)){
            items[index] = items[index].getNext();
            totalItems--;
            return;
        }
        SetEntry<E> current = items[index];
        while (current.getNext() != null){
            if (current.getNext().getValue().equals(value)){
                current.setNext(current.getNext().getNext());
                totalItems--;
                return;
            }
            current = current.getNext();
        }
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

    // TODO: Contains all

    public boolean isEmpty() {
        return totalItems == 0;
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
        for (SetEntry<E> entry : items){
            while (entry != null){
                sb.append(entry.getValue());
                sb.append(", ");
                entry = entry.getNext();
            }
        }
        sb.replace(sb.length()-2, sb.length(), "");
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
        private SetEntry<E> current = items[index];

        @Override
        public boolean hasNext() {
            if (current != null){
                return true;
            }
            for (int i = index+1; i < buckets; i++){
                if (items[i] != null){
                    return true;
                }
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
            current = current.getNext();
            return value;
        }
    }
}
