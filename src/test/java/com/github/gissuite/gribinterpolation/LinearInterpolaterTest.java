package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.LinearInt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearInterpolaterTest {
    @Test
    public void exampleTest() {
        double[] datapoint1 = {-62, 38.8, 271.4, 300};
        double[] datapoint2 = {-62, 38.8, 273.3, 400};
        LinearInt.Interpolate(datapoint1, datapoint2);
    }
}
