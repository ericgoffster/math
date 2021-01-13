package org.granitesoft.math.matrix;

public abstract class AbstractMatrix<T> implements Matrix<T> {
    final int width;
    final int height;
    
    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public AbstractMatrix(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i = 0; i < height; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(getRow(i));
        }
        sb.append("}");
        return sb.toString();
    }
}
