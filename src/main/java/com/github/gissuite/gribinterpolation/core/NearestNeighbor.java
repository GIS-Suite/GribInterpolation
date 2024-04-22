package com.github.gissuite.gribinterpolation.core;
import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.*;
import java.util.stream.Collectors;
public class NearestNeighbor {
    /**
     * @param dataPoints All the DataPoints in the data set
     * @param longitudeToInterpolate The longitude value of the point needed to be interpolated
     * @param latitudeToInterpolate  The latitude value of the point needed to be interpolated
     * @param k How many nearest neighbors to return
     * @param amountOfDataPoints The total about of DataPoints
     * @return ArrayList of nearest neighbors (DataPoints)
     */
        public static ArrayList<DataPoint> getNearestNeighbor(ArrayList<DataPoint> dataPoints, float longitudeToInterpolate, float latitudeToInterpolate, int k, int amountOfDataPoints) {
            ArrayList<DataPoint> nearestNeighbors = new ArrayList<>();
            HashMap<DataPoint, Double> hashMap = new HashMap<>();
            for (int i = 0; i < amountOfDataPoints; i++) {
                double distance = DistanceFinder.haverSine(dataPoints.get(i).getLatitude(), dataPoints.get(i).getLongitude(), latitudeToInterpolate, longitudeToInterpolate);
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

    }


