package com.github.gissuite.gribinterpolation.NAVGEM;
import org.junit.jupiter.api.Test;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import static com.github.gissuite.gribinterpolation.core.InverseWeighted.inverseWeighted;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

public class InverseWeightedTest {
    @Test
    public void testInverseWeighted() {
        //sample data points
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        dataPoints.add(new DataPoint(37.7749, -122.4194, 10));
        dataPoints.add(new DataPoint(34.0522, -118.2437, 20));
        dataPoints.add(new DataPoint(40.7128, -74.0060, 30));

        //sample lat/long
        double latitude = 38.9072;
        double longitude = -77.0369;


    }
}