package org.granitesoft.math.matrix;

import java.util.Iterator;

public class IntegerRange implements Iterable<Integer> {
    final int low;
    final int high;
    
    public IntegerRange(int low, int high) {
        super();
        this.low = low;
        this.high = high;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int i = low;

            @Override
            public boolean hasNext() {
                return i < high;
            }

            @Override
            public Integer next() {
                return i++;
            }            
        };
    }
}
