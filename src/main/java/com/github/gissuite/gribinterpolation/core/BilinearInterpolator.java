package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

import java.util.ArrayList;

public class BilinearInterpolator {

    //Call this method to interpolate temperature with static Latitudes
    public static ArrayList<DataPoint> interpolateWithStaticLat(float[] targetLonLat, DataPoint upperDepthDataPoint1, DataPoint upperDepthDataPoint2, DataPoint lowerDepthDataPoint1, DataPoint lowerDepthDataPoint2) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        ArrayList<DataPoint> terpedDataPointArrayList = new ArrayList<>();

        //interpolate temperature value from upperDepthDataPoint1 and upperDepthDataPoint2;
        double[] lonOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getLongitude(), upperDepthDataPoint2.getLongitude()};
        double[] tempsOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getTemperatureK(), upperDepthDataPoint2.getTemperatureK()};
        double upperDepthTemp = linearInterpolator.interpolate(lonOfUDDP1AndUDDP2, tempsOfUDDP1AndUDDP2).value(targetLonLat[0]);                //interpolates temperature at the upper depth level at target Lon/Lat

        //interpolate temperature value from lowerDepthDataPoint1 and lowerDepthDataPoint2;
        double[] lonOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getLongitude(), lowerDepthDataPoint2.getLongitude()};
        double[] tempsOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getTemperatureK(), lowerDepthDataPoint2.getTemperatureK()};
        double lowerDepthTemp = linearInterpolator.interpolate(lonOfLDDP1AndLDDP2, tempsOfLDDP1AndLDDP2).value(targetLonLat[0]);                //interpolates temperature at the lower depth level at target Lon/Lat

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
    public static ArrayList<DataPoint> interpolateWithStaticLon(float[] targetLonLat, DataPoint upperDepthDataPoint1, DataPoint upperDepthDataPoint2, DataPoint lowerDepthDataPoint1, DataPoint lowerDepthDataPoint2) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        ArrayList<DataPoint> terpedDataPointArrayList = new ArrayList<>();

        //interpolate temperature value from upperDepthDataPoint1 and upperDepthDataPoint2;
        double[] latOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getLatitude(), upperDepthDataPoint2.getLatitude()};
        double[] tempsOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getTemperatureK(), upperDepthDataPoint2.getTemperatureK()};
        double upperDepthTemp = linearInterpolator.interpolate(latOfUDDP1AndUDDP2, tempsOfUDDP1AndUDDP2).value(targetLonLat[0]);            //interpolates temperature at the upper depth level at target Lon/Lat

        //interpolate temperature value from lowerDepthDataPoint1 and lowerDepthDataPoint2;
        double[] latOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getLatitude(), lowerDepthDataPoint2.getLatitude()};
        double[] tempsOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getTemperatureK(), lowerDepthDataPoint2.getTemperatureK()};
        double lowerDepthTemp = linearInterpolator.interpolate(latOfLDDP1AndLDDP2, tempsOfLDDP1AndLDDP2).value(targetLonLat[0]);            //interpolates temperature at the lower depth level at target Lon/Lat

        //assign interpolated upperDepthTemp to target Lon/Lat upper depth data point and load into array.
        DataPoint targetDataPointFirst = new DataPoint(targetLonLat[0], targetLonLat[1], (float)upperDepthTemp, upperDepthDataPoint1.getDepth());
        terpedDataPointArrayList.add(targetDataPointFirst);
        //interpolate temperature value for target data points in between upper and lower depths
        double[] upperAndLowerDepthOfDP1 = {upperDepthDataPoint1.getDepth(), lowerDepthDataPoint1.getDepth()};
        double[] temperatureOfUpperAndLowerDepth = {upperDepthTemp, lowerDepthTemp};

        for (double depthLevel = upperDepthDataPoint1.getDepth() + 1; depthLevel < lowerDepthDataPoint1.getDepth(); depthLevel++) {
            DataPoint dataPoint = new DataPoint(targetLonLat[0],
                    targetLonLat[1],
                    (float)linearInterpolator.interpolate(upperAndLowerDepthOfDP1, temperatureOfUpperAndLowerDepth).value(depthLevel),         //interpolates temperature at each level between upper and lower depths.
                    (float)depthLevel);
            terpedDataPointArrayList.add(dataPoint);
        }

        //assign interpolated lowerDepthTemp to target Lon/lat
        DataPoint targetDataPointLast = new DataPoint(targetLonLat[0], targetLonLat[1], (float)lowerDepthTemp, lowerDepthDataPoint1.getDepth());
        terpedDataPointArrayList.add(targetDataPointLast);

        return terpedDataPointArrayList;
    }
}
