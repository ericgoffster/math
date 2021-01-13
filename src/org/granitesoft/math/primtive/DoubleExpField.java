package org.granitesoft.math.primtive;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.granitesoft.math.ExpField;
import org.granitesoft.math.MappedField;
import org.granitesoft.math.bigdecimal.BigDecimalField;

/**
 * The exponential field of real numbers represented by "Double". This is mostly a veneer on
 * top of java. math
 */
public final class DoubleExpField implements ExpField<Double> {

    public static final DoubleExpField R = new DoubleExpField();

    private DoubleExpField() {
    }

    @Override
    public MappedField<Double, ?> upgradePrecision(int additionalDigits) {
        if (additionalDigits == 0) {
            return new MappedField<Double, Double>(this, x -> x, x -> x);
        }
        ExpField<BigDecimal> g = new BigDecimalField(new MathContext(16 + additionalDigits, RoundingMode.HALF_EVEN));
        return new MappedField<Double, BigDecimal>(g,
                d -> BigDecimal.valueOf(d),
                bd -> bd.doubleValue());
    }
    
    @Override
    public Double valueOf(Number l) {
        return l.doubleValue();
    }

    @Override
    public Double valueOf(String n) {
        return new BigDecimal(n).doubleValue();
    }

    @Override
    public Double e() {
        return Math.E;
    }

    @Override
    public Double pi() {
        return Math.PI;
    }

    @Override
    public Double negate(Double x) {
        return -x;
    }

    @Override
    public Double multiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double divide(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double subtract(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double remainder(Double x, Double y) {
        return subtract(x, multiply(quotient(x, y), y));
    }

    @Override
    public Double sqrt(Double x) {
        return Math.sqrt(x);
    }

    @Override
    public Double exp(Double x) {
        return Math.exp(x);
    }

    @Override
    public Double atan2(Double y, Double x) {
        return Math.atan2(y, x);
    }

    @Override
    public Double asin(Double x) {
        return Math.asin(x);
    }

    @Override
    public Double acos(Double x) {
        return Math.acos(x);
    }

    @Override
    public Double atan(Double x) {
        return Math.atan(x);
    }

    @Override
    public Double log(Double x) {
        return Math.log(x);
    }

    @Override
    public Double cos(Double x) {
        return Math.cos(x);
    }

    @Override
    public Double sin(Double x) {
        return Math.sin(x);
    }

    @Override
    public Double tanh(Double x) {
        return Math.tanh(x);
    }

    @Override
    public Double sinh(Double x) {
        return Math.sinh(x);
    }

    @Override
    public Double cosh(Double x) {
        return Math.cosh(x);
    }

    @Override
    public Double asinh(Double x) {
        return log(add(x, sqrt(add(1.0, multiply(x, x)))));
    }

    @Override
    public Double acosh(Double x) {
        return log(add(x, sqrt(subtract(multiply(x, x), 1.0))));
    }

    @Override
    public Double atanh(Double x) {
        return divide(log(divide(add(1.0, x), subtract(1.0, x))), 2.0);
    }

    @Override
    public Double pow(Double x, Double y) {
        return Math.pow(x, y);
    }

    @Override
    public Double tan(Double x) {
        return Math.tan(x);
    }

    @Override
    public Double floor(Double x) {
        return Math.floor(x);
    }

    @Override
    public Double ceil(Double x) {
        return Math.ceil(x);
    }

    @Override
    public Double quotient(Double x, Double y) {
        return Math.floor(divide(x, y));
    }

    @Override
    public boolean closerToZero(Double x, Double y) {
        return Math.abs(x) < Math.abs(y);
    }

    @Override
    public boolean isZero(Double x) {
        return x == 0.0;
    }

    @Override
    public boolean isNeg(Double x) {
        return x < 0;
    }

    @Override
    public String toString(Double x) {
        return String.valueOf(x);
    }
}
