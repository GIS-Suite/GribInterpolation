package com.github.gissuite.gribinterpolation.core.interpolation;


import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.DistanceFinder.haverSine;

public class InverseWeightedIntepolator {
    /**
     * @param dataPoints An array list of data points with known temperatures used to compute the temperature of the interpolatedPoint
     * @param interpolatedPoint The data point with a missing temperature value to find
     * @return The data point with the interpolated temperature value
     */
    public static DataPoint interpolate(ArrayList<DataPoint> dataPoints, DataPoint interpolatedPoint) {
        float weightedSum = 0;
        float inverseWeightedSum = 0;
        float longitude = interpolatedPoint.getLongitude();
        float latitude = interpolatedPoint.getLatitude();

        for (DataPoint dataPoint : dataPoints) {
            float distance = (float) haverSine(latitude, longitude, dataPoint.getLatitude(), dataPoint.getLongitude());

            float inverseWeight = (float) (1.0 / distance);
            inverseWeightedSum += inverseWeight;
            weightedSum += dataPoint.getTemperatureK() * inverseWeight;
        }
        float temperatureK = weightedSum / inverseWeightedSum;
        interpolatedPoint.setTemperatureK(temperatureK);
        return interpolatedPoint;
    }


}
