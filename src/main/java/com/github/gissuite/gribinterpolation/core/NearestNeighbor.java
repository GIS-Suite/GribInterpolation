package com.github.gissuite.gribinterpolation.core;

import java.lang.Math;
import java.util.Arrays;
public class NearestNeighbor {

        public static void main(String[] args) {
            System.out.println("Hello world!");
            // need to get nearest neighbor in general.
//        System.out.println(getDistanceXYZ(2, 2, 2, 2, 2, 2));
            float point1x = 1F;
            float point1y = 1F;
            float point1z = 1F;
            float[] point1 = new float[3];
            point1[0] = 1F;
            point1[1] = 1F;
            point1[2] = 1F;
            float[] myPoint = new float[3];
            myPoint[0] = 2F;
            myPoint[1] = 2F;
            myPoint[2] = 2F;
            float[] point3 = new float[3];
            point3[0] = 5F;
            point3[1] = 5F;
            point3[2] = 5F;

            float[][] points = {point1, point3};

            System.out.println(Arrays.toString( getNearestNeighbor(points, myPoint)));


        }


        public static double haverSine (double latitude1, double longitude1, double latitude2, double longitude2){
            double rad = 6378.137; //km
            double distanceBetweenLatitudes = latitude2-latitude1;
            double distanceBetweenLongitudes = longitude2-longitude1;
            double DistancelatInRAD = distanceBetweenLongitudes * Math.PI/180;
            double DistancelongInRAD = distanceBetweenLongitudes * Math.PI/180;
            return 10.7;
        }


        public static float getDistanceXYZ(float[] points, float[] mypoint) {
            float[] point1;
            float[] point2;
            return (float) Math.sqrt(Math.pow(points[0] - mypoint[0], 2) + Math.pow(points[1] - mypoint[1], 2) + Math.pow(points[2] - mypoint[2], 2));
        }

        //float
        // K nearest neighbor we need to determine how many neighbors we want to look at
        // above depends on how many points we find in grib files/netCDF
        //need to find out what to return for getNearestNeighbor function
        public static float[] getNearestNeighbor(float[][] points, float[] mypoint) {

            int amountOfPointsToLookAt = 3;
//      float[][] points = {point1, point2, point3};
            float dist1;
            float dist2 = 0;
            float[] closestPoints = new float[3];

            int counter =0;
            for (int i = 0; i < points.length; i++) {
//            float distanceBetweenPoints = getDistanceXYZ(points);
                dist1 = getDistanceXYZ(points[i], mypoint);
                if (dist1 == dist2) {
                    closestPoints[i] = dist1;
                    closestPoints[i+1] = dist2;
                    counter++; //need to find a way to tell if a point in array is null so that closestPoints[i]
                    //doesn't get overwritten
                } else if (dist1 < dist2) {
                    dist1 = dist1;
                    closestPoints[i] = dist1;

                } else if (dist1 > dist2) {
                    dist1 = dist2;
                    closestPoints[i] = dist1;
                }
            }
            return closestPoints;
        }
    }

