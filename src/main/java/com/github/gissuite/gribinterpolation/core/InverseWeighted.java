package com.github.gissuite.gribinterpolation.core;


import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.DistanceFinder.haverSine;

public class InverseWeighted {
    public static float inverseWeighted(ArrayList<DataPoint> dataPoints, DataPoint interpolatedPoint) {
        float weightedSum = 0;
        float inverseWeightedSum = 0;
        float longitude = interpolatedPoint.getLongitude();
        float latitude = interpolatedPoint.getLatitude();
        float temp = interpolatedPoint.getTemperatureK();


        // Iterate through the data points
        for (DataPoint dataPoint : dataPoints) {
            System.out.print("latitude is " + dataPoint.getLatitude());
            System.out.println();
            System.out.print("longitude is " + dataPoint.getLongitude());
            System.out.println();



            //convert lat+long points into distance
            double distance = haverSine(latitude, longitude, dataPoint.getLatitude(), dataPoint.getLongitude());
            System.out.print("distance is " + distance);
            System.out.println();

            //inverse weight
            double inverseWeight = 1.0 / distance;
            inverseWeightedSum += inverseWeight;

            // add the weighted data to the weighted sum
            weightedSum += dataPoint.getTemperatureK() * inverseWeight;

            System.out.print(weightedSum + " / " + inverseWeightedSum + " = " + weightedSum / inverseWeightedSum);
            System.out.println();
        }

        if (weightedSum == 0) {
            return 0; // Handle division by zero
        }
        // Return the interpolated point temp
        return weightedSum / inverseWeightedSum;

    }

}
