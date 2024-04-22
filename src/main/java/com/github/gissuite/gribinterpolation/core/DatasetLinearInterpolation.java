package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;
import java.util.stream.*;

public class DatasetLinearInterpolation {
    /**
     * @param dataPointArrayList The ArrayList of all the data points including NaN-temperature data points
     * @return The ArrayList of all the data points with NaN-temperature replaced with linear interpolated temperature
     */
    public static ArrayList<DataPoint> dataPointsLinearInterpolation(ArrayList<DataPoint> dataPointArrayList){
        Stream<DataPoint> LonLatDataPointStream = dataPointArrayList.stream().filter(dp -> dp.getLongitude() == 100);
        long count = LonLatDataPointStream.count();
        System.out.println(count);
        return dataPointArrayList;
    }

}
