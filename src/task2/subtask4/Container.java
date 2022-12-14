package task2.subtask4;

import java.util.Iterator;

public interface Container extends Iterable<Object> {
    // Removes all the elements.
    void clear();

    // Returns the number of elements.
    int size();

    // Returns a string representation of this container.
    String toString();

    // Returns an iterator over elements.
    // Iterator must implement the remove method.
    Iterator<Object> iterator();
}