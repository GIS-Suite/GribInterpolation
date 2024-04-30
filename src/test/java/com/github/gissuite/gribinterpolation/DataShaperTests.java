package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.gissuite.gribinterpolation.data.DataShaper.groupByLatLonWithDepthSort;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataShaperTests {
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

        //get result map from method
        Map<Pair<Float, Float>, List<DataPoint>> resultMap = groupByLatLonWithDepthSort(dataPointArrayList);

        //create manually sorted list of data points for each latitude & longitude group
        List<DataPoint> dataPointsA = new ArrayList<>();
        dataPointsA.add(pointA0);dataPointsA.add(pointA3);dataPointsA.add(pointA7);

        List<DataPoint> dataPointsB = new ArrayList<>();
        dataPointsB.add(pointB1);dataPointsB.add(pointB2);dataPointsB.add(pointB9);

        List<DataPoint> dataPointsC = new ArrayList<>();
        dataPointsC.add(pointC1);dataPointsC.add(pointC4);dataPointsC.add(pointC10);

        List<DataPoint> dataPointsD = new ArrayList<>();
        dataPointsD.add(pointD3);dataPointsD.add(pointD6);dataPointsD.add(pointD11);

        //create expected result Map grouped by latitude & longitude, sorted by depth, and without groups with NaN temperature at all depths
        Map<Pair<Float, Float>, List<DataPoint>> expectedResultMap = new HashMap<>() {{
            put(new Pair<>(-90f, 100f), dataPointsA);
            put(new Pair<>(-90f, 101f), dataPointsB);
            put(new Pair<>(-90f, 102f), dataPointsC);
            put(new Pair<>(-90f, 103f), dataPointsD);
        }};

        //check both Maps
        assertEquals(expectedResultMap, resultMap);
    }
}