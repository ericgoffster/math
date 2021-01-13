package org.granitesoft.math.matrix;

public class ZeroVector<T> extends AbstractVector<T> {
    final T zero;

    public ZeroVector(int size, T zero) {
        super(size, new int[0]);
        this.zero = zero;
    }
    
    @Override
    public T get(int i) {
        return zero;
    }
}
