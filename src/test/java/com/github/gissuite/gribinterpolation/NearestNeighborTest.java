package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.NearestNeighbor;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.logging.Logger;

public class NearestNeighborTest {
    Logger logger = Logger.getLogger(NearestNeighborTest.class.getName());
    public static void main(String[] args) {}
    @Test
    public void knn_Should_Return_DataPoint(){
        // testing data
        ArrayList<DataPoint> neighbors = new ArrayList<>();
        neighbors.add(new DataPoint(3, 3, 37, 4));
        neighbors.add(new DataPoint(3, 4, 43, 4));
        neighbors.add(new DataPoint(10, 9, 73, 5));
        neighbors.add(new DataPoint(2, 3, 90, 3));
        neighbors.add(new DataPoint(2, 3, 1000, 30)); // testing if the depth
        // Creating point that we are interpolating
        DataPoint interpolatingPoint = new DataPoint(2, 3, NaN, 4);
        // expected result to compare
        DataPoint expectedResultDataPoint = new DataPoint(2, 3, 56.6666667f, 4);

        try{
            int k = 3;
            DataPoint resultTemp = NearestNeighbor.knnInterpolation(neighbors, interpolatingPoint, k);
            assertEquals(expectedResultDataPoint.getTemperatureK(), interpolatingPoint.getTemperatureK());
        }
        catch (Exception e){ logger.warning("Error with interpolation" + e); }

    }
}
