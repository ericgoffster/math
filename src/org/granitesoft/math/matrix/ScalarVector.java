package org.granitesoft.math.matrix;

public class ScalarVector<T> extends AbstractVector<T> {
    final T zero;
    final T val;
    final int pos;

    public ScalarVector(int size, T zero, int pos, T val) {
        super(size, new int[] {pos});
        this.zero = zero;
        this.val = val;
        this.pos = pos;
    }
    
    @Override
    public T get(int i) {
        return i == pos ? val : zero;
    }
}
