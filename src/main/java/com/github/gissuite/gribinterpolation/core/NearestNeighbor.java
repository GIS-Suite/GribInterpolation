package com.github.gissuite.gribinterpolation.core;
import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.*;
import java.util.stream.Collectors;

public class NearestNeighbor {
public static Map<DataPoint, Double> sortByValue(HashMap<DataPoint, Double> map) {
    Map<DataPoint, Double> sortedMap = map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (firstValue, streamValue) -> firstValue, LinkedHashMap::new));
    return sortedMap;
}
    public static void main(String[] args) {
        DataPoint first = new DataPoint(-66, 38.8f, 271.4f, 300);
        DataPoint second = new DataPoint(-62, 38.8f, 273.3f, 400);
        DataPoint third = new DataPoint(-68, 38.8f, 273.3f, 400);
        DataPoint expectedResult = new DataPoint(-62, 38.8f, 272.44498f, 355);
        DataPoint[] arrayOfDataPoints = {first, second, third};
        DataPoint[] nearestNeighbors = getNearestNeighbor(arrayOfDataPoints, -62, 38.8f, 2, 3);
        System.out.println(Arrays.toString(nearestNeighbors)); //prints out the location of the DataPoints

        //can get temperature this way:
        DataPoint a = nearestNeighbors[0];
        System.out.println(a.getTemperatureK());
    }
    public static DataPoint[] getNearestNeighbor(DataPoint[] dataPoint, float longitudeToInterpolate, float latitudeToInterpolate, int k, int amountOfDataPoints) {
        DataPoint[] nearestNeighbors = new DataPoint[k];
        HashMap<DataPoint, Double> hashMap = new HashMap<>();
        for (int i = 0; i < amountOfDataPoints; i++) {
            double distance = DistanceFinder.haverSine(dataPoint[i].getLatitude(), dataPoint[i].getLongitude(), latitudeToInterpolate, longitudeToInterpolate);
            hashMap.put(dataPoint[i], distance);
//            System.out.println(hashMap.get(dataPoint[i]));
        }
        System.out.println(hashMap.values());
//        HashMap sortedHashMap = sortByValue(hashMap);
        Map<DataPoint, Double> sortedHashMap = sortByValue(hashMap);
        System.out.println(sortedHashMap.values());
//            System.out.println(sortedHashMap.keySet());
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

