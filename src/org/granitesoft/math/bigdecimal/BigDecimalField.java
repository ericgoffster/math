package org.granitesoft.math.bigdecimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import org.granitesoft.math.ExpField;
import org.granitesoft.math.Exponential;
import org.granitesoft.math.MappedField;
import org.granitesoft.math.complex.Complex;
import org.granitesoft.math.complex.ComplexExpField2;

/**
 * The simple field of real numbers represented by "BigDecimal" and a MathContext.
 */
public final class BigDecimalField implements ExpField<BigDecimal> {
    private final MathContext mc;

    public BigDecimalField(MathContext mc) {
        this.mc = mc;
    }
    
    ExpField<BigDecimal> expf() {
        ExpField<BigDecimal> g = new BigDecimalField(new MathContext(mc.getPrecision() + 3, mc.getRoundingMode()));
        return new MappedField<>(new ComplexExpField2<BigDecimal>(g), this::toComplex, this::toReal);
    }

    @Override
    public BigDecimal multiply(BigDecimal x, BigDecimal y) {
        return x.multiply(y, mc);
    }

    @Override
    public BigDecimal divide(BigDecimal x, BigDecimal y) {
        return x.divide(y, mc);
    }

    @Override
    public BigDecimal remainder(BigDecimal x, BigDecimal y) {
        BigDecimal z = x.divideAndRemainder(y, mc)[1];
        if (isZero(z)) {
            return BigDecimal.ZERO;
        }
        if (isNeg(y) != isNeg(z)) {
            return z.add(y, mc);
        }
        return z;
    }

    @Override
    public BigDecimal add(BigDecimal x, BigDecimal y) {
        return x.add(y, mc);
    }

    @Override
    public BigDecimal subtract(BigDecimal x, BigDecimal y) {
        return x.subtract(y, mc);
    }

    @Override
    public BigDecimal negate(BigDecimal x) {
        return x.negate();
    }

    @Override
    public BigDecimal floor(BigDecimal x) {
        BigDecimal remainder = remainder(x, BigDecimal.ONE);
        if (isZero(remainder)) {
            return x;
        }
        return subtract(x, remainder);
    }

    @Override
    public BigDecimal ceil(BigDecimal x) {
        BigDecimal remainder = remainder(x, BigDecimal.ONE);
        if (isZero(remainder)) {
            return x;
        }
        return add(x, BigDecimal.ONE.subtract(remainder));
    }

    @Override
    public boolean closerToZero(BigDecimal x, BigDecimal y) {
        return x.abs().compareTo(y.abs()) < 0 && !isZero(y);
    }

    @Override
    public BigDecimal quotient(BigDecimal x, BigDecimal y) {
        return floor(divide(x, y));
    }

    @Override
    public boolean isNeg(BigDecimal x) {
        return x.signum() < 0;
    }

    static BigDecimal _valueOf(Number l) {
        if (l instanceof BigDecimal) {
            return ((BigDecimal) l);
        }
        if (l instanceof BigInteger) {
            return new BigDecimal((BigInteger) l);
        }
        if (l instanceof Float || l instanceof Double) {
            return BigDecimal.valueOf(l.doubleValue());
        }
        return BigDecimal.valueOf(l.longValue());
    }

    @Override
    public boolean isZero(BigDecimal x) {
        return x.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public MappedField<BigDecimal, BigDecimal> upgradePrecision(int n) {
        if (n == 0) {
            return new MappedField<BigDecimal, BigDecimal>(this, x -> x, x -> x);
        }
        ExpField<BigDecimal> g = new BigDecimalField(new MathContext(mc.getPrecision() + n, mc.getRoundingMode()));
        return new MappedField<BigDecimal, BigDecimal>(g, x -> x, x -> x.round(mc));
    }

    @Override
    public BigDecimal pow(final BigDecimal x, final BigDecimal y) {
        return expf().pow(x, y);
    }

    @Override
    public BigDecimal sqrt(BigDecimal x) {
        return Exponential._sqrt(x, this);
    }

    @Override
    public BigDecimal exp(BigDecimal x) {
        return Exponential._exp(x, this);
    }
    
    @Override
    public BigDecimal atan2(BigDecimal y, BigDecimal x) {
        if (isNeg(x)) {
            BigDecimal pi = pi();
            if (isNeg(y)) {
                return subtract(atan2(negate(y), negate(x)), pi);
            }
            return add(negate(atan2(y, negate(x))), pi);
        }
        if (isNeg(y)) {
            return negate(atan2(negate(y), x));
        }
        return expf().atan2(y, x);
    }

    @Override
    public BigDecimal asin(BigDecimal x) {
        if (isNeg(x)) {
            return negate(expf().asin(negate(x)));
        }
        return expf().asin(x);
    }

    @Override
    public BigDecimal acos(BigDecimal x) {
        if (isNeg(x)) {
            return subtract(pi(), expf().acos(negate(x)));
        }
        return expf().acos(x);
    }

    @Override
    public BigDecimal atan(BigDecimal x) {
        return expf().atan(x);
    }

    @Override
    public BigDecimal log(BigDecimal x) {
        if (isNeg(x)) {
            throw new ArithmeticException();
        }
        return Exponential._log(x, this);
    }

    @Override
    public BigDecimal cos(BigDecimal x) {
        return expf().cos(x);
    }

    @Override
    public BigDecimal sin(BigDecimal x) {
        return expf().sin(x);
    }

    @Override
    public BigDecimal tan(BigDecimal x) {
        return expf().tan(x);
    }

    @Override
    public BigDecimal tanh(BigDecimal x) {
        return expf().tanh(x);
    }

    @Override
    public BigDecimal sinh(BigDecimal x) {
        return expf().sinh(x);
    }

    @Override
    public BigDecimal cosh(BigDecimal x) {
        return expf().cosh(x);
    }

    @Override
    public BigDecimal asinh(BigDecimal x) {
        return expf().asinh(x);
    }

    @Override
    public BigDecimal acosh(BigDecimal x) {
        return expf().acosh(x);
    }

    @Override
    public BigDecimal atanh(BigDecimal x) {
        return expf().atanh(x);
    }

    @Override
    public BigDecimal pi() {
        return expf().pi();
    }

    @Override
    public BigDecimal e() {
        return expf().e();
    }

    @Override
    public BigDecimal valueOf(Number l) {
        return _valueOf(l).round(mc);
    }

    @Override
    public BigDecimal valueOf(String n) {
        return new BigDecimal(n).round(mc);
    }
    
    @Override
    public String toString(BigDecimal x) {
        if (x.stripTrailingZeros().scale() <= 0 && Math.abs(x.precision()) >= Math.abs(x.scale())) {
            return x.toBigInteger().toString();
        }
        return x.toEngineeringString();
    }

    BigDecimal _sqr(BigDecimal x) {
        return multiply(x, x);
    }

    Complex<BigDecimal> toComplex(BigDecimal x) {
        return new Complex<BigDecimal>(x, BigDecimal.ZERO);
    }

    BigDecimal toReal(Complex<BigDecimal> z) {
        final BigDecimal r2 = add(_sqr(z.r), _sqr(z.i));
        if (isZero(r2)) {
            return valueOf(0);
        }
        if (!isEq(_sqr(z.r), r2)) {
            throw new ArithmeticException();
        }
        return z.r.round(mc);
    }

    boolean isEq(BigDecimal x, BigDecimal y) {
        return !closerToZero(x, y) && !closerToZero(y, x);
    }
}
