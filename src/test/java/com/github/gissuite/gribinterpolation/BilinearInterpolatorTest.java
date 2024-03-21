package com.github.gissuite.gribinterpolation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.gissuite.gribinterpolation.core.BilinearInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

public class BilinearInterpolatorTest {
    @Test
    public void bilinearInterpolatorTest() {
        //Using Bilinear Interpolation with dynamic longitude.
        float[] targetLonLatDynamicLon = {101, 100};
        float targetDepth = 2;
        //2 closest points to target Lon/Lat at identical latitude at closest upper depth. upperDepthDataPoint1 = lower Lon
        DataPoint upperDepthDataPoint1 = new DataPoint(100, 100, 100, 0);
        DataPoint upperDepthDataPoint2 = new DataPoint(102, 100, 105, 0);

        //2 closest points to target Lon/Lat at identical latitude at closest lower depth. lowerDepthDataPoint1 = lower Lon
        DataPoint lowerDepthDataPoint1 = new DataPoint(100, 100, 50, 4);
        DataPoint lowerDepthDataPoint2 = new DataPoint(102, 100, 48, 4);

        DataPoint expectedResultDynamicLon = new DataPoint(101f, 100f, 75.75f, 2f);

        DataPoint resultDynamicLon = BilinearInterpolator.interpolateWithDynamicLon(targetLonLatDynamicLon, targetDepth, upperDepthDataPoint1, upperDepthDataPoint2, lowerDepthDataPoint1, lowerDepthDataPoint2);
        assertEquals(expectedResultDynamicLon.getTemperatureK(), resultDynamicLon.getTemperatureK());

        //Using Bilinear Interpolation with dynamic latitude.
        float[] targetLonLatDynamicLat = {100, 0};
        float targetDepthDynamicLat = 6;
        //2 closest points to target Lon/Lat at identical latitude at closest upper depth. upperDepthDataPoint3 = lower Lat
        DataPoint upperDepthDataPoint3 = new DataPoint(100, -1, 100, 4);
        DataPoint upperDepthDataPoint4 = new DataPoint(100, 1, 105, 4);

        //2 closest points to target Lon/Lat at identical latitude at closest lower depth. lowerDepthDataPoint3 = lower Lat
        DataPoint lowerDepthDataPoint3 = new DataPoint(100, -1, 50, 8);
        DataPoint lowerDepthDataPoint4 = new DataPoint(100, 1, 47, 8);

        DataPoint expectedResultDynamicLat = new DataPoint(100f, 0f, 75.5f, 6f);

        DataPoint resultDynamicLat = BilinearInterpolator.interpolateWithDynamicLat(targetLonLatDynamicLat, targetDepthDynamicLat, upperDepthDataPoint3, upperDepthDataPoint4, lowerDepthDataPoint3, lowerDepthDataPoint4);
        assertEquals(expectedResultDynamicLat.getTemperatureK(), resultDynamicLat.getTemperatureK());
    }
}
