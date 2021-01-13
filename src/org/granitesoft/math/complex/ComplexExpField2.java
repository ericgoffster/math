package org.granitesoft.math.complex;

import java.math.BigDecimal;

import org.granitesoft.math.ExpField;
import org.granitesoft.math.Exponential;
import org.granitesoft.math.MappedField;

/**
 * A complex exponent field built from a complex field.
 */
public final class ComplexExpField2<R> implements ExpField<Complex<R>> {
    final ExpField<R> f;

    public ComplexExpField2(ExpField<R> f) {
        this.f = f;
    }
    
    @Override
    public MappedField<Complex<R>, ?> upgradePrecision(int additionalDigits) {
        if (additionalDigits == 0) {
            return new MappedField<Complex<R>, Complex<R>>(this, x -> x, x -> x);
        }
        return upit(additionalDigits);
    }
    
    <D> MappedField<Complex<R>, Complex<D>> upit(int additionalDigits) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(additionalDigits);
        ComplexExpField2<D> g = new ComplexExpField2<D>(f2.f);
        return new MappedField<Complex<R>,Complex<D>>(g, 
                x -> new Complex<D>(f2.upcast.apply(x.r), f2.upcast.apply(x.i)), 
                x -> new Complex<R>(f2.downcast.apply(x.r), f2.downcast.apply(x.i)));
    }

    @Override
    public Complex<R> pi() {
        return negate(conjugate(log(valueOf(-1))));
    }

    @Override
    public Complex<R> e() {
        return exp(valueOf(1));
    }

    @Override
    public Complex<R> pow(final Complex<R> x, final Complex<R> y) {
        // x^0 = 1
        if (isZero(y)) {
            return valueOf(1);
        }
        if (isZero(x)) {
            // e^y < 1 => y is "negative" and 0^negative is an error
            if (closerToZero(exp(y), valueOf(1))) {
                throw new ArithmeticException();
            }
            // 0^n = 0
            return valueOf(0);
        }
        // x ^ y = e^(log(x) * y)
        return exp(multiply(log(x), y));
    }

    @Override
    public Complex<R> sqrt(Complex<R> z) {
        // (𝑟(cos(𝜃)+𝑖sin(𝜃)))^1/2=±𝑟^1/2(cos(𝜃/2)+𝑖sin(𝜃/2))
        final R r = f.sqrt(f.add(_sqr(z.r, f), _sqr(z.i, f)));
        final R i_sqrt = f.add(f.sqrt(f.divide(f.subtract(r, z.r), f.valueOf(2))), f.valueOf(0));
        final R r_sqrt = f.add(f.sqrt(f.divide(f.add(r, z.r), f.valueOf(2))), f.valueOf(0));
        if (f.isNeg(z.i)) {
            return new Complex<R>(r_sqrt, f.negate(i_sqrt));
        }
        return new Complex<R>(r_sqrt, i_sqrt);
    }

    @Override
    public Complex<R> exp(Complex<R> z) {
        return Exponential._exp(z, this);
    }

    @Override
    public Complex<R> atan2(Complex<R> y, Complex<R> x) {
        if (isZero(y)) {
            if (isZero(x)) {
                throw new ArithmeticException();
            }
            return valueOf(0);
        }
        if (isZero(x)) {
            return divide(pi(), valueOf(2));
        }
        return atan(divide(y, x));
    }

    @Override
    public Complex<R> asin(Complex<R> z) {
        return atan2(z, sqrt(subtract(valueOf(1), _sqr(z))));
    }
    
    @Override
    public Complex<R> acos(Complex<R> z) {
        return atan2(sqrt(subtract(valueOf(1), _sqr(z))), z);
    }
    
    @Override
    public Complex<R> atan(Complex<R> z) {
        Complex<R> conjz = conjugate(z);
        return conjugate(divide(log(divide(add(valueOf(1), conjz), subtract(valueOf(1), conjz))), valueOf(-2)));
    }

    @Override
    public Complex<R> log(Complex<R> z) {
        return Exponential._log(z, this);
    }

    @Override
    public Complex<R> cos(Complex<R> z) {
        final Complex<R> ce = exp(conjugate(z));
        return divide(add(ce, divide(valueOf(1), ce)), valueOf(2));
    }
    
    @Override
    public Complex<R> sin(Complex<R> z) {
        final Complex<R> ce = exp(conjugate(z));
        return divide(conjugate(subtract(ce, divide(valueOf(1), ce))), valueOf(-2));
    }

    @Override
    public Complex<R> tan(Complex<R> z) {
        final Complex<R> ce2 = exp(conjugate(z));
        final Complex<R> ce = multiply(ce2, ce2);
        return negate(conjugate(divide(subtract(ce, valueOf(1)), add(ce, valueOf(1)))));
    }
    
    @Override
    public Complex<R> tanh(Complex<R> z) {
        // tanh identity
        final Complex<R> ex = exp(z);
        final Complex<R> recip = divide(valueOf(1), ex);
        return divide(subtract(ex, recip), add(ex, recip));
    }

    @Override
    public Complex<R> sinh(Complex<R> z) {
        final Complex<R> ex = exp(z);
        return divide(subtract(ex, divide(valueOf(1), ex)), valueOf(2));
    }
    
    @Override
    public Complex<R> cosh(Complex<R> z) {
        final Complex<R> ex = exp(z);
        return divide(add(ex, divide(valueOf(1), ex)), valueOf(2));
    }
    
    @Override
    public Complex<R> asinh(Complex<R> z) {
        return log(add(z, sqrt(add(_sqr(z), valueOf(1)))));
    }
    
    @Override
    public Complex<R> acosh(Complex<R> z) {
        return log(add(z, sqrt(subtract(_sqr(z), valueOf(1)))));
    }
    
    @Override
    public Complex<R> atanh(Complex<R> z) {
        return divide(log(divide(add(z, valueOf(1)), subtract(valueOf(1), z))), valueOf(2));
    }

    @Override
    public boolean isNeg(Complex<R> x) {
        return false;
    }

    @Override
    public Complex<R> add(Complex<R> x, Complex<R> y) {
        return new Complex<R>(f.add(x.r, y.r), f.add(x.i, y.i));
    }

    @Override
    public Complex<R> subtract(Complex<R> x, Complex<R> y) {
        return new Complex<R>(f.subtract(x.r, y.r), f.subtract(x.i, y.i));
    }

    @Override
    public Complex<R> multiply(Complex<R> x, Complex<R> y) {
        return new Complex<R>(f.subtract(f.multiply(x.r, y.r), f.multiply(x.i, y.i)),
                f.add(f.multiply(x.i, y.r), f.multiply(x.r, y.i)));
    }

    @Override
    public Complex<R> negate(Complex<R> x) {
        return new Complex<R>(f.negate(x.r), f.negate(x.i));
    }

    @Override
    public Complex<R> divide(Complex<R> x, Complex<R> y) {
        final R r = f.add(f.multiply(y.r, y.r), f.multiply(y.i, y.i));
        return new Complex<R>(f.divide(f.add(f.multiply(x.r, y.r), f.multiply(x.i, y.i)), r),
                f.divide(f.subtract(f.multiply(x.i, y.r), f.multiply(x.r, y.i)), r));
    }

    @Override
    public boolean isZero(Complex<R> x) {
        return f.isZero(innerProduct(x, x));
    }

    @Override
    public boolean closerToZero(Complex<R> x, Complex<R> y) {
        return f.closerToZero(innerProduct(x, x), innerProduct(y, y));
    }

    @Override
    public Complex<R> quotient(Complex<R> x, Complex<R> y) {
        final Complex<R> q = divide(x, y);
        final Complex<R> qcc = new Complex<R>(f.ceil(q.r), f.ceil(q.i));
        final Complex<R> qcf = new Complex<R>(f.ceil(q.r), f.floor(q.i));
        final Complex<R> qfc = new Complex<R>(f.floor(q.r), f.ceil(q.i));
        final Complex<R> qff = new Complex<R>(f.floor(q.r), f.floor(q.i));

        final Complex<R> w1 = closerToZero(subtract(x, multiply(qcc, y)), subtract(x, multiply(qcf, y))) ? qcc : qcf;
        final Complex<R> w2 = closerToZero(subtract(x, multiply(qfc, y)), subtract(x, multiply(qff, y))) ? qfc : qff;

        return closerToZero(subtract(x, multiply(w1, y)), subtract(x, multiply(w2, y))) ? w1 : w2;
    }

    @Override
    public Complex<R> ceil(Complex<R> x) {
        return new Complex<R>(f.ceil(x.r), f.ceil(x.i));
    }

    @Override
    public Complex<R> floor(Complex<R> x) {
        return new Complex<R>(f.floor(x.r), f.floor(x.i));
    }

    @Override
    public Complex<R> remainder(Complex<R> x, Complex<R> y) {
        return subtract(x, multiply(quotient(x, y), y));
    }

    public R innerProduct(Complex<R> x, Complex<R> y) {
        return f.add(f.multiply(x.r, y.r), f.multiply(x.i, y.i));
    }

    @Override
    public Complex<R> valueOf(Number n) {
        return new Complex<R>(f.valueOf(n), f.valueOf(0));
    }

    @Override
    public Complex<R> valueOf(String n) {
        int pos = advanceNum(n, 0);
        final R num = f.valueOf(n.substring(0, pos));
        if (pos >= n.length()) {
            return new Complex<R>(num, f.valueOf(0));
        } else if (isI(n.charAt(pos))) {
            if (pos != n.length() - 1) {
                throw new NumberFormatException("Invalid complex number");
            }
            return new Complex<R>(f.valueOf(0), num);
        }
        while (pos < n.length() && Character.isWhitespace(n.charAt(pos))) {
            pos++;
        }
        char sign = '+';
        if (isSign(n.charAt(pos))) {
            sign = n.charAt(pos++);
        }
        while (pos < n.length() && Character.isWhitespace(n.charAt(pos))) {
            pos++;
        }
        int pos2 = advanceNum(n, pos);
        if (pos2 != n.length() - 1 || !isI(n.charAt(pos2))) {
            throw new NumberFormatException("Invalid complex number");
        }
        R num2 = pos == pos2 ? f.valueOf(1) : f.valueOf(n.substring(pos, pos2));
        if (sign == '-') {
            num2 = f.negate(num2);
        }
        return new Complex<R>(num, num2);
    }
    
    @Override
    public String toString(Complex<R> x) {
        String rpart = f.toString(x.r);
        if (f.isZero(x.i)) {
            return rpart;
        }
        if (f.isZero(x.r)) {
            return f.toString(x.i) + "i";
        }
        if (f.isNeg(x.i)) {
            R xi = f.negate(x.i);
            String ipart = xi.equals(BigDecimal.ONE) ? "i" : (f.toString(xi) + "i");
            return rpart + "-" + ipart;
        }
        String ipart = x.i.equals(BigDecimal.ONE) ? "i" : (f.toString(x.i) + "i");
        return rpart + "+" + ipart;
    }

    public Complex<R> conjugate(Complex<R> z) {
        return new Complex<R>(f.negate(z.i), z.r);
    }

    static boolean isSign(char c) {
        return c == '+' || c == '-';
    }

    static boolean isI(char c) {
        return c == 'i' || c == 'I';
    }

    static boolean isE(char c) {
        return c == 'e' || c == 'E';
    }

    static int advanceNum(String n, int pos) {
        if (isSign(n.charAt(pos))) {
            pos++;
        }
        while (pos < n.length() && !isSign(n.charAt(pos)) && !isI(n.charAt(pos))
                && !Character.isWhitespace(n.charAt(pos))) {
            if (pos < n.length() - 1 && isE(n.charAt(pos)) && isSign(n.charAt(pos + 1))) {
                pos += 2;
            } else {
                pos++;
            }
        }
        return pos;
    }

    static <T> T _sqr(T x, ExpField<T> f) {
        return f.multiply(x, x);
    }

    Complex<R> _sqr(Complex<R> x) {
        return _sqr(x, this);
    }  
}
