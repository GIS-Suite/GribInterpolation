package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

import java.util.ArrayList;

public class BilinearInterpolator {

    //Call this method to interpolate temperature with static Latitudes
    public static ArrayList<DataPoint> InterpolateWithStaticLat(float[] targetLonLat, DataPoint upperDepthDataPoint1, DataPoint upperDepthDataPoint2, DataPoint lowerDepthDataPoint1, DataPoint lowerDepthDataPoint2) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        double upperDepthTemp;
        double lowerDepthTemp;
        ArrayList<DataPoint> terpedDataPointArrayList = new ArrayList<>();

        //interpolate temperature value from upperDepthDataPoint1 and upperDepthDataPoint2;
        double[] LonOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getLongitude(), upperDepthDataPoint2.getLongitude()};
        double[] tempsOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getTemperatureK(), upperDepthDataPoint2.getTemperatureK()};
        upperDepthTemp = linearInterpolator.interpolate(LonOfUDDP1AndUDDP2, tempsOfUDDP1AndUDDP2).value(targetLonLat[0]);

        //interpolate temperature value from lowerDepthDataPoint1 and lowerDepthDataPoint2;
        double[] LonOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getLongitude(), lowerDepthDataPoint2.getLongitude()};
        double[] tempsOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getTemperatureK(), lowerDepthDataPoint2.getTemperatureK()};
        lowerDepthTemp = linearInterpolator.interpolate(LonOfLDDP1AndLDDP2, tempsOfLDDP1AndLDDP2).value(targetLonLat[0]);

        //assign interpolated upperDepthTemp to target Lon/Lat upper depth data point and load into array.
        DataPoint targetDataPointFirst = new DataPoint(targetLonLat[0], targetLonLat[1], (float)upperDepthTemp, upperDepthDataPoint1.getDepth());
        terpedDataPointArrayList.add(targetDataPointFirst);
        //interpolate temperature value for target data points in between upper and lower depths
        double[] upperAndLowerDepthOfDP1 = {upperDepthDataPoint1.getDepth(), lowerDepthDataPoint1.getDepth()};
        double[] temperatureOfUpperAndLowerDepth = {upperDepthTemp, lowerDepthTemp};

        for (double depthLevel = upperDepthDataPoint1.getDepth() + 1; depthLevel < lowerDepthDataPoint1.getDepth(); depthLevel++) {
            DataPoint dataPoint = new DataPoint(targetLonLat[0],
                    targetLonLat[1],
                    (float)linearInterpolator.interpolate(upperAndLowerDepthOfDP1, temperatureOfUpperAndLowerDepth).value(depthLevel),
                    (float)depthLevel);
            terpedDataPointArrayList.add(dataPoint);

        }

        //assign interpolated lowerDepthTemp to target Lon/lat
        DataPoint targetDataPointLast = new DataPoint(targetLonLat[0], targetLonLat[1], (float)lowerDepthTemp, lowerDepthDataPoint1.getDepth());
        terpedDataPointArrayList.add(targetDataPointLast);

        return terpedDataPointArrayList;
    }

    //Call this method to interpolate temperature with static Longitudes
    public static ArrayList<DataPoint> InterpolateWithStaticLon(float[] targetLonLat, DataPoint upperDepthDataPoint1, DataPoint upperDepthDataPoint2, DataPoint lowerDepthDataPoint1, DataPoint lowerDepthDataPoint2) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        double upperDepthTemp;
        double lowerDepthTemp;
        ArrayList<DataPoint> terpedDataPointArrayList = new ArrayList<>();

        //interpolate temperature value from upperDepthDataPoint1 and upperDepthDataPoint2;
        double[] LatOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getLatitude(), upperDepthDataPoint2.getLatitude()};
        double[] tempsOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getTemperatureK(), upperDepthDataPoint2.getTemperatureK()};
        upperDepthTemp = linearInterpolator.interpolate(LatOfUDDP1AndUDDP2, tempsOfUDDP1AndUDDP2).value(targetLonLat[0]);

        //interpolate temperature value from lowerDepthDataPoint1 and lowerDepthDataPoint2;
        double[] LatOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getLatitude(), lowerDepthDataPoint2.getLatitude()};
        double[] tempsOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getTemperatureK(), lowerDepthDataPoint2.getTemperatureK()};
        lowerDepthTemp = linearInterpolator.interpolate(LatOfLDDP1AndLDDP2, tempsOfLDDP1AndLDDP2).value(targetLonLat[0]);

        //assign interpolated upperDepthTemp to target Lon/Lat upper depth data point and load into array.
        DataPoint targetDataPointFirst = new DataPoint(targetLonLat[0], targetLonLat[1], (float)upperDepthTemp, upperDepthDataPoint1.getDepth());
        terpedDataPointArrayList.add(targetDataPointFirst);
        //interpolate temperature value for target data points in between upper and lower depths
        double[] upperAndLowerDepthOfDP1 = {upperDepthDataPoint1.getDepth(), lowerDepthDataPoint1.getDepth()};
        double[] temperatureOfUpperAndLowerDepth = {upperDepthTemp, lowerDepthTemp};

        for (double depthLevel = upperDepthDataPoint1.getDepth() + 1; depthLevel < lowerDepthDataPoint1.getDepth(); depthLevel++) {
            DataPoint dataPoint = new DataPoint(targetLonLat[0],
                    targetLonLat[1],
                    (float)linearInterpolator.interpolate(upperAndLowerDepthOfDP1, temperatureOfUpperAndLowerDepth).value(depthLevel),
                    (float)depthLevel);
            terpedDataPointArrayList.add(dataPoint);

        }

        //assign interpolated lowerDepthTemp to target Lon/lat
        DataPoint targetDataPointLast = new DataPoint(targetLonLat[0], targetLonLat[1], (float)lowerDepthTemp, lowerDepthDataPoint1.getDepth());
        terpedDataPointArrayList.add(targetDataPointLast);

        return terpedDataPointArrayList;
    }
}
