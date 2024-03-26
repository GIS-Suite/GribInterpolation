package com.github.gissuite.gribinterpolation.core.interpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;

public class LinearInterpolator {

    /**
     * Returns a new {@link DataPoint} that is the result of linearly interpolating between the two given data points.
     *
     * @param datapoint1 the first data point
     * @param datapoint2 the second data point
     * @param wanted the depth value at which to interpolate
     * @return a new data point that is the result of linearly interpolating between the two given data points
     */
    public static DataPoint interpolate(DataPoint datapoint1, DataPoint datapoint2, float wanted){
        org.apache.commons.math3.analysis.interpolation.LinearInterpolator interpolator = new org.apache.commons.math3.analysis.interpolation.LinearInterpolator();
        double[] depths = {datapoint1.getDepth(), datapoint2.getDepth()};
        double[] temps = {datapoint1.getTemperatureK(), datapoint2.getTemperatureK()};
        return new DataPoint(datapoint1.getLongitude(), datapoint1.getLatitude(), (float)interpolator.interpolate(depths, temps).value(wanted), wanted);
    }
}
