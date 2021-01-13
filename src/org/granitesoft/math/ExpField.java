package org.granitesoft.math;

/**
 * Defines am exponential field. (i.e. sin, cos, tan, log, exp, etc)
 *
 * @param <F> The type of all members of the exponential field.
 */
public interface ExpField<F> {
    /**
     * Computes a value from a scalar number.
     * 
     * @param n The number
     * @return a value from a scalar number
     */
    F valueOf(Number n);

    /**
     * Computes a value from a string.
     * 
     * @param n The string representation of the number
     * @return a value from a string
     */
    F valueOf(String n);

    /**
     * Computes the negation of the number (-x).
     * 
     * @param x The number
     * @return the negation of the number (-x).
     */
    F negate(F x);

    /**
     * Computes the product of x and y (x * y).
     * 
     * @param x left
     * @param y right
     * @return he product of x and y (x * y)
     */
    F multiply(F x, F y);

    /**
     * Computes the quotient of x and y (x / y).
     * 
     * @param x left
     * @param y right
     * @return the quotient of x and y (x / y)
     */
    F divide(F x, F y);

    /**
     * Computes the "integral" quotient of x and y (x div y).
     * 
     * @param x left
     * @param y right
     * @return the "integral" quotient of x and y (x div y)
     */
    F quotient(F x, F y);

    /**
     * Computes the remainder of x div y.
     * 
     * @param x left
     * @param y right
     * @return the remainder of x div y
     */
    F remainder(F x, F y);

    /**
     * Computes the integer closest to -∞ starting from x.
     * 
     * @param x The starting point
     * @return the integer closest to -∞ starting from x
     */
    F floor(F x);

    /**
     * Computes the integer closest to +∞ starting from x.
     * 
     * @param x The starting point
     * @return the integer closest to +∞ starting from x
     */
    F ceil(F x);

    /**
     * Computes the sum of x and y (x + y).
     * 
     * @param x left
     * @param y right
     * @return the sum of x and y (x + y)
     */
    F add(F x, F y);

    /**
     * Computes the difference of x and y (x - y).
     * 
     * @param x left
     * @param y right
     * @return the difference of x and y (x - y)
     */
    F subtract(F x, F y);

    /**
     * Decides whether <code>x</code> is strictly closer to 0 than <code>y</code>.
     * If <code>x</code> and <code>y</code> are the same distance, will return
     * false. Two numbers can be considered "the same" if !closerToZero(x,y) &&
     * !closerToZero(y,x)
     * 
     * @param x left hand side
     * @param y right hand side
     * @return true if x is closer to
     */
    boolean closerToZero(F x, F y);

    /**
     * Decides whether <code>x</code> is 0.
     * 
     * @param x The number.
     * @return true if x is 0
     */
    boolean isZero(F x);

    /**
     * Decides whether <code>x</code> is negative. Note that for some sort of
     * objects (i.e. Complex Numbers), the concept does not make sense.
     * 
     * @param x The number.
     * @return true if x is negative.
     */
    boolean isNeg(F x);

    /**
     * Computes π
     * 
     * @return π
     */
    F pi();

    /**
     * Computes euler's constant (e)
     * 
     * @return euler's constant (e)
     */
    F e();

    /**
     * Computes the sqrt of x.
     * 
     * @param x the number
     * @return the sqrt of x
     */
    F sqrt(F x);

    /**
     * Computes the inverse tangent of y / x (tan⁻¹(y / x)).
     * 
     * @param x the number
     * @return the inverse tangent of y / x (tan⁻¹(y / x))
     */
    F atan2(F y, F x);

    /**
     * Computes the inverse sine of x (sin⁻¹(x)).
     * 
     * @param x the number
     * @return the inverse sine of x (sin⁻¹(x))
     */
    F asin(F x);

    /**
     * Computes the inverse cosine of x (cos⁻¹(x)).
     * 
     * @param x the number
     * @return the inverse cosine of x (cos⁻¹(x))
     */
    F acos(F x);

    /**
     * Computes the inverse tangent of x (tan⁻¹(x)).
     * 
     * @param x the number
     * @return the inverse tangent of x (tan⁻¹(x))
     */
    F atan(F x);

    /**
     * Computes the principal log of x.
     * 
     * @param x the number
     * @return the principal log of x
     */
    F log(F x);

    /**
     * Computes the cosine of x.
     * 
     * @param x the number
     * @return the cosine of x
     */
    F cos(F x);

    /**
     * Computes the sine of x.
     * 
     * @param x the number
     * @return the sine of x
     */
    F sin(F x);

    /**
     * Computes the tangent of x.
     * 
     * @param x the number
     * @return the tangent of x
     */
    F tan(F x);

    /**
     * Computes the hyperbolic tangent of x.
     * 
     * @param x the number
     * @return the hyperbolic tangent of x
     */
    F tanh(F x);

    /**
     * Computes the hyperbolic sine of x.
     * 
     * @param x the number
     * @return the hyperbolic sine of x
     */
    F sinh(F x);

    /**
     * Computes the hyperbolic cosine of x.
     * 
     * @param x the number
     * @return the hyperbolic cosine of x
     */
    F cosh(F x);

    /**
     * Computes the inverse hyperbolic sine of x (sinh⁻¹(x)).
     * 
     * @param x the number
     * @return the inverse hyperbolic sine of x (sinh⁻¹(x))
     */
    F asinh(F x);

    /**
     * Computes the inverse hyperbolic cosine of x (cosh⁻¹(x)).
     * 
     * @param x the number
     * @return the inverse hyperbolic cosine of x (cosh⁻¹(x))
     */
    F acosh(F x);

    /**
     * Computes the inverse hyperbolic tangent of x (tanh⁻¹(x)).
     * 
     * @param x the number
     * @return the inverse hyperbolic tangent of x (tanh⁻¹(x))
     */
    F atanh(F x);

    /**
     * Computes x raised to the power of y (xʸ).
     * 
     * @param x the number
     * @return x raised to the power of y (xʸ)
     */
    F pow(F x, F y);

    MappedField<F, ?> upgradePrecision(int additionalDigits);

    F exp(F x);
    
    String toString(F x);
}
