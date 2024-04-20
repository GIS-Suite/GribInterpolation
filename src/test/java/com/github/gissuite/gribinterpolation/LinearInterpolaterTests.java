package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.LinearInt;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearInterpolaterTests {
    @Test
    public void linearInterpolation_Should_Return_DataPoint() {
        DataPoint upperDepthDataPoint = new DataPoint(-62, 38.8f, 271.4f, 300);
        DataPoint lowerDepthDataPoint = new DataPoint(-62, 38.8f, 273.3f, 400);
        DataPoint targetDataPoint = new DataPoint(-62, 38.8f, NaN, 355);
        DataPoint expectedResult = new DataPoint(-62, 38.8f, 272.44498f, 355);

        DataPoint result = LinearInt.interpolate(upperDepthDataPoint, lowerDepthDataPoint, targetDataPoint);
        System.out.println("Lon:" + result.getLongitude() +  " Lat:" + result.getLatitude() + " Temperature:" + result.getTemperatureK() + " Depth:" + result.getDepth());
        assertEquals(expectedResult.getTemperatureK(), result.getTemperatureK());
    }
}
