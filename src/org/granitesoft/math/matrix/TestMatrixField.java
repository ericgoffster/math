package org.granitesoft.math.matrix;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.granitesoft.math.ExpField;
import org.granitesoft.math.primtive.DoubleExpField;
import org.junit.Test;

public class TestMatrixField {
    
    ExpField<Matrix<Double>> rf3 = new MatrixField<>(2, DoubleExpField.R);

    boolean same(Matrix<Double> d, Matrix<Double> d2) {
        double diff = 0 ;
        double ulp = 0;
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                Double double1 = d.getRow(i).get(j);
                Double double2 = d2.getRow(i).get(j);
                double dx = double1 - double2;
                diff += dx * dx;
                ulp += Math.ulp(double2) * Math.ulp(double2);
            }
        }
        return diff < ulp;
    }

    Matrix<Double> fromArray(double[][] d) {
        List<Vector<Double>> rows = new ArrayList<>(d.length);
        List<Vector<Double>> cols = new ArrayList<>(d.length);
        int[] nonZeroValues = new int[d.length];
        for(int j = 0; j < d.length; j++) {
            nonZeroValues[j] = j;
        }
        for(int i = 0; i < d.length; i++) {
            final int r = i;
            rows.add(new VirtualVector<Double>(d.length, c -> d[r][c], nonZeroValues));
        }
        for(int j = 0; j < d.length; j++) {
            final int c = j;
            cols.add(new VirtualVector<Double>(d.length, r -> d[r][c], nonZeroValues));
        }
        return new VirtualMatrix<Double>(rows, cols);
    }

    @Test
    public void testDivide() {
        Matrix<Double> a = rf3.valueOf(1);
        Matrix<Double> b = fromArray(new double[][] {{4.0, 7.0}, {2.0, 6.0}});
        Matrix<Double> c = rf3.divide(a, b);
        Matrix<Double> d = fromArray(new double[][] {{0.6, -0.7}, {-0.2, 0.4}});
        assertTrue(same(c,d));
    }
    @Test
    public void testAdd() {
        Matrix<Double> a = fromArray(new double[][] {{1.5, 1.2}, {1.1, 1.4}});
        Matrix<Double> b = fromArray(new double[][] {{1.1, 1.3}, {1.0, 0.9}});
        Matrix<Double> c = rf3.add(a, b);
        Matrix<Double> d = fromArray(new double[][] {{2.6, 2.5}, {2.1, 2.3}});
        assertTrue(same(c,d));
    }
    @Test
    public void testMultiply() {
        Matrix<Double> a = fromArray(new double[][] {{1.5, 1.2}, {1.1, 1.4}});
        Matrix<Double> b = fromArray(new double[][] {{1.1, 1.3}, {1.0, 0.9}});
        Matrix<Double> c = rf3.multiply(a, b);
        Matrix<Double> d = fromArray(new double[][] {{2.85, 3.03}, {2.61, 2.69}});
        assertTrue(same(c,d));
    }
    @Test
    public void testSubtract() {
        Matrix<Double> a = fromArray(new double[][] {{1.5, 1.2}, {1.1, 1.4}});
        Matrix<Double> b = fromArray(new double[][] {{1.1, 1.3}, {1.0, 0.9}});
        Matrix<Double> c = rf3.subtract(a, b);
        Matrix<Double> d = fromArray(new double[][] {{.4, -.1}, {.1, .5}});
        assertTrue(same(c,d));
    }
    @Test
    public void testQuotient() {
        Matrix<Double> a = fromArray(new double[][] {{3.1, 1.2}, {-2.1, 3.1}});
        Matrix<Double> b = fromArray(new double[][] {{1.1, 1.3}, { 1.0, 0.9}});
        Matrix<Double> c = rf3.quotient(a, b);
        Matrix<Double> d = fromArray(new double[][] {{-18, 10}, {17, -7}});
        assertTrue(same(c,d));
    }
    @Test
    public void testFloor() {
        Matrix<Double> a = fromArray(new double[][] {{1.7, 1.2}, {1.0, 1.4}});
        Matrix<Double> c = rf3.floor(a);
        Matrix<Double> d = fromArray(new double[][] {
            {1.0, 1.0},
            {1.0,  1.0}});
        assertTrue(same(c,d));
    }
    @Test
    public void testCeil() {
        Matrix<Double> a = fromArray(new double[][] {{1.7, 1.2}, {1.0, 1.4}});
        Matrix<Double> c = rf3.ceil(a);
        Matrix<Double> d = fromArray(new double[][] {
            {2.0, 2.0},
            {1.0, 2.0}});
        assertTrue(same(c,d));
    }
    @Test
    public void testLog() {
        Matrix<Double> a = fromArray(new double[][] {{1.5, 1.2}, {1.1, 1.4}});
        Matrix<Double> c = rf3.log(a);
        Matrix<Double> d = fromArray(new double[][] {
            {-0.0772853698806982427, 1.12668743444523775},
            { 1.03279681490813460,  -0.171175989417801388}});
        assertTrue(same(c,d));
    }
    @Test
    public void testSin() {
        Matrix<Double> a = fromArray(new double[][] {{.1, .2}, {.3, 1.5}});
        Matrix<Double> c = rf3.sin(a);
        Matrix<Double> d = fromArray(new double[][] {
            { 0.08475792618018088, 0.126915077008002957},
            { 0.19037261551200444, 0.973163465236201578}});
        assertTrue(same(c,d));
    }
    @Test
    public void testCos() {        
        Matrix<Double> a = fromArray(new double[][] {{.1, .2}, {.3, 1.5}});
        Matrix<Double> c = rf3.cos(a);
        Matrix<Double> d = fromArray(new double[][] {
            { 0.97110264949662404,-0.1306766567584559946},
            {-0.19601498513768399, 0.0563660521874320779}});
        assertTrue(same(c,d));
        
        
    }
    @Test
    public void testTan() {
        Matrix<Double> a = fromArray(new double[][] {{.1, .2}, {.3, 1.5}});
        Matrix<Double> c = rf3.tan(a);
        Matrix<Double> d = fromArray(new double[][] {
            {1.0182705599837023,4.6123377347367516},
            {6.9185066021051274,33.304634703140963}});     
        assertTrue(same(c,d));
    }
    @Test
    public void testCosh() {
        Matrix<Double> a = fromArray(new double[][] {{1.5, 1.2}, {1.1, 1.4}});
        Matrix<Double> c = rf3.cosh(a);
        Matrix<Double> d = fromArray(new double[][] {
            {4.0315997102049396,2.9862611960760791},
            {2.7374060964030725,3.7827446105319330}});
        assertTrue(same(c,d));
    }
    @Test
    public void testSinh() {
        Matrix<Double> a = fromArray(new double[][] {{1.5, 1.2}, {1.1, 1.4}});
        Matrix<Double> c = rf3.sinh(a);
        Matrix<Double> d = fromArray(new double[][] {
            {3.6385439116801177,3.3340236182329751},
            {3.0561883167135605,3.3607086101607031}});
        assertTrue(same(c,d));
    }
    @Test
    public void testTanh() {
        Matrix<Double> a = fromArray(new double[][] {{1.5, 1.2}, {1.1, 1.4}});
        Matrix<Double> c = rf3.tanh(a);
        Matrix<Double> d = fromArray(new double[][] {
            {0.65533772014698653,0.36402510769539563},
            {0.33368968205411266,0.62500229450570356}});
        assertTrue(same(c,d));
    }
    @Test
    public void testExp() {
        Matrix<Double> a = fromArray(new double[][] {{2.0, 3.0}, {1.0, 4.0}});
        Matrix<Double> c = rf3.exp(a);
        Matrix<Double> d = fromArray(new double[][] {
            {39.142001146988434,109.27115795558817},
            {36.423719318529390,111.98943978404721}});     
        assertTrue(same(c,d));
    }
}
