package org.to2mbn.basiscommands.utils.command;

import java.util.Iterator;

public class CommandArguments {
    private final Iterator<Object> iterator;

    public CommandArguments(Iterator<Object> _iterator) {
        iterator = _iterator;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public <T> T next() {
        return (T) iterator.next();
    }

    public int nextInteger() {
        return next();
    }

    public String nextString() {
        return next();
    }

    public double nextDouble() {
        return next();
    }
}
