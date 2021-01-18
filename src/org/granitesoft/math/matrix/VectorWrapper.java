package org.granitesoft.math.matrix;

import java.lang.reflect.Array;

public class VectorWrapper<T> {
    private final Object members;
    
    public VectorWrapper(Object arr) {
        this.members = arr;
    }
    
    public int getSize() {
        return Array.getLength(members);
    }
    
    public T get(int row) {
        return (T)Array.get(members, row);
    }
    
    public void set(int row, T v) {
        Array.set(members, row, v);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i = 0; i < getSize(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(get(i));                
        }
        sb.append("}");
        return sb.toString();
    }
}
