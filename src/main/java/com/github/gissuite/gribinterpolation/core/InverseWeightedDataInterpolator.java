package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.lang.Float.NaN;

public class InverseWeightedDataInterpolator {
        /**
     * @param dataPoints An array list of all the data points with known and unknown temperatures
     *
     * @return the dataPoints array list with all the unknown temperature values replaced with interpolated values
     */
    public static ArrayList<ArrayList<ArrayList<DataPoint>>> DataInterpolator(ArrayList<ArrayList<ArrayList<DataPoint>>> dataPoints){

        //create 2 arraylists of points with known and unknown temperatures
        //extrapolate data points and allocate to the correct list
        ArrayList<DataPoint> pointsToInterpolate = (ArrayList<DataPoint>) dataPoints.stream()
                .flatMap(ArrayList::stream)
                .flatMap(ArrayList::stream)
                .filter(dataPoint -> Float.isNaN(dataPoint.getTemperatureK()))
                .collect(Collectors.toList());

        ArrayList<DataPoint> knownDataPoints = (ArrayList<DataPoint>) dataPoints.stream()
                .flatMap(ArrayList::stream)
                .flatMap(ArrayList::stream)
                .filter(dataPoint -> !Float.isNaN(dataPoint.getTemperatureK()))
                .collect(Collectors.toList());


        //call the inverseWeighted interpolation method finding the
        //temperatures of pointsToInterpolate using knownDataPoints
        for (DataPoint interpolatePoint : pointsToInterpolate) {
            InverseWeighted.inverseWeighted(knownDataPoints, interpolatePoint);
        }

        //return updated arraylist
        return dataPoints;
    }
}
