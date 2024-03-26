package com.github.gissuite.gribinterpolation.core.interpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class BilinearInterpolator {

    /**
     * @param interpolationPoint The data point for the bilinear interpolated temperature
     * @param upperDepthDataPoint1 The data point closest to the interpolation data point at the upper depth with the lower longitude
     * @param upperDepthDataPoint2 The data point closest to the interpolation data point at the upper depth with the higher longitude
     * @param lowerDepthDataPoint1 The data point closest to the interpolation data point at the lower depth with the lower longitude
     * @param lowerDepthDataPoint2 The data point closest to the interpolation data point at the lower depth with the higher longitude
     * @return The data point with the interpolated temperature value
     */
    public static DataPoint interpolateWithDynamicLon(DataPoint interpolationPoint,DataPoint upperDepthDataPoint1, DataPoint upperDepthDataPoint2, DataPoint lowerDepthDataPoint1, DataPoint lowerDepthDataPoint2) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();

        //interpolate temperature value from upperDepthDataPoint1 and upperDepthDataPoint2; (this is linear interpolation in the x-direction)
        double[] upperDepthDataPointLongitudes = {upperDepthDataPoint1.getLongitude(), upperDepthDataPoint2.getLongitude()};
        double[] upperDepthDataPointTemperatures = {upperDepthDataPoint1.getTemperatureK(), upperDepthDataPoint2.getTemperatureK()};
        double upperDepthInterpolationPointTemperature = linearInterpolator.interpolate(upperDepthDataPointLongitudes, upperDepthDataPointTemperatures).value(interpolationPoint.getLongitude());

        //interpolate temperature value from lowerDepthDataPoint1 and lowerDepthDataPoint2; (this is linear interpolation in the x-direction)
        double[] lowerDepthDataPointLongitudes = {lowerDepthDataPoint1.getLongitude(), lowerDepthDataPoint2.getLongitude()};
        double[] lowerDepthDataPointTemperatures = {lowerDepthDataPoint1.getTemperatureK(), lowerDepthDataPoint2.getTemperatureK()};
        double lowerDepthInterpolationPointTemperature = linearInterpolator.interpolate(lowerDepthDataPointLongitudes, lowerDepthDataPointTemperatures).value(interpolationPoint.getLongitude());

        //interpolate temperature value from upperDepthTemp and lowerDepthTemp at interpolation point depth (this is linear interpolation in the y-direction)
        double[] upperAndLowerDepths = {upperDepthDataPoint1.getDepth(), lowerDepthDataPoint1.getDepth()};
        double[] upperAndLowerDepthTemperatures = {upperDepthInterpolationPointTemperature, lowerDepthInterpolationPointTemperature};
        float interpolationPointTemperature = (float)linearInterpolator.interpolate(upperAndLowerDepths, upperAndLowerDepthTemperatures).value(interpolationPoint.getDepth());

        //set interpolated temperature to interpolation point and return
        interpolationPoint.setTemperatureK(interpolationPointTemperature);
        return interpolationPoint;
    }

    /**
     * @param interpolationPoint The data point for the bilinear interpolated temperature
     * @param upperDepthDataPoint1 The data point closest to the interpolation data point at the upper depth with the lower latitude
     * @param upperDepthDataPoint2 The data point closest to the interpolation data point at the upper depth with the higher latitude
     * @param lowerDepthDataPoint1 The data point closest to the interpolation data point at the lower depth with the lower latitude
     * @param lowerDepthDataPoint2 The data point closest to the interpolation data point at the lower depth with the higher latitude
     * @return The data point with the interpolated temperature value
     */
    public static DataPoint interpolateWithDynamicLat(DataPoint interpolationPoint, DataPoint upperDepthDataPoint1, DataPoint upperDepthDataPoint2, DataPoint lowerDepthDataPoint1, DataPoint lowerDepthDataPoint2) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();

        //interpolate temperature value from upperDepthDataPoint1 and upperDepthDataPoint2; (this is linear interpolation in the x-direction)
        double[] upperDepthDataPointLatitudes = {upperDepthDataPoint1.getLatitude(), upperDepthDataPoint2.getLatitude()};
        double[] upperDepthDataPointTemperatures = {upperDepthDataPoint1.getTemperatureK(), upperDepthDataPoint2.getTemperatureK()};
        double upperDepthInterpolationPointTemperature = linearInterpolator.interpolate(upperDepthDataPointLatitudes, upperDepthDataPointTemperatures).value(interpolationPoint.getLatitude());

        //interpolate temperature value from lowerDepthDataPoint1 and lowerDepthDataPoint2; (this is linear interpolation in the x-direction)
        double[] lowerDepthDataPointLatitudes = {lowerDepthDataPoint1.getLatitude(), lowerDepthDataPoint2.getLatitude()};
        double[] lowerDepthDataPointTemperatures = {lowerDepthDataPoint1.getTemperatureK(), lowerDepthDataPoint2.getTemperatureK()};
        double lowerDepthInterpolationPointTemperature = linearInterpolator.interpolate(lowerDepthDataPointLatitudes, lowerDepthDataPointTemperatures).value(interpolationPoint.getLatitude());

        //interpolate temperature value from upperDepthInterpolationPointTemperature and lowerDepthInterpolationPointTemperature at interpolation point depth (this is linear interpolation in the y-direction)
        double[] upperAndLowerDepths = {upperDepthDataPoint1.getDepth(), lowerDepthDataPoint1.getDepth()};
        double[] upperAndLowerDepthTemperatures = {upperDepthInterpolationPointTemperature, lowerDepthInterpolationPointTemperature};
        float interpolationPointTemperature = (float)linearInterpolator.interpolate(upperAndLowerDepths, upperAndLowerDepthTemperatures).value(interpolationPoint.getDepth());

        //set interpolated temperature to interpolation point and return
        interpolationPoint.setTemperatureK(interpolationPointTemperature);
        return interpolationPoint;
    }
}
