package com.github.gissuite.gribinterpolation.data;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupBy {

    public static Map<Pair<Float, Float>, List<DataPoint>> groupByLatLonWithDepthSort(ArrayList<DataPoint> dataPointArrayList) {

        // group all data points by latitude and longitude
        Map<Pair<Float, Float>, List<DataPoint>> allDataPointsGroupByLatLon = dataPointArrayList
                .stream()
                .collect(
                        Collectors.groupingBy(dp -> new Pair<>(dp.getLatitude(),dp.getLongitude()))
                );

        // Test print all data points grouped by Lat/Lon (DELETE LATER)
        System.out.println("All data points grouped by Lat/Lon");
        for (Map.Entry<?, ?> entry : allDataPointsGroupByLatLon.entrySet()) {
            System.out.printf("%-15s : %s%n", entry.getKey(), entry.getValue());
        }
        System.out.println();

        // filter group of data points with non NaN temperatures
        Map<Pair<Float, Float>, List<DataPoint>> filteredDataPointsGroupByLatLon = allDataPointsGroupByLatLon.entrySet()
                .stream()
                .filter(
                        x -> x.getValue()
                                .stream()
                                .anyMatch(y -> !Float.isNaN(y.getTemperatureK()))
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Test print data points without all-NaN data points (Delete Later)
        System.out.println("data points without all-NaN data points");
        for (Map.Entry<?, ?> entry : filteredDataPointsGroupByLatLon.entrySet()) {
            System.out.printf("%-15s : %s%n", entry.getKey(), entry.getValue());
        }
        System.out.println();

        // Sort filteredDataPointsGroupByLatLon by depth:
        for(Map.Entry<Pair<Float, Float>, List<DataPoint>> latLonEntry: filteredDataPointsGroupByLatLon.entrySet()){

            List<DataPoint> dataPointsAtSpecificLatLon = latLonEntry.getValue();

            //test print order of data points before sort (DELETE LATER)
            System.out.println("data points for each Lat/Lon before and after sort by depth");
            System.out.print(latLonEntry.getKey() + "=");
            System.out.println(dataPointsAtSpecificLatLon);

            // sort depths
            dataPointsAtSpecificLatLon.sort(Comparator.comparing(DataPoint::getDepth));

            //  update map with depth-sorted data points
            filteredDataPointsGroupByLatLon.put(latLonEntry.getKey(), dataPointsAtSpecificLatLon);

            //Test print order of data points after sort (DELETE LATER)
            System.out.print(latLonEntry.getKey() + "=");
            System.out.println(dataPointsAtSpecificLatLon);
            System.out.println();
        }
        return filteredDataPointsGroupByLatLon;
    }

}