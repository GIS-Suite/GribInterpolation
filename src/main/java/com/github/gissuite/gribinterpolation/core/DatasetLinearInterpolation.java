package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.stream.*;

public class DatasetLinearInterpolation {
    /**
     * @param dataPointArrayList The ArrayList of all the data points including NaN-temperature data points
     * @return The ArrayList of all the data points with NaN-temperature replaced with linear interpolated temperature
     */
    public static ArrayList<DataPoint> dataPointsLinearInterpolation(ArrayList<DataPoint> dataPointArrayList){
        // group all data points by latitude and longitude
        Map<Pair<Float, Float>, List<DataPoint>> allDataPointsByLatLon = dataPointArrayList
                .stream()
                .collect(
                        Collectors.groupingBy(dp -> new Pair<>(dp.getLatitude(),dp.getLongitude()))
                );
        // Test print (DELETE LATER)
        for (Map.Entry<?, ?> entry : allDataPointsByLatLon.entrySet()) {
            System.out.printf("%-15s : %s%n", entry.getKey(), entry.getValue());
        }
        System.out.println();

        // filter group of data points with non NaN temperatures
        Map<Pair<Float, Float>, List<DataPoint>> dataPointsByLatLonWithTemp = allDataPointsByLatLon.entrySet()
                .stream()
                .filter(
                        x -> x.getValue()
                        .stream()
                        .anyMatch(y -> !Float.isNaN(y.getTemperatureK()))
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Test print (Delete Later)
        for (Map.Entry<?, ?> entry : dataPointsByLatLonWithTemp.entrySet()) {
            System.out.printf("%-15s : %s%n", entry.getKey(), entry.getValue());
        }

        // Sort dataPointsByLatLonWithTemp by depth:
        //      for each entry: pull depth values out, binary sort, use streams to match up the order
        for(Map.Entry<Pair<Float, Float>, List<DataPoint>> latLonEntry: dataPointsByLatLonWithTemp.entrySet()){

            List<DataPoint> dataPointsAtSpecificLatLon = latLonEntry.getValue();

            // get all depths
            ArrayList<Float> depthsFromDataPoints = new ArrayList<>();
            for(DataPoint dataPoint : dataPointsAtSpecificLatLon) {
                 float depth = dataPoint.getDepth();
                depthsFromDataPoints.add(depth);
            }

            // sort depths
            Collections.sort(depthsFromDataPoints);

            //

        }

        return dataPointArrayList;
    }



}
