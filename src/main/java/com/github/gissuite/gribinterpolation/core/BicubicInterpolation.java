package com.github.gissuite.gribinterpolation.core;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolatingFunction;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;

public class BicubicInterpolation {
    //would be bad to use a points array smaller than a 4x4
    //Apache also states that interpolated points near the edge of the known points will be inaccurate
    //Input points have to be in a square grid Apache wont take anything else

    /**
     * @param points 2d array of known data points to interpolate with
     * @param unknownTemp the data point with the missing temp
     * @return the data point with the interpolated temp
     */
    //interplates based off latitude and longitude
    public static DataPoint interpolateLatLong(DataPoint[][] points, DataPoint unknownTemp){
        BicubicInterpolator bicubic = new BicubicInterpolator();

        //extracting the information from the data points
        int rowlen = points[0].length;
        double[] lats = new double[rowlen];
        double[] longs = new double[rowlen];
        double[][] temps = new double[rowlen][rowlen];
        
        for(int i = 0; i < rowlen; i++){
            for(int j = 0; j < rowlen; j++){
                temps[i][j] = points[i][j].getTemperatureK();
            }
            longs[i] = points[0][i].getLongitude();
            lats[i] = points[i][0].getLatitude();
        }

        BicubicInterpolatingFunction bicubicfunc = bicubic.interpolate(longs, lats, temps);

        unknownTemp.setTemperatureK((float)bicubicfunc.value(unknownTemp.getLongitude(), unknownTemp.getLatitude()));
        return unknownTemp;
    }

    /**
     * @param points 2d array of known data points to interpolate with
     * @param unknownTemp the data point with the missing temp
     * @return the data point with the interpolated temp
     */
    //interpolates based off latitude and depth
    public static DataPoint interpolateLatDepth(DataPoint[][] points, DataPoint unknownTemp){
        BicubicInterpolator bicubic = new BicubicInterpolator();

        int rowlen = points[0].length;
        double[] lats = new double[rowlen];
        double[] depths = new double[rowlen];
        double[][] temps = new double[rowlen][rowlen];

        for(int i = 0; i < rowlen; i++){
            for(int j = 0; j < rowlen; j++){
                temps[i][j] = points[i][j].getTemperatureK();
            }
            depths[i] = points[i][0].getDepth();
            lats[i] = points[0][i].getLatitude();
        }

        BicubicInterpolatingFunction bicubicfunc = bicubic.interpolate(lats, depths, temps);

        unknownTemp.setTemperatureK((float)bicubicfunc.value(unknownTemp.getLatitude(), unknownTemp.getDepth()));
        return unknownTemp;
    }

    /**
     * @param points 2d array of known data points to interpolate with
     * @param unknownTemp the data point with the missing temp
     * @return the data point with the interpolated temp
     */
    //interpolates based off longitude and depth
    public static DataPoint interpolateLongDepth(DataPoint[][] points, DataPoint unknownTemp){
        BicubicInterpolator bicubic = new BicubicInterpolator();

        int rowlen = points[0].length;
        double[] longs = new double[rowlen];
        double[] depths = new double[rowlen];
        double[][] temps = new double[rowlen][rowlen];

        for(int i = 0; i < rowlen; i++){
            for(int j = 0; j < rowlen; j++){
                temps[i][j] = points[i][j].getTemperatureK();
            }
            depths[i] = points[i][0].getDepth();
            longs[i] = points[0][i].getLongitude();
        }

        BicubicInterpolatingFunction bicubicfunc = bicubic.interpolate(longs, depths, temps);

        unknownTemp.setTemperatureK((float)bicubicfunc.value(unknownTemp.getLongitude(), unknownTemp.getDepth()));
        return unknownTemp;
    }    
}
