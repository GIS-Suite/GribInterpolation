package com.github.gissuite.gribinterpolation;

import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.gissuite.gribinterpolation.core.interpolation.BilinearInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

public class BilinearInterpolatorTests {
    @Test
    public void bilinearInterpolation_DynamicLat_Should_Return_DataPoint() {

        //data point with missing temperature
        DataPoint interpolationPoint = new DataPoint(100, 0, NaN, 6);
        DataPoint[] upperDataPoints = {new DataPoint(100, -1, 100, 4), new DataPoint(100, 1, 105, 4)};
        DataPoint[] lowerDataPoints = {new DataPoint(100, -1, 50, 8), new DataPoint(100, 1, 47, 8)};

        DataPoint expectedResultDataPoint = new DataPoint(100f, 0f, 75.5f, 6f);

        DataPoint resultDataPoint = BilinearInterpolator.interpolateWithDynamicLat(interpolationPoint, upperDataPoints, lowerDataPoints);
        assertEquals(expectedResultDataPoint.getTemperatureK(), resultDataPoint.getTemperatureK());
    }

    @Test
    public void bilinearInterpolation_DynamicLon_Should_Return_DataPoint() {

        DataPoint interpolationPoint = new DataPoint(101, 100, Float.NaN, 2);

        DataPoint[] upperDataPoints = {new DataPoint(100, 100, 100, 0), new DataPoint(102, 100, 105, 0)};
        DataPoint[] lowerDataPoints = {new DataPoint(100, 100, 50, 4), new DataPoint(102, 100, 48, 4)};

        DataPoint expectedResultDataPoint = new DataPoint(101f, 100f, 75.75f, 2f);

        DataPoint resultDataPoint = BilinearInterpolator.interpolateWithDynamicLon(interpolationPoint, upperDataPoints, lowerDataPoints);
        assertEquals(expectedResultDataPoint.getTemperatureK(), resultDataPoint.getTemperatureK());
    }
}
