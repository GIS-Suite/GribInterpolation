package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.Arrays;

import static com.github.gissuite.gribinterpolation.core.NearestNeighbor.getNearestNeighbor;

public class NearestNeighborAlgorithm {
    public static void main (String[] args){
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
    public static float nearestNeighborAlgorithm(int k, DataPoint[] nearestNeighbors){
        return 1F;
    }

}
