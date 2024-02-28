package com.github.gissuite.gribinterpolation.core;
import java.lang.Math;

public class DistanceFinder {
    public static void main(String[] args){

    }
    public static double haverSine(double latitude1, double longitude1, double latitude2, double longitude2) {
        double r = 6371.0; //radians
        // If data is in degrees change all the latitude and longitudes to radians with the following:

         latitude1 = convertToRadians(latitude1);
         longitude1= convertToRadians(longitude1);
         latitude2 = convertToRadians(latitude2);
         longitude2 = convertToRadians(longitude2);

        double aTest  =latitude2-latitude1;
        double aTest2 = longitude2-longitude1;
        double sineSquaredOfLat = Math.pow((Math.sin((latitude2 - latitude1) / 2)), 2);
        double sineSquaredOfLong = Math.pow((Math.sin((longitude2 - longitude1) / 2)), 2);
        //Quadratic Formula

        double A = sineSquaredOfLat + Math.cos((latitude1)) * (Math.cos((latitude2)) * sineSquaredOfLong);
//        double B= (2 *r)* Math.asin(Math.sqrt(sineSquaredOfLat + Math.cos((latitude1)) * (Math.cos((latitude2)) * sineSquaredOfLong)));
        double C = 2 * Math.atan2(Math.sqrt(A), Math.sqrt(1 - A));
        double distance = r * C;
//        double b = r * (A);
        return distance;
    }

    private static double convertToRadians(double degrees) {
        return degrees * Math.PI / 180.0;
    }
}
