package com.github.gissuite.gribinterpolation.core;
import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.*;
import java.util.stream.Collectors;

//gets k nearest neighbors' memory locations stored in a DataPoint array
//amountOfDataPoints is how many points to find the distance for; so should be how many DataPoints in total we have.
//k is how many neighbors to look for

//can get temperature this way:
//ArrayList<DataPoint> nearestNeighbors = (getNearestNeighbor(arrayOfDataPoints, longitudeToInterpolate, latitudeToInterpolate, k, amountOfDataPoints));
//DataPoint a = (DataPoint) nearestNeighbors.get(0);
//System.out.println(a.getTemperatureK());
public class NearestNeighbor {

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


