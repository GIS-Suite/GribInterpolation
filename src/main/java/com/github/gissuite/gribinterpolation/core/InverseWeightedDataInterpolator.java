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
//        ArrayList<DataPoint> knownDataPoints = new ArrayList<>();
//        ArrayList<DataPoint> pointsToInterpolate = new ArrayList<>();

        //extrapolate data points and allocate to the correct list

//        List<Integer> filteredList = array3D.stream()
//                .flatMap(List::stream) // Flatten the outermost dimension
//                .flatMap(List::stream) // Flatten the second dimension
//                .filter(value -> value > threshold)
//                .collect(Collectors.toList());

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


//
//        for (ArrayList<ArrayList<DataPoint>> dataPoints1 : dataPoints) {
//            for(ArrayList<DataPoint> points : dataPoints1){
//                for (DataPoint dataPoint : points){
//                    if (Float.isNaN(dataPoint.getTemperatureK())) {
////                        pointsToInterpolate.add(dataPoint);
//                    }
//                    else {
//                        knownDataPoints.add(dataPoint);
//                    }
//                }
//            }
//        }


        //call the inverseWeighted interpolation method finding the
        //temperatures of pointsToInterpolate using knownDataPoints
        for (DataPoint interpolatePoint : pointsToInterpolate) {
            InverseWeighted.inverseWeighted(knownDataPoints, interpolatePoint);
        }

        //return updated arraylist
        return dataPoints;
    }
}
