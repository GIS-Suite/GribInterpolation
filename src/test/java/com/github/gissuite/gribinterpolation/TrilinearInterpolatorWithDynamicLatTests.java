package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.TrilinearInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrilinearInterpolatorWithDynamicLatTests {
    @Test
    public void trilinearInterpolation_Should_Return_DataPoint() {

        //data point with missing temperature
        DataPoint interpolationPoint = new DataPoint(100, 50, NaN, 6);

        //The 4 adjacent pre-defined data points surrounding the interpolation point with the static lower longitude
        DataPoint upperDepthLowerLatLowerLon = new DataPoint(96, 48, 74, 4);
        DataPoint upperDepthUpperLatLowerLon = new DataPoint(96, 52, 71, 4);
        DataPoint lowerDepthLowerLatLowerLon = new DataPoint(96, 49, 55, 8);
        DataPoint lowerDepthUpperLatLowerLon = new DataPoint(96, 53, 52,8);

        //Add the 4 lower longitude data points by lower latitude and upper depth first
        ArrayList<DataPoint> lowerLonDataPoints = new ArrayList<>();
        lowerLonDataPoints.add(upperDepthLowerLatLowerLon);
        lowerLonDataPoints.add(upperDepthUpperLatLowerLon);
        lowerLonDataPoints.add(lowerDepthLowerLatLowerLon);
        lowerLonDataPoints.add(lowerDepthUpperLatLowerLon);

        //  The 4 adjacent pre-defined data points surrounding the interpolation point with the static higher longitude
        DataPoint upperDepthLowerLatUpperLon = new DataPoint(101, 49, 76, 4);
        DataPoint upperDepthUpperLatUpperLon = new DataPoint(101, 52, 70, 4);
        DataPoint lowerDepthLowerLatUpperLon = new DataPoint(101, 49, 53, 8);
        DataPoint lowerDepthUpperLatUpperLon = new DataPoint(101, 51, 50, 8);

        //Add the 4 higher longitude data points by lower latitude and upper depth first
        ArrayList<DataPoint> upperLonDataPoints = new ArrayList<>();
        upperLonDataPoints.add(upperDepthLowerLatUpperLon);
        upperLonDataPoints.add(upperDepthUpperLatUpperLon);
        upperLonDataPoints.add(lowerDepthLowerLatUpperLon);
        upperLonDataPoints.add(lowerDepthUpperLatUpperLon);

        //expected data point with interpolated temperature value
        DataPoint expectedResultDataPoint = new DataPoint(100, 0, 62.875f, 6);

        DataPoint resultDataPoint = TrilinearInterpolator.interpolateWithDynamicLat(interpolationPoint, lowerLonDataPoints, upperLonDataPoints);

        assertEquals(expectedResultDataPoint.getTemperatureK(), resultDataPoint.getTemperatureK());
    }
}
