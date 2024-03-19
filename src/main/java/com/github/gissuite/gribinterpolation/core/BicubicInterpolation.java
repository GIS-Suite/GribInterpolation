package com.github.gissuite.gribinterpolation.core;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolatingFunction;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;

public class BicubicInterpolation {
    private BicubicInterpolatingFunction bicubicfunc;
    
    BicubicInterpolation(DataPoint[][] points){
        BicubicInterpolator bicubic = new BicubicInterpolator();

        //extracting the information from the fake data points
        int rowlen = points[0].length;
        double[] lats = new double[rowlen];
        double[] longs = new double[rowlen];
        double[][] temps = new double[rowlen][rowlen];
        
        for(int i = 0; i < rowlen; i++){
            lats[i] = points[i][0].getLatitude();
        }

        for(int i = 0; i < rowlen; i++){
            longs[i] = points[0][i].getLongitude();
        }
        
        for(int i = 0; i < rowlen; i++){
            for(int j = 0; j < rowlen; j++){
                temps[i][j] = points[i][j].getTemperatureK();
            }
        }

        bicubicfunc = bicubic.interpolate(lats, longs, temps);

    }

    public double interpolate(double x, double y){
        return bicubicfunc.value(x,y);
    }

    public static void main(String args[]){
        //creating fake data points
        DataPoint[][] points = new DataPoint[4][4];
        float latitude = 60;
        float longitude = 60;
        float tempk = 300;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                points[i][j] = new DataPoint(latitude, longitude, tempk);
                latitude += 0.5;
                tempk += 5;
            }
            longitude += 0.5;
        }

        BicubicInterpolation interpolator = new BicubicInterpolation(points);
        //should be about 327.5
        System.out.println(interpolator.interpolate(60.55, 60.55));
    }
}
