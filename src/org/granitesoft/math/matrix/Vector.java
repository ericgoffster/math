package org.granitesoft.math.matrix;

/**
 * Represents the generic 1-dimensional vector of elements.
 * @author egoff
 *
 * @param <T> The element type
 */
public interface Vector<T> extends Iterable<T> {
    /**
     * Get the element with the specified position.
     * @param i The index
     * @return the element with the specified position.
     */
    T get(int o);

    /**
     * Get the size of the vector.
     * @return the size of the vector
     */
    int getSize();
    
    int[] nonZeroValues();
}
