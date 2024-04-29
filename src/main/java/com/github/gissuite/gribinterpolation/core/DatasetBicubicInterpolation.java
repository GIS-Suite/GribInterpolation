package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import com.github.gissuite.gribinterpolation.data.GroupBy;
import org.apache.commons.math3.util.Pair;

import java.util.*;

public class DatasetBicubicInterpolation {//need to look at interval being 0.04, what happens when the intervals miss by small amounts, and better way of doing the inverse weighted/////
    private static Map<Pair<Float, Float>, List<DataPoint>> shapedDataPoints;
    
    /**
     * @param dataPointArrayList The ArrayList of all the data points including NaN-temperature data points
     * @return The ArrayList of all the data points with NaN-temperature replaced with Bicubic interpolated temperature
     */
    public static ArrayList<DataPoint> dataPointsBicubicInterpolation(ArrayList<DataPoint> dataPoints){

        shapedDataPoints = GroupBy.groupByLatLonWithDepthSort(dataPoints);
        ArrayList<DataPoint> pointsToReturn = new ArrayList<DataPoint>();
        
        for(Map.Entry<Pair<Float, Float>, List<DataPoint>> entry : shapedDataPoints.entrySet()){
            List<DataPoint> pointList = entry.getValue();

            for(DataPoint dp : pointList){
                if(Float.isNaN(dp.getTemperatureK())){
                    DataPoint interpolated = interpolate(dp);
                    pointsToReturn.add(interpolated); 
                }
                else{
                    pointsToReturn.add(dp);
                }
            }
        }

        return pointsToReturn;
    }
    
    private static DataPoint interpolate(DataPoint dp){
        float interval = 0.01f;
        DataPoint[][] inputArr = new DataPoint[4][4];
        DataPoint pointToReturn;

        //Interpolating by making a square around the point, expanding if neccessary//
        while(interval < 1f){
            try{
                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < 4; j++){
                        inputArr[i][j] = shapedDataPoints.get(new Pair<>(dp.getLatitude() - (2*i-3) * interval, dp.getLongitude() + (2*j - 3) * interval))
                                                        .stream()
                                                        .filter(
                                                            x -> x.getDepth() == dp.getDepth()
                                                        ).findFirst().get();
                    }
                }

                pointToReturn = BicubicInterpolation.interpolateLatLong(inputArr, dp);
                return pointToReturn;
            }
            catch(Exception e){
                System.out.println(e);// please for the love of God don't forget to delete this later ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                interval = interval + 0.01f;
            }
        }

        //if the square gets too big without finding enough points. This goes to a set size and finds the NaN points via inverse weighted//
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                inputArr[i][j] = shapedDataPoints.get(new Pair<>(dp.getLatitude() - (2*i-3) * 0.08f, dp.getLongitude() + (2*j-3) * 0.08f))
                                                .stream()
                                                .filter(
                                                    x -> x.getDepth() == dp.getDepth()
                                                ).findFirst().get();

                if(Float.isNaN(inputArr[i][j].getTemperatureK())){
                    inputArr[i][j] = InverseWeighted.inverseWeighted(new ArrayList<DataPoint>(shapedDataPoints.get(new Pair<>(dp.getLatitude() - (2*i-3) * 0.04f, dp.getLongitude() + (2*j-3) * 0.04f))), inputArr[i][j]);
                }
            }
        }

        pointToReturn = BicubicInterpolation.interpolateLatLong(inputArr, dp);
        return pointToReturn;
    }
}
