package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.*;


public class NearestNeighbor {
    public static HashMap<DataPoint,Double>sortByValue(HashMap<DataPoint,Double>map) {
        List<Map.Entry<DataPoint, Double>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<DataPoint, Double>>() {
            @Override
            public int compare(Map.Entry<DataPoint, Double> o1, Map.Entry<DataPoint, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        HashMap<DataPoint, Double> sortedMap = new LinkedHashMap<>();
        for(Map.Entry<DataPoint,Double> entry : list){
            sortedMap.put(entry.getKey(), entry.getValue());

        }
        return sortedMap;
    }
    public static void main(String[] args) {

//        datapoints[0] = {lat,long,temp,depth}
//        DataPoint a = new DataPoint(1,1,5,1);
        DataPoint first = new DataPoint(-66, 38.8f, 271.4f, 300);
        DataPoint second = new DataPoint(-62, 38.8f, 273.3f, 400);
        DataPoint expectedResult = new DataPoint(-62, 38.8f, 272.44498f, 355);
        DataPoint[] arrayOfDataPoints = {first,second};
//        DataPoint dataPointToInterpolate = {-62, 38.8f, 355 };
        getNearestNeighbor(arrayOfDataPoints, -62,38.8f, 2,2);
    }//end main
    public static void getNearestNeighbor(DataPoint[] dataPoint, float longitudeToInterpolate, float latitudeToInterpolate, int k, int amountOfDataPoints) {
        DataPoint[] nearestNeighbors = new DataPoint[k];
        double[] distancesFromPointInterpolating = new double[k];
        double maxValue = Double.MAX_VALUE;
        HashMap<DataPoint, Double> hashMap = new HashMap<>();

        for (int i = 0; i < amountOfDataPoints; i++) {
            double distance = DistanceFinder.haverSine(dataPoint[i].getLatitude(), dataPoint[i].getLongitude(), latitudeToInterpolate,longitudeToInterpolate);
            hashMap.put(dataPoint[i], distance);
//            System.out.println(hashMap.get(dataPoint[i]));
            HashMap sortedHashMap = sortByValue(hashMap);
            System.out.println(sortedHashMap.values());

            //just need to find a way to order hash map by distance.
//            if (distance < maxValue){
//                maxValue = distance;


                //create a map from datapoint to double(of distances), sort by map[pointc] - map[pointb].. then take first k elements
//
//
//                nearestNeighbors[i][] = closetPoint; //function to get lat/long/depth/temp and put in array of nearestNeighbors

//                distancesFromPointInterpolating[i] = distance;
//                if (distance < distancesFromPointInterpolating[i]){
//                    distancesFromPointInterpolating[i] = distance;
//                            //maybe a sort to sort all the points in acending order based on distances????
                }

//            }
//            else if (distance > maxValue) {
//                //do nothing
//            }
            }//end nearest neighbor

//        return nearestNeighbors;
    }//end class


//       for (int i = 0; i < points.length; i++) {
//double distance = DistanceFinder.haverSine(points[i][0], points[0][i], Pointinterpolating[0],Pointinterpolating[1]);
////            dist1 = getDistanceXYZ(points[i], mypoint);
//            if (dist1 == dist2) {
//closestPoints[i] = dist1;
//closestPoints[i+1] = dist2;
//counter++; //need to find a way to tell if a point in array is null so that closestPoints[i]
//        //doesn't get overwritten
//        } else if (dist1 < dist2) {
//dist1 = dist1;
//closestPoints[i] = dist1;
//
//            } else if (dist1 > dist2) {
//
//closestPoints[i] = dist1;
//            }
//                    }
//                    return closestPoints;
//    }
//            }
