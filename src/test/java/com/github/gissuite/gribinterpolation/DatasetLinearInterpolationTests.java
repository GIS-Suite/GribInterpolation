package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.DatasetLinearInterpolation.dataPointsLinearInterpolator;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatasetLinearInterpolationTests {
    @Test
    public void datasetLinearInterpolation_Should_Return_DataPointArrayList(){
        //Data points that represent the dataset
        DataPoint pointA0 = new DataPoint(100, -90, NaN, 0);
        DataPoint pointB2 = new DataPoint(101, -90, 49, 2);
        DataPoint pointC10 = new DataPoint(102, -90, 40, 10);
        DataPoint pointD11 = new DataPoint(103, -90, NaN, 11);

        DataPoint pointA3 = new DataPoint(100, -90, 50, 3);
        DataPoint pointB1 = new DataPoint(101, -90, 50, 1);
        DataPoint pointC4 = new DataPoint(102, -90, NaN, 4);
        DataPoint pointD3 = new DataPoint(103, -90, 47, 3);

        DataPoint pointA7 = new DataPoint(100, -90, 44, 6);
        DataPoint pointB9 = new DataPoint(101, -90, 42, 6);
        DataPoint pointC1 = new DataPoint(102, -90, 49, 1);
        DataPoint pointD6 = new DataPoint(103, -90, 44, 6);

        DataPoint pointE0 = new DataPoint(100, -89, NaN, 0);
        DataPoint pointF0 = new DataPoint(101, -89, NaN, 22);
        DataPoint pointG0 = new DataPoint(102, -89, NaN, 3);
        DataPoint pointH0 = new DataPoint(103, -89, NaN, 6);

        DataPoint pointE2 = new DataPoint(100, -89, NaN, 5);
        DataPoint pointF2 = new DataPoint(101, -89, NaN, 6);
        DataPoint pointG2 = new DataPoint(102, -89, NaN, 2);
        DataPoint pointH2 = new DataPoint(103, -89, NaN, 40);

        //add all data points to an ArrayList unsorted
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
        dataPointArrayList.add(pointA0);dataPointArrayList.add(pointB2);dataPointArrayList.add(pointC10);dataPointArrayList.add(pointD11);
        dataPointArrayList.add(pointA3);dataPointArrayList.add(pointB1);dataPointArrayList.add(pointC4);dataPointArrayList.add(pointD3);
        dataPointArrayList.add(pointA7);dataPointArrayList.add(pointB9);dataPointArrayList.add(pointC1);dataPointArrayList.add(pointD6);
        dataPointArrayList.add(pointE0);dataPointArrayList.add(pointF0);dataPointArrayList.add(pointG0);dataPointArrayList.add(pointH0);
        dataPointArrayList.add(pointE2);dataPointArrayList.add(pointF2);dataPointArrayList.add(pointG2);dataPointArrayList.add(pointH2);

        ArrayList<DataPoint> result = dataPointsLinearInterpolator(dataPointArrayList);

        //Dataset for testing
        DataPoint point1A = new DataPoint(100.0f, -90.0f, 50.0f, 3.0f);
        DataPoint point1B = new DataPoint(100.0f, -90.0f, 48.0f, 4.0f);
        DataPoint point1C = new DataPoint(100.0f, -90.0f, 46.0f, 5.0f);
        DataPoint point1D = new DataPoint(100.0f, -90.0f, 44.0f, 6.0f);

        DataPoint point2A = new DataPoint(101.0f, -90.0f, 50.0f, 1.0f);
        DataPoint point2B = new DataPoint(101.0f, -90.0f, 49.0f, 2.0f);
        DataPoint point2C = new DataPoint(101.0f, -90.0f, 47.25f, 3.0f);
        DataPoint point2D = new DataPoint(101.0f, -90.0f, 45.5f, 4.0f);
        DataPoint point2E = new DataPoint(101.0f, -90.0f, 43.75f, 5.0f);
        DataPoint point2F = new DataPoint(101.0f, -90.0f, 42.0f, 6.0f);

        DataPoint point3A = new DataPoint(102.0f, -90.0f, 49.0f, 1.0f);
        DataPoint point3B = new DataPoint(102.0f, -90.0f, 48.0f, 2.0f);
        DataPoint point3C = new DataPoint(102.0f, -90.0f, 47.0f, 3.0f);
        DataPoint point3D = new DataPoint(102.0f, -90.0f, 46.0f, 4.0f);
        DataPoint point3E = new DataPoint(102.0f, -90.0f, 45.0f, 5.0f);
        DataPoint point3F = new DataPoint(102.0f, -90.0f, 44.0f, 6.0f);
        DataPoint point3G = new DataPoint(102.0f, -90.0f, 43.0f, 7.0f);
        DataPoint point3H = new DataPoint(102.0f, -90.0f, 42.0f, 8.0f);
        DataPoint point3I = new DataPoint(102.0f, -90.0f, 41.0f, 9.0f);
        DataPoint point3J = new DataPoint(102.0f, -90.0f, 40.0f, 10.0f);

        DataPoint point4A = new DataPoint(103.0f, -90.0f, 47.0f, 3.0f);
        DataPoint point4B = new DataPoint(103.0f, -90.0f, 46.0f, 4.0f);
        DataPoint point4C = new DataPoint(103.0f, -90.0f, 45.0f, 5.0f);
        DataPoint point4D = new DataPoint(103.0f, -90.0f, 44.0f, 6.0f);

        ArrayList<DataPoint> expectedResult = new ArrayList<>();
        expectedResult.add(point1A);expectedResult.add(point1B);expectedResult.add(point1C);expectedResult.add(point1D);
        expectedResult.add(point2A);expectedResult.add(point2B);expectedResult.add(point2C);expectedResult.add(point2D);expectedResult.add(point2E);expectedResult.add(point2F);
        expectedResult.add(point3A);expectedResult.add(point3B);expectedResult.add(point3C);expectedResult.add(point3D);expectedResult.add(point3E);
        expectedResult.add(point3F);expectedResult.add(point3G);expectedResult.add(point3H);expectedResult.add(point3I);expectedResult.add(point3J);
        expectedResult.add(point4A);expectedResult.add(point4B);expectedResult.add(point4C);expectedResult.add(point4D);

        assertEquals(result.size(), expectedResult.size());

        ArrayList<Float> resultLatitudes = new ArrayList<>();
        ArrayList<Float> resultLongitudes = new ArrayList<>();
        ArrayList<Float> resultDepths = new ArrayList<>();
        ArrayList<Float> resultTemperatures = new ArrayList<>();
        for (DataPoint currentDataPoint : result) {
            resultLatitudes.add(currentDataPoint.getLatitude());
            resultLongitudes.add(currentDataPoint.getLongitude());
            resultDepths.add(currentDataPoint.getDepth());
            resultTemperatures.add(currentDataPoint.getTemperatureK());
        }

        ArrayList<Float> expectedResultLatitudes = new ArrayList<>();
        ArrayList<Float> expectedResultLongitudes = new ArrayList<>();
        ArrayList<Float> expectedResultDepths = new ArrayList<>();
        ArrayList<Float> expectedResultTemperatures = new ArrayList<>();
        for (DataPoint currentDataPoint : expectedResult) {
            expectedResultLatitudes.add(currentDataPoint.getLatitude());
            expectedResultLongitudes.add(currentDataPoint.getLongitude());
            expectedResultDepths.add(currentDataPoint.getDepth());
            expectedResultTemperatures.add(currentDataPoint.getTemperatureK());
        }

        assertTrue(resultLatitudes.containsAll(expectedResultLatitudes) && expectedResultLatitudes.containsAll(resultLatitudes));
        assertTrue(resultLongitudes.containsAll(expectedResultLongitudes) && expectedResultLongitudes.containsAll(resultLongitudes));
        assertTrue(resultDepths.containsAll(expectedResultDepths) && expectedResultDepths.containsAll(resultDepths));
        assertTrue(resultTemperatures.containsAll(expectedResultTemperatures) && expectedResultTemperatures.containsAll(resultTemperatures));
    }
}
