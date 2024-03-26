package com.github.gissuite.gribinterpolation.core.interpolation;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.apache.commons.math3.analysis.interpolation.DividedDifferenceInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;

import java.util.ArrayList;

public class DividedDifference {
    /**
     * @param columnedDataPoints Datapoints that all contain the same lat and lon to be used for building the interpolator function
     * @param wantedPoint        The data point to be interpolated
     * @return The data point that contains the interpolated temperature value
     */

    public static DataPoint interpolate(ArrayList<DataPoint> columnedDataPoints, DataPoint wantedPoint){
        double[] x = columnedDataPoints.stream().mapToDouble(dataPoint -> (float)(dataPoint.getDepth())).toArray();
        double[] y = columnedDataPoints.stream().mapToDouble(dataPoint -> (float)(dataPoint.getTemperatureK())).toArray();

        PolynomialFunctionNewtonForm interpolator = new DividedDifferenceInterpolator().interpolate(x,y);
        double interpolatedTemperature = interpolator.value(wantedPoint.getDepth());
        wantedPoint.setTemperatureK((float)interpolatedTemperature);
        return wantedPoint;
    }
}
