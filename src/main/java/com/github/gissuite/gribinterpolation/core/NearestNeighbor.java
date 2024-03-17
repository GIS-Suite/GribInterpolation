package com.github.gissuite.gribinterpolation.core;

public class NearestNeighbor {
    public static void main(String[] args) {
        //datapoints[0] = {lat,long,temp,depth}
        double[] point1 = {3,3,37,-4};
        double[] point2 = {3,4, 43, -4};
        double[] point3 = {6,8, 54, -3};
        double[] point4 = {10, 9, 73, 5};
        double[] point5 = {2,3, 90, 3};
        double[][] points = {point1,point2,point3,point4,point5};
        double[] pointinterpolating= {2,3,-4}; // only lat, long, depth //want to find temp
        getNearestNeighbor(points, pointinterpolating);
    }
    public static double[][] getNearestNeighbor(double[][] points, double[] Pointinterpolating, int k) {
        double[][] nearestNeighbors = new double[k][4];
        double[] distancesFromPointInterpolating = new double[k];
        int counter = 0;
        double maxValue = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            double closetPoint = DistanceFinder.haverSine(points[i][0], points[0][i], Pointinterpolating[0],Pointinterpolating[1]);
            if (closetPoint < maxValue){
                maxValue = closetPoint;
                nearestNeighbors[i][] = closetPoint;
            }
            else if (dist1 > dist2) {

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
