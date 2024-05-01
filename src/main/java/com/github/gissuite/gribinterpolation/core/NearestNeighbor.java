package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import com.github.gissuite.gribinterpolation.data.FilterNaN;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import java.util.Map.Entry;
import static com.github.gissuite.gribinterpolation.data.GroupBy.groupByLatLonWithDepthSort;

public class NearestNeighbor {

    public static void main(String[] args) {
//        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>(Arrays.asList(
//                new DataPoint(101, 90, Float.NaN, 10),
//                new DataPoint(102, 90, 49, 11),
//                new DataPoint(100, 90, Float.NaN, 10),
//                new DataPoint(103, 90, Float.NaN, 40)
//        ));
//        DataPoint dataPointToInterpolate = new DataPoint(103, 90,40,11);
//        ArrayList<DataPoint> result = new ArrayList<>();
//        result = NearestNeighbor.getNearestNeighbor(dataPointArrayList,dataPointToInterpolate,4);
//
//        ArrayList<DataPoint> expectedResult = new ArrayList<>(Arrays.asList(
//                new DataPoint(103,-90, Float.NaN, 11),
//                new DataPoint(101, -90, 49, 22)
//
//        ));
        DataPoint pointA0 = new DataPoint(100, -90, 51, 10);
        DataPoint pointB2 = new DataPoint(101, -90, Float.NaN, 2);
        DataPoint pointC10 = new DataPoint(102, -90, 50, 0);
        DataPoint pointD11 = new DataPoint(103, -90, 48, 11);
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
        dataPointArrayList.add(pointA0);dataPointArrayList.add(pointB2);dataPointArrayList.add(pointC10);dataPointArrayList.add(pointD11);
        KnnInterpolation(dataPointArrayList);


    }

    public static void KnnInterpolation(ArrayList<DataPoint> arrayListOfDataPoints) {
        ArrayList<DataPoint> myArrayListWithoutNaNValues = FilterNaN.Filter(arrayListOfDataPoints);

        for(int i =0; i < myArrayListWithoutNaNValues.size(); i++){
            //may need to group by depth
            Float myDepth = myArrayListWithoutNaNValues.get(i).getDepth();
            myDepth+=1;
//            myDepth.equals(myArrayListWithoutNaNValues.get(i+1).getDepth());

            if( !(myDepth.equals(myArrayListWithoutNaNValues.get(i+1).getDepth())){
                System.out.println("It got true");
            }
        }
//        ArrayList<DataPoint> nearestNeighbors = getNearestNeighbor(myArrayListWithoutNaNValues,dataPointToInterpolate,3);


        //how do i know what is the dataPointToInterpolate?

        }






    static Logger logger = Logger.getLogger(NearestNeighbor.class.getName());
    /**
     * @param dataPoints All the DataPoints in the data set
     * @param dataPointToInterpolate The DataPoint needed to be interpolated
     * @param k How many nearest neighbors to return
     * @return ArrayList of nearest neighbors (DataPoints)
     */
    public static ArrayList<DataPoint> getNearestNeighbor(ArrayList<DataPoint> dataPoints,DataPoint dataPointToInterpolate, int k) {
        ArrayList<DataPoint> nearestNeighbors = new ArrayList<>();
        HashMap<DataPoint, Double> hashMap = new HashMap<>();
        double distance = 0;
        double subtractedDepth= 0;
        double abssubstractedDepth =0;
        for (int i = 0; i < dataPoints.size(); i++) {
            subtractedDepth = (dataPointToInterpolate.getDepth() - dataPoints.get(i).getDepth())/1000;
            abssubstractedDepth = Math.abs(subtractedDepth);
            if ((dataPoints.get(i).getLatitude() == dataPointToInterpolate.getLatitude() && (dataPoints.get(i).getLongitude() == dataPointToInterpolate.getLongitude()))){
                distance= abssubstractedDepth;
            }
            else {
                double distanceHaversine = DistanceFinder.haverSine(dataPoints.get(i).getLatitude(), dataPoints.get(i).getLongitude(), dataPointToInterpolate.getLatitude(), dataPointToInterpolate.getLongitude());
                distance = Math.pow(distanceHaversine, 2) + (Math.pow(abssubstractedDepth, 2));
            }
            hashMap.put(dataPoints.get(i), distance);
        }
        Map<DataPoint, Double> sortedHashMap = sortByValue(hashMap);
        Set<DataPoint> keySet = sortedHashMap.keySet();
        Iterator<DataPoint> iterator = keySet.iterator();
        int count = 0;
        while (iterator.hasNext() && count < k) {
            DataPoint key = iterator.next();
            nearestNeighbors.add(key);
            count++;
        }
        return nearestNeighbors;
    }

    private static Map<DataPoint, Double> sortByValue(HashMap<DataPoint, Double> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (firstValue, streamValue) -> firstValue, LinkedHashMap::new));
    }

    /**
     * @param neighbors list of neighbors
     * @param dataPointToInterpolate point we are interpolating
     */
    public static DataPoint knnInterpolationForOneDataPoint(ArrayList<DataPoint> neighbors, DataPoint dataPointToInterpolate, int k){
        int amount = neighbors.size();
        ArrayList<DataPoint> nearest = getNearestNeighbor(neighbors,dataPointToInterpolate,k);
        // placeholder values
        float interpolatedTemp = 0.0f;
        float totalTemp = 0;
        try {
            for (DataPoint nearestNeighbor : nearest) { // look through the array list of nearest neighbor
                // getting temperature
                totalTemp += nearestNeighbor.getTemperatureK();
            }
            interpolatedTemp = totalTemp/k;
            dataPointToInterpolate.setTemperatureK(interpolatedTemp);
        }
        catch(Exception e){ logger.warning("Error with interpolation: " + e); }
        return dataPointToInterpolate;
    } // end of knnInterpolation() :)
}
