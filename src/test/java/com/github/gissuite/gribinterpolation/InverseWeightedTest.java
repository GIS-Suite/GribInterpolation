package com.github.gissuite.gribinterpolation;
import org.junit.jupiter.api.Test;
import com.github.gissuite.gribinterpolation.data.DataPoint;

import static com.github.gissuite.gribinterpolation.core.DistanceFinder.haverSine;
import static com.github.gissuite.gribinterpolation.core.InverseWeighted.inverseWeighted;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

public class InverseWeightedTest {
    @Test
    public void test1InverseWeighted() {
        //sample data points
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        dataPoints.add(new DataPoint((float)-122.4194,(float)37.7749, 10));
        dataPoints.add(new DataPoint((float)-118.2437, (float)34.0522, 20));
        dataPoints.add(new DataPoint((float)-74.0060, (float)40.7128, 30));

        //sample lat/long
        float latitude = (float) 38.9072;
        float longitude = (float) -77.0369;

        //calculate
        double expected = 27.81695454; // Expected value based on provided sample data
        double result = inverseWeighted(dataPoints, latitude, longitude);
        System.out.println("Expected Inverse Weighted Average: " + expected);
        System.out.println("Actual Inverse Weighted Average: " + result);

        //test
        assertEquals(expected, result);
    }
    @Test
    public void test2InverseWeighted() {
        //sample data points
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        dataPoints.add(new DataPoint((float)-1.0109,(float) 0.8993, 15));
        dataPoints.add(new DataPoint((float)-0.6251, (float)-0.7543, 13));
        dataPoints.add(new DataPoint((float)0.8134, (float) 0.5261, 20));

        //sample lat/long
        float latitude = (float) 0.0;
        float longitude = (float) 0.0;

        //calculate
        double expected = 16.1173942; // Expected value based on provided sample data
        double result = inverseWeighted(dataPoints, latitude, longitude);
        System.out.println("Expected Inverse Weighted Average: " + expected);
        System.out.println("Actual Inverse Weighted Average: " + result);

        //test
        assertEquals(expected, result);
    }
}