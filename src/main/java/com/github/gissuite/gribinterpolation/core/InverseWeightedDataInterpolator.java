package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;

public class InverseWeightedDataInterpolator {
        /**
     * @param dataPoints An array list of all the data points with known and unknown temperatures
     *
     * @return the dataPoints array list with all the unknown temperature values replaced with interpolated values
     */
    public static ArrayList<ArrayList<ArrayList<DataPoint>>> DataInterpolator(ArrayList<ArrayList<ArrayList<DataPoint>>> dataPoints){

        //create 2 arraylists of points with known and unknown temperatures
        ArrayList<DataPoint> knownDataPoints = new ArrayList<>();
        ArrayList<DataPoint> pointsToInterpolate = new ArrayList<>();

        for (ArrayList<ArrayList<DataPoint>> dataPoints1 : dataPoints) {
            for(ArrayList<DataPoint> points : dataPoints1){
                for (DataPoint dataPoint : points){
                    if (Float.isNaN(dataPoint.getTemperatureK())) {
                        pointsToInterpolate.add(dataPoint);
                    }
                    else {
                        knownDataPoints.add(dataPoint);
                    }
                }
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
