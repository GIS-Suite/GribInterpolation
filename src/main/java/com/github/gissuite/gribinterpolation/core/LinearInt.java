package com.github.gissuite.gribinterpolation.core;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class LinearInt {
    public static void Interpolate(double[] datapoint1, double[] datapoint2){
        LinearInterpolator equation = new LinearInterpolator();
        double[] depth = {datapoint1[3], datapoint2[3]};
        double[] temp = {datapoint1[2], datapoint2[2]};
        double[][] dataPointArray = new double[(int)(datapoint2[3]-datapoint1[3]-1)][4];
        int j = 0;
        for(double i=depth[0]+1; i<depth[1]; i++){
            dataPointArray[j][0] = datapoint1[0]; //latitude
            dataPointArray[j][1] = datapoint1[1]; //longitude
            dataPointArray[j][2] = equation.interpolate(depth, temp).value(i);
            dataPointArray[j][3] = i;
            j++;
        }

        for(int l = 0; l<dataPointArray.length; l++){
            for(int x=0; x<4; x++) {
                System.out.print(dataPointArray[l][x] + " ");
            }
            System.out.println();
        }
    }
}
