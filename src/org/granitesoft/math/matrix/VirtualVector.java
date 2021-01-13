package org.granitesoft.math.matrix;

import java.util.function.Function;

public class VirtualVector<T> extends AbstractVector<T> {
    final Function<Integer, T> f;

    public VirtualVector(int size, Function<Integer, T> f, int[] nonZeroValues) {
        super(size, nonZeroValues);
        this.f = f;
    }
    
    @Override
    public T get(int i) {
        return f.apply(i);
    }
}
