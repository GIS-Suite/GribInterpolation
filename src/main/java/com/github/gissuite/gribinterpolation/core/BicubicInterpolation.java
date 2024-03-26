package com.github.gissuite.gribinterpolation.core;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolatingFunction;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;

public class BicubicInterpolation {

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
            lats[i] = points[i][0].getLatitude();
            longs[i] = points[0][i].getLongitude();
        }

        BicubicInterpolatingFunction bicubicfunc = bicubic.interpolate(longs, lats, temps);

        unknownTemp.setTemperatureK((float)bicubicfunc.value(unknownTemp.getLongitude(), unknownTemp.getLatitude()));
        return unknownTemp;
    }

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

    public static void main(String args[]){
        //creating fake data points
        DataPoint[][] fakepoints = new DataPoint[4][4];
        float latitude = 60;
        float longitude = 60;
        float depth = 10;
        float tempk = 300;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                fakepoints[i][j] = new DataPoint(longitude, latitude, tempk, depth);
                longitude += 0.5;
                tempk += 5;
            }
            longitude = 60;
            latitude += 0.5;
            depth += 5;
        }

        //fake data points created to show interpolating with l
        DataPoint[][] fakepoints2 = new DataPoint[4][4];
        latitude = 60;
        longitude = 60;
        depth = 10;
        tempk = 300;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                fakepoints2[i][j] = new DataPoint(longitude, latitude, tempk, depth);
                latitude += 0.5;
                tempk += 5;
            }
            latitude = 60;
            depth += 5;
        }

        //should be about 327.5
        DataPoint pointToFind = new DataPoint((float)(60.55), (float)(60.55), (float)(0), (float)(22.5));
        pointToFind = BicubicInterpolation.interpolateLatLong(fakepoints, pointToFind);
        System.out.println(pointToFind.getTemperatureK());

        //347.5
        DataPoint pointToFind2 = new DataPoint((float)(61), (float)(61), (float)(0), (float)(17.5));
        pointToFind2 = BicubicInterpolation.interpolateLatDepth(fakepoints2, pointToFind2);
        System.out.println(pointToFind2.getTemperatureK());

        //347.5
        DataPoint pointToFind3 = new DataPoint((float)(61), (float)(61), (float)(0), (float)(17.5));
        pointToFind3 = BicubicInterpolation.interpolateLongDepth(fakepoints, pointToFind3);
        System.out.println(pointToFind3.getTemperatureK());
    }
}
