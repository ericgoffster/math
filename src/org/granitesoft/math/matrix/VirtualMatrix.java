package org.granitesoft.math.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.granitesoft.math.ExpField;

public class VirtualMatrix<T> extends AbstractMatrix<T> {
    final List<Vector<T>> byRow;
    final List<Vector<T>> byCol;

    @Override
    public Vector<T> getRow(int row) {
        return byRow.get(row);
    }
    
    @Override
    public Vector<T> getColumn(int column) {
        return byCol.get(column);
    }

    private VirtualMatrix(List<Vector<T>> byRow, List<Vector<T>> byCol) {
        super(byCol.size(), byRow.size());
        this.byRow = byRow;
        this.byCol = byCol;
    }
    
    static <T> Matrix<T> fromArray(ExpField<T> field, Object arr) {
        return create(field, new MatrixWrapper<T>(arr));
    }

    static <T> Matrix<T> diagonal(final Vector<T> diagonal, ExpField<T> field, int width, int height) {
        T zero = field.valueOf(0);
        List<Vector<T>> rows = new ArrayList<Vector<T>>();
        List<Vector<T>> cols = new ArrayList<Vector<T>>();
        for(int row = 0; row < height; row++) {
            rows.add(new ScalarVector<T>(width, row, zero, diagonal.get(row)));
        }
        for(int col = 0; col < width; col++) {
            cols.add(new ScalarVector<T>(height, col, zero, diagonal.get(col)));
        }
        return new VirtualMatrix<T>(rows, cols);
    }
    
    static <T> Matrix<T> scalar(T val, ExpField<T> field, int width, int height) {
        T zero = field.valueOf(0);
        List<Vector<T>> rows = new ArrayList<Vector<T>>();
        List<Vector<T>> cols = new ArrayList<Vector<T>>();
        for(int row = 0; row < height; row++) {
            rows.add(new ScalarVector<T>(width, row, zero, val));
        }
        for(int col = 0; col < width; col++) {
            cols.add(new ScalarVector<T>(height, col, zero, val));
        }
        return new VirtualMatrix<T>(rows, cols);
    }
    
    static <T> Matrix<T> zero(ExpField<T> field, int width, int height) {
        T zero = field.valueOf(0);
        List<Vector<T>> rows = new ArrayList<Vector<T>>();
        List<Vector<T>> cols = new ArrayList<Vector<T>>();
        ZeroVector<T> zeroRow = new ZeroVector<T>(width, zero);
        for(int row = 0; row < height; row++) {
            rows.add(zeroRow);
        }
        ZeroVector<T> zeroCol = new ZeroVector<T>(height, zero);
        for(int col = 0; col < width; col++) {
            cols.add(zeroCol);
        }
        return new VirtualMatrix<T>(rows, cols);
    }
    
    static <T> VirtualMatrix<T> create(ExpField<T> field, MatrixWrapper<T> arr) {
        int height = arr.getHeight();
        int width = arr.getWidth();
        List<Vector<T>> rows = new ArrayList<>(height);
        List<Vector<T>> cols = new ArrayList<>(width);
        for(int row = 0; row < height; row++) {
            int[] nz = new int[width];
            int j = 0;
            for(int col = 0; col < width; col++) {
                if (!field.isZero(arr.get(row,  col))) {
                    nz[j++] = col;
                }
            }
            final int r = row;
            rows.add(new VirtualVector<T>(width, col -> arr.get(r, col), Arrays.copyOf(nz, j)));
        }
        for(int col = 0; col < width; col++) {
            int[] nz = new int[height];
            int j = 0;
            for(int row = 0; row < height; row++) {
                if (!field.isZero(arr.get(row,  col))) {
                    nz[j++] = row;
                }
            }
            final int c = col;
            cols.add(new VirtualVector<T>(height, row -> arr.get(row, c), Arrays.copyOf(nz, j)));
        }
        return new VirtualMatrix<T>(rows, cols);
    }
    /**
     * @param field  
     */
    static <T> VirtualMatrix<T> create(int width, int height, ExpField<T> field, BiFunction<Integer, Integer, T> f) {
        MatrixWrapper<T> arr = new MatrixWrapper<T>(width, height);
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                arr.set(row, col, f.apply(row, col));
            }
        }
        return create(field, arr);
    }
}
