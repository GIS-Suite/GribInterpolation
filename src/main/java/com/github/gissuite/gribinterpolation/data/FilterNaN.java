package com.github.gissuite.gribinterpolation.data;

import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class FilterNaN {
    public static ArrayList<DataPoint> Filter (ArrayList<DataPoint> dataPointArrayList) {
        Map<Pair<Float, Float>, List<DataPoint>> groupDataPoints = dataPointArrayList
                .stream()
                .collect(
                        Collectors.groupingBy(dp -> new Pair<>(dp.getDepth(), dp.getLongitude()))
                );

        ArrayList<DataPoint> filteredDataPoints = dataPointArrayList.stream()
                .filter(dataPoint -> !Float.isNaN(dataPoint.getTemperatureK()))
                .collect(Collectors.toCollection(ArrayList::new));

        return filteredDataPoints;
    }
}


