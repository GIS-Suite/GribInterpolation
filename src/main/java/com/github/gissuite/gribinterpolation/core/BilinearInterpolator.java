package com.github.gissuite.gribinterpolation.core;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class BilinearInterpolator {
    LinearInterpolator linearTerp = new LinearInterpolator();
    double[] dataPointMT;
    double[] dataPoint1;
    double[] dataPoint2;
    double[] dataPoint3;
    double[] dataPoint4;
    double[][] terpedDataPointArray;
    int numberOfDepthsMT;

    public BilinearInterpolator(double[] dPMissingTemp, double[] dP1, double[] dP2, double[] dP3, double[] dP4) {
        this.dataPointMT = dPMissingTemp;
        this.dataPoint1 = dP1;
        this.dataPoint2 = dP2;
        this.dataPoint3 = dP3;
        this.dataPoint4 = dP4;
        this.numberOfDepthsMT = (int)(dP3[2] - dP1[2] - 1);
        this.terpedDataPointArray = new double[numberOfDepthsMT][dP1.length];
    }

    //Call this method to interpolate temperature with dynamic longitude;
    public double[][] InterpolateWithDynamicLon() {
        //interpolate temperature value from datapoint1 and datapoint2; East/West Upper surface depth
        double[] longitudeOfDP1AndDP2 = {dataPoint1[1], dataPoint2[1]};
        double[] temperatureOfDP1AndDp2 = {dataPoint1[3], dataPoint2[3]};
        double lonOfDataPointMT = dataPointMT[1];
        double upperDepthTemp = linearTerp.interpolate(longitudeOfDP1AndDP2, temperatureOfDP1AndDp2).value(lonOfDataPointMT);

        //interpolate temperature value from datapoint3 and datapoint4; East/West Lower surface depth
        double[] longitudeOfDP3AndDP4 = {dataPoint3[1], dataPoint4[1]};
        double[] temperatureOfDP3AndDP4 = {dataPoint3[3], dataPoint4[3]};
        double lowerDepthTemp = linearTerp.interpolate(longitudeOfDP3AndDP4, temperatureOfDP3AndDP4).value(lonOfDataPointMT);

        //interpolate temperature value from upperDepth and lowerDepth;
        double[] depthOfDP1AndDP3 = {dataPoint1[2], dataPoint3[2]};
        double[] temperatureOfUpperAndLowerDepth = {upperDepthTemp, lowerDepthTemp};

        //interpolate temperature value for each missing depth at the coordinate of DataPointMT; fill terpedDataPointArray
        int depthIndex = 0;
        for (double depthLevel = depthOfDP1AndDP3[0]+1; depthLevel < depthOfDP1AndDP3[1]; depthLevel++) {
            terpedDataPointArray[depthIndex][0] = dataPointMT[0];
            terpedDataPointArray[depthIndex][1] = dataPointMT[1];
            terpedDataPointArray[depthIndex][2] = depthLevel;
            terpedDataPointArray[depthIndex][3] = linearTerp.interpolate(depthOfDP1AndDP3, temperatureOfUpperAndLowerDepth).value(depthLevel);
            depthIndex++;
        }
        return terpedDataPointArray;
    }

    //call this method to interpolate temperature with dynamic latitude
    public double[][] InterpolateWithDynamicLat() {
        //interpolate temperature value from datapoint1 and datapoint2; South/North Upper surface depth
        double[] latitudeOfDP1AndDP2 = {dataPoint1[0], dataPoint2[0]};
        double[] temperatureOfDP1AndDp2 = {dataPoint1[3], dataPoint2[3]};
        double latOfDataPointMT = dataPointMT[1];
        double upperDepthTemp = linearTerp.interpolate(latitudeOfDP1AndDP2, temperatureOfDP1AndDp2).value(latOfDataPointMT);

        //interpolate temperature value from datapoint3 and datapoint4; South/North Lower surface depth
        double[] latitudeOfDP3AndDP4 = {dataPoint3[0], dataPoint4[0]};
        double[] temperatureOfDP3AndDP4 = {dataPoint3[3], dataPoint4[3]};
        double lowerDepthTemp = linearTerp.interpolate(latitudeOfDP3AndDP4, temperatureOfDP3AndDP4).value(latOfDataPointMT);

        //interpolate temperature value from upperDepth and lowerDepth;
        double[] depthOfDP1AndDP3 = {dataPoint1[2], dataPoint3[2]};
        double[] temperatureOfUpperAndLowerDepth = {upperDepthTemp, lowerDepthTemp};

        //interpolate temperature value for each missing depth at the coordinate of DataPointMT; fill terpedDataPointArray
        int depthIndex = 0;
        for (double depthLevel = depthOfDP1AndDP3[0]+1; depthLevel < depthOfDP1AndDP3[1]; depthLevel++) {
            terpedDataPointArray[depthIndex][0] = dataPointMT[0];
            terpedDataPointArray[depthIndex][1] = dataPointMT[1];
            terpedDataPointArray[depthIndex][2] = depthLevel;
            terpedDataPointArray[depthIndex][3] = linearTerp.interpolate(depthOfDP1AndDP3, temperatureOfUpperAndLowerDepth).value(depthLevel);
            depthIndex++;
        }
        return terpedDataPointArray;
    }
}
