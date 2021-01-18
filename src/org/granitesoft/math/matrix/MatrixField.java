package org.granitesoft.math.matrix;

import java.util.ArrayList;
import java.util.List;

import org.granitesoft.math.ExpField;
import org.granitesoft.math.Exponential;
import org.granitesoft.math.MappedField;
import org.granitesoft.math.complex.Complex;
import org.granitesoft.math.complex.ComplexExpField2;

public class MatrixField<R> implements ExpField<Matrix<R>> {
    
    final ExpField<R> f;
    final int size;
    
    public MatrixField(int size, ExpField<R> f) {
        super();
        this.f = f;
        this.size = size;
    }
    
    @Override
    public Matrix<R> valueOf(Number n) {
        return VirtualMatrix.scalar(f.valueOf(n), f, size, size);
    }

    @Override
    public Matrix<R> valueOf(String n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Matrix<R> negate(Matrix<R> x) {
        return VirtualMatrix.map(x, f::negate);
    }

    @Override
    public Matrix<R> multiply(Matrix<R> x, Matrix<R> y) {
        return _multiply(x, y);
    }
    
    @Override
    public Matrix<R> divide(Matrix<R> x, Matrix<R> y) {
        return _divide(x, y);
    }

    @Override
    public Matrix<R> quotient(Matrix<R> x, Matrix<R> y) {
        final Matrix<R> z = divide(x, y);
        return VirtualMatrix.map(z, v -> {
            final R flr = f.floor(v);
            final R ceil = f.ceil(v);
            return f.closerToZero(f.subtract(v, flr), f.subtract(v, ceil)) ? flr : ceil;
        });
    }

    @Override
    public Matrix<R> remainder(Matrix<R> x, Matrix<R> y) {
        return subtract(x, multiply(quotient(x, y), y));
    }

    @Override
    public Matrix<R> floor(Matrix<R> x) {
        return VirtualMatrix.map(x, f::floor);
    }

    @Override
    public Matrix<R> ceil(Matrix<R> x) {
        return VirtualMatrix.map(x, f::ceil);
    }

    @Override
    public Matrix<R> add(Matrix<R> x, Matrix<R> y) {
        return _add(x, y);
    }

    @Override
    public Matrix<R> subtract(Matrix<R> x, Matrix<R> y) {
        return _subtract(x, y);
    }

    @Override
    public boolean closerToZero(Matrix<R> x, Matrix<R> y) {
        return f.closerToZero(pnorm(x, f), pnorm(y, f));
    }
    
    @Override
    public boolean isZero(Matrix<R> x) {
        return f.isZero(pnorm(x, f));
    }

    @Override
    public boolean isNeg(Matrix<R> x) {
        return false;
    }

    @Override
    public MappedField<Matrix<R>, ?> upgradePrecision(int additionalDigits) {
        if (additionalDigits == 0) {
            return new MappedField<Matrix<R>, Matrix<R>>(this, x -> x, x -> x);
        }
        return upit(additionalDigits);
    }
    
    @Override
    public Matrix<R> pow(final Matrix<R> x, final Matrix<R> y) {
        return expf().pow(x, y);
    }

    @Override
    public Matrix<R> sqrt(Matrix<R> x) {
        return Exponential._sqrt(x, this);
    }

    @Override
    public Matrix<R> exp(Matrix<R> x) {
        return Exponential._exp(x, this);
    }
    
    @Override
    public Matrix<R> atan2(Matrix<R> y, Matrix<R> x) {
        return expf().atan2(y, x);
    }

    @Override
    public Matrix<R> asin(Matrix<R> x) {
        return expf().asin(x);
    }

    @Override
    public Matrix<R> acos(Matrix<R> x) {
        return expf().acos(x);
    }

    @Override
    public Matrix<R> atan(Matrix<R> x) {
        return expf().atan(x);
    }

    @Override
    public Matrix<R> log(Matrix<R> x) {
        return Exponential._log(x, this);
    }

    @Override
    public Matrix<R> cos(Matrix<R> x) {
        return expf().cos(x);
    }

    @Override
    public Matrix<R> sin(Matrix<R> x) {
        return expf().sin(x);
    }

    @Override
    public Matrix<R> tan(Matrix<R> x) {
        return expf().tan(x);
    }

    @Override
    public Matrix<R> tanh(Matrix<R> x) {
        return expf().tanh(x);
    }

    @Override
    public Matrix<R> sinh(Matrix<R> x) {
        return expf().sinh(x);
    }

    @Override
    public Matrix<R> cosh(Matrix<R> x) {
        return expf().cosh(x);
    }

    @Override
    public Matrix<R> asinh(Matrix<R> x) {
        return expf().asinh(x);
    }

    @Override
    public Matrix<R> acosh(Matrix<R> x) {
        return expf().acosh(x);
    }

    @Override
    public Matrix<R> atanh(Matrix<R> x) {
        return expf().atanh(x);
    }

    @Override
    public Matrix<R> pi() {
        return expf().pi();
    }

    @Override
    public Matrix<R> e() {
        return expf().e();
    }
    
    @Override
    public String toString(Matrix<R> x) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i = 0; i < x.getHeight(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            Vector<R> row = x.getRow(i);
            sb.append("{");
            for(int j = 0; j < x.getWidth(); j++) {
                if (j > 0) {
                    sb.append(",");
                }
                sb.append(f.toString(row.get(j)));
            }
            sb.append("}");
        }
        sb.append("}");
        return sb.toString();
    }

