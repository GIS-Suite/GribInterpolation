package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.gissuite.gribinterpolation.core.LinearInt.interpolate;
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

        printMap(dataPointsMap);
        //Use linear interpolation on each group of data points
        //traverse through every key(lat/lon) in the map to get each list of data points
        for (Map.Entry<Pair<Float, Float>, List<DataPoint>> latLonKey : dataPointsMap.entrySet()) {

            //get list of data points at current latitude and longitude
            List<DataPoint> groupedDataPointsList = latLonKey.getValue();

            int listIndex = 0;
            int nextListIndex;
            int upperBoundIndex = 0;
            int lowerBoundIndex = 0;
            DataPoint upperBoundDataPoint = null;
            DataPoint lowerBoundDataPoint = null;

            while (listIndex < groupedDataPointsList.size()) {
                DataPoint currentDataPoint = groupedDataPointsList.get(listIndex);

                if (!Float.isNaN(currentDataPoint.getTemperatureK())) {

                    //get upper and lower data point boundaries for linear interpolation
                    if (upperBoundDataPoint == null) {
                        upperBoundDataPoint = currentDataPoint;
                        upperBoundIndex = listIndex;
                        listIndex++;
                    } else {
                        if((int)currentDataPoint.getDepth() == (int)upperBoundDataPoint.getDepth() + 1) {
                            upperBoundDataPoint = currentDataPoint;
                            upperBoundIndex = listIndex;
                            listIndex++;
                        } else {
                            lowerBoundDataPoint = currentDataPoint;
                            lowerBoundIndex = listIndex;

                            nextListIndex = upperBoundIndex + 1;
                            int numberOfMissingDepths = (int)(lowerBoundDataPoint.getDepth() - upperBoundDataPoint.getDepth()) - 1;
                            int currentTargetDepth = (int)upperBoundDataPoint.getDepth() + 1;

                            //use loop to create interpolated data point and add to the list of data points
                            for (int i = 0; i < numberOfMissingDepths; i++) {

                                //Create target data point
                                Pair<Float, Float> latLonPair = latLonKey.getKey();
                                DataPoint targetDataPoint = new DataPoint(latLonPair.getSecond(), latLonPair.getFirst(), NaN, (float)currentTargetDepth);

                                //Call linear interpolation method with upper bound data point, lower bound data point, and target data point
                                DataPoint interpolatedDataPoint = interpolate(upperBoundDataPoint, lowerBoundDataPoint, targetDataPoint);

                                //if temperature of next data point in list is NaN, replace with interpolated data point

                                if (Float.isNaN(groupedDataPointsList.get(nextListIndex).getTemperatureK())){
                                    groupedDataPointsList.set(nextListIndex, interpolatedDataPoint);
                                } else {
                                    groupedDataPointsList.add(nextListIndex, interpolatedDataPoint);
                                    lowerBoundIndex += 1;
                                }
                                nextListIndex++;

                                currentTargetDepth++;
                            }

                            //assign previous lowerBoundDataPoint to
                            upperBoundDataPoint = groupedDataPointsList.get(lowerBoundIndex);
                            lowerBoundDataPoint = null;
                            listIndex = lowerBoundIndex + 1;
                        }
                    }
                }
            }

            //sort list

            //update map entry with updated list
            dataPointsMap.put(latLonKey.getKey(), groupedDataPointsList);

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
