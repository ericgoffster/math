package org.granitesoft.math.matrix;

public class TripleVector<T> extends AbstractVector<T> {
    final T zero;
    final T val1;
    final T val2;
    final T val3;
    final int pos1;
    final int pos2;
    final int pos3;

    public TripleVector(int size, T zero, int pos1, T val1, int pos2, T val2, int pos3, T val3) {
        super(size, new int[] {pos1, pos2});
        this.zero = zero;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.pos3 = pos3;
    }
    
    @Override
    public T get(int i) {
        return i == pos1 ? val1 : i == pos2 ? val2 : i == pos3 ? val3 : zero;
    }
}
