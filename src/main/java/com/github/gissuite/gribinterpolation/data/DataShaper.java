package com.github.gissuite.gribinterpolation.data;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataShaper {

    /**
     * @param dataPointArrayList An Arraylist of all the data points in the dataset
     * @return A Map of the data points grouped by Latitude & Longitude and sorted by depth without the all-NaN temperature groups
     */
    public static Map<Pair<Float, Float>, List<DataPoint>> groupByLatLonWithDepthSort(ArrayList<DataPoint> dataPointArrayList) {

        // group all data points by latitude & longitude
        Map<Pair<Float, Float>, List<DataPoint>> allDataPointsGroupByLatLon = dataPointArrayList
                .stream()
                .collect(
                        Collectors.groupingBy(dp -> new Pair<>(dp.getLatitude(),dp.getLongitude()))
                );

        // filter group of data points with non NaN temperatures
        Map<Pair<Float, Float>, List<DataPoint>> filteredDataPointsGroupByLatLon = allDataPointsGroupByLatLon.entrySet()
                .stream()
                .filter(
                        x -> x.getValue()
                                .stream()
                                .anyMatch(y -> !Float.isNaN(y.getTemperatureK()))
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
                );

        // Sort filteredDataPointsGroupByLatLon by depth:
        for(Map.Entry<Pair<Float, Float>, List<DataPoint>> mapEntry : filteredDataPointsGroupByLatLon.entrySet()){
            List<DataPoint> dataPointsAtSpecificLatLon = mapEntry.getValue();
            dataPointsAtSpecificLatLon.sort(Comparator.comparing(DataPoint::getDepth));

            //  update map with depth-sorted data points
            filteredDataPointsGroupByLatLon.put(mapEntry.getKey(), dataPointsAtSpecificLatLon);
        }
        return filteredDataPointsGroupByLatLon;
    }

    /**
     * @param dataPointsMap Map of data points in the dataset
     */
    public static void printGroupByLatLonMap(Map<Pair<Float, Float>, List<DataPoint>> dataPointsMap) {
        System.out.println("\n------------------Data Shape------------------");

        for (Map.Entry<Pair<Float, Float>, List<DataPoint>> latLonKey : dataPointsMap.entrySet()) {

            List<DataPoint> dataPointsOfKey = latLonKey.getValue();

            System.out.println("[Lat, Lon]=" + latLonKey.getKey());
            for (DataPoint dataPointEntry : dataPointsOfKey) {
                System.out.println("Depth: " + dataPointEntry.getDepth() + ", " + "Temp: " + dataPointEntry.getTemperatureK());
            }
            System.out.println();
        }
    }
}