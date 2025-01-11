package com.rwalker;

@SuppressWarnings("unchecked")
public class SetEntry<E> {
    private E value;
    private SetEntry<E> next;

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
