package com.doctor.commons.core.collection;

import java.util.Iterator;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016.12.06
 *         <p>
 */
public class ArrayIterator<E> implements Iterable<E>, Iterator<E> {

    private final E[] array;
    private int       startIndex;
    private int       endIndex;

    public ArrayIterator(E[] array) {
        this.array = array;
        this.startIndex = 0;
        this.endIndex = array.length;
    }

    public ArrayIterator(E[] array, int offSet, int length) {
        this.array = array;
        this.startIndex = offSet - 1;
        this.endIndex = offSet + length - 1;
    }

    @Override
    public boolean hasNext() {
        return startIndex < endIndex;
    }

    @Override
    public E next() {
        if (startIndex < endIndex) {
            return array[startIndex++];
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();

    }

    @Override
    public Iterator<E> iterator() {
        return this;
    }

}
