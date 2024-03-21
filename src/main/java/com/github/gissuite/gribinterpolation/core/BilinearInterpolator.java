package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class BilinearInterpolator {

    //Call this method to interpolate temperature with dynamic Longitude
    public static DataPoint interpolateWithDynamicLon(float[] targetLonLat, float targetDepth,DataPoint upperDepthDataPoint1, DataPoint upperDepthDataPoint2, DataPoint lowerDepthDataPoint1, DataPoint lowerDepthDataPoint2) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();

        //interpolate temperature value from upperDepthDataPoint1 and upperDepthDataPoint2; (this is linear interpolation in the x-direction)
        double[] lonOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getLongitude(), upperDepthDataPoint2.getLongitude()};
        double[] tempsOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getTemperatureK(), upperDepthDataPoint2.getTemperatureK()};
        double targetLonUpperDepthTemp = linearInterpolator.interpolate(lonOfUDDP1AndUDDP2, tempsOfUDDP1AndUDDP2).value(targetLonLat[0]);

        //interpolate temperature value from lowerDepthDataPoint1 and lowerDepthDataPoint2; (this is linear interpolation in the x-direction)
        double[] lonOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getLongitude(), lowerDepthDataPoint2.getLongitude()};
        double[] tempsOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getTemperatureK(), lowerDepthDataPoint2.getTemperatureK()};
        double targetLonLowerDepthTemp = linearInterpolator.interpolate(lonOfLDDP1AndLDDP2, tempsOfLDDP1AndLDDP2).value(targetLonLat[0]);

        //interpolate temperature value from upperDepthTemp and lowerDepthTemp at targetLonLat (this is linear interpolation in the y-direction)
        double[] upperAndLowerDepthOfDP1 = {upperDepthDataPoint1.getDepth(), lowerDepthDataPoint1.getDepth()};
        double[] temperatureOfUpperAndLowerDepth = {targetLonUpperDepthTemp, targetLonLowerDepthTemp};
        float targetLonLatDepthTemperature = (float)linearInterpolator.interpolate(upperAndLowerDepthOfDP1, temperatureOfUpperAndLowerDepth).value(targetDepth);

        return new DataPoint(targetLonLat[0], targetLonLat[1], targetLonLatDepthTemperature, targetDepth);
    }

    //Call this method to interpolate temperature with dynamic latitude
    public static DataPoint interpolateWithDynamicLat(float[] targetLonLat, float targetDepth, DataPoint upperDepthDataPoint1, DataPoint upperDepthDataPoint2, DataPoint lowerDepthDataPoint1, DataPoint lowerDepthDataPoint2) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();

        //interpolate temperature value from upperDepthDataPoint1 and upperDepthDataPoint2; (this is linear interpolation in the x-direction)
        double[] latOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getLatitude(), upperDepthDataPoint2.getLatitude()};
        double[] tempsOfUDDP1AndUDDP2 = {upperDepthDataPoint1.getTemperatureK(), upperDepthDataPoint2.getTemperatureK()};
        double upperDepthTemp = linearInterpolator.interpolate(latOfUDDP1AndUDDP2, tempsOfUDDP1AndUDDP2).value(targetLonLat[1]);

        //interpolate temperature value from lowerDepthDataPoint1 and lowerDepthDataPoint2; (this is linear interpolation in the x-direction)
        double[] latOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getLatitude(), lowerDepthDataPoint2.getLatitude()};
        double[] tempsOfLDDP1AndLDDP2 = {lowerDepthDataPoint1.getTemperatureK(), lowerDepthDataPoint2.getTemperatureK()};
        double lowerDepthTemp = linearInterpolator.interpolate(latOfLDDP1AndLDDP2, tempsOfLDDP1AndLDDP2).value(targetLonLat[1]);

        //interpolate temperature value from upperDepthTemp and lowerDepthTemp at targetLonLat (this is linear interpolation in the y-direction)
        double[] upperAndLowerDepthOfDP1 = {upperDepthDataPoint1.getDepth(), lowerDepthDataPoint1.getDepth()};
        double[] temperatureOfUpperAndLowerDepth = {upperDepthTemp, lowerDepthTemp};
        float targetLonLatDepthTemperature = (float)linearInterpolator.interpolate(upperAndLowerDepthOfDP1, temperatureOfUpperAndLowerDepth).value(targetDepth);

        return new DataPoint(targetLonLat[0], targetLonLat[1], targetLonLatDepthTemperature, targetDepth);
    }
}
