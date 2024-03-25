package com.github.gissuite.gribinterpolation.core;


import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.DistanceFinder.haverSine;

public class InverseWeighted {
    /**
     * @param dataPoints An array of data points with known temperatures used to compute the temperature of the interpolatedPoint
     * @param interpolatedPoint The data point with a missing temperature value to find
     * @return The data point with the interpolated temperature value
     */
    public static DataPoint inverseWeighted(ArrayList<DataPoint> dataPoints, DataPoint interpolatedPoint) {
        float weightedSum = 0;
        float inverseWeightedSum = 0;
        float longitude = interpolatedPoint.getLongitude();
        float latitude = interpolatedPoint.getLatitude();

        // Iterate through the data points
        for (DataPoint dataPoint : dataPoints) {
            //convert lat+long points into distance
            float distance = (float) haverSine(latitude, longitude, dataPoint.getLatitude(), dataPoint.getLongitude());

            //calculate the inverse weighted sum
            float inverseWeight = (float) (1.0 / distance);
            inverseWeightedSum += inverseWeight;
            weightedSum += dataPoint.getTemperatureK() * inverseWeight;
        }
        // Return the interpolated point temp
        float temperatureK = weightedSum / inverseWeightedSum;
        interpolatedPoint.setTemperatureK(temperatureK);
        return interpolatedPoint;
    }

}
