package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.*;
import java.util.stream.*;
import static java.lang.Float.NaN;

public class DatasetBicubicInterpolation {
    private static Map<Object, List<DataPoint>> shapedDataPoints;
    
    /**
     * @param dataPointArrayList The ArrayList of all the data points including NaN-temperature data points
     * @return The ArrayList of all the data points with NaN-temperature replaced with Bicubic interpolated temperature
     */
    public static ArrayList<DataPoint> dataPointsBicubicInterpolation(ArrayList<DataPoint> dataPoints){

        //shapedDataPoints = GroupBy.groupByLatLonWithDepthSort(dataPoints);
        shapedDataPoints = ShapeData(dataPoints);
        ArrayList<DataPoint> pointsToReturn = new ArrayList<DataPoint>();
        
        for(Map.Entry<Object, List<DataPoint>> entry : shapedDataPoints.entrySet()){
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
    /*
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
    */

    private static DataPoint interpolate(DataPoint dp){
        float interval = 0.01f;
        DataPoint[][] arr = new DataPoint[4][4];
        DataPoint pointToReturn;

        while(interval < 1f){
            try{
                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < 4; j++){
                        final int j1 = j;
                        final float interval1 = interval;
                        arr[i][j] = shapedDataPoints.get(dp.getLatitude() - (2*i-3) * interval)
                            .stream()
                            .filter(
                                x -> x.getLongitude() == dp.getLongitude() + (2*j1-3) * interval1 && x.getDepth() == dp.getDepth()
                            ).findFirst().get();
                    }
                }
                pointToReturn = BicubicInterpolation.interpolateLatLong(arr, dp);
                return pointToReturn;
            }
            catch(Exception e){//Map throws an error when trying to get a null point
                System.out.println(e);//Delete Later///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                interval = interval + 0.01f;
            }
        }
        //if the point doesn't exist and we want it too this will break fix later//
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                final int j1 = j;
                arr[i][j] = shapedDataPoints.get(dp.getLatitude() - (2*i-3) * 0.08f)
                    .stream()
                    .filter(
                        x -> x.getLongitude() == dp.getLongitude() + (2*j1-3) * 0.08f && x.getDepth() == dp.getDepth()
                    ).findFirst().get();
                if(Float.isNaN(arr[i][j].getTemperatureK())){
                    arr[i][j] = InverseWeighted.inverseWeighted(new ArrayList<DataPoint>(shapedDataPoints.get(dp.getLatitude() - (2*i-3))), arr[i][j]);
                }
            }
        }
        //maybe add another default case where it just returns NaN just so the program doesn't explode on unforseen edge cases//
        pointToReturn = BicubicInterpolation.interpolateLatLong(arr, dp);
        return pointToReturn;
    }

    private static Map<Object, List<DataPoint>> ShapeData(ArrayList<DataPoint> data){
        Map<Object, List<DataPoint>> filteredShapedData = data.stream()
            .collect(
            Collectors.groupingBy(dp -> Math.round(dp.getLatitude() * 100.0) / 100.0)
            );

        filteredShapedData.entrySet()
                .stream()
                .forEach(
                    z -> {
                        z.getValue()
                        .removeIf(y -> 
                            z.getValue()
                            .stream()
                            .filter(
                                x -> y.getLongitude() == x.getLongitude()
                            )
                            .allMatch(x -> Float.isNaN(x.getTemperatureK()))
                        );
                    }
                );

        for(Map.Entry<Object, List<DataPoint>> entry : filteredShapedData.entrySet()){
            List<DataPoint> longs = entry.getValue();
            longs.sort(Comparator.comparing(DataPoint::getLongitude));
            filteredShapedData.put(entry.getKey(), longs);
        }

        /*
        System.out.println("Start\n");
        for(Map.Entry<Object, List<DataPoint>> entry : filteredShapedData.entrySet()){
            for(DataPoint dp : entry.getValue()){
                System.out.println(dp.getDepth());
                System.out.println(entry.getKey() + " " + dp.getLongitude());
                System.out.println(dp.getTemperatureK());
                System.out.println();
            }
        }
        //*/

        return filteredShapedData;
    }

    public static void main(String[] args){//make test data closer to actual grib file
        DataPoint pa1 = new DataPoint(0f, 0f, 300f, 0f);
        DataPoint pa2 = new DataPoint(0f, 0f, 301f, 1f);
        DataPoint pb1 = new DataPoint(0f, 1f, 302f, 0f);
        DataPoint pb2 = new DataPoint(0f, 1f, 303f, 1f);

        DataPoint pa1n = new DataPoint(1f, 0f, NaN, 0f);
        DataPoint pa2n = new DataPoint(1f, 0f, NaN, 1f);
        DataPoint pa3n = new DataPoint(1f, 0f, NaN, 2f);

        DataPoint pb1n = new DataPoint(1f, 2f, NaN, 0f);
        DataPoint pb2n = new DataPoint(1f, 2f, NaN, 1f);
        DataPoint pb3n = new DataPoint(1f, 2f, 304f, 2f);

        ArrayList<DataPoint> arr = new ArrayList<DataPoint>();
        arr.add(pa1);
        arr.add(pa2);
        arr.add(pb1);
        arr.add(pb2);
        arr.add(pa1n);
        arr.add(pa2n);
        arr.add(pa3n);
        arr.add(pb1n);
        arr.add(pb2n);
        arr.add(pb3n);

        ShapeData(arr);
        //dataPointsBicubicInterpolation(arr);
    }
}
