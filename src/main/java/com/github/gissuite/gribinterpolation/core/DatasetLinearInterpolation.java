package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.gissuite.gribinterpolation.core.LinearInt.interpolate;
import static com.github.gissuite.gribinterpolation.data.DataShaper.groupByLatLonWithDepthSort;
import static java.lang.Float.NaN;

public class DatasetLinearInterpolation {

    /**
     * @param dataPointArrayList The ArrayList of all the data points including NaN-temperature data points
     * @return The ArrayList of all the data points with NaN-temperature replaced with linear interpolated temperature
     */
    public static ArrayList<DataPoint> dataPointsLinearInterpolation(ArrayList<DataPoint> dataPointArrayList){
        ArrayList<DataPoint> interpolatedDataPointArrayList = new ArrayList<>();

        // shape the dataset
        Map<Pair<Float, Float>, List<DataPoint>> dataPointsMap = groupByLatLonWithDepthSort(dataPointArrayList);

        printMap(dataPointsMap);
        //Use linear interpolation on each group of data points
        //traverse through every key(lat/lon) in the map to get each list of data points
        for (Map.Entry<Pair<Float, Float>, List<DataPoint>> latLonKey : dataPointsMap.entrySet()) {

            //get list of data points at current latitude and longitude
            List<DataPoint> groupedDataPointsList = latLonKey.getValue();

            int listIndex = 0;
            int upperBoundIndex = 0;
            int lowerBoundIndex;
            DataPoint upperBoundDataPoint = null;
            DataPoint lowerBoundDataPoint;

            while (listIndex < groupedDataPointsList.size()) {
                DataPoint currentDataPoint = groupedDataPointsList.get(listIndex);

                //edge case: Check if first Data Point temperature is NaN. If data point temperature is NaN, remove
                if (!Float.isNaN(currentDataPoint.getTemperatureK())) {

                    //get upper and lower data point boundaries for linear interpolation
                    if (upperBoundDataPoint == null) {
                        upperBoundDataPoint = currentDataPoint;
                        listIndex++;
                    } else {
                        if((int)currentDataPoint.getDepth() == (int)upperBoundDataPoint.getDepth() + 1) {
                            upperBoundDataPoint = currentDataPoint;
                            upperBoundIndex = listIndex;
                            listIndex++;
                        } else {
                            lowerBoundDataPoint = currentDataPoint;
                            lowerBoundIndex = listIndex;

                            //
                            int nextListIndex = upperBoundIndex + 1;
                            int numberOfMissingDepths = (int)(lowerBoundDataPoint.getDepth() - upperBoundDataPoint.getDepth()) - 1;
                            int currentTargetDepth = (int)upperBoundDataPoint.getDepth() + 1;

                            //use loop to create interpolated data point and add to the list of data points
                            for (int i = 0; i < numberOfMissingDepths; i++) {

                                //Create target data point
                                Pair<Float, Float> latLonPair = latLonKey.getKey();
                                DataPoint targetDataPoint = new DataPoint(latLonPair.getSecond(), latLonPair.getFirst(), NaN, (float)currentTargetDepth);

                                //Call linear interpolation method with upper bound data point, lower bound data point, and target data point
                                DataPoint interpolatedDataPoint = interpolate(upperBoundDataPoint, lowerBoundDataPoint, targetDataPoint);

                                //edge case: if temperature of next data point in list is NaN, replace with interpolated data point
                                if (Float.isNaN(groupedDataPointsList.get(nextListIndex).getTemperatureK())){
                                    groupedDataPointsList.set(nextListIndex, interpolatedDataPoint);
                                } else {
                                    groupedDataPointsList.add(nextListIndex, interpolatedDataPoint);
                                    lowerBoundIndex += 1;
                                }
                                nextListIndex++;
                                currentTargetDepth++;
                            }

                            //assign previous lowerBoundDataPoint to upperBoundDataPoint
                            upperBoundIndex = lowerBoundIndex;
                            upperBoundDataPoint = lowerBoundDataPoint;
                            listIndex = lowerBoundIndex + 1;
                        }
                    }
                }
                else {
                    groupedDataPointsList.remove(listIndex);
                }
            }

            //update map entry with updated list
            dataPointsMap.put(latLonKey.getKey(), groupedDataPointsList);

            //add updated list of data points to ArrayList
            interpolatedDataPointArrayList.addAll(groupedDataPointsList);
        }

        System.out.println("Data shape after Linear Interpolation:");
        printMap(dataPointsMap);

        //return all data points
        return interpolatedDataPointArrayList;
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
