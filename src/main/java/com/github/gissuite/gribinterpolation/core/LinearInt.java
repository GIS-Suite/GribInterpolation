package com.github.gissuite.gribinterpolation.core;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class LinearInt {
    public static void Interpolate(double[] datapoint1, double[] datapoint2){
        LinearInterpolator what = new LinearInterpolator();
        double[] depth = {datapoint1[3], datapoint2[3]};
        double[] temp = {datapoint1[2], datapoint2[2]};
        for(double i=depth[0]+1; i<depth[1]; i++){
            System.out.println("Temp at depth "+ i + ": " + what.interpolate(depth, temp).value(i));
        }
    }
}
