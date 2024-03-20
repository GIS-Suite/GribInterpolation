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
            //convert lat+long points into distance
            float distance = (float) haverSine(latitude, longitude, dataPoint.getLatitude(), dataPoint.getLongitude());

            //calculate the inverse weighted sum
            float inverseWeight = (float) (1.0 / distance);
            inverseWeightedSum += inverseWeight;
            weightedSum += dataPoint.getTemperatureK() * inverseWeight;
        }
        if (weightedSum == 0) {
            return 0; // Handle division by zero
        }
        // Return the interpolated point temp
        return weightedSum / inverseWeightedSum;
    }

}
