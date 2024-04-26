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

        // Test print all data points grouped by Lat/Lon (DELETE LATER)
        System.out.println("All data points grouped by Lat/Lon");
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

        // Test print data points without all-NaN data points (Delete Later)
        System.out.println("data points without all-NaN data points");
        for (Map.Entry<?, ?> entry : dataPointsByLatLonWithTemp.entrySet()) {
            System.out.printf("%-15s : %s%n", entry.getKey(), entry.getValue());
        }
        System.out.println();

        // Sort dataPointsByLatLonWithTemp by depth:
        //      for each entry: sort data points by depth for each LatLon entry, use streams to match up the order
        for(Map.Entry<Pair<Float, Float>, List<DataPoint>> latLonEntry: dataPointsByLatLonWithTemp.entrySet()){

            List<DataPoint> dataPointsAtSpecificLatLon = latLonEntry.getValue();

            //test print order of data points before sort (DELETE LATER)
            System.out.println("data points for each Lat/Lon before and after sort by depth");
            System.out.print(latLonEntry.getKey() + "=");
            System.out.println(dataPointsAtSpecificLatLon);

            // sort depths
            dataPointsAtSpecificLatLon.sort(Comparator.comparing(DataPoint::getDepth));

            //  update map with depth-sorted data points
            dataPointsByLatLonWithTemp.put(latLonEntry.getKey(), dataPointsAtSpecificLatLon);

            //Test print order of data points after sort (DELETE LATER)
            System.out.print(latLonEntry.getKey() + "=");
            System.out.println(dataPointsAtSpecificLatLon);
            System.out.println();
        }

        //traverse through every key(lat/lon) in the map to get each list of data points
        for (Map.Entry<Pair<Float, Float>, List<DataPoint>> latLonEntry: dataPointsByLatLonWithTemp.entrySet()) {

            List<DataPoint> dataPointsAtSpecificLatLon = latLonEntry.getValue();

            //traverse through List of data points starting with depth at 0.
//            for
//            if (dataPoint.getDepth() != NaN) {
//                DataPoint upperBoundaryPoint =
//            }


        }


        return dataPointArrayList;
    }
}
