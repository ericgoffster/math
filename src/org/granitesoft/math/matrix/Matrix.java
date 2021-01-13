package org.granitesoft.math.matrix;

/**
 * Represents the generic 2-dimensional matrix of elements.
 * @author egoff
 *
 * @param <T> The element type
 */
public interface Matrix<T> {
    /**
     * Get the row for the row index.
     * @param row The row index.
     * @return the row for the row index.
     */
    Vector<T> getRow(int row);

    /**
     * Get the column for the column index.
     * @param column The column index.
     * @return the column for the column index.
     */
    Vector<T> getColumn(int column);

    /**
     * Get the height of the matrix.
     * @return the height of the matrix
     */
    int getHeight();
    
    /**
     * Get the width of the matrix.
     * @return the width of the matrix
     */
    int getWidth();    
}
