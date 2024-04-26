package com.github.gissuite.gribinterpolation;
import org.junit.jupiter.api.Test;
import com.github.gissuite.gribinterpolation.data.DataPoint;

import static com.github.gissuite.gribinterpolation.core.DistanceFinder.haverSine;
import static com.github.gissuite.gribinterpolation.core.InverseWeighted.inverseWeighted;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

public class InverseWeightedTest {
    @Test
    public void inverseWeightedInterpolation_Should_Return_DataPoint1() {
        //sample data points
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        dataPoints.add(new DataPoint((float)-122.4194,(float)37.7749, 10, 0));
        dataPoints.add(new DataPoint((float)-118.2437, (float)34.0522, 20, 0));
        dataPoints.add(new DataPoint((float)-74.0060, (float)40.7128, 30, 0));

        //sample lat/long
        DataPoint interpolatedPoint = new DataPoint((float) -77.0369, (float) 38.9072, NaN, 0);

        //calculate
        float expected = 27.81695454F; // Expected value based on provided sample data
        DataPoint result = inverseWeighted(dataPoints, interpolatedPoint);
        System.out.println("Expected Inverse Weighted Average: " + expected);
        System.out.println("Actual Inverse Weighted Average: " + result.getTemperatureK());

        //test
        assertEquals(expected, result.getTemperatureK());
    }
    @Test
    public void inverseWeightedInterpolation_Should_Return_DataPoint2() {
        //sample data points
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        dataPoints.add(new DataPoint((float)-1.0109,(float) 0.8993, 15, 0));
        dataPoints.add(new DataPoint((float)-0.6251, (float)-0.7543, 13, 2));
        dataPoints.add(new DataPoint((float)0.8134, (float) 0.5261, 20, 5));

        //sample lat/long
        DataPoint interpolatedPoint = new DataPoint((float) 0.0, (float) 0.0, NaN, 1);

        //calculate
        float expected = 16.116459F; // Expected value based on provided sample data
        DataPoint result = inverseWeighted(dataPoints, interpolatedPoint);
        System.out.println("Expected Inverse Weighted Average: " + expected);
        System.out.println("Actual Inverse Weighted Average: " + result.getTemperatureK());

        //test
        assertEquals(expected, result.getTemperatureK());
    }
}