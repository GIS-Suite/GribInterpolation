package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.NearestNeighbor;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
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
        neighbors.add(new DataPoint(2, 3, 90, 30));

        // Creating point that we are interpolating
        DataPoint interpolatingPoint = new DataPoint(2, 3, NaN, -4);
        // expected result to compare
        DataPoint expectedResultDataPoint = new DataPoint(2, 3, 56.6666667f, -4);

        try{
            int k = 3;
            DataPoint resultTemp = NearestNeighbor.knnInterpolationForOneDataPoint(neighbors, interpolatingPoint, k);
//            interpolatingPoint.setTemperatureK(resultTemp);
            assertEquals(expectedResultDataPoint.getTemperatureK(), interpolatingPoint.getTemperatureK());
        }
        catch (Exception e){ logger.warning("Error with knn_should_return_datapoint" + e); }

    }
    @Test
    public void nearestNeighbor_test(){
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>(Arrays.asList(
                new DataPoint(100, -90, Float.NaN, 0),
                new DataPoint(101, -90, 49, 22),
                new DataPoint(103, -90, Float.NaN, 11),
                new DataPoint(103, -89, Float.NaN, 40)
        ));
        DataPoint dataPointToInterpolate = new DataPoint(103 -89,40,11);
        ArrayList<DataPoint> result = new ArrayList<>();
        result = NearestNeighbor.getNearestNeighbor(dataPointArrayList,dataPointToInterpolate,2);

        ArrayList<DataPoint> expectedResult = new ArrayList<>(Arrays.asList(
                new DataPoint(103,-90, Float.NaN, 11),
                new DataPoint(101, -90, 49, 22)
        ));
//        assertEquals(expectedResult.get(0), result.get(0));
        System.out.println(result.get(0));

    }
}