    <D> MappedField<Matrix<R>, Matrix<D>> upit(int additionalDigits) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(additionalDigits);
        return new MappedField<Matrix<R>,Matrix<D>>(new MatrixField<D>(size, f2.f),
                m -> upcast(f2, m),
                arr -> downcast(f2, arr));
    }

    static <R,D> Matrix<R> downcast(MappedField<R, D> f2, Matrix<D> m) {
        return VirtualMatrix.map(m, v -> f2.downcast.apply(v));
    }

    static <R,D> Matrix<D> upcast(MappedField<R, D> f2, Matrix<R> m) {
        return VirtualMatrix.map(m, v -> f2.upcast.apply(v));
    }

    <D> Matrix<R> _multiply(Matrix<R> a, Matrix<R> b) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(1);
        return downcast(f2, _multiply2(f2.f, upcast(f2, a), upcast(f2, b)));
    }

    static <D> Matrix<D> _multiply2(ExpField<D> f2, Matrix<D> x, Matrix<D> y) {
        int size = x.getWidth();
        List<VectorWrapper<D>> arr = new ArrayList<VectorWrapper<D>>(size);
        boolean[][] isNonZero = new boolean[size][size];
        for(int i = 0; i < size; i++) {
            final Vector<D> row = x.getRow(i);
            final int[] nonZeroesR = row.nonZeroValues();
            Object[] vrow = new Object[size];
            boolean[] isnz = isNonZero[i];
            for(int j = 0; j < size; j++) {
                final Vector<D> column = y.getColumn(j);
                final int[] nonZeroesC = column.nonZeroValues();
                D sum = f2.valueOf(0);
                int irow = 0;
                int icol = 0;
                boolean nz = false;
                while(irow < nonZeroesR.length && icol < nonZeroesC.length) {
                    if (nonZeroesR[irow] < nonZeroesC[icol]) {
                        irow++;
                        continue;
                    }
                    if (nonZeroesR[irow] > nonZeroesC[icol]) {
                        icol++;
                        continue;
                    }
                    nz = true;
                    sum = f2.add(sum, f2.multiply(row.get(nonZeroesR[irow]), column.get(nonZeroesC[icol])));
                    irow++;
                    icol++;
                }
                vrow[j] = sum;
                isnz[j] = nz;
            }
            arr.add(new VectorWrapper<D>(vrow));
        }

        return VirtualMatrix.create(new MatrixWrapper<D>(arr), isNonZero);
    }

    <D> Matrix<R> _divide(Matrix<R> a, Matrix<R> b) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(1);
        return downcast(f2, _divide2(f2.f, upcast(f2, a), upcast(f2, b)));
    }

    static <D> MatrixWrapper<D> makeCopy(Matrix<D> x) {
        int size = x.getWidth();
        List<VectorWrapper<D>> rows = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            Object[] arr = new Object[size];
            Vector<D> xrow = x.getRow(i);
            for(int j = 0; j < size; j++) {
                arr[j] = xrow.get(j);
            }
            rows.add(new VectorWrapper<D>(arr));
        }
        return new MatrixWrapper<D>(rows);
    }
    static <D> VirtualMatrix<D> _divide2(ExpField<D> f2, Matrix<D> x, Matrix<D> y) {
        int size = x.getWidth();
        final MatrixWrapper<D> z1 = makeCopy(y);
        final MatrixWrapper<D> z2 = makeCopy(x);
        for(int i = 0; i < size; i++) {
            int rowMax = i;
            VectorWrapper<D> rowi = z1.getRow(rowMax);
            for(int j = i + 1; j < size; j++) {
                VectorWrapper<D> rowj = z1.getRow(j);
                if (f2.closerToZero(rowi.get(i), rowj.get(i))) {
                    rowMax = j;
                    rowi = rowj;
                }
            }
            z1.swapRows(i, rowMax);
            z2.swapRows(i, rowMax);
            final VectorWrapper<D> pivotRow1 = z1.getRow(i);
            final VectorWrapper<D> pivotRow2 = z2.getRow(i);
            final D pivot = pivotRow1.get(i);
            for(int j = i + 1; j < size; j++) {
                final VectorWrapper<D> row1 = z1.getRow(j);
                final VectorWrapper<D> row2 = z2.getRow(j);
                final D div = row1.get(i);
                for(int k = i + 1; k < size; k++) {
                    row1.set(k, f2.subtract(f2.multiply(row1.get(k), pivot), f2.multiply(pivotRow1.get(k), div)));
                }
                for(int k = 0; k < size; k++) {
                    row2.set(k, f2.subtract(f2.multiply(row2.get(k), pivot), f2.multiply(pivotRow2.get(k), div)));
                }
            }
        }
        boolean[][] isNonZero = new boolean[size][size];
        for(int i = size - 1; i >= 0; i--) {
            final VectorWrapper<D> pivotRow1 = z1.getRow(i);
            final VectorWrapper<D> pivotRow2 = z2.getRow(i);
            final D pivot = pivotRow1.get(i);
            for(int j = i - 1; j >= 0; j--) {
                final VectorWrapper<D> row1 = z1.getRow(j);
                final VectorWrapper<D> row2 = z2.getRow(j);
                final D div = row1.get(i);
                for(int k = 0; k < size; k++) {
                    row2.set(k, f2.subtract(row2.get(k), f2.divide(f2.multiply(pivotRow2.get(k), div), pivot)));
                }
            }
            final boolean[] inz = isNonZero[i];
            for(int k = 0; k < size; k++) {
                inz[k] = !f2.isZero(pivotRow2.get(k));
                if (inz[k]) {
                    pivotRow2.set(k, f2.divide(pivotRow2.get(k), pivot));
                }
            }
        }
        return VirtualMatrix.create(z2, isNonZero);
    }

    <D> Matrix<R> _subtract(Matrix<R> a, Matrix<R> b) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(1);
        return downcast(f2, VirtualMatrix.map(upcast(f2, a), upcast(f2, b), f2.f::subtract));
    }

    <D> Matrix<R> _add(Matrix<R> a, Matrix<R> b) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(1);
        return downcast(f2, VirtualMatrix.map(upcast(f2, a), upcast(f2, b), f2.f::add));
    }

    <D> ExpField<Matrix<R>> expf() {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(3);
        return new MappedField<Matrix<R>, Complex<Matrix<D>>>(new ComplexExpField2<Matrix<D>>(new MatrixField<D>(size, f2.f)),
                x -> toComplex(x, f2),
                x -> toReal(x, f2));
    }

    static <R> R pnorm(Matrix<R> x, ExpField<R> f) {
        R norm = f.valueOf(0);
        for(int i = 0; i < x.getHeight(); i++) {
            Vector<R> row = x.getRow(i);
            for(int j = 0; j < x.getWidth(); j++) {
                final R t = row.get(j);
                norm = f.add(norm, f.multiply(t, t));
            }
        }
        return norm;
    }

    static <R, D> Complex<Matrix<D>> toComplex(Matrix<R> x, MappedField<R, D> f2) {     
        return new Complex<Matrix<D>>(upcast(f2, x), VirtualMatrix.zero(f2.f, x.getWidth(), x.getHeight()));
    }

    static <R, D> Matrix<R> toReal(Complex<Matrix<D>> z, MappedField<R, D> f2) {
        ExpField<D> f = f2.f;
        final D r2 = f.add(pnorm(z.r, f), pnorm(z.i, f));
        if (f.isZero(r2)) {
            return VirtualMatrix.zero(f2, z.r.getWidth(), z.r.getHeight());
        }
        if (!isEq(pnorm(z.r, f), r2, f)) {
            throw new ArithmeticException();
        }
        return downcast(f2, z.r);
    }

    static <R> boolean isEq(R x, R y, ExpField<R> f) {
        return !f.closerToZero(x, y) && !f.closerToZero(y, x);
    }
}
