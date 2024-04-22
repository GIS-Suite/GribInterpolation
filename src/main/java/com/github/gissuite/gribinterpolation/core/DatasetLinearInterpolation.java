package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

public class DatasetLinearInterpolation {
    /**
     * @param dataPointArrayList The ArrayList of all the data points including NaN-temperature data points
     * @return The ArrayList of all the data points with NaN-temperature replaced with linear interpolated temperature
     */
    public static ArrayList<DataPoint> dataPointsLinearInterpolation(ArrayList<DataPoint> dataPointArrayList){
        //group all data points by latitude
        Map<Float, List<DataPoint>> dataPointByLatitude = dataPointArrayList
                .stream()
                .collect(
                        Collectors.groupingBy(DataPoint::getLatitude)
                );

        //filter out group of data points with all NaN temperatures


        return dataPointArrayList;
    }

}
