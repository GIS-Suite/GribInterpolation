package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.gissuite.gribinterpolation.data.GroupBy.groupByLatLonWithDepthSort;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupByTests {
    @Test
    public void GroupBy_Should_Return_DataPointMap(){

        //Data points that represent the dataset
        DataPoint pointA0 = new DataPoint(100, -90, 51, 0);
        DataPoint pointB2 = new DataPoint(101, -90, 49, 2);
        DataPoint pointC10 = new DataPoint(102, -90, 50, 10);
        DataPoint pointD11 = new DataPoint(103, -90, 48, 11);

        DataPoint pointA3 = new DataPoint(100, -90, 48, 3);
        DataPoint pointB1 = new DataPoint(101, -90, 46, 1);
        DataPoint pointC4 = new DataPoint(102, -90, 47, 4);
        DataPoint pointD3 = new DataPoint(103, -90, 45, 3);

        DataPoint pointA7 = new DataPoint(100, -90, 48, 7);
        DataPoint pointB9 = new DataPoint(101, -90, 46, 9);
        DataPoint pointC1 = new DataPoint(102, -90, 47, 1);
        DataPoint pointD6 = new DataPoint(103, -90, 45, 6);

        DataPoint pointE0 = new DataPoint(100, -89, NaN, 0);
        DataPoint pointF0 = new DataPoint(101, -89, NaN, 0);
        DataPoint pointG0 = new DataPoint(102, -89, NaN, 0);
        DataPoint pointH0 = new DataPoint(103, -89, NaN, 0);

        DataPoint pointE2 = new DataPoint(100, -89, NaN, 2);
        DataPoint pointF2 = new DataPoint(101, -89, NaN, 2);
        DataPoint pointG2 = new DataPoint(102, -89, NaN, 2);
        DataPoint pointH2 = new DataPoint(103, -89, NaN, 2);

        //add all data points to an ArrayList unsorted
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
        dataPointArrayList.add(pointA0);dataPointArrayList.add(pointB2);dataPointArrayList.add(pointC10);dataPointArrayList.add(pointD11);
        dataPointArrayList.add(pointA3);dataPointArrayList.add(pointB1);dataPointArrayList.add(pointC4);dataPointArrayList.add(pointD3);
        dataPointArrayList.add(pointA7);dataPointArrayList.add(pointB9);dataPointArrayList.add(pointC1);dataPointArrayList.add(pointD6);
        dataPointArrayList.add(pointE0);dataPointArrayList.add(pointF0);dataPointArrayList.add(pointG0);dataPointArrayList.add(pointH0);
        dataPointArrayList.add(pointE2);dataPointArrayList.add(pointF2);dataPointArrayList.add(pointG2);dataPointArrayList.add(pointH2);

        //get result
        Map<Pair<Float, Float>, List<DataPoint>> resultMap = groupByLatLonWithDepthSort(dataPointArrayList);

        //add all data points to an ArrayList with manual sort & filter (added to array list by lowest depth first for each lat/lon) and get expected result
        ArrayList<DataPoint> dataPointArrayListManualSort = new ArrayList<>();
        dataPointArrayListManualSort.add(pointA0);dataPointArrayListManualSort.add(pointA3);dataPointArrayListManualSort.add(pointA7);dataPointArrayListManualSort.add(pointB1);
        dataPointArrayListManualSort.add(pointB2);dataPointArrayListManualSort.add(pointB9);dataPointArrayListManualSort.add(pointC1);dataPointArrayListManualSort.add(pointC4);
        dataPointArrayListManualSort.add(pointC10);dataPointArrayListManualSort.add(pointD3);dataPointArrayListManualSort.add(pointD6);dataPointArrayListManualSort.add(pointD11);

        //get expected result
        Map<Pair<Float, Float>, List<DataPoint>> expectedResultMap = dataPointArrayListManualSort.stream().collect(Collectors.groupingBy(dp -> new Pair<>(dp.getLatitude(),dp.getLongitude())));

        //test
        assertEquals(expectedResultMap, resultMap);
    }
}