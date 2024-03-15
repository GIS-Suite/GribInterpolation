package com.github.gissuite.gribinterpolation.core;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class BilinearInterpolator {
    double[] dataPointMissingTemp;
    double[] dataPoint1;
    double[] dataPoint2;
    double[] dataPoint3;
    double[] dataPoint4;
    double[][] interpolatedDataPointArray;

    public BilinearInterpolator(double[] dpMissingTemp, double[] dp1, double[] dp2, double[] dp3, double[] dp4) {
        this.dataPointMissingTemp = dpMissingTemp;
        this.dataPoint1 = dp1;
        this.dataPoint2 = dp2;
        this.dataPoint3 = dp3;
        this.dataPoint4 = dp4;
    }

    public double[][] Interpolate() {
        //interpolate temperature value from datapoint1 and datapoint2
        double dp1SurfaceDepth = dataPoint1[2];
        double dp1Temperature = dataPoint1[3];
        double dp2SurfaceDepth = dataPoint2[2];
        double dp2Temperature = dataPoint2[3];
        LinearInterpolator linTerp = new LinearInterpolator();

        //interpolate temperature value from datapoint3 and datapoint4

        //interpolate temperature value from dp1/dp2 and dp3/dp4
        return interpolatedDataPointArray;
    }

}
