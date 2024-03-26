package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;

public class TrilinearInterpolator {
    /**
     * @param interpolationDataPoint The target data point with the missing temperature value
     * @param dataPoints The 8 adjacent pre-defined data points surrounding the interpolation data point
     * @return the target data point with the interpolated temperature value
     */
    public static DataPoint interpolateWithLonLat(DataPoint interpolationDataPoint, ArrayList<DataPoint> dataPoints) {
        //Interpolate temperature at the interpolationDataPoint longitude in the Longitude direction for the four lower latitude data points

        //Interpolate temperature at the interpolationDataPoint longitude in the longitude direction for the four higher latitude data points

        return interpolationDataPoint;
    }

    public static DataPoint interpolateWithLatLon(DataPoint interpolationDataPoint, ArrayList<DataPoint> dataPoints) {
        //Interpolate temperature at the interpolationDataPoint latitude in the Latitude direction for the four lower longitude data points

        //Interpolate temperature at the interpolationDataPoint latitude in the latitude direction for the four higher longitude data points

        return interpolationDataPoint;
    }
}