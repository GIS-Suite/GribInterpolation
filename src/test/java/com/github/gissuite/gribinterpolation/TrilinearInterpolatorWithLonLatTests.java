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

        //The 4 adjacent pre-defined data points surrounding the interpolation point with the static lower latitude
        DataPoint upperDepthLowerLonLowerLat = new DataPoint(98, -5, 70, 4);
        DataPoint upperDepthUpperLonLowerLat = new DataPoint(102, -5, 71, 4);
        DataPoint lowerDepthLowerLonLowerLat = new DataPoint(97, -5, 50, 8);
        DataPoint lowerDepthUpperLonLowerLat = new DataPoint(103, -5, 51,8);

        //Add the 4 upper latitude data points by lower longitude and upper depth first
        ArrayList<DataPoint> lowerLatDataPoints = new ArrayList<>();
        lowerLatDataPoints.add(upperDepthLowerLonLowerLat);
        lowerLatDataPoints.add(upperDepthUpperLonLowerLat);
        lowerLatDataPoints.add(lowerDepthLowerLonLowerLat);
        lowerLatDataPoints.add(lowerDepthUpperLonLowerLat);

        //  The 4 adjacent pre-defined data points surrounding the interpolation point with the static higher latitude
        DataPoint upperDepthLowerLonUpperLat = new DataPoint(99, 1, 110, 4);
        DataPoint upperDepthUpperLonUpperLat = new DataPoint(101, 1, 110, 4);
        DataPoint lowerDepthLowerLonUpperLat = new DataPoint(98, 1, 89, 8);
        DataPoint lowerDepthUpperLonUpperLat = new DataPoint(102, 1, 89, 8);

        //Add the 4 lower latitude data points by lower longitude and upper depth first
        ArrayList<DataPoint> upperLatDataPoints = new ArrayList<>();
        upperLatDataPoints.add(upperDepthLowerLonUpperLat);
        upperLatDataPoints.add(upperDepthUpperLonUpperLat);
        upperLatDataPoints.add(lowerDepthLowerLonUpperLat);
        upperLatDataPoints.add(lowerDepthUpperLonUpperLat);

        //expected data point with interpolated temperature value
        DataPoint expectedResultDataPoint = new DataPoint(100, 0, 93f, 6);

        DataPoint resultDataPoint = TrilinearInterpolator.interpolateWithLonLat(interpolationPoint, lowerLatDataPoints, upperLatDataPoints);

        assertEquals(expectedResultDataPoint.getTemperatureK(), resultDataPoint.getTemperatureK());
    }
}