package org.granitesoft.math.matrix;

import java.util.List;

public class MatrixWrapper<T> {
    private final List<VectorWrapper<T>> members;
    
    public MatrixWrapper(List<VectorWrapper<T>> arr) {
        this.members = arr;
    }
    
    public int getHeight() {
        return members.size();
    }
    
    public int getWidth() {
        return members.get(0).getSize();
    }
    
    public VectorWrapper<T> getRow(int row) {
        return members.get(row);
    }
    
    public void swapRows(int row1, int row2) {
        if (row1 != row2) {
            VectorWrapper<T> l = members.get(row1);
            members.set(row1, members.get(row2));
            members.set(row2, l);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i = 0; i < getHeight(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(getRow(i));
        }
        sb.append("}");
        return sb.toString();
    }
}
