package com.rwalker;

/**
 * Implementation of a Set using hashCode to determine location of objects
 * 
 * @author Rhys Walker
 * @version 10-01-2025
 */

@SuppressWarnings("unchecked")
public class Set<E> {
    
    private SetEntry<E>[] items;
    private int buckets = 16;
    private int totalItems = 0;
    private double overloadFactor = 1.25;

    public Set() {
        items = new SetEntry[buckets];
    }

    public Set(int buckets) {
        this.buckets = buckets;
        items = new SetEntry[buckets];
    }

    /**
     * Add an item to the set
     * @param value
     */
    public void add(E value) {

        // Check how overloaded the set is
        if (totalItems > buckets * overloadFactor){
            rehash();
        }

        totalItems++;
        int index = getHashCode(value);
        if (items[index] == null){
            items[index] = new SetEntry<E>(value);
        }
        else {
            SetEntry<E> current = items[index];
            while (current.getNext() != null){
                current = current.getNext();
            }
            current.setNext(new SetEntry<E>(value));
        }
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
     * Get the hashCode within certain bounds for object location
     * @param value
     * @return
     */
    public int getHashCode(E value) {
        int code = value.hashCode()%buckets;
        if (code < 0){
            code *= -1;
        }
        return code;
    }

    /**
     * Expand the subArray that handles the sets implementation. This is called whenever the set is overloaded
     */
    private void rehash() {
        SetEntry<E>[] oldItems = items;
        buckets *= 2;
        items = new SetEntry[buckets];
        for (SetEntry<E> entry : oldItems){
            while (entry != null){
                add(entry.getValue());
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
            while (entry != null){
                sb.append(entry.getValue() + " ");
                sb.append(", ");
                entry = entry.getNext();
            }
            sb.replace(sb.length()-2, sb.length(), "");
            sb.append("| ");
        }
        sb.append(" ]");
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
}
