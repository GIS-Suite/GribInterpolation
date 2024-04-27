package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.gissuite.gribinterpolation.data.GroupBy.groupByLatLonWithDepthSort;
import static java.lang.Float.NaN;

public class DatasetLinearInterpolation {

    /**
     * @param dataPointArrayList The ArrayList of all the data points including NaN-temperature data points
     * @return The ArrayList of all the data points with NaN-temperature replaced with linear interpolated temperature
     */
    public static ArrayList<DataPoint> dataPointsLinearInterpolation(ArrayList<DataPoint> dataPointArrayList){
        // shape the dataset
        Map<Pair<Float, Float>, List<DataPoint>> dataPointsMap = groupByLatLonWithDepthSort(dataPointArrayList);

        //Use linear interpolation on each group of data points
        //traverse through every key(lat/lon) in the map to get each list of data points
        for (Map.Entry<Pair<Float, Float>, List<DataPoint>> latLonEntry : dataPointsMap.entrySet()) {

            //get list of data points at current latitude and longitude
            List<DataPoint> groupedDataPointsList = latLonEntry.getValue();

            int listIndex = 0;
            DataPoint upperBoundDataPoint = null;
            DataPoint lowerBoundDataPoint = null;

            //traverse through list and use linear interpolation for the data points at missing depths
            while (listIndex < groupedDataPointsList.size()) {
                DataPoint currentDataPoint = groupedDataPointsList.get(listIndex);
                if (!Float.isNaN(currentDataPoint.getTemperatureK())) {

                    //get upper and lower data point boundaries for linear interpolation
                    while (upperBoundDataPoint == null || lowerBoundDataPoint == null) {
                        if (upperBoundDataPoint == null) {
                            upperBoundDataPoint = currentDataPoint;
                        } else {
                            lowerBoundDataPoint = currentDataPoint;
                        }
                    }




                }
            }

            //sort list

            //update map entry with updated list

        }

        printMap(dataPointsMap);

        return dataPointArrayList;
    }

    /**
     *
     * @param dataPointsMap Map of data points in the dataset
     */
    public static void printMap(Map<Pair<Float, Float>, List<DataPoint>> dataPointsMap) {
        System.out.println("\n------------------Data Shape------------------");

        for (Map.Entry<Pair<Float, Float>, List<DataPoint>> latLonKey : dataPointsMap.entrySet()) {

            List<DataPoint> dataPointsOfKey = latLonKey.getValue();

            System.out.println(latLonKey.getKey() + "=");
            for (DataPoint dataPointEntry : dataPointsOfKey) {
                System.out.println("Depth: " + dataPointEntry.getDepth() + ", " + "Temp: " + dataPointEntry.getTemperatureK());
            }
            System.out.println();
        }
    }

}
