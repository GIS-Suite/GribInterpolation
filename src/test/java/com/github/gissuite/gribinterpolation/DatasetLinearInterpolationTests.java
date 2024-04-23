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
        DataPoint pointB0 = new DataPoint(101, -90, 49, 0);
        DataPoint pointC0 = new DataPoint(102, -90, 50, 0);
        DataPoint pointD0 = new DataPoint(103, -90, 48, 0);

        DataPoint pointA3 = new DataPoint(100, -90, 48, 3);
        DataPoint pointB3 = new DataPoint(101, -90, 46, 3);
        DataPoint pointC3 = new DataPoint(102, -90, 47, 3);
        DataPoint pointD3 = new DataPoint(103, -90, 45, 3);

        DataPoint pointE0 = new DataPoint(100, -89, NaN, 0);
        DataPoint pointF0 = new DataPoint(101, -89, NaN, 0);
        DataPoint pointG0 = new DataPoint(102, -89, NaN, 0);
        DataPoint pointH0 = new DataPoint(103, -89, NaN, 0);

        DataPoint pointE2 = new DataPoint(100, -89, NaN, 2);
        DataPoint pointF2 = new DataPoint(101, -89, NaN, 2);
        DataPoint pointG2 = new DataPoint(102, -89, NaN, 2);
        DataPoint pointH2 = new DataPoint(103, -89, NaN, 2);

        //add all data points to an ArrayList
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
        dataPointArrayList.add(pointA0);dataPointArrayList.add(pointB0);dataPointArrayList.add(pointC0);dataPointArrayList.add(pointD0);
        dataPointArrayList.add(pointA3);dataPointArrayList.add(pointB3);dataPointArrayList.add(pointC3);dataPointArrayList.add(pointD3);
        dataPointArrayList.add(pointE0);dataPointArrayList.add(pointF0);dataPointArrayList.add(pointG0);dataPointArrayList.add(pointH0);
        dataPointArrayList.add(pointE2);dataPointArrayList.add(pointF2);dataPointArrayList.add(pointG2);dataPointArrayList.add(pointH2);

        ArrayList<DataPoint>interpolatedDataPointArrayList = dataPointsLinearInterpolation(dataPointArrayList);

    }
}
