package com.github.gissuite.gribinterpolation.core;

public class NearestNeighbor {
    public static void main(String[] args) {
        //datapoints[0] = {lat,long,temp,depth}
        double[] point1 = {3,3,37,-4};
        double[] point2 = {3,4, 43, -4};
        double[] point3 = {2,3, 38, -4};
        double[] point4 = {3, 4,44, -3};
        double[] point5 = {2,3, 90, -3};
        double[][] points = {point1,point2,point3,point4,point5};
        double[] pointinterpolating= {2,3,-4}; // only lat, long, depth //want to find temp
        getNearestNeighbor(points, pointinterpolating, 2);
    }
    public static double[][] getNearestNeighbor(double[][] points, double[] Pointinterpolating, int k) {
        double[][] nearestNeighbors = new double[k][4];
        double[] distancesFromPointInterpolating = new double[k];
        double maxValue = Double.MAX_VALUE;

        for (int i = 0; i < points.length; i++) {
            double distance = DistanceFinder.haverSine(points[i][0], points[0][i], Pointinterpolating[0],Pointinterpolating[1]);
            if (distance < maxValue){
                maxValue = distance;
//                nearestNeighbors[i][] = closetPoint; //function to get lat/long/depth/temp and put in array of nearestNeighbors

                distancesFromPointInterpolating[i] = distance;
                if (distance < distancesFromPointInterpolating[i]){
                    distancesFromPointInterpolating[i] = distance;
                            //maybe a sort to sort all the points in acending order based on distances????
                }

            }
            else if (distance > maxValue) {
                //do nothing
            }
            }

        return nearestNeighbors;
    }
}
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
