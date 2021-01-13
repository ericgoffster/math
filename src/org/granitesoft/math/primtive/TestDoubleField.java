package org.granitesoft.math.primtive;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class TestDoubleField {
    private static final double HALF = .5;
    static DoubleExpField rf = DoubleExpField.R;

    boolean same(double d, Double d2) {
        return (float) d == d2.floatValue();
    }

    @Test
    public void test() {
        assertTrue(same(.5, rf.valueOf(".5")));
        assertTrue(same(.5, rf.valueOf(BigDecimal.valueOf(.5))));
        assertTrue(same(5, rf.valueOf(BigInteger.valueOf(5))));
        assertTrue(same(5, rf.valueOf(5)));

        assertTrue(same(Math.E, rf.e()));

        assertTrue(same(-5, rf.negate(rf.valueOf(5))));
        assertTrue(same(12, rf.add(rf.valueOf(5), rf.valueOf(7))));
        assertTrue(same(35, rf.multiply(rf.valueOf(5), rf.valueOf(7))));
        assertTrue(same(4, rf.divide(rf.valueOf(12), rf.valueOf(3))));

        assertTrue(same(Math.exp(.5), rf.exp(HALF)));
        assertTrue(same(Math.exp(.999999), rf.exp(rf.valueOf(.999999))));
        assertTrue(same(Math.exp(1), rf.exp(rf.valueOf(1))));
        assertTrue(same(Math.exp(1.00001), rf.exp(rf.valueOf(1.00001))));
        assertTrue(same(Math.exp(20), rf.exp(rf.valueOf(20))));
        assertTrue(same(Math.exp(-20), rf.exp(rf.valueOf(-20))));
        assertTrue(same(Math.exp(-.5), rf.exp(rf.valueOf(-.5))));
        assertTrue(same(Math.exp(0), rf.exp(rf.valueOf(0))));
        assertTrue(same(Math.exp(1e-5), rf.exp(rf.valueOf(1e-5))));
        assertTrue(same(Math.exp(1e5), rf.exp(rf.valueOf(1e5))));

        assertTrue(same(Math.log(.5), rf.log(HALF)));
        assertTrue(same(Math.log(.99999), rf.log(rf.valueOf(.99999))));
        assertTrue(same(Math.log(1), rf.log(rf.valueOf(1))));
        assertTrue(same(Math.log(1.00001), rf.log(rf.valueOf(1.00001))));
        assertTrue(same(Math.log(50), rf.log(rf.valueOf(50))));
        assertTrue(same(Math.log(1.34e27), rf.log(rf.valueOf(1.34e27))));
        assertTrue(same(Math.log(1.34e-27), rf.log(rf.valueOf(1.34e-27))));

        assertTrue(same(Math.sqrt(0), rf.sqrt(rf.valueOf(0))));
        assertTrue(same(Math.sqrt(.5), rf.sqrt(HALF)));
        assertTrue(same(Math.sqrt(1), rf.sqrt(rf.valueOf(1))));
        assertTrue(same(Math.sqrt(1e27), rf.sqrt(rf.valueOf(1e27))));
        assertTrue(same(Math.sqrt(1e-100), rf.sqrt(rf.valueOf(1e-100))));

        assertTrue(same(7.0 / 3.0, rf.divide(rf.valueOf(7), rf.valueOf(3))));
        assertTrue(same(4, rf.subtract(rf.valueOf(7), rf.valueOf(3))));
        assertTrue(same(10, rf.add(rf.valueOf(7), rf.valueOf(3))));
        assertTrue(same(21, rf.multiply(rf.valueOf(7), rf.valueOf(3))));

        assertTrue(same(Math.acos(.5), rf.acos(HALF)));
        assertTrue(same(Math.acos(0), rf.acos(rf.valueOf(0))));
        assertTrue(same(Math.acos(1), rf.acos(rf.valueOf(1))));

        assertTrue(same(Math.asin(.5), rf.asin(HALF)));
        assertTrue(same(Math.asin(0), rf.asin(rf.valueOf(0))));
        assertTrue(same(Math.asin(1), rf.asin(rf.valueOf(1))));

        assertTrue(same(Math.atan(.5), rf.atan(HALF)));
        assertTrue(same(Math.atan(0), rf.atan(rf.valueOf(0))));
        assertTrue(same(Math.atan(1), rf.atan(rf.valueOf(1))));
        assertTrue(same(Math.atan(50), rf.atan(rf.valueOf(50))));

        assertTrue(same(Math.atan2(3, 2), rf.atan2(rf.valueOf(3), rf.valueOf(2))));

        assertTrue(same(Math.cos(.5), rf.cos(HALF)));
        assertTrue(same(1.0, rf.cos(rf.valueOf(0))));
        assertTrue(same(Math.cos(Math.PI / 4), rf.cos(rf.multiply(rf.pi(), rf.valueOf(1.0 / 4)))));
        assertTrue(same(Math.cos(Math.PI / 3), rf.cos(rf.multiply(rf.pi(), rf.valueOf(1.0 / 3)))));
        assertTrue(same(Math.cos(Math.PI * 2 / 3), rf.cos(rf.multiply(rf.pi(), rf.valueOf(2.0 / 3)))));
        assertTrue(same(-1.0, rf.cos(rf.pi())));

        assertTrue(same(Math.sin(.5), rf.sin(HALF)));
        assertTrue(same(0.0, rf.sin(rf.valueOf(0))));
        assertTrue(same(Math.sin(Math.PI / 4), rf.sin(rf.multiply(rf.pi(), rf.valueOf(1.0 / 4)))));
        assertTrue(same(Math.sin(Math.PI / 3), rf.sin(rf.multiply(rf.pi(), rf.valueOf(1.0 / 3)))));
        assertTrue(same(Math.sin(Math.PI * 2 / 3), rf.sin(rf.multiply(rf.pi(), rf.valueOf(2.0 / 3)))));
        assertTrue(same(1.0, rf.sin(rf.divide(rf.pi(), rf.valueOf(2)))));

        assertTrue(same(Math.tan(.5), rf.tan(HALF)));
        assertTrue(same(0.0, rf.tan(rf.valueOf(0))));
        assertTrue(same(Math.tan(Math.PI / 4), rf.tan(rf.multiply(rf.pi(), rf.valueOf(1.0 / 4)))));
        assertTrue(same(Math.tan(Math.PI / 3), rf.tan(rf.multiply(rf.pi(), rf.valueOf(1.0 / 3)))));
        assertTrue(same(Math.tan(Math.PI * 2 / 3), rf.tan(rf.multiply(rf.pi(), rf.valueOf(2.0 / 3)))));

        assertTrue(same(Math.cosh(.5), rf.cosh(HALF)));
        assertTrue(same(Math.sinh(.5), rf.sinh(HALF)));
        assertTrue(same(Math.tanh(.5), rf.tanh(HALF)));

        assertTrue(same(Math.pow(0, 0), rf.pow(rf.valueOf(0), rf.valueOf(0))));
        assertTrue(same(Math.pow(3, 0), rf.pow(rf.valueOf(3), rf.valueOf(0))));
        assertTrue(same(Math.pow(3, 1.5), rf.pow(rf.valueOf(3), rf.valueOf(1.5))));
        assertTrue(same(Math.pow(0, 1.5), rf.pow(rf.valueOf(0), rf.valueOf(1.5))));
        assertTrue(same(Math.pow(3, -1.5), rf.pow(rf.valueOf(3), rf.valueOf(-1.5))));

        assertTrue(same(0.4812118250596, rf.asinh(HALF)));
        assertTrue(same(0.96242365011921, rf.acosh(rf.valueOf(1.5))));
        assertTrue(same(0.54930614433406, rf.atanh(rf.valueOf(.5))));
        assertTrue(same(1.0, rf.remainder(rf.valueOf(7), rf.valueOf(3))));
        assertTrue(same(-2.0, rf.remainder(rf.valueOf(7), rf.valueOf(-3))));
        assertTrue(same(2.0, rf.remainder(rf.valueOf(-7), rf.valueOf(3))));
        assertTrue(same(-1, rf.remainder(rf.valueOf(-7), rf.valueOf(-3))));

        assertTrue(same(1.0, rf.quotient(rf.valueOf(3.0), rf.valueOf(2.0))));
        assertTrue(same(-2.0, rf.quotient(rf.valueOf(-3.0), rf.valueOf(2.0))));
        assertTrue(same(1.0, rf.quotient(rf.valueOf(-3.0), rf.valueOf(-2.0))));
        assertTrue(same(-2.0, rf.quotient(rf.valueOf(3.0), rf.valueOf(-2.0))));

        assertTrue(same(2.0, rf.ceil(rf.valueOf(2.0))));
        assertTrue(same(-2.0, rf.ceil(rf.valueOf(-2.0))));
        assertTrue(same(2.0, rf.ceil(rf.valueOf(1.3))));
        assertTrue(same(-1.0, rf.ceil(rf.valueOf(-1.3))));

        assertTrue(same(2.0, rf.floor(rf.valueOf(2.0))));
        assertTrue(same(-2.0, rf.floor(rf.valueOf(-2.0))));
        assertTrue(same(1.0, rf.floor(rf.valueOf(1.3))));
        assertTrue(same(-2.0, rf.floor(rf.valueOf(-1.3))));
    }
}
