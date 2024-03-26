package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.TrilinearInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrilinearInterpolatorTests {
    @Test
    public void trilinearInterpolation_Should_Return_DataPoint() {

        //data point with missing temperature
        DataPoint interpolationPoint = new DataPoint(100, 0, NaN, 6);

        //2 closest points to interpolation point at identical latitude at closest upper depth. upperDepthDataPoint1 = lower Lat
        DataPoint upperDepthDataPoint1 = new DataPoint(100, -1, 100, 4);
        DataPoint upperDepthDataPoint2 = new DataPoint(100, 1, 105, 4);

        //2 closest points to interpolation point at identical latitude at closest lower depth. lowerDepthDataPoint1 = lower Lat
        DataPoint lowerDepthDataPoint1 = new DataPoint(100, -1, 50, 8);
        DataPoint lowerDepthDataPoint2 = new DataPoint(100, 1, 47, 8);

        //
        ArrayList<DataPoint> dataPoints = null;
        
        //expected data point with interpolated temperature value
        DataPoint expectedResultDataPoint = new DataPoint(100f, 0f, 75.5f, 6f);

        DataPoint resultDataPoint = TrilinearInterpolator.interpolateWithLonLat(interpolationPoint, dataPoints);
        assertEquals(expectedResultDataPoint.getTemperatureK(), resultDataPoint.getTemperatureK());
    }
}