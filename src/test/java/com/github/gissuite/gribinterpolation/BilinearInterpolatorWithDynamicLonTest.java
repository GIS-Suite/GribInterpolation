package com.github.gissuite.gribinterpolation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.gissuite.gribinterpolation.core.BilinearInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

public class BilinearInterpolatorWithDynamicLonTest {
    @Test
    public void bilinearInterpolatorWithDynamicLonTest() {

        //data point with missing temperature
        DataPoint interpolationPoint = new DataPoint(101, 100, 0, 2);

        //2 closest points to interpolation point at identical latitude at closest upper depth. upperDepthDataPoint1 = lower Lon
        DataPoint upperDepthDataPoint1 = new DataPoint(100, 100, 100, 0);
        DataPoint upperDepthDataPoint2 = new DataPoint(102, 100, 105, 0);

        //2 closest points to interpolation point at identical latitude at closest lower depth. lowerDepthDataPoint1 = lower Lon
        DataPoint lowerDepthDataPoint1 = new DataPoint(100, 100, 50, 4);
        DataPoint lowerDepthDataPoint2 = new DataPoint(102, 100, 48, 4);

        //expected data point with interpolated temperature value
        DataPoint expectedResultDataPoint = new DataPoint(101f, 100f, 75.75f, 2f);

        DataPoint resultDataPoint = BilinearInterpolator.interpolateWithDynamicLon(interpolationPoint, upperDepthDataPoint1, upperDepthDataPoint2, lowerDepthDataPoint1, lowerDepthDataPoint2);
        assertEquals(expectedResultDataPoint.getTemperatureK(), resultDataPoint.getTemperatureK());
    }
}
