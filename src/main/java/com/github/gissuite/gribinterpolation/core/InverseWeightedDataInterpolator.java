package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;

import static java.lang.Float.NaN;

public class InverseWeightedDataInterpolator {
        /**
     * @param dataPoints An array list of all the data points with known and unknown temperatures
     *
     * @return the dataPoints array list with all the unknown temperature values replaced with interpolated values
     */
    public static ArrayList<DataPoint> DataInterpolator(ArrayList<DataPoint> dataPoints){

        //create 2 arraylists of points with known and unknown temperatures
        ArrayList<DataPoint> knownDataPoints = new ArrayList<>();
        ArrayList<DataPoint> pointsToInterpolate = new ArrayList<>();
        for (DataPoint dataPoint : dataPoints){
            if (Float.isNaN(dataPoint.getTemperatureK())) {
                pointsToInterpolate.add(dataPoint);
            }
            else {
                knownDataPoints.add(dataPoint);
            }
        }

        //call the inverseWeighted interpolation method finding the
        //temperatures of pointsToInterpolate using knownDataPoints
        for (DataPoint interpolatePoint : pointsToInterpolate) {
            InverseWeighted.inverseWeighted(knownDataPoints, interpolatePoint);
        }

        //return updated arraylist
        return dataPoints;
    }
}
