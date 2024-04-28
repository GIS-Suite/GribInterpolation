package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.logging.Logger;
public class NearestNeighbor {
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
            for (int i = 0; i < dataPoints.size(); i++) {
                double distanceHaversine = DistanceFinder.haverSine(dataPoints.get(i).getLatitude(), dataPoints.get(i).getLongitude(), dataPointToInterpolate.getLatitude(), dataPointToInterpolate.getLongitude());
                double subtractedDepth = (dataPointToInterpolate.getDepth() - dataPoints.get(i).getDepth());
                double distance = Math.pow(distanceHaversine,2) + Math.pow(subtractedDepth,2);

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
    public static DataPoint knnInterpolation(ArrayList<DataPoint> neighbors, DataPoint toInterpolate, int k){
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
        toInterpolate.setTemperatureK((float)interpolatedTemp);
        return toInterpolate;
    } // end of knnInterpolation() :)
}
