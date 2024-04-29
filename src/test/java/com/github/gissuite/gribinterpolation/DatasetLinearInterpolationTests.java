package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static com.github.gissuite.gribinterpolation.core.DatasetLinearInterpolation.dataPointsLinearInterpolator;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatasetLinearInterpolationTests {
    @Test
    public void datasetLinearInterpolation_Should_Return_DataPointArrayList(){
        //Data points that represent the dataset
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>(Arrays.asList(
                new DataPoint(100, -90, NaN, 0),
                new DataPoint(101, -90, 49, 2),
                new DataPoint(102, -90, 40, 10),
                new DataPoint(103, -90, NaN, 11),
                new DataPoint(100, -90, 50, 3),
                new DataPoint(101, -90, 50, 1),
                new DataPoint(102, -90, NaN, 4),
                new DataPoint(103, -90, 47, 3),
                new DataPoint(100, -90, 44, 6),
                new DataPoint(101, -90, 42, 6),
                new DataPoint(102, -90, 49, 1),
                new DataPoint(103, -90, 44, 6),
                new DataPoint(100, -89, NaN, 0),
                new DataPoint(101, -89, NaN, 22),
                new DataPoint(102, -89, NaN, 3),
                new DataPoint(103, -89, NaN, 6),
                new DataPoint(100, -89, NaN, 5),
                new DataPoint(101, -89, NaN, 6),
                new DataPoint(102, -89, NaN, 2),
                new DataPoint(103, -89, NaN, 40)
        ));

        //get result from interpolation method
        ArrayList<DataPoint> result = dataPointsLinearInterpolator(dataPointArrayList);

        //Dataset for testing
        ArrayList<DataPoint> expectedResult = new ArrayList<>(Arrays.asList(
                new DataPoint(100.0f, -90.0f, 50.0f, 3.0f),
                new DataPoint(100.0f, -90.0f, 48.0f, 4.0f),
                new DataPoint(100.0f, -90.0f, 46.0f, 5.0f),
                new DataPoint(100.0f, -90.0f, 44.0f, 6.0f),
                new DataPoint(101.0f, -90.0f, 50.0f, 1.0f),
                new DataPoint(101.0f, -90.0f, 49.0f, 2.0f),
                new DataPoint(101.0f, -90.0f, 47.25f, 3.0f),
                new DataPoint(101.0f, -90.0f, 45.5f, 4.0f),
                new DataPoint(101.0f, -90.0f, 43.75f, 5.0f),
                new DataPoint(101.0f, -90.0f, 42.0f, 6.0f),
                new DataPoint(102.0f, -90.0f, 49.0f, 1.0f),
                new DataPoint(102.0f, -90.0f, 48.0f, 2.0f),
                new DataPoint(102.0f, -90.0f, 47.0f, 3.0f),
                new DataPoint(102.0f, -90.0f, 46.0f, 4.0f),
                new DataPoint(102.0f, -90.0f, 45.0f, 5.0f),
                new DataPoint(102.0f, -90.0f, 44.0f, 6.0f),
                new DataPoint(102.0f, -90.0f, 43.0f, 7.0f),
                new DataPoint(102.0f, -90.0f, 42.0f, 8.0f),
                new DataPoint(102.0f, -90.0f, 41.0f, 9.0f),
                new DataPoint(102.0f, -90.0f, 40.0f, 10.0f),
                new DataPoint(103.0f, -90.0f, 47.0f, 3.0f),
                new DataPoint(103.0f, -90.0f, 46.0f, 4.0f),
                new DataPoint(103.0f, -90.0f, 45.0f, 5.0f),
                new DataPoint(103.0f, -90.0f, 44.0f, 6.0f)
        ));

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

        assertEquals(result.size(), expectedResult.size());
        assertTrue(resultLatitudes.containsAll(expectedResultLatitudes) && expectedResultLatitudes.containsAll(resultLatitudes));
        assertTrue(resultLongitudes.containsAll(expectedResultLongitudes) && expectedResultLongitudes.containsAll(resultLongitudes));
        assertTrue(resultDepths.containsAll(expectedResultDepths) && expectedResultDepths.containsAll(resultDepths));
        assertTrue(resultTemperatures.containsAll(expectedResultTemperatures) && expectedResultTemperatures.containsAll(resultTemperatures));
    }
}
