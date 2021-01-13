package org.granitesoft.math.matrix;

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
        return VirtualMatrix.create(size, size, f,
                (row, col) -> f.negate(x.getRow(row).get(col)));
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
        final Matrix<R> flr = floor(z);
        final Matrix<R> ceil = ceil(z);
        final Matrix<R> m1 = subtract(z, flr);
        final Matrix<R> m2 = subtract(z, ceil);
        return VirtualMatrix.create(size, size, f,
                (row, col) ->  f.closerToZero(m1.getRow(row).get(col), m2.getRow(row).get(col)) ? flr.getRow(row).get(col) : ceil.getRow(row).get(col));
    }

    @Override
    public Matrix<R> remainder(Matrix<R> x, Matrix<R> y) {
        return subtract(x, multiply(quotient(x, y), y));
    }

    @Override
    public Matrix<R> floor(Matrix<R> x) {
        return VirtualMatrix.create(size, size, f,
                (row, col) -> f.floor(x.getRow(row).get(col)));
    }

    @Override
    public Matrix<R> ceil(Matrix<R> x) {
        return VirtualMatrix.create(size, size, f,
                (row, col) -> f.ceil(x.getRow(row).get(col)));
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
        return VirtualMatrix.create(m.getWidth(), m.getHeight(), f2,
                (row,col) -> f2.downcast.apply(m.getRow(row).get(col)));
    }

    static <R,D> Matrix<D> upcast(MappedField<R, D> f2, Matrix<R> m) {
        return VirtualMatrix.create(m.getWidth(), m.getHeight(), f2.f,
                (row,col) -> f2.upcast.apply(m.getRow(row).get(col)));
    }

    <D> Matrix<R> _multiply(Matrix<R> a, Matrix<R> b) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(1);
        Matrix<D> x = upcast(f2, a);
        Matrix<D> y = upcast(f2, b);
        final Matrix<D> z = VirtualMatrix.create(size, size, f2.f, (i, j) -> {
            final Vector<D> row = x.getRow(i);
            final Vector<D> column = y.getColumn(j);
            D sum = f2.f.valueOf(0);
            final int[] nonZeroesR = row.nonZeroValues();
            final int[] nonZeroesC = column.nonZeroValues();
            int irow = 0;
            int icol = 0;
            while(irow < nonZeroesR.length && icol < nonZeroesC.length) {
                if (nonZeroesR[irow] < nonZeroesC[icol]) {
                    irow++;
                    continue;
                }
                if (nonZeroesR[irow] > nonZeroesC[icol]) {
                    icol++;
                    continue;
                }
                sum = f2.f.add(sum, f2.f.multiply(row.get(nonZeroesR[irow]), column.get(nonZeroesC[icol])));
                irow++;
                icol++;
            }
            return sum;
        });
        return downcast(f2, z);
    }

    <D> Matrix<R> _divide(Matrix<R> a, Matrix<R> b) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(1);
        Matrix<D> x = upcast(f2, a);
        Matrix<D> y = upcast(f2, b);
        final MatrixWrapper<D> z = new MatrixWrapper<D>(size * 2, size);
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                z.set(i, j, y.getRow(i).get(j));
                z.set(i, j + size, x.getRow(i).get(j));
            }
        }
        for(int i = 0; i < size; i++) {
            for(int j = i + 1; j < size; j++) {
                if (f2.f.closerToZero(z.get(i, i), z.get(j, i))) {
                    z.swapRows(i, j);
                }
            }
            D pivot = z.get(i, i);
            for(int k = i + 1; k < size * 2; k++) {
                z.set(i, k, f2.f.divide(z.get(i, k), pivot));
            }               
            for(int j = i + 1; j < size; j++) {
                final D div = z.get(j, i);
                for(int k = i + 1; k < size * 2; k++) {
                    z.set(j, k, f2.f.subtract(z.get(j, k), f2.f.multiply(z.get(i, k), div)));
                }               
            }
        }
        for(int i = size - 1; i >= 0; i--) {
            for(int j = i - 1; j >= 0; j--) {
                final D div = z.get(j, i);
                for(int k = size; k < size * 2; k++) {
                    z.set(j, k, f2.f.subtract(z.get(j, k), f2.f.multiply(z.get(i, k), div)));
                }               
            }
        }
        return downcast(f2, VirtualMatrix.create(size, size, f2.f,
                (row, col) -> z.get(row, col + size)));
    }

    <D> Matrix<R> _subtract(Matrix<R> a, Matrix<R> b) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(1);
        Matrix<D> x = upcast(f2, a);
        Matrix<D> y = upcast(f2, b);
        return downcast(f2, VirtualMatrix.create(size, size, f2.f,
                (row, col) -> f2.f.subtract(x.getRow(row).get(col), y.getRow(row).get(col))));
    }

    <D> Matrix<R> _add(Matrix<R> a, Matrix<R> b) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(1);
        Matrix<D> x = upcast(f2, a);
        Matrix<D> y = upcast(f2, b);
        return downcast(f2, VirtualMatrix.create(size, size, f2.f,
                (row, col) ->  f2.f.add(x.getRow(row).get(col), y.getRow(row).get(col))));
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
