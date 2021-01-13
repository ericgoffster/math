package org.granitesoft.math.matrix;

import java.util.Iterator;

public abstract class AbstractVector<T> implements Vector<T> {
    final int size;
    final int[] nonZeroValues;

    @Override
    public int getSize() {
        return size;
    }

    public AbstractVector(int size, int[] nonZeroValues) {
        super();
        this.size = size;
        this.nonZeroValues = nonZeroValues;
    }
 
    @Override
    public int[] nonZeroValues() {
        return nonZeroValues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int j = 0; j < size; j++) {
            if (j > 0) {
                sb.append(",");
            }
            sb.append(get(j));
        }
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public T next() {
                return get(i++);
            }            
        };
    }
}
