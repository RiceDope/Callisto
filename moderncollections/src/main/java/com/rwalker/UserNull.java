package com.rwalker;

/**
 * Dummy object used to represent an absence of a value
 */

public class UserNull<E> {
    public E get() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == null || obj instanceof UserNull;
    }
}
