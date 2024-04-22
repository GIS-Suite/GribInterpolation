package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.DatasetLinearInterpolation.dataPointsLinearInterpolation;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatasetLinearInterpolationTests {
    @Test
    public void datasetLinearInterpolation_Should_Return_DataPointArrayList(){
        //Data points that represent the dataset
        DataPoint pointA0 = new DataPoint(100, 10, 51, 0);
        DataPoint pointB0 = new DataPoint(100, 11, 50, 0);
        DataPoint pointC0 = new DataPoint(100, 12, 49, 0);
        DataPoint pointD0 = new DataPoint(100, 13, 48, 0);

        DataPoint pointA3 = new DataPoint(100, 10, 48, 3);
        DataPoint pointB3 = new DataPoint(100, 11, 47, 3);
        DataPoint pointC3 = new DataPoint(100, 12, 46, 3);
        DataPoint pointD3 = new DataPoint(100, 13, 45, 3);

        DataPoint pointE0 = new DataPoint(101, 10, 49.9f, 0);
        DataPoint pointF0 = new DataPoint(101, 11, 48.9f, 0);
        DataPoint pointG0 = new DataPoint(101, 12, 47.9f, 0);
        DataPoint pointH0 = new DataPoint(101, 13, 46.9f, 0);

        DataPoint pointE2 = new DataPoint(101, 10, 48.9f, 2);
        DataPoint pointF2 = new DataPoint(101, 11, 47.9f, 2);
        DataPoint pointG2 = new DataPoint(101, 12, 46.9f, 2);
        DataPoint pointH2 = new DataPoint(101, 13, 45.9f, 2);

        //add all data points to an ArrayList
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
        dataPointArrayList.add(pointA0);dataPointArrayList.add(pointB0);dataPointArrayList.add(pointC0);dataPointArrayList.add(pointD0);
        dataPointArrayList.add(pointA3);dataPointArrayList.add(pointB3);dataPointArrayList.add(pointC3);dataPointArrayList.add(pointD3);
        dataPointArrayList.add(pointE0);dataPointArrayList.add(pointF0);dataPointArrayList.add(pointG0);dataPointArrayList.add(pointH0);
        dataPointArrayList.add(pointE2);dataPointArrayList.add(pointF2);dataPointArrayList.add(pointG2);dataPointArrayList.add(pointH2);

        ArrayList<DataPoint>interpolatedDataPointArrayList = dataPointsLinearInterpolation(dataPointArrayList);

    }
}
