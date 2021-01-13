package org.granitesoft.math.complex;

import java.util.Objects;

/**
 * A generic complex number allowing any type for the components.
 *
 * @param <R> The component type
 */
public class Complex<R> {
    public final R r;
    public final R i;

    public Complex(R r, R i) {
        this.r = r;
        this.i = i;
    }

    @Override
    public String toString() {
        return Objects.toString(r) + "+" + Objects.toString(i) + "i";
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, r);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Complex<?> other = (Complex<?>) obj;
        return Objects.equals(i, other.i) && Objects.equals(r, other.r);
    }
}