package com.github.gissuite.gribinterpolation.core;
import java.lang.Math;

public class DistanceFinder {
    public static double haverSine(double latitude1, double longitude1, double latitude2, double longitude2) {
        double r = 6371.0;
         latitude1 = convertToRadians(latitude1);
         longitude1= convertToRadians(longitude1);
         latitude2 = convertToRadians(latitude2);
         longitude2 = convertToRadians(longitude2);

        double sineSquaredOfLat = Math.pow((Math.sin((latitude2 - latitude1) / 2)), 2);
        double sineSquaredOfLong = Math.pow((Math.sin((longitude2 - longitude1) / 2)), 2);

        double A = sineSquaredOfLat + Math.cos(latitude1) * (Math.cos(latitude2) * sineSquaredOfLong);

        double distance =(2 * r) * Math.asin(Math.sqrt(A));

        return distance;
    }

    private static double convertToRadians(double degrees) {
        return degrees * Math.PI / 180.0;
    }
}
