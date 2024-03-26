package com.github.gissuite.gribinterpolation.core.interpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;

public class LinearInterpolator {
    public static DataPoint interpolate(DataPoint datapoint1, DataPoint datapoint2, float wanted){
        org.apache.commons.math3.analysis.interpolation.LinearInterpolator equation = new org.apache.commons.math3.analysis.interpolation.LinearInterpolator();
        double[] depth = {datapoint1.getDepth(), datapoint2.getDepth()};
        double[] temp = {datapoint1.getTemperatureK(), datapoint2.getTemperatureK()};
        return new DataPoint(datapoint1.getLongitude(), datapoint1.getLatitude(), (float)equation.interpolate(depth, temp).value(wanted), wanted);
    }
}
