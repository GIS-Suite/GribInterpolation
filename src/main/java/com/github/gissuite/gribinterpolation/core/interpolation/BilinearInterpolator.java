package com.github.gissuite.gribinterpolation.core.interpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class BilinearInterpolator {

    /**
     * Performs bilinear interpolation to estimate the temperature at a specified longitude and depth.
     * This method interpolates temperatures between two layers (upper and lower) at a given longitude.
     *
     * @param interpolationPoint The target point for interpolation, specifying the desired longitude and depth.
     * @param upperDataPoints Data points on the upper layer, providing temperature and longitude.
     * @param lowerDataPoints Data points on the lower layer, providing temperature and longitude.
     * @return The interpolation point with its temperature set to the interpolated value.
     */
    public static DataPoint interpolateWithDynamicLon(DataPoint interpolationPoint, DataPoint[] upperDataPoints, DataPoint[] lowerDataPoints) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();

        double upperTemperature = interpolateTemperatureAtLongitude(linearInterpolator, upperDataPoints, interpolationPoint.getLongitude());
        double lowerTemperature = interpolateTemperatureAtLongitude(linearInterpolator, lowerDataPoints, interpolationPoint.getLongitude());

        // Perform linear interpolation between the upper and lower temperatures at the given depth
        double[] depths = { upperDataPoints[0].getDepth(), lowerDataPoints[0].getDepth() };
        double[] temps = { upperTemperature, lowerTemperature};
        double interpolatedTemperature = interpolateTemperatureAtDepth(linearInterpolator, depths, temps, interpolationPoint.getDepth());

        interpolationPoint.setTemperatureK((float)interpolatedTemperature);
        return interpolationPoint;
    }

    /**
     * Performs bilinear interpolation to estimate the temperature at a specified latitude and depth.
     * This method interpolates temperatures between two layers (upper and lower) at a given latitude.
     *
     * @param interpolationPoint The target point for interpolation, specifying the desired latitude and depth.
     * @param upperDataPoints Data points on the upper layer, providing temperature and latitude.
     * @param lowerDataPoints Data points on the lower layer, providing temperature and latitude.
     * @return The interpolation point with its temperature set to the interpolated value.
     */
    public static DataPoint interpolateWithDynamicLat(DataPoint interpolationPoint, DataPoint[] upperDataPoints, DataPoint[] lowerDataPoints) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();

        double upperTemperature = interpolateTemperatureAtLatitude(linearInterpolator, upperDataPoints[0], upperDataPoints[1], interpolationPoint.getLatitude());
        double lowerTemperature = interpolateTemperatureAtLatitude(linearInterpolator, lowerDataPoints[0], lowerDataPoints[1], interpolationPoint.getLatitude());

        double[] depths = { upperDataPoints[0].getDepth(), lowerDataPoints[0].getDepth()};
        double[] temps = { upperTemperature, lowerTemperature};
        double interpolatedTemperature = interpolateTemperatureAtDepth(linearInterpolator, depths, temps, interpolationPoint.getDepth());

        interpolationPoint.setTemperatureK((float)interpolatedTemperature);
        return interpolationPoint;
    }

    private static double interpolateTemperatureAtLongitude(LinearInterpolator interpolator, DataPoint[] dataPoints, double longitude) {
        double[] longitudes = new double[]{dataPoints[0].getLongitude(), dataPoints[1].getLongitude()};
        double[] temperatures = new double[]{dataPoints[0].getTemperatureK(), dataPoints[1].getTemperatureK()};
        return interpolator.interpolate(longitudes, temperatures).value(longitude);
    }

    private static double interpolateTemperatureAtLatitude(LinearInterpolator interpolator, DataPoint point1, DataPoint point2, double latitude) {
        double[] latitudes = new double[]{point1.getLatitude(), point2.getLatitude()};
        double[] temperatures = new double[]{point1.getTemperatureK(), point2.getTemperatureK()};
        return interpolator.interpolate(latitudes, temperatures).value(latitude);
    }

    private static double interpolateTemperatureAtDepth(LinearInterpolator interpolator, double[] depths, double[] temperatures, double targetDepth) {
        return interpolator.interpolate(depths, temperatures).value(targetDepth);
    }
}
