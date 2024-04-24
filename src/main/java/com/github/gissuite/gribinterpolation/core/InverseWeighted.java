package com.github.gissuite.gribinterpolation.core;


import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.DistanceFinder.haverSine;
import static java.lang.Math.abs;

public class InverseWeighted {
    /**
     * @param dataPoints An array list of data points with known temperatures used to compute the temperature of the interpolatedPoint
     * @param interpolatedPoint The data point with a missing temperature value to find
     * @return The data point with the interpolated temperature value
     */
    public static DataPoint inverseWeighted(ArrayList<DataPoint> dataPoints, DataPoint interpolatedPoint) {
        float weightedSum = 0;
        float inverseWeightedSum = 0;
        float longitude = interpolatedPoint.getLongitude();
        float latitude = interpolatedPoint.getLatitude();
        float depth = interpolatedPoint.getDepth();

        // Iterate through the data points
        for (DataPoint dataPoint : dataPoints) {
            //convert lat+long points into distance
            float hdistance = (float) haverSine(latitude, longitude, dataPoint.getLatitude(), dataPoint.getLongitude());

            float vdistance = Math.abs((float)dataPoint.getDepth()-interpolatedPoint.getDepth());

            float totalDistance = (float)Math.sqrt(Math.pow(hdistance, 2) + Math.pow(vdistance, 2) );

            //calculate the inverse weighted sum
            float inverseWeight = (float) (1.0 / totalDistance);
            inverseWeightedSum += inverseWeight;
            weightedSum += dataPoint.getTemperatureK() * inverseWeight;
        }
        // Return the interpolated point temp
        float temperatureK = weightedSum / inverseWeightedSum;
        interpolatedPoint.setTemperatureK(temperatureK);
        return interpolatedPoint;
    }

}
