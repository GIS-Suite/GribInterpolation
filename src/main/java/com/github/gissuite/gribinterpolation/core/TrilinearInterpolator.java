package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import java.util.ArrayList;
import static java.lang.Float.NaN;

public class TrilinearInterpolator {

    /**
     * @param interpolationDataPoint The target data point with the missing temperature value
     * @param lowerLatDataPoints The 4 adjacent pre-defined data points surrounding the interpolation point with the lower latitude
     * @param upperLatDataPoints The 4 adjacent pre-defined data points surrounding the interpolation point with the higher latitude
     * @return the target data point with the interpolated temperature value
     */
    public static DataPoint interpolateWithDynamicLon(DataPoint interpolationDataPoint, ArrayList<DataPoint> lowerLatDataPoints, ArrayList<DataPoint> upperLatDataPoints) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();

        //Interpolate temperature at the interpolationDataPoint longitude and depth for the four lower latitude data points using bilinear interpolation.
        DataPoint lowerLatInterpolationPoint = new DataPoint(interpolationDataPoint.getLongitude(), lowerLatDataPoints.get(0).getLatitude(), NaN, interpolationDataPoint.getDepth());
        DataPoint upperDepthLowerLonLowerLatDataPoint = lowerLatDataPoints.get(0);
        DataPoint upperDepthUpperLonLowerLatDataPoint = lowerLatDataPoints.get(1);
        DataPoint lowerDepthLowerLonLowerLatDataPoint = lowerLatDataPoints.get(2);
        DataPoint lowerDepthUpperLonLowerLatDataPoint = lowerLatDataPoints.get(3);

        DataPoint lowerLatDataPoint = BilinearInterpolator.interpolateWithDynamicLon(
                lowerLatInterpolationPoint,
                upperDepthLowerLonLowerLatDataPoint,
                upperDepthUpperLonLowerLatDataPoint,
                lowerDepthLowerLonLowerLatDataPoint,
                lowerDepthUpperLonLowerLatDataPoint);

        //Interpolate temperature at the interpolationDataPoint longitude and depth for the four upper latitude data points using bilinear interpolation.
        DataPoint upperLatInterpolationPoint = new DataPoint(interpolationDataPoint.getLongitude(), upperLatDataPoints.get(0).getLatitude(), NaN, interpolationDataPoint.getDepth());
        DataPoint upperDepthLowerLonUpperLatDataPoint = upperLatDataPoints.get(0);
        DataPoint upperDepthUpperLonUpperLatDataPoint = upperLatDataPoints.get(1);
        DataPoint lowerDepthLowerLonUpperLatDataPoint = upperLatDataPoints.get(2);
        DataPoint lowerDepthUpperLonUpperLatDataPoint = upperLatDataPoints.get(3);

        DataPoint upperLatDataPoint = BilinearInterpolator.interpolateWithDynamicLon(
                upperLatInterpolationPoint,
                upperDepthLowerLonUpperLatDataPoint,
                upperDepthUpperLonUpperLatDataPoint,
                lowerDepthLowerLonUpperLatDataPoint,
                lowerDepthUpperLonUpperLatDataPoint);

        //Interpolate temperature at the interpolationDataPoint between lower and upper latitude data points using linear interpolation.
        double[] lowerAndUpperLats = {lowerLatDataPoint.getLatitude(), upperLatDataPoint.getLatitude()};
        double[] lowerAndUpperLatTemperatures = {lowerLatDataPoint.getTemperatureK(), upperLatDataPoint.getTemperatureK()};
        float interpolationPointTemperature = (float)linearInterpolator.interpolate(lowerAndUpperLats, lowerAndUpperLatTemperatures).value(interpolationDataPoint.getLatitude());

        interpolationDataPoint.setTemperatureK(interpolationPointTemperature);

        return interpolationDataPoint;
    }

    /**
     * @param interpolationDataPoint The target data point with the missing temperature value
     * @param lowerLonDataPoints The 4 adjacent pre-defined data points surrounding the interpolation point with the lower longitude
     * @param upperLonDataPoints The 4 adjacent pre-defined data points surrounding the interpolation point with the higher longitude
     * @return the target data point with the interpolated temperature value
     */
    public static DataPoint interpolateWithDynamicLat(DataPoint interpolationDataPoint, ArrayList<DataPoint> lowerLonDataPoints, ArrayList<DataPoint> upperLonDataPoints) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();

        //Interpolate temperature at the interpolationDataPoint latitude and depth for the four lower longitude data points using bilinear interpolation.
        DataPoint lowerLonInterpolationPoint = new DataPoint(lowerLonDataPoints.get(0).getLongitude(), interpolationDataPoint.getLatitude(), NaN, interpolationDataPoint.getDepth());
        DataPoint upperDepthLowerLatLowerLonDataPoint = lowerLonDataPoints.get(0);
        DataPoint upperDepthUpperLatLowerLonDataPoint = lowerLonDataPoints.get(1);
        DataPoint lowerDepthLowerLatLowerLonDataPoint = lowerLonDataPoints.get(2);
        DataPoint lowerDepthUpperLatLowerLonDataPoint = lowerLonDataPoints.get(3);

        DataPoint lowerLonDataPoint = BilinearInterpolator.interpolateWithDynamicLat(
                lowerLonInterpolationPoint,
                upperDepthLowerLatLowerLonDataPoint,
                upperDepthUpperLatLowerLonDataPoint,
                lowerDepthLowerLatLowerLonDataPoint,
                lowerDepthUpperLatLowerLonDataPoint);

        //Interpolate temperature at the interpolationDataPoint latitude and depth for the four higher longitude data points using bilinear interpolation.
        DataPoint upperLonInterpolationPoint = new DataPoint(upperLonDataPoints.get(0).getLongitude(), interpolationDataPoint.getLatitude(), NaN, interpolationDataPoint.getDepth());
        DataPoint upperDepthLowerLatUpperLonDataPoint = upperLonDataPoints.get(0);
        DataPoint upperDepthUpperLatUpperLonDataPoint = upperLonDataPoints.get(1);
        DataPoint lowerDepthLowerLatUpperLonDataPoint = upperLonDataPoints.get(2);
        DataPoint lowerDepthUpperLatUpperLonDataPoint = upperLonDataPoints.get(3);

        DataPoint upperLonDataPoint = BilinearInterpolator.interpolateWithDynamicLat(
                upperLonInterpolationPoint,
                upperDepthLowerLatUpperLonDataPoint,
                upperDepthUpperLatUpperLonDataPoint,
                lowerDepthLowerLatUpperLonDataPoint,
                lowerDepthUpperLatUpperLonDataPoint);

        //Interpolate temperature at the interpolationDataPoint between upper and lower longitude data points using linear interpolation.
        double[] lowerAndUpperLons = {lowerLonDataPoint.getLongitude(), upperLonDataPoint.getLongitude()};
        double[] lowerAndUpperLonTemperatures = {lowerLonDataPoint.getTemperatureK(), upperLonDataPoint.getTemperatureK()};
        float interpolationPointTemperature = (float)linearInterpolator.interpolate(lowerAndUpperLons, lowerAndUpperLonTemperatures).value(interpolationDataPoint.getLongitude());

        interpolationDataPoint.setTemperatureK(interpolationPointTemperature);

        return interpolationDataPoint;
    }
}