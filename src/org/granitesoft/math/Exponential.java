package org.granitesoft.math;

import java.math.BigInteger;

public final class Exponential {

    public static <T, U> T _exp(T z, ExpField<T> f) {
        if (f.isZero(z)) {
            return f.valueOf(1);
        }      
        int n = Exponential.getPow2(z, f) + 1;
        MappedField<T, U> upgradePrecision = (MappedField<T, U>)f.upgradePrecision(n);
        U arg = upgradePrecision.upcast.apply(z);
        U res = _expWithMutliplier(arg, upgradePrecision.f, n);
        return upgradePrecision.downcast.apply(res);
    }

    // Babylonian method
    public static <T, U> T _sqrt(T z, ExpField<T> f) {
        if (f.isZero(z)) {
            return f.valueOf(0);
        }
        MappedField<T, U> upgradePrecision = (MappedField<T, U>)f.upgradePrecision(1);
        U arg = upgradePrecision.upcast.apply(z);
        U res = _sqrt2(arg, upgradePrecision.f);
        return upgradePrecision.downcast.apply(res);
    }

    private static <T> T _sqrt2(T z, ExpField<T> f) {
        T x = f.divide(f.add(z, f.valueOf(1)), f.valueOf(2));
        T prevDelta = null;
        for (;;) {
            final T newx = f.multiply(f.add(x, f.divide(z, x)), f.valueOf(.5));
            final T delta = f.subtract(newx, x);
            if (prevDelta != null && !f.closerToZero(delta, prevDelta)) {
                break;
            }
            x = newx;
            prevDelta = delta;
        }
        return x;
    }

    private static <T> int getPow2(T z, ExpField<T> f) {
        int n = 0;
        BigInteger multiplier = BigInteger.valueOf(1);
        while(f.closerToZero(f.valueOf(multiplier), z)) {
            n++;
            multiplier = multiplier.shiftLeft(1);
        }
        return n;
    }

    private static <T> T _expWithMutliplier(T z, ExpField<T> f, int n) {    
        // e^(z) = 1 + (z) + (z)^2/2! + (z)^3/3! ...
        T term = f.valueOf(1);
        T newz = f.divide(z, f.valueOf(BigInteger.ONE.shiftLeft(n)));
        T sum = f.valueOf(0);
        long i = 0;
        T prevDelta = null;
        for (;;) {
            final T newsum = f.add(sum, term);
            final T delta = f.subtract(newsum, sum);
            if (f.closerToZero(term, f.valueOf(1)) && prevDelta != null && !f.closerToZero(delta, prevDelta)) {
                break;
            }
            sum = newsum;
            prevDelta = delta;
            term = f.divide(f.multiply(term, newz), f.valueOf(i + 1));
            i++;
        }
        
        // e^z = (e^(z / (2^n))) ^ (2^n)
        // Now square "n" times
        while(n > 0) {
            sum = f.multiply(sum, sum);
            n--;
        }
        return sum;
    }

    public static <T, U> T _log(T z, ExpField<T> f) {
        if (f.isZero(z)) {
            throw new ArithmeticException();
        }
    
        // Apply:
        //     log(z) = log(sqrt(z)) * 2
        // Until |z - 1| < 1/16
        int n = 0;
        T tz = z;
        for(;;) {
            if (f.closerToZero(f.subtract(tz, f.valueOf(1)), f.valueOf(1.0 / 16))) {
                break;
            }
            tz = f.sqrt(tz);
            n++;
        }
        
        MappedField<T, U> upgradePrecision = (MappedField<T, U>)f.upgradePrecision(n);
        U arg = upgradePrecision.upcast.apply(z);
        U res = _logit(arg, upgradePrecision.f, n);
        return upgradePrecision.downcast.apply(res);
    }

    private static <T> T _logit(T z, ExpField<T> f, int n) {
        for(int i = 0; i < n; i++) {
            z = f.sqrt(z);
        }
        
        // z = 1 + x
        // log(1 + x) = x - x^2/2 + x^3/3 ...
        T x = f.subtract(z, f.valueOf(1));
        T term = f.multiply(x, f.valueOf(BigInteger.ONE.shiftLeft(n)));
        T sum = f.valueOf(0);
        T prevDelta = null;
        long i = 0;
        for (;;) {
            final T newsum = f.add(sum, f.divide(term, f.valueOf(i + 1)));
            final T delta = f.subtract(newsum, sum);
            if (prevDelta != null && !f.closerToZero(delta, prevDelta)) {
                break;
            }
            sum = newsum;
            prevDelta = delta;
            term = f.negate(f.multiply(term, x));
            i++;
        }
        return sum;
    }
}
