package com.github.gissuite.gribinterpolation.core;
import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.*;
import java.util.stream.Collectors;

public class NearestNeighbor {
private static Map<DataPoint, Double> sortByValue(HashMap<DataPoint, Double> map) {
    return map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (firstValue, streamValue) -> firstValue, LinkedHashMap::new));
}
    public static DataPoint[] getNearestNeighbor(DataPoint[] dataPoint, float longitudeToInterpolate, float latitudeToInterpolate, int k, int amountOfDataPoints) {
        DataPoint[] nearestNeighbors = new DataPoint[k];
        HashMap<DataPoint, Double> hashMap = new HashMap<>();
        for (int i = 0; i < amountOfDataPoints; i++) {
            double distance = DistanceFinder.haverSine(dataPoint[i].getLatitude(), dataPoint[i].getLongitude(), latitudeToInterpolate, longitudeToInterpolate);
            hashMap.put(dataPoint[i], distance);
        }
        Map<DataPoint, Double> sortedHashMap = sortByValue(hashMap);
        Set<DataPoint> keySet = sortedHashMap.keySet();
        Iterator<DataPoint> iterator = keySet.iterator();
        int count = 0;
        while(iterator.hasNext() && count < k){
            DataPoint key = iterator.next();
            nearestNeighbors[count] = key;
            count++;
        }
        return nearestNeighbors;
    }

}

