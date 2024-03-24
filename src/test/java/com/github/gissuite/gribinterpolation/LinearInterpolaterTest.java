package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.LinearInt;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearInterpolaterTests {
    @Test
    public void linearInterpolation_Should_Return_DataPoint() {
        DataPoint first = new DataPoint(-62, 38.8f, 271.4f, 300);
        DataPoint second = new DataPoint(-62, 38.8f, 273.3f, 400);
        DataPoint expectedResult = new DataPoint(-62, 38.8f, 272.44498f, 355);

        DataPoint result = LinearInt.interpolate(first, second, 355);
        System.out.print(result.getLongitude() + " " + result.getLatitude() + " " + result.getTemperatureK() + " " + result.getDepth());
        assertEquals(expectedResult.getTemperatureK(), result.getTemperatureK());
    }
}
