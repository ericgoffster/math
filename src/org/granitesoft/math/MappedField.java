package org.granitesoft.math;

import java.util.function.Function;

/**
 * A complex exponent field built from a complex field.
 */
public final class MappedField<C, U> implements ExpField<C> {
    public final ExpField<U> f;
    public final Function<C,U> upcast;
    public final Function<U,C> downcast;
    
    public MappedField(ExpField<U> f, Function<C,U> upcast, Function<U,C> downcast) {
        this.f = f;
        this.upcast = upcast;
        this.downcast = downcast;
    }
    
    @Override
    public MappedField<C, ?> upgradePrecision(int additionalDigits) {
        if (additionalDigits == 0) {
            return new MappedField<C, C>(this, x -> x, x -> x);
        }
        return upit(additionalDigits);
    }
    
    public <V> MappedField<C, V> upit(int additionalDigits) {
        MappedField<U, V> g = (MappedField<U, V>)f.upgradePrecision(additionalDigits);
        return new MappedField<C,V>(g.f, 
                x -> g.upcast.apply(upcast.apply(x)), 
                x -> downcast.apply(g.downcast.apply(x)));
    }
    
    @Override
    public C pi() {
        return downcast.apply(f.pi());
    }

    @Override
    public C e() {
        return downcast.apply(f.e());
    }

    @Override
    public C pow(final C x, final C y) {
        return downcast.apply(f.pow(upcast.apply(x), upcast.apply(y)));
    }

    @Override
    public C sqrt(C x) {
        return downcast.apply(f.sqrt(upcast.apply(x)));
    }

    @Override
    public C exp(C x) {
        return downcast.apply(f.exp(upcast.apply(x)));
    }

    @Override
    public C atan2(C y, C x) {
        return downcast.apply(f.atan2(upcast.apply(y), upcast.apply(x)));
    }

    @Override
    public C asin(C x) {
        return downcast.apply(f.asin(upcast.apply(x)));
    }
    
    @Override
    public C acos(C x) {
        return downcast.apply(f.acos(upcast.apply(x)));
    }
    
    @Override
    public C atan(C x) {
        return downcast.apply(f.atan(upcast.apply(x)));
    }

    @Override
    public C log(C x) {
        return downcast.apply(f.log(upcast.apply(x)));
    }

    @Override
    public C cos(C x) {
        return downcast.apply(f.cos(upcast.apply(x)));
    }
    
    @Override
    public C sin(C x) {
        return downcast.apply(f.sin(upcast.apply(x)));
    }

    @Override
    public C tan(C x) {
        return downcast.apply(f.tan(upcast.apply(x)));
    }
    
    @Override
    public C tanh(C x) {
        return downcast.apply(f.tanh(upcast.apply(x)));
    }

    @Override
    public C sinh(C x) {
        return downcast.apply(f.sinh(upcast.apply(x)));
    }
    
    @Override
    public C cosh(C x) {
        return downcast.apply(f.cosh(upcast.apply(x)));
    }
    
    @Override
    public C asinh(C x) {
        return downcast.apply(f.asinh(upcast.apply(x)));
    }
    
    @Override
    public C acosh(C x) {
        return downcast.apply(f.acosh(upcast.apply(x)));
    }
    
    @Override
    public C atanh(C x) {
        return downcast.apply(f.atanh(upcast.apply(x)));
    }

    @Override
    public boolean isNeg(C x) {
        return f.isZero(upcast.apply(x));
    }

    @Override
    public C add(C x, C y) {
        return downcast.apply(f.add(upcast.apply(x), upcast.apply(y)));
    }

    @Override
    public C subtract(C x, C y) {
        return downcast.apply(f.subtract(upcast.apply(x), upcast.apply(y)));
    }

    @Override
    public C multiply(C x, C y) {
        return downcast.apply(f.multiply(upcast.apply(x), upcast.apply(y)));
    }

    @Override
    public C negate(C x) {
        return downcast.apply(f.negate(upcast.apply(x)));
    }

    @Override
    public C divide(C x, C y) {
        return downcast.apply(f.divide(upcast.apply(x), upcast.apply(y)));
    }

    @Override
    public boolean isZero(C x) {
        return f.isZero(upcast.apply(x));
    }

    @Override
    public boolean closerToZero(C x, C y) {
        return f.closerToZero(upcast.apply(x), upcast.apply(y));
    }

    @Override
    public C quotient(C x, C y) {
        return downcast.apply(f.quotient(upcast.apply(x), upcast.apply(y)));
    }

    @Override
    public C ceil(C x) {
        return downcast.apply(f.ceil(upcast.apply(x)));
    }

    @Override
    public C floor(C x) {
        return downcast.apply(f.floor(upcast.apply(x)));
    }

    @Override
    public C remainder(C x, C y) {
        return downcast.apply(f.remainder(upcast.apply(x), upcast.apply(y)));
    }

    @Override
    public C valueOf(Number n) {
        return downcast.apply(f.valueOf(n));
    }

    @Override
    public C valueOf(String n) {
        return downcast.apply(f.valueOf(n));
    }

    @Override
    public String toString(C x) {
        return f.toString(upcast.apply(x));
    }
}
