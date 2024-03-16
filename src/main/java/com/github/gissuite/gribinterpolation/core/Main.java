/*
* MIT License
*
* Copyright (c) 2024 OpenGISViewer
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*
 */
package com.github.gissuite.gribinterpolation.core;

public class Main {
    public static void main(String[] args) {
        //PLEASE NOTE: dataPoint = {latitude, longitude, depth below surface, temperature value}
        //datapoint1 and datapoint2; Western/Eastern Upper surface depth
        //datapoint3 and datapoint4; Western/Eastern Lower surface depth
        double[] dataPoint1 = {100, 100, 0, 100};
        double[] dataPoint2 = {100, 102, 0, 105};
        double[] dataPoint3 = {100, 100, 4, 50};
        double[] dataPoint4 = {100, 102, 4, 49.6};
        double[] dataPointMTLon = {100, 100.3};

        System.out.println("Interpolating with dynamic longitude:");
        BilinearInterpolator terpingDynamicLon = new BilinearInterpolator(dataPointMTLon, dataPoint1, dataPoint2, dataPoint3, dataPoint4);
        double[][] LonterpedDataPointArray = terpingDynamicLon.InterpolateWithDynamicLon();
        System.out.println("Upper Depth temperature(@DataPointMT): " + terpingDynamicLon.lonUpperDepthTemp);
        System.out.println("Lower Depth temperature(@DataPointMT): " + terpingDynamicLon.lonLowerDepthTemp);
        System.out.println("\nData Points at the missing depths: ");
        for(int dataPointIndex = 0; dataPointIndex < LonterpedDataPointArray.length; dataPointIndex++){
            for(int dataPointValuesIndex = 0; dataPointValuesIndex < 4; dataPointValuesIndex++) {
                System.out.print(LonterpedDataPointArray[dataPointIndex][dataPointValuesIndex] + ", ");
            }
            System.out.println();
        }
        System.out.println();


        //PLEASE NOTE: dataPoint = {latitude, longitude, depth below surface, temperature value}
        //datapoint1 and datapoint2; Southern/Northern Upper surface depth
        //datapoint3 and datapoint4; Southern/Northern Lower surface depth
        double[] dataPoint5 = {5, 100, 0, 100};
        double[] dataPoint6 = {7, 100, 0, 96};
        double[] dataPoint7 = {5, 100, 4, 50};
        double[] dataPoint8 = {7, 100, 4, 46};
        double[] dataPointMTLat = {5.8, 100};

        System.out.println("Interpolating with dynamic latitude: ");
        BilinearInterpolator terpingDynamicLat = new BilinearInterpolator(dataPointMTLat, dataPoint5, dataPoint6, dataPoint7, dataPoint8);
        double[][] LatterpedDataPointArray = terpingDynamicLat.InterpolateWithDynamicLat();
        System.out.println("Upper Depth temperature(@DataPointMT): " + terpingDynamicLat.latUpperDepthTemp);
        System.out.println("Lower Depth temperature(@DataPointMT): " + terpingDynamicLat.latLowerDepthTemp);
        System.out.println("\nData Points at the missing depths: ");
        for(int dataPointIndex = 0; dataPointIndex < LatterpedDataPointArray.length; dataPointIndex++){
            for(int dataPointValuesIndex = 0; dataPointValuesIndex < 4; dataPointValuesIndex++) {
                System.out.print(LatterpedDataPointArray[dataPointIndex][dataPointValuesIndex] + ", ");
            }
            System.out.println();
        }

    }
}
