package org.granitesoft.math.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

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

    VirtualMatrix(List<Vector<T>> byRow, List<Vector<T>> byCol) {
        super(byCol.size(), byRow.size());
        this.byRow = byRow;
        this.byCol = byCol;
    }
    
    static <T> Matrix<T> upperBiDiagonal(final Vector<T> diagonal, final Vector<T> upper, ExpField<T> field, int width, int height) {
        T zero = field.valueOf(0);
        List<Vector<T>> rows = new ArrayList<Vector<T>>();
        List<Vector<T>> cols = new ArrayList<Vector<T>>();
        for(int row = 0; row < height; row++) {
            if (row == height - 1) {
                rows.add(new ScalarVector<T>(width, zero, row, diagonal.get(row)));
            } else {
                rows.add(new DoubleVector<T>(width, zero, row, diagonal.get(row), row + 1, upper.get(row)));
            }
        }
        for(int col = 0; col < width; col++) {
            if (col == 0) {
                cols.add(new ScalarVector<T>(width, zero, col, diagonal.get(col)));
            } else {
                cols.add(new DoubleVector<T>(width, zero, col - 1, upper.get(col - 1), col, diagonal.get(col)));
            }
        }
        return new VirtualMatrix<T>(rows, cols);
    }
    static <T> Matrix<T> lowerBiDiagonal(final Vector<T> diagonal, final Vector<T> lower, ExpField<T> field, int width, int height) {
        T zero = field.valueOf(0);
        List<Vector<T>> rows = new ArrayList<Vector<T>>();
        List<Vector<T>> cols = new ArrayList<Vector<T>>();
        for(int row = 0; row < height; row++) {
            if (row == 0) {
                rows.add(new ScalarVector<T>(width, zero, row, diagonal.get(row)));
            } else if (row == height - 1) {
                rows.add(new DoubleVector<T>(width, zero, row - 1, lower.get(row - 1), row, diagonal.get(row)));
            } else {
                rows.add(new DoubleVector<T>(width, zero, row - 1, lower.get(row - 1), row, diagonal.get(row)));
            }
        }
        for(int col = 0; col < width; col++) {
            if (col == 0) {
                cols.add(new DoubleVector<T>(width, zero, col, diagonal.get(col), col + 1, lower.get(col)));
            } else if (col == height - 1) {
                cols.add(new ScalarVector<T>(width, zero, col, diagonal.get(col)));
            } else {
                cols.add(new DoubleVector<T>(width, zero, col, diagonal.get(col), col + 1, lower.get(col)));
            }
        }
        return new VirtualMatrix<T>(rows, cols);
    }
    static <T> Matrix<T> triDiagonal(final Vector<T> diagonal, final Vector<T> upper, final Vector<T> lower, ExpField<T> field, int width, int height) {
        T zero = field.valueOf(0);
        List<Vector<T>> rows = new ArrayList<Vector<T>>();
        List<Vector<T>> cols = new ArrayList<Vector<T>>();
        for(int row = 0; row < height; row++) {
            if (row == 0) {
                rows.add(new DoubleVector<T>(width, zero, row, diagonal.get(row), row + 1, upper.get(row)));
            } else if (row == height - 1) {
                rows.add(new DoubleVector<T>(width, zero, row - 1, lower.get(row - 1), row, diagonal.get(row)));
            } else {
                rows.add(new TripleVector<T>(width, zero, row - 1, lower.get(row - 1), row, diagonal.get(row), row + 1, upper.get(row)));
            }
        }
        for(int col = 0; col < width; col++) {
            if (col == 0) {
                cols.add(new DoubleVector<T>(width, zero, col, diagonal.get(col), col + 1, lower.get(col)));
            } else if (col == height - 1) {
                cols.add(new DoubleVector<T>(width, zero, col - 1, upper.get(col - 1), col, diagonal.get(col)));
            } else {
                cols.add(new TripleVector<T>(width, zero, col - 1, upper.get(col - 1), col, diagonal.get(col), col + 1, lower.get(col)));
            }
        }
        return new VirtualMatrix<T>(rows, cols);
    }
    static <T> Matrix<T> diagonal(final Vector<T> diagonal, ExpField<T> field, int width, int height) {
        T zero = field.valueOf(0);
        List<Vector<T>> rows = new ArrayList<Vector<T>>();
        List<Vector<T>> cols = new ArrayList<Vector<T>>();
        for(int row = 0; row < height; row++) {
            rows.add(new ScalarVector<T>(width, zero, row, diagonal.get(row)));
        }
        for(int col = 0; col < width; col++) {
            cols.add(new ScalarVector<T>(height, zero, col, diagonal.get(col)));
        }
        return new VirtualMatrix<T>(rows, cols);
    }
    
    static <T> Matrix<T> scalar(T val, ExpField<T> field, int width, int height) {
        T zero = field.valueOf(0);
        List<Vector<T>> rows = new ArrayList<Vector<T>>();
        List<Vector<T>> cols = new ArrayList<Vector<T>>();
        for(int row = 0; row < height; row++) {
            rows.add(new ScalarVector<T>(width, zero, row, val));
        }
        for(int col = 0; col < width; col++) {
            cols.add(new ScalarVector<T>(height, zero, col, val));
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
    
    static int[] merge(int[] a, int[] b, int size) {
        int i = 0;
        int j = 0;
        int k = 0;
        int[] c = new int[size];
        while(i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                c[k++] = a[i++];
            } else if (a[i] > b[j]) {
                c[k++] = b[j++];
            } else {
                c[k++] = a[i++];
                j++;
            }
        }
        while(i < a.length) {
            c[k++] = a[i++];
        }
        while(j < b.length) {
            c[k++] = b[j++];
        }
        return Arrays.copyOf(c, k);
    }

    // It is a requirement here that
    // f(0,0) == 0
    static <T> VirtualMatrix<T> map(Matrix<T> x, Matrix<T> y, BinaryOperator<T> f) {
        final int height = x.getHeight();
        final int width = y.getWidth();
        final ArrayList<Vector<T>> rows = new ArrayList<>(height);
        final ArrayList<Vector<T>> cols = new ArrayList<>(width);
        for(int row = 0; row < height; row++) {
            Vector<T> rwx = x.getRow(row);
            Vector<T> rwy = y.getRow(row);
            int[] merged = merge(rwx.nonZeroValues(), rwy.nonZeroValues(), width);
            rows.add(new VirtualVector<T>(width, col -> f.apply(rwx.get(col), rwy.get(col)), merged));
        }
        for(int col = 0; col < width; col++) {
            Vector<T> clx = x.getColumn(col);
            Vector<T> cly = y.getColumn(col);
            int[] merged = merge(clx.nonZeroValues(), cly.nonZeroValues(), height);
            cols.add(new VirtualVector<T>(height, row -> f.apply(clx.get(row), cly.get(row)), merged));
        }
        return new VirtualMatrix<T>(rows, cols);
    }
 
    // It is a requirement here that
    // f(0) == 0
    static <T, U> VirtualMatrix<T> map(Matrix<U> src, Function<U, T> f) {
        final int height = src.getHeight();
        final int width = src.getWidth();
        final List<Vector<T>> rows = new ArrayList<>(height);
        final List<Vector<T>> cols = new ArrayList<>(width);
        for(int row = 0; row < height; row++) {
            final Vector<U> rw = src.getRow(row);
            rows.add(new VirtualVector<T>(width, col -> f.apply(rw.get(col)), rw.nonZeroValues()));
        }
        for(int col = 0; col < width; col++) {
            final Vector<U> cl = src.getColumn(col);
            cols.add(new VirtualVector<T>(height, row -> f.apply(cl.get(row)), cl.nonZeroValues()));
        }
        return new VirtualMatrix<T>(rows, cols);
    }

    static <T> VirtualMatrix<T> create(MatrixWrapper<T> arr, boolean[][] isNonZero) {
        int height = arr.getHeight();
        int width = arr.getWidth();
        List<Vector<T>> rows = new ArrayList<>(height);
        List<Vector<T>> cols = new ArrayList<>(width);
        for(int row = 0; row < height; row++) {
            boolean[] inz = isNonZero[row];
            int[] nz = new int[width];
            int j = 0;
            for(int col = 0; col < width; col++) {
                if (inz[col]) {
                    nz[j++] = col;
                }
            }
            final VectorWrapper<T> r = arr.getRow(row);
            rows.add(new VirtualVector<T>(width, col -> r.get(col), Arrays.copyOf(nz, j)));
        }
        for(int col = 0; col < width; col++) {
            int[] nz = new int[height];
            int j = 0;
            for(int row = 0; row < height; row++) {
                boolean[] inz = isNonZero[row];
                if (inz[col]) {
                    nz[j++] = row;
                }
            }
            final int c = col;
            cols.add(new VirtualVector<T>(height, row -> arr.getRow(row).get(c), Arrays.copyOf(nz, j)));
        }
        return new VirtualMatrix<T>(rows, cols);
    }
}
