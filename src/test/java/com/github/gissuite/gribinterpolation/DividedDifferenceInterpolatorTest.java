package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.interpolation.DividedDifferenceInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DividedDifferenceInterpolatorTest {
    @Test
    public void dividedDifferenceInterpolation_Should_Return_DataPoint() {
        //initialize wanted dataPoint
        DataPoint wanted = new DataPoint(10, 10, NaN, 4);

        //initialize new dataPoint array
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        //add existing dataPoints with identical longitude and latitude
        dataPoints.add(new DataPoint(10, 10, 0, 0));
        dataPoints.add(new DataPoint(10, 10, 1, 1));
        dataPoints.add(new DataPoint(10, 10, 4, 2));
        dataPoints.add(new DataPoint(10, 10, 9, 3));
        dataPoints.add(new DataPoint(10, 10, 25, 5));

        //expected return result for interpolated value
        DataPoint expectedReturnValue = new DataPoint(10, 10, 16, 4);

        DataPoint interpolatedValue = DividedDifferenceInterpolator.interpolate(dataPoints, wanted);

        assertEquals(expectedReturnValue.getTemperatureK(), interpolatedValue.getTemperatureK());
    }
}
