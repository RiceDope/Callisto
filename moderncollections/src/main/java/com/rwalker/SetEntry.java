package com.rwalker;

/**
 * A single entry in a set.
 * This supports insertion order via a doubly linked list.
 * 
 * @author Rhys Walker
 * @version 18/01/2025
 */

@SuppressWarnings("unchecked")
public class SetEntry<E> {
    private E value; // The value being held there
    private SetEntry<E> next; // The next entry in the bucket

    private SetEntry<E> nextInsertion; // The next entry in the insertion order
    private SetEntry<E> previousInsertion; // The previous entry in the insertion order

    public SetEntry(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public SetEntry<E> getNext() {
        return next;
    }

    public void setNext(SetEntry<E> next) {
        this.next = next;
    }

    public void setNextInsertion(SetEntry<E> nextInsertion) {
        this.nextInsertion = nextInsertion;
    }

    public SetEntry<E> getNextInsertion() {
        return nextInsertion;
    }

    public void setPreviousInsertion(SetEntry<E> previousInsertion) {
        this.previousInsertion = previousInsertion;
    }

    public SetEntry<E> getPreviousInsertion() {
        return previousInsertion;
    }

    public void clear() {
        next = null;
        nextInsertion = null;
        previousInsertion = null;
        value = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        SetEntry<E> other = (SetEntry<E>) obj;
        return value.equals(other.value);
    }
}
