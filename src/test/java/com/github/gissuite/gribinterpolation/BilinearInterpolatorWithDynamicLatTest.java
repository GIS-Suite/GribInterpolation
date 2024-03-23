package com.github.gissuite.gribinterpolation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.gissuite.gribinterpolation.core.BilinearInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

public class BilinearInterpolatorWithDynamicLatTest {
    @Test
    public void bilinearInterpolatorWithDynamicLatTest() {
        float[] targetLonLatDynamicLat = {100, 0};
        float targetDepthDynamicLat = 6;
        //2 closest points to target Lon/Lat at identical latitude at closest upper depth. upperDepthDataPoint1 = lower Lat
        DataPoint upperDepthDataPoint1 = new DataPoint(100, -1, 100, 4);
        DataPoint upperDepthDataPoint2 = new DataPoint(100, 1, 105, 4);

        //2 closest points to target Lon/Lat at identical latitude at closest lower depth. lowerDepthDataPoint1 = lower Lat
        DataPoint lowerDepthDataPoint1 = new DataPoint(100, -1, 50, 8);
        DataPoint lowerDepthDataPoint2 = new DataPoint(100, 1, 47, 8);

        DataPoint expectedResultDynamicLat = new DataPoint(100f, 0f, 75.5f, 6f);

        DataPoint resultDynamicLat = BilinearInterpolator.interpolateWithDynamicLat(targetLonLatDynamicLat, targetDepthDynamicLat, upperDepthDataPoint1, upperDepthDataPoint2, lowerDepthDataPoint1, lowerDepthDataPoint2);
        assertEquals(expectedResultDynamicLat.getTemperatureK(), resultDynamicLat.getTemperatureK());
    }
}
