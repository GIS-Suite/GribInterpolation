package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.LinearInt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearInterpolaterTest {
    @Test
    public void exampleTest() {
        double[] datapoint1 = {-62, 38.8, 271.4, 300};
        double[] datapoint2 = {-62, 38.8, 273.3, 400};

        double[] newDataPoint = LinearInt.interpolate(datapoint1, datapoint2, 355);
        for(int i = 0; i<4; i++){
            System.out.print(newDataPoint[i] + " ");
        }
    }
}
