package com.github.gissuite.gribinterpolation.core;
import java.lang.Math;

public class DistanceFinder {
    public static void main(String[] args){

    }
    public static double haverSine(double latitude1, double longitude1, double latitude2, double longitude2) {
        double r = 6371.0; //radians
        // If data is in degrees change all the latitude and longitudes to radians with the following:
        /*
         double latitude1InRad = (latitude1*Math.PI)/180;
         double longitude1InRad = (longitude1*Math.PI)/180;
         double latitude2InRad = (latitude2*Math.PI)/180;
         double longitude2InRad = (longitude2*Math.PI)/180;
        */

        double sineSquaredOfLat = Math.pow((Math.sin((latitude2 - latitude1) / 2)), 2);
        double sineSquaredOfLong = Math.pow((Math.sin((longitude2 - longitude1) / 2)), 2);
        //Quadratic Formula
        double distance= (2 * r) * Math.asin(Math.sqrt(sineSquaredOfLat + Math.cos((latitude1)) * (Math.cos((latitude2)) * sineSquaredOfLong)));

        return distance;
    }
}
