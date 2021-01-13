package org.granitesoft.math.matrix;

import java.lang.reflect.Array;

public class MatrixWrapper<T> {
    private final Object members;
    
    public MatrixWrapper(Object arr) {
        this.members = arr;
    }
    public MatrixWrapper(int width, int height) {
        this.members = Array.newInstance(Object.class, height, width);
    }
    
    public int getHeight() {
        return Array.getLength(members);
    }
    
    public int getWidth() {
        return Array.getLength(Array.get(members, 0));
    }
    
    public T get(int row, int col) {
        return (T)Array.get(Array.get(members, row), col);
    }
    
    public void set(int row, int col, T v) {
        Array.set(Array.get(members, row), col, v);
    }

    public void swapRows(int row1, int row2) {
        Object l = Array.get(members, row1);
        Array.set(members, row1, Array.get(members, row2));
        Array.set(members, row2, l);
    }
}
