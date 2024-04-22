package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.DatasetLinearInterpolation.dataPointsLinearInterpolation;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatasetLinearInterpolationTests {
    @Test
    public void datasetLinearInterpolation_Should_Return_DataPointArrayList(){
        //Data points that represent the dataset
        DataPoint pointA0 = new DataPoint(100, 10, 51, 0);
        DataPoint pointB0 = new DataPoint(100, 11, 50, 0);
        DataPoint pointC0 = new DataPoint(100, 12, 49, 0);
        DataPoint pointD0 = new DataPoint(100, 13, 48, 0);

        DataPoint pointA1 = new DataPoint(100, 10, NaN, 1);
        DataPoint pointB1 = new DataPoint(100, 11, NaN, 1);
        DataPoint pointC1 = new DataPoint(100, 12, NaN, 1);
        DataPoint pointD1 = new DataPoint(100, 13, NaN, 1);

        DataPoint pointA2 = new DataPoint(100, 10, NaN, 2);
        DataPoint pointB2 = new DataPoint(100, 11, NaN, 2);
        DataPoint pointC2 = new DataPoint(100, 12, NaN, 2);
        DataPoint pointD2 = new DataPoint(100, 13, NaN, 2);

        DataPoint pointA3 = new DataPoint(100, 10, 48, 3);
        DataPoint pointB3 = new DataPoint(100, 11, 47, 3);
        DataPoint pointC3 = new DataPoint(100, 12, 46, 3);
        DataPoint pointD3 = new DataPoint(100, 13, 45, 3);

        DataPoint pointE0 = new DataPoint(101, 10, 49.9f, 0);
        DataPoint pointF0 = new DataPoint(101, 12, 48.9f, 0);
        DataPoint pointG0 = new DataPoint(101, 13, 47.9f, 0);
        DataPoint pointH0 = new DataPoint(101, 14, 46.9f, 0);

        DataPoint pointE1 = new DataPoint(101, 10, NaN, 1);
        DataPoint pointF1 = new DataPoint(101, 12, NaN, 1);
        DataPoint pointG1 = new DataPoint(101, 13, NaN, 1);
        DataPoint pointH1 = new DataPoint(101, 14, NaN, 1);

        DataPoint pointE2 = new DataPoint(101, 10, 48.9f, 2);
        DataPoint pointF2 = new DataPoint(101, 12, 47.9f, 2);
        DataPoint pointG2 = new DataPoint(101, 13, 46.9f, 2);
        DataPoint pointH2 = new DataPoint(101, 14, 45.9f, 2);

        //add all data points to an ArrayList
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
        dataPointArrayList.add(pointA0);dataPointArrayList.add(pointB0);dataPointArrayList.add(pointC0);dataPointArrayList.add(pointD0);
        dataPointArrayList.add(pointA1);dataPointArrayList.add(pointB1);dataPointArrayList.add(pointC1);dataPointArrayList.add(pointD1);
        dataPointArrayList.add(pointA2);dataPointArrayList.add(pointB2);dataPointArrayList.add(pointC2);dataPointArrayList.add(pointD2);
        dataPointArrayList.add(pointA3);dataPointArrayList.add(pointB3);dataPointArrayList.add(pointC3);dataPointArrayList.add(pointD3);
        dataPointArrayList.add(pointE0);dataPointArrayList.add(pointF0);dataPointArrayList.add(pointG0);dataPointArrayList.add(pointH0);
        dataPointArrayList.add(pointE1);dataPointArrayList.add(pointF1);dataPointArrayList.add(pointG1);dataPointArrayList.add(pointH1);
        dataPointArrayList.add(pointE2);dataPointArrayList.add(pointF2);dataPointArrayList.add(pointG2);dataPointArrayList.add(pointH2);

        //Test stream().filter() (16 data point count)
        ArrayList<DataPoint>interpolatedDataPointArrayList = dataPointsLinearInterpolation(dataPointArrayList);

    }
}
