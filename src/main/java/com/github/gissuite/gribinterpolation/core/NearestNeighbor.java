package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.logging.Logger;

//gets k nearest neighbors' memory locations stored in a DataPoint array
//amountOfDataPoints is how many points to find the distance for; so should be how many DataPoints in total we have.
//k is how many neighbors to look for

//can get temperature this way:
//ArrayList<DataPoint> nearestNeighbors = (getNearestNeighbor(arrayOfDataPoints, longitudeToInterpolate, latitudeToInterpolate, k, amountOfDataPoints));
//DataPoint a = (DataPoint) nearestNeighbors.get(0);
//System.out.println(a.getTemperatureK());

public class NearestNeighbor {
    static Logger logger = Logger.getLogger(NearestNeighbor.class.getName());
    /**
     * @param dataPoints All the DataPoints in the data set
     * @param lonInterpolate The longitude value of the point needed to be interpolated
     * @param latInterpolate  The latitude value of the point needed to be interpolated
     * @param k How many nearest neighbors to return
     * @param amountOfDataPoints The total about of DataPoints
     * @return ArrayList of nearest neighbors (DataPoints)
     */
        public static ArrayList<DataPoint> getNearestNeighbor(ArrayList<DataPoint> dataPoints, float lonInterpolate, float latInterpolate, int k, int amountOfDataPoints) {
            ArrayList<DataPoint> nearestNeighbors = new ArrayList<>();
            HashMap<DataPoint, Double> hashMap = new HashMap<>();
            for (int i = 0; i < amountOfDataPoints; i++) {
                double distance = DistanceFinder.haverSine(dataPoints.get(i).getLatitude(), dataPoints.get(i).getLongitude(), latInterpolate, lonInterpolate);
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
     * @param toInterpolate point we are interpolating
     */
    public static DataPoint knnInterpolation(ArrayList<DataPoint> neighbors, DataPoint toInterpolate, int k){
        int amount = neighbors.size();
        ArrayList<DataPoint> nearest = getNearestNeighbor(neighbors,toInterpolate.getLongitude(),toInterpolate.getLatitude(),k, amount);
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
