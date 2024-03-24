package com.github.gissuite.gribinterpolation;

import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.gissuite.gribinterpolation.core.BilinearInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

public class BilinearInterpolatorWithDynamicLatTest {
    @Test
    public void bilinearInterpolation_Should_Return_DataPoint() {

        //data point with missing temperature
        DataPoint interpolationPoint = new DataPoint(100, 0, NaN, 6);

        //2 closest points to interpolation point at identical latitude at closest upper depth. upperDepthDataPoint1 = lower Lat
        DataPoint upperDepthDataPoint1 = new DataPoint(100, -1, 100, 4);
        DataPoint upperDepthDataPoint2 = new DataPoint(100, 1, 105, 4);

        //2 closest points to interpolation point at identical latitude at closest lower depth. lowerDepthDataPoint1 = lower Lat
        DataPoint lowerDepthDataPoint1 = new DataPoint(100, -1, 50, 8);
        DataPoint lowerDepthDataPoint2 = new DataPoint(100, 1, 47, 8);

        //expected data point with interpolated temperature value
        DataPoint expectedResultDataPoint = new DataPoint(100f, 0f, 75.5f, 6f);

        DataPoint resultDataPoint = BilinearInterpolator.interpolateWithDynamicLat(interpolationPoint, upperDepthDataPoint1, upperDepthDataPoint2, lowerDepthDataPoint1, lowerDepthDataPoint2);
        assertEquals(expectedResultDataPoint.getTemperatureK(), resultDataPoint.getTemperatureK());
    }
}
