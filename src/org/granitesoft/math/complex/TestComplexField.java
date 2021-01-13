package org.granitesoft.math.complex;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;

import org.granitesoft.math.bigdecimal.BigDecimalField;
import org.junit.Test;

public class TestComplexField {
    ComplexExpField<Complex<BigDecimal>, BigDecimal> cf = new ComplexExpField<>((r,i) -> new Complex<>(r,i), new BigDecimalField(MathContext.DECIMAL64));

    boolean isEq(Complex<Double> x, Complex<Double> y) {
        double dx = x.r - y.r;
        double dy = x.i - y.i;
        double r = dx * dx + dy * dy;
        double ulpr = Math.ulp(x.r);
        double ulpi = Math.ulp(x.i);
        double ru = ulpr * ulpr + ulpi * ulpi;
        return r < ru;                
    }
    
    Complex<Double> toComplex(Complex<BigDecimal> c) {
        return new Complex<>(c.r.doubleValue(), c.i.doubleValue());
        
    }

    boolean same1(Complex<Double> c, Complex<BigDecimal> d2) {
        return isEq(c, toComplex(d2));
    }
    boolean same(Complex<BigDecimal> c, Complex<BigDecimal> d2) {
        return isEq(toComplex(c), toComplex(d2));
    }
    @Test
    public void testBasic() {
        assertTrue(same1(new Complex<>(21.0, 3.0), cf.multiply(cf.valueOf(7,1), cf.valueOf(3))));
        assertTrue(same1(new Complex<>(4.0, 2.0), cf.divide(cf.valueOf(8,4), cf.valueOf(2))));

        assertTrue(same(cf.valueOf("2.33333333333333333333333333333333"), cf.divide(cf.valueOf(7), cf.valueOf(3))));
        assertTrue(same1(new Complex<>(4.0, 0.0), cf.subtract(cf.valueOf(7), cf.valueOf(3))));
        assertTrue(same1(new Complex<>(10.0, 0.0), cf.add(cf.valueOf(7), cf.valueOf(3))));
        assertTrue(same1(new Complex<>(21.0, 0.0), cf.multiply(cf.valueOf(7), cf.valueOf(3))));

        assertTrue(same(cf.valueOf("2.33333333333333333333333333333333"), cf.divide(cf.valueOf(0,7), cf.valueOf(0,3))));
        assertTrue(same1(new Complex<>(0.0, 4.0), cf.subtract(cf.valueOf(0,7), cf.valueOf(0,3))));
        assertTrue(same1(new Complex<>(0.0, 10.0), cf.add(cf.valueOf(0,7), cf.valueOf(0,3))));
        assertTrue(same1(new Complex<>(-21.0, 0.0), cf.multiply(cf.valueOf(0,7), cf.valueOf(0,3))));        
    }
    @Test
    public void test() {
        assertTrue(same(cf.valueOf("-1.0201104142632773159912477339017049051984675162486694178238051 + " + 
                "0.58800260354756755124561108062508542760170724605592435372604720i"), cf.log(cf.valueOf(".3 + .2i"))));
        assertTrue(same(cf.valueOf("0.574697802130127747242565435086786534393157706919836983698106989 + " + 
                "0.174004493543125100111570650656478036203316372074636020908254273i"), cf.sqrt(cf.valueOf(".3 + .2i"))));

        assertTrue(same(cf.valueOf("1.27276192695174191981939120315155357248300327638396751548733707 - " + 
                "0.207726376248123046319308685903982545552625974937171493377390892i"), cf.acos(cf.valueOf(".3 + .2i"))));
        assertTrue(same(cf.valueOf("0.298034399843154699411930488488197869615581423303585395000135218 + " + 
                "0.207726376248123046319308685903982545552625974937171493377390892i"), cf.asin(cf.valueOf(".3 + .2i"))));
        assertTrue(same(cf.valueOf("0.301874666698718187284001272568868155345559877131049923383887390 +" + 
                "0.184994620061011083486979058569908843494607820839911506542731886i"), cf.atan(cf.valueOf(".3 + .2i"))));
        assertTrue(same(cf.valueOf("1.0172219678978513677227889615504829220635608769868365871492026 - " + 
                "0.055785887828552438941573772577458625843650271387001803417821968i"), cf.atan2(cf.valueOf("2 + 3i"), cf.valueOf("1 + 2i"))));

        assertTrue(same(cf.valueOf("0.301450338428911466367054278987421786829988700459919262847531216 +" + 
                "0.192343629802192822247100685207669831266987127460990576903403298i"), cf.sin(cf.valueOf(".3 + .2i"))));
        assertTrue(same(cf.valueOf("0.296181340678381049702711706132759507307981799221920808443282023 + " + 
                "0.215458773073784043261097964259735187955341137114497128487911066i"), cf.tan(cf.valueOf(".3 + .2i"))));

        assertTrue(same(cf.valueOf("1.0245013402279207091720209362684839480835959293825668684307874 + " + 
                "0.060498842912659489163059849275063711378245467280495855993549903i"), cf.cosh(cf.valueOf(".3 + .2i"))));
        assertTrue(same(cf.valueOf("0.298450161881951745363302186197235677486066717912605165815756352 + " + 
                "0.207676703056284355833281378069597204612280250008513488473993328i"), cf.sinh(cf.valueOf(".3 + .2i"))));
        assertTrue(same(cf.valueOf("0.302229128907772146912645152217214732986144241242557495975149202 + " + 
                "0.184862804006414547351766531082184651290730136143078282594307727i"), cf.tanh(cf.valueOf(".3 + .2i"))));

        assertTrue(same(cf.valueOf("0.301073539446642448445638444839582523257412195407146142843410920 + " + 
                "0.192451447160453127638686646354534724244631444296442435251654547i"), cf.asinh(cf.valueOf(".3 + .2i"))));
        assertTrue(same(cf.valueOf("0.207726376248123046319308685903982545552625974937171493377390892 + " + 
                "1.27276192695174191981939120315155357248300327638396751548733707i"), cf.acosh(cf.valueOf(".3 + .2i"))));
        assertTrue(same(cf.valueOf("0.295749920236414278197257812352983424706468788700714170842201319 + " + 
                "0.215474493700188255577845828951161123620119976355234399378437301i"), cf.atanh(cf.valueOf(".3 + .2i"))));


        assertTrue(same(cf.valueOf("2.2 + 2.1i"), cf.remainder(cf.valueOf("9.1 + 3.2i"), cf.valueOf("6.9 + 1.1i"))));
        assertTrue(same(cf.valueOf("1.1 - 2.6i"), cf.remainder(cf.valueOf("9.1 + 3.2i"), cf.valueOf("-6.9 + 1.1i"))));
        assertTrue(same(cf.valueOf("1.1 - 2.6i"), cf.remainder(cf.valueOf("9.1 + 3.2i"), cf.valueOf("6.9 - 1.1i"))));
        assertTrue(same(cf.valueOf("-2.2 + 2.1i"), cf.remainder(cf.valueOf("-9.1 + 3.2i"), cf.valueOf("6.9 - 1.1i"))));
        assertTrue(same(cf.valueOf("-1.1 + 2.6i"), cf.remainder(cf.valueOf("-9.1 - 3.2i"), cf.valueOf("6.9 - 1.1i"))));

        assertTrue(same(cf.valueOf("-9-3i"), cf.ceil(cf.valueOf("-9.1 - 3.2i"))));
        assertTrue(same(cf.valueOf("-9+4i"), cf.ceil(cf.valueOf("-9.1 + 3.2i"))));
        assertTrue(same(cf.valueOf("10-3i"), cf.ceil(cf.valueOf("9.1 - 3.2i"))));
        assertTrue(same(cf.valueOf("10+4i"), cf.ceil(cf.valueOf("9.1 + 3.2i"))));

        assertTrue(same(cf.valueOf("-10-4i"), cf.floor(cf.valueOf("-9.1 - 3.2i"))));
        assertTrue(same(cf.valueOf("-10+3i"), cf.floor(cf.valueOf("-9.1 + 3.2i"))));
        assertTrue(same(cf.valueOf("9-4i"), cf.floor(cf.valueOf("9.1 - 3.2i"))));
        assertTrue(same(cf.valueOf("9+3i"), cf.floor(cf.valueOf("9.1 + 3.2i"))));

        assertTrue(same(cf.valueOf("9"), cf.valueOf(9)));
        assertTrue(same(cf.valueOf("-9"), cf.valueOf(-9)));
        assertTrue(same(cf.valueOf("9e+2"), cf.valueOf(9e2)));
        assertTrue(same(cf.valueOf("9e+2+5e+2i"), cf.valueOf(9e2, 5e2)));
        assertTrue(same(cf.valueOf("9e2+5e2i"), cf.valueOf(9e2, 5e2)));
        assertTrue(same(cf.valueOf("9i"), cf.valueOf(0, 9)));
        assertTrue(same(cf.valueOf("-9i"), cf.valueOf(0, -9)));
        assertTrue(same(cf.valueOf("9+3i"), cf.valueOf(9, 3)));
        assertTrue(same(cf.valueOf("9+-3i"), cf.valueOf(9, -3)));
        assertTrue(same(cf.valueOf("9-3i"), cf.valueOf(9, -3)));
        assertThrows(NumberFormatException.class, () -> cf.valueOf("5+6"));
        assertThrows(NumberFormatException.class, () -> cf.valueOf("5i+6"));
        assertTrue(same1(new Complex<Double>(Math.E, 0.0), cf.e()));
    }

    @Test
    public void testPi() {
        assertTrue(same(cf.valueOf("3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679821480865"), cf.pi()));
    }

    @Test
    public void testSqrt() {
        assertTrue(same(cf.valueOf("1.4142135623730950488016887242096980785696718753769480731766797379907324784621070388503875343276415727350138462"),
                cf.pow(new Complex<BigDecimal>(BigDecimal.valueOf(2), BigDecimal.valueOf(0)), new Complex<BigDecimal>(BigDecimal.valueOf(.5), BigDecimal.valueOf(0)))));
    }

    @Test
    public void testCos() {
        assertTrue(same(cf.valueOf("0.97450699298687546536740751801644282503020706017420517086123083995942941523167729194855630228796560851197756845-0.059498857079312085062400253773686196092006135760438469500184271128039412468528590097522961867250525014526723598i"), cf.cos(cf.valueOf(".3 + .2i"))));
    }

    @Test
    public void testExp() {
        assertTrue(same(cf.valueOf("1.32295150210987245453532312246571962556966264729517203424654375 + " + 
                "0.268175545968943844996341227344660915990525717289009344467543232i"), cf.exp(cf.valueOf(".3 + .2i"))));
    }

}
