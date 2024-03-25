package com.github.gissuite.gribinterpolation.core;


import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.*;

public class NearestNeighbor {
    public static HashMap<DataPoint, Double> sortByValue(HashMap<DataPoint, Double> map) {
        List<Map.Entry<DataPoint, Double>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<DataPoint, Double>>() {
            @Override
            public int compare(Map.Entry<DataPoint, Double> o1, Map.Entry<DataPoint, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        HashMap<DataPoint, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<DataPoint, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());

        }
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
//still need to find a way to get the amount of datapoints...
    //also need to know k
    public static DataPoint[] getNearestNeighbor(DataPoint[] dataPoint, float longitudeToInterpolate, float latitudeToInterpolate, int k, int amountOfDataPoints) {
        DataPoint[] nearestNeighbors = new DataPoint[k];
        HashMap<DataPoint, Double> hashMap = new HashMap<>();
        for (int i = 0; i < amountOfDataPoints; i++) {
            double distance = DistanceFinder.haverSine(dataPoint[i].getLatitude(), dataPoint[i].getLongitude(), latitudeToInterpolate, longitudeToInterpolate);
            hashMap.put(dataPoint[i], distance);
//            System.out.println(hashMap.get(dataPoint[i]));
        }
        System.out.println(hashMap.values());
        HashMap sortedHashMap = sortByValue(hashMap);
        System.out.println(sortedHashMap.values());
//            System.out.println(sortedHashMap.keySet());
        Set<DataPoint> keySet = hashMap.keySet();
        Iterator<DataPoint> iterator = keySet.iterator();
        int count = 0;
        while(iterator.hasNext() && count < k){
            DataPoint key = iterator.next();
            nearestNeighbors[count] = key;
            count++;
        }
        return nearestNeighbors;
    }
    public static float nearestNeighborAlgorithm(int k, DataPoint[] nearestNeighbors){
        return 1F;
    }
}

