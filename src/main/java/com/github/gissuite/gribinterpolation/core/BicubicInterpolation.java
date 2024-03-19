package com.github.gissuite.gribinterpolation.core;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolatingFunction;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;

public class BicubicInterpolation {
    BicubicInterpolator bicubic = new BicubicInterpolator();
    DataPoint[][] points = new DataPoint[4][4];

    BicubicInterpolation(){
        //creating fake data points
        float lat = 60;
        float longi = 60;
        float tempk = 300;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                points[i][j] = new DataPoint(lat, longi, tempk);
                lat += 0.5;
                tempk += 5;
            }
            longi += 0.5;
        }

        //extracting the information from the fake data points
        double[] lats = new double[4];
        double[] longs = new double[4];
        double[][] temps = new double[4][4];

        for(int i = 0; i < 4; i++){
            lats[i] = points[i][0].getLatitude();
        }

        for(int i = 0; i < 4; i++){
            longs[i] = points[0][i].getLongitude();
        }

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                temps[i][j] = points[i][j].getTemperatureK();
            }
        }

        BicubicInterpolatingFunction bicubicfunc = bicubic.interpolate(lats, longs, temps);

        //value at this point should be about 327.5
        double interpolated = bicubicfunc.value(60.55, 60.55);
        System.out.println(interpolated);
    }

    public static void main(String args[]){
        BicubicInterpolation interpolation = new BicubicInterpolation();
    }
}
