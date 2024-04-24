package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class LinearInt {
    /**
     * @param datapoint1 The data point with the upper depth boundary.
     * @param datapoint2 The data point with the lower depth boundary.
     * @param targetDataPoint The data point with the target depth for temperature interpolation.
     * @return The targetDataPoint with the interpolated temperature attribute.
     */
    public static DataPoint interpolate(DataPoint datapoint1, DataPoint datapoint2, DataPoint targetDataPoint){
        LinearInterpolator equation = new LinearInterpolator();

        double[] depth = {datapoint1.getDepth(), datapoint2.getDepth()};
        double[] temp = {datapoint1.getTemperatureK(), datapoint2.getTemperatureK()};

        float tempForTargetDataPoint = (float)equation.interpolate(depth, temp).value(targetDataPoint.getDepth());
        targetDataPoint.setTemperatureK(tempForTargetDataPoint);

        return targetDataPoint;
    }
}
