package com.github.gissuite.gribinterpolation.core;


import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.DistanceFinder.haverSine;

public class InverseWeighted {
    public static void main(String[] args) {

    }
    public static double inverseWeighted(ArrayList<DataPoint> dataPoints, double latitude, double longitude) {
        double weightedSum = 0;
        double inverseWeightedSum = 0;

        // Iterate through the data points
        for (DataPoint dataPoint : dataPoints) {

            //convert lat+long points into distance
            double distance = haverSine(latitude, longitude, dataPoint.getLatitude(), dataPoint.getLongitude());

            //inverse weight
            double inverseWeight = 1.0 / distance;
            inverseWeightedSum += inverseWeight;

            // add the weighted data to the weighted sum
            weightedSum += dataPoint.getTemperature() * inverseWeight;
        }

        if (weightedSum == 0) {
            return 0; // Handle division by zero
        }

        // Return the inverse weighted average
        return weightedSum / inverseWeightedSum;
    }
}
