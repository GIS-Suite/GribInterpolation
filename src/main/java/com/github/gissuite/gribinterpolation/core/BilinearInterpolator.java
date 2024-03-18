package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class BilinearInterpolator {
    LinearInterpolator linearInterpolator = new LinearInterpolator();
    DataPoint targetDataPoint;
    DataPoint upperDepthDataPoint1;
    DataPoint upperDepthDataPoint2;
    DataPoint lowerDepthDataPoint1;
    DataPoint lowerDepthDataPoint2;
    DataPoint[] terpedDataPointArray;
    int numberOfDepthsMissingTemp;
    double lonUpperDepthTemp;
    double lonLowerDepthTemp;
    double latUpperDepthTemp;
    double latLowerDepthTemp;

    public BilinearInterpolator(DataPoint targetDataPoint, DataPoint upperDpthDP1, DataPoint upperDpthDP2, DataPoint lowDpthDP1, DataPoint lowDpthDP2) {
        this.targetDataPoint = targetDataPoint;
        this.upperDepthDataPoint1 = upperDpthDP1;
        this.upperDepthDataPoint2 = upperDpthDP2;
        this.lowerDepthDataPoint1 = lowDpthDP1;
        this.lowerDepthDataPoint2 = lowDpthDP2;
        this.numberOfDepthsMissingTemp = (int)(lowerDepthDataPoint1.getDepth() - upperDepthDataPoint1.getDepth() - 1);
        this.terpedDataPointArray = new DataPoint[numberOfDepthsMissingTemp];
    }

    //Call this method to interpolate temperature with dynamic longitude;
    public double[][] InterpolateWithDynamicLon() {
        //interpolate temperature value from upperDepthDataPoint1 and upperDepthDataPoint2;
        double[] depthsOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getDepth(), upperDepthDataPoint2.getDepth()};
        double[] tempsOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getTemperatureK(), upperDepthDataPoint2.getTemperatureK()};
        double upperDepthTemp = linearInterpolator.interpolate(depthsOfUDDP1AndUDDP2, tempsOfUDDP1AndUDDP2).value(targetDataPoint.getDepth());
//        double[] longitudeOfDP1AndDP2 = {upperDepthDataPoint1.getLongitude(), upperDepthDataPoint2.getLongitude()};
//        double[] temperatureOfDP1AndDp2 = {upperDepthDataPoint1.getTemperatureK(), upperDepthDataPoint2.getTemperatureK()};
//        double lonOfDataPointMT = dataPointMT[1];
//        lonUpperDepthTemp = linearInterpolator.interpolate(longitudeOfDP1AndDP2, temperatureOfDP1AndDp2).value(lonOfDataPointMT);

        //interpolate temperature value from lowerDepthDataPoint1 and lowerDepthDataPoint2;
        double[] depthsOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getDepth(), lowerDepthDataPoint2.getDepth()};
        double[] tempsOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getTemperatureK(), lowerDepthDataPoint2.getTemperatureK()};
        double lowerDepthTemp = linearInterpolator.interpolate(depthsOfLDDP1AndLDDP2, tempsOfLDDP1AndLDDP2).value(targetDataPoint.getDepth());
//        double[] longitudeOfDP3AndDP4 = {lowerDepthDataPoint1[1], lowerDepthDataPoint2[1]};
//        double[] temperatureOfDP3AndDP4 = {lowerDepthDataPoint1[3], lowerDepthDataPoint2[3]};
//        lonLowerDepthTemp = linearInterpolator.interpolate(longitudeOfDP3AndDP4, temperatureOfDP3AndDP4).value(lonOfDataPointMT);

        //interpolate temperature value for each missing depth at the coordinate of DataPointMT; fill terpedDataPointArray
        double[] upperAndLowerDepthOfDP1 = {upperDepthDataPoint1.getDepth(), lowerDepthDataPoint1.getDepth()};
        double[] temperatureOfUpperAndLowerDepth = {lonUpperDepthTemp, lonLowerDepthTemp};
        int depthIndex = 0;
        for (double depthLevel = upperDepthDataPoint1.getDepth() + 1; depthLevel < lowerDepthDataPoint1.getDepth(); depthLevel++) {
            terpedDataPointArray[depthIndex] = DataPoint;
            terpedDataPointArray[depthIndex] = dataPointMT[1];
            terpedDataPointArray[depthIndex] = depthLevel;
            terpedDataPointArray[depthIndex] = linearInterpolator.interpolate(depthOfDP1AndDP3, temperatureOfUpperAndLowerDepth).value(depthLevel);
            depthIndex++;
        }
        return terpedDataPointArray;
    }

    //call this method to interpolate temperature with dynamic latitude
//    public double[][] InterpolateWithDynamicLat() {
//        //interpolate temperature value from datapoint1 and datapoint2; South/North Upper surface depth
//        double[] latitudeOfDP1AndDP2 = {upperDepthDataPoint1[0], upperDepthDataPoint2[0]};
//        double[] temperatureOfDP1AndDp2 = {upperDepthDataPoint1[3], upperDepthDataPoint2[3]};
//        double latOfDataPointMT = dataPointMT[0];
//        latUpperDepthTemp = linearInterpolator.interpolate(latitudeOfDP1AndDP2, temperatureOfDP1AndDp2).value(latOfDataPointMT);
//
//        //interpolate temperature value from datapoint3 and datapoint4; South/North Lower surface depth
//        double[] latitudeOfDP3AndDP4 = {lowerDepthDataPoint1[0], lowerDepthDataPoint2[0]};
//        double[] temperatureOfDP3AndDP4 = {lowerDepthDataPoint1[3], lowerDepthDataPoint2[3]};
//        latLowerDepthTemp = linearInterpolator.interpolate(latitudeOfDP3AndDP4, temperatureOfDP3AndDP4).value(latOfDataPointMT);
//
//        //interpolate temperature value from upperDepth and lowerDepth;
//        double[] depthOfDP1AndDP3 = {upperDepthDataPoint1[2], lowerDepthDataPoint1[2]};
//        double[] temperatureOfUpperAndLowerDepth = {latUpperDepthTemp, latLowerDepthTemp};
//
//        //interpolate temperature value for each missing depth at the coordinate of DataPointMT; fill terpedDataPointArray
//        int depthIndex = 0;
//        for (double depthLevel = depthOfDP1AndDP3[0]+1; depthLevel < depthOfDP1AndDP3[1]; depthLevel++) {
//            terpedDataPointArray[depthIndex][0] = dataPointMT[0];
//            terpedDataPointArray[depthIndex][1] = dataPointMT[1];
//            terpedDataPointArray[depthIndex][2] = depthLevel;
//            terpedDataPointArray[depthIndex][3] = linearInterpolator.interpolate(depthOfDP1AndDP3, temperatureOfUpperAndLowerDepth).value(depthLevel);
//            depthIndex++;
//        }
//        return terpedDataPointArray;
//    }
}
