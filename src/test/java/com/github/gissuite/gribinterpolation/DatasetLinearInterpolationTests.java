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
        DataPoint pointA0 = new DataPoint(100, -90, 51, 0);
        DataPoint pointB2 = new DataPoint(101, -90, 49, 2);
        DataPoint pointC10 = new DataPoint(102, -90, 40, 10);
        DataPoint pointD11 = new DataPoint(103, -90, 39, 11);

        DataPoint pointA3 = new DataPoint(100, -90, 48, 3);
        DataPoint pointB1 = new DataPoint(101, -90, 50, 1);
        DataPoint pointC4 = new DataPoint(102, -90, 46, 4);
        DataPoint pointD3 = new DataPoint(103, -90, 47, 3);

        DataPoint pointA7 = new DataPoint(100, -90, 44, 7);
        DataPoint pointB9 = new DataPoint(101, -90, 42, 9);
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

        ArrayList<DataPoint>interpolatedDataPointArrayList = dataPointsLinearInterpolation(dataPointArrayList);

    }
}
