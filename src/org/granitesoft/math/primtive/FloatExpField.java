package org.granitesoft.math.primtive;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.granitesoft.math.ExpField;
import org.granitesoft.math.MappedField;
import org.granitesoft.math.bigdecimal.BigDecimalField;

/**
 * The exponential field of real numbers represented by "Float". This is mostly a veneer on
 * top of java. math
 */
public final class FloatExpField implements ExpField<Float> {

    public static final FloatExpField R = new FloatExpField();

    private FloatExpField() {
    }

    @Override
    public MappedField<Float, ?> upgradePrecision(int additionalDigits) {
        if (additionalDigits == 0) {
            return new MappedField<Float, Float>(this, x -> x, x -> x);
        }
        ExpField<BigDecimal> g = new BigDecimalField(new MathContext(7 + additionalDigits, RoundingMode.HALF_EVEN));
        return new MappedField<Float, BigDecimal>(g,
                d -> BigDecimal.valueOf(d),
                bd -> bd.floatValue());
    }

    @Override
    public Float valueOf(Number l) {
        return l.floatValue();
    }

    @Override
    public Float valueOf(String n) {
        return new BigDecimal(n).floatValue();
    }

    @Override
    public Float e() {
        return (float) Math.E;
    }

    @Override
    public Float pi() {
        return (float) Math.PI;
    }

    @Override
    public Float negate(Float x) {
        return -x;
    }

    @Override
    public Float multiply(Float x, Float y) {
        return x * y;
    }

    @Override
    public Float divide(Float x, Float y) {
        return x / y;
    }

    @Override
    public Float add(Float x, Float y) {
        return x + y;
    }

    @Override
    public Float subtract(Float x, Float y) {
        return x - y;
    }

    @Override
    public Float remainder(Float x, Float y) {
        return subtract(x, multiply(quotient(x, y), y));
    }

    @Override
    public Float sqrt(Float x) {
        return (float) Math.sqrt(x);
    }

    @Override
    public Float exp(Float x) {
        return (float) Math.exp(x);
    }
    
    @Override
    public Float atan2(Float y, Float x) {
        return (float) Math.atan2(y, x);
    }

    @Override
    public Float asin(Float x) {
        return (float) Math.asin(x);
    }

    @Override
    public Float acos(Float x) {
        return (float) Math.acos(x);
    }

    @Override
    public Float atan(Float x) {
        return (float) Math.atan(x);
    }

    @Override
    public Float log(Float x) {
        return (float) Math.log(x);
    }

    @Override
    public Float cos(Float x) {
        return (float) Math.cos(x);
    }

    @Override
    public Float sin(Float x) {
        return (float) Math.sin(x);
    }

    @Override
    public Float tanh(Float x) {
        return (float) Math.tanh(x);
    }

    @Override
    public Float sinh(Float x) {
        return (float) Math.sinh(x);
    }

    @Override
    public Float cosh(Float x) {
        return (float) Math.cosh(x);
    }

    @Override
    public Float asinh(Float x) {
        return log(add(x, sqrt(add(1.0F, multiply(x, x)))));
    }

    @Override
    public Float acosh(Float x) {
        return log(add(x, sqrt(subtract(multiply(x, x), 1.0F))));
    }

    @Override
    public Float atanh(Float x) {
        return divide(log(divide(add(1.0F, x), subtract(1.0F, x))), 2.0F);
    }

    @Override
    public Float pow(Float x, Float y) {
        return (float) Math.pow(x, y);
    }

    @Override
    public Float tan(Float x) {
        return (float) Math.tan(x);
    }

    @Override
    public Float floor(Float x) {
        return (float) Math.floor(x);
    }

    @Override
    public Float ceil(Float x) {
        return (float) Math.ceil(x);
    }

    @Override
    public Float quotient(Float x, Float y) {
        return (float) Math.floor(divide(x, y));
    }

    @Override
    public boolean closerToZero(Float x, Float y) {
        return Math.abs(x) < Math.abs(y);
    }

    @Override
    public boolean isZero(Float x) {
        return x == 0.0;
    }

    @Override
    public boolean isNeg(Float x) {
        return x < 0;
    }

    @Override
    public String toString(Float x) {
        return String.valueOf(x);
    }
}
