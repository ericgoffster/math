package org.granitesoft.math.matrix;

public class DoubleVector<T> extends AbstractVector<T> {
    final T zero;
    final T val1;
    final T val2;
    final int pos1;
    final int pos2;

    public DoubleVector(int size, T zero, int pos1, T val1, int pos2, T val2) {
        super(size, new int[] {pos1, pos2});
        this.zero = zero;
        this.val1 = val1;
        this.val2 = val2;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }
    
    @Override
    public T get(int i) {
        return i == pos1 ? val1 : i == pos2 ? val2 : zero;
    }
}
