package com.github.gissuite.gribinterpolation.core;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class LinearInt {
    public static double[] interpolate(double[] datapoint1, double[] datapoint2, double wanted){
        LinearInterpolator equation = new LinearInterpolator();
        double[] depth = {datapoint1[3], datapoint2[3]};
        double[] temp = {datapoint1[2], datapoint2[2]};
        double[] newDataPoint = new double[4];

        newDataPoint[0] = datapoint1[0]; //latitude
        newDataPoint[1] = datapoint1[1]; //longitude
        newDataPoint[2] = equation.interpolate(depth, temp).value(wanted);
        newDataPoint[3] = wanted;

        return newDataPoint;
    }
}
