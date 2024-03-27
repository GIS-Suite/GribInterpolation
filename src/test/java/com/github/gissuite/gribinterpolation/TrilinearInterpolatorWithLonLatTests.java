package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.TrilinearInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrilinearInterpolatorWithLonLatTests {
    @Test
    public void trilinearInterpolation_Should_Return_DataPoint() {

        //data point with missing temperature
        DataPoint interpolationPoint = new DataPoint(100, 0, NaN, 6);

        //The 4 adjacent pre-defined data points surrounding the interpolation point with the higher latitude
        DataPoint upperDepthLowerLonUpperLat = new DataPoint(98, 2, 103, 8);
        DataPoint upperDepthUpperLonUpperLat = new DataPoint(102, 3, 104, 8);
        ArrayList<DataPoint> upperLatDataPoints = null;

        //  The 4 adjacent pre-defined data points surrounding the interpolation point with the lower latitude
        ArrayList<DataPoint> lowerLatDataPoints = null;

        //expected data point with interpolated temperature value
        DataPoint expectedResultDataPoint = new DataPoint(100f, 0f, 75.5f, 6f);


        DataPoint resultDataPoint = TrilinearInterpolator.interpolateWithLonLat(interpolationPoint, upperLatDataPoints, lowerLatDataPoints);
        assertEquals(expectedResultDataPoint.getTemperatureK(), resultDataPoint.getTemperatureK());
    }
}