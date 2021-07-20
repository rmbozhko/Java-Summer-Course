package edu.summer.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Represents an immutable version of ArrayList. Elements cannot be deleted or modified whilst in the collection.
 * @param <E> is any possible type to be stored in ArrayList
 * @see ArrayList
 */
public class ImmutableArrayList<E> extends ArrayList<E> {
    /**
     * Error message to be displayed when elements' modification operations are called.
     */
    private static final String elementModificationErrorMessage = "Elements deleting and rewriting are not supported.";

    @Override
    public void clear() {
        throw new UnsupportedOperationException(elementModificationErrorMessage);
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException(elementModificationErrorMessage);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException(elementModificationErrorMessage);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(elementModificationErrorMessage);
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException(elementModificationErrorMessage);
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException(elementModificationErrorMessage);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(elementModificationErrorMessage);
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException(elementModificationErrorMessage);
    }
}
