package org.granitesoft.math.complex;

import java.util.function.BiFunction;

import org.granitesoft.math.ExpField;
import org.granitesoft.math.MappedField;

/**
 * A complex exponent field built from a complex field.
 */
public final class ComplexExpField<C extends Complex<R>, R> implements ExpField<C> {
    final ExpField<R> f;
    final BiFunction<R, R, C> creator;
    
    public ComplexExpField(BiFunction<R, R, C> creator, ExpField<R> f) {
        this.f = f;
        this.creator = creator;
    }
    
    public C valueOf(Number r, Number i) {
        return creator.apply(f.valueOf(r), f.valueOf(i));
    }
    
    @Override
    public MappedField<C, ?> upgradePrecision(int additionalDigits) {
        if (additionalDigits == 0) {
            return new MappedField<C, C>(this, x -> x, x -> x);
        }
        return upit(additionalDigits);
    }
    
    <D> MappedField<C, Complex<D>> upit(int additionalDigits) {
        MappedField<R, D> f2 = (MappedField<R, D>) f.upgradePrecision(additionalDigits);
        return new MappedField<C,Complex<D>>(new ComplexExpField2<D>(f2.f),
                x -> new Complex<D>(f2.upcast.apply(x.r), f2.upcast.apply(x.i)),
                x -> creator.apply(f2.downcast.apply(x.r), f2.downcast.apply(x.i)));
    }
    
    ExpField<C> expf() {
        return upit(3);
    }

    @Override
    public C pi() {
        return expf().pi();
    }

    @Override
    public C e() {
        return expf().e();
    }

    @Override
    public C pow(final C x, final C y) {
        return expf().pow(x, y);
    }

    @Override
    public C sqrt(C z) {
        return expf().sqrt(z);
    }

    @Override
    public C exp(C z) {
        return expf().exp(z);
    }

    @Override
    public C atan2(C y, C x) {
        return expf().atan2(y, x);
    }

    @Override
    public C asin(C z) {
        return expf().asin(z);
    }
    
    @Override
    public C acos(C z) {
        return expf().acos(z);
    }
    
    @Override
    public C atan(C z) {
        return expf().atan(z);
    }

    @Override
    public C log(C z) {
        return expf().log(z);
    }

    @Override
    public C cos(C z) {
        return expf().cos(z);
    }
    
    @Override
    public C sin(C z) {
        return expf().sin(z);
    }

    @Override
    public C tan(C z) {
        return expf().tan(z);
    }
    
    @Override
    public C tanh(C z) {
        return expf().tanh(z);
    }

    @Override
    public C sinh(C z) {
        return expf().sinh(z);
    }
    
    @Override
    public C cosh(C z) {
        return expf().cosh(z);
    }
    
    @Override
    public C asinh(C z) {
        return expf().asinh(z);
    }
    
    @Override
    public C acosh(C z) {
        return expf().acosh(z);
    }
    
    @Override
    public C atanh(C z) {
        return expf().atanh(z);
    }

    @Override
    public boolean isNeg(C x) {
        return false;
    }

    @Override
    public C add(C x, C y) {
        return expf().add(x, y);
    }

    @Override
    public C subtract(C x, C y) {
        return expf().subtract(x, y);
    }

    @Override
    public C multiply(C x, C y) {
        return expf().multiply(x, y);
    }

    @Override
    public C negate(C x) {
        return expf().negate(x);
    }

    @Override
    public C divide(C x, C y) {
        return expf().divide(x, y);
    }

    @Override
    public boolean isZero(C x) {
        return expf().isZero(x);
    }

    @Override
    public boolean closerToZero(C x, C y) {
        return expf().closerToZero(x, y);
    }

    @Override
    public C quotient(C x, C y) {
        return expf().quotient(x, y);
    }

    @Override
    public C ceil(C x) {
        return expf().ceil(x);
    }

    @Override
    public C floor(C x) {
        return expf().floor(x);
    }

    @Override
    public C remainder(C x, C y) {
        return expf().remainder(x, y);
    }

    @Override
    public C valueOf(Number n) {
        return expf().valueOf(n);
    }

    @Override
    public C valueOf(String n) {
        return expf().valueOf(n);
    }
    
    @Override
    public String toString(C x) {
        return expf().toString(x);
    }
}
