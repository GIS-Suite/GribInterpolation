package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.util.Pair;


import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.logging.Logger;
public class NearestNeighbor {

    public static void main(String[] args) {
        ArrayList<DataPoint> dataPointArrayList = new ArrayList<>(Arrays.asList(
                new DataPoint(101, 90, Float.NaN, 10),
                new DataPoint(102, 90, 49, 11),
                new DataPoint(100, 90, Float.NaN, 10),
                new DataPoint(103, 90, Float.NaN, 40)
        ));
        DataPoint dataPointToInterpolate = new DataPoint(103, 90,40,11);
        ArrayList<DataPoint> result = new ArrayList<>();
        result = NearestNeighbor.getNearestNeighbor(dataPointArrayList,dataPointToInterpolate,4);

        ArrayList<DataPoint> expectedResult = new ArrayList<>(Arrays.asList(
                new DataPoint(103,-90, Float.NaN, 11),
                new DataPoint(101, -90, 49, 22)
        ));
//        assertEquals(expectedResult.get(0), result.get(0));
        System.out.println(result.get(0).getDepth());
    }
//    public static ArrayList<DataPoint> DataShaper(ArrayList<DataPoint> dataPointArrayList){
//        Map<Pair<Float, Float>, List<DataPoint>> allDataPointsGroupByLatLon = dataPointArrayList
//                .stream()
//                .collect(
//                        Collectors.groupingBy(dp -> new Pair<>(dp.getLatitude(),dp.getLongitude()))
//                );
//        Map<Pair<Float, Float>, List<DataPoint>> filter = allDataPointsGroupByLatLon.entrySet().stream().filter(x->x.getValue().stream().anyMatch(y -> !Float.isNaN(y.getTemperatureK()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    return;
//    }


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
//                    distance = Math.sqrt(distanceSquared);
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
    public static double knnInterpolation(ArrayList<DataPoint> neighbors, DataPoint dataPointToInterpolate, int k){
        int amount = neighbors.size();
        ArrayList<DataPoint> nearest = getNearestNeighbor(neighbors,dataPointToInterpolate,k);
        // placeholder values
        double interpolatedTemp = 0.0;
        float totalTemp = 0;
        try {
            for (DataPoint nearestNeighbor : nearest) { // look through the array list of nearest neighbor
                // getting temperature
                totalTemp += nearestNeighbor.getTemperatureK();
            }
            interpolatedTemp = totalTemp/k;
        }
        catch(Exception e){ logger.warning("Error with interpolation: " + e); }
        return interpolatedTemp;
    } // end of knnInterpolation() :)
}
