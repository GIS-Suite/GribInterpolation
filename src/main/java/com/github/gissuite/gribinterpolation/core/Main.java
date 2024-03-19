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

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.BilinearInterpolator.*;

public class Main {
    public static void main(String[] args) {
        //Using Bilinear Interpolation with static latitudes
        float[] targetLonLat = {100.4f, 100f};

        //2 closest points to target Lon/Lat at identical latitude at closest upper depth. upperDepthDataPoint1 = lower Lon
        DataPoint upperDepthDataPoint1 = new DataPoint(100, 100, 100, 0);
        DataPoint upperDepthDataPoint2 = new DataPoint(102, 100, 106, 0);

        //2 closest points to target Lon/Lat at identical latitude at closest lower depth. lowerDepthDataPoint1 = lower Lon
        DataPoint lowerDepthDataPoint1 = new DataPoint(100, 100, 50, 4);
        DataPoint lowerDepthDataPoint2 = new DataPoint(102, 100, 47, 4);

        //pass target Lon/Lat and 4 datapoints into BilinearInterpolator to interpolate temperature values for the depths at the target Lon/Lat
        ArrayList<DataPoint> LonterpedDataPointArray = interpolateWithStaticLat(targetLonLat, upperDepthDataPoint1, upperDepthDataPoint2, lowerDepthDataPoint1, lowerDepthDataPoint2);
        System.out.println("\nTemperature interpolation at target Lon/Lat: ");
        System.out.println("\nData Points:");
        for (DataPoint dataPoint : LonterpedDataPointArray) {
            System.out.println(dataPoint.getLongitude() + ", " +
                    dataPoint.getLatitude() + ", " +
                    dataPoint.getTemperatureK() + ", " +
                    dataPoint.getDepth());
        }

        //Using Bilinear Interpolation with static longitudes
        float[] targetLonLat2 = {100, 100};

        //2 closest points to target Lon/Lat at identical longitudes at closet upper depth. upperDepthDataPoint3 = lower Lat
        DataPoint upperDepthDataPoint3 = new DataPoint(100, 99.4f, 100, 1);
        DataPoint upperDepthDataPoint4 = new DataPoint(100, 100.7f, 99.3f, 1);

        //2 closest points to target Lon/Lat at identical longitudes at closest lower depth. lowerDepthDataPoint3 = lower Lat
        DataPoint lowerDepthDataPoint3 = new DataPoint(100, 99.4f, 50, 4);
        DataPoint lowerDepthDataPoint4 = new DataPoint(100, 100.7f, 48, 4);

        //pass target Lon/Lat and 4 datapoints into BilinearInterpolator to interpolate temperature values for the depths at the target Lon/Lat
        ArrayList<DataPoint> LatterpedDataPointArray = interpolateWithStaticLon(targetLonLat2, upperDepthDataPoint3, upperDepthDataPoint4, lowerDepthDataPoint3, lowerDepthDataPoint4);
        System.out.println("\nTemperature interpolation at target Lon/Lat: ");
        System.out.println("\nData Points:");
        for (DataPoint dataPoint : LatterpedDataPointArray) {
            System.out.println(dataPoint.getLongitude() + ", " +
                    dataPoint.getLatitude() + ", " +
                    dataPoint.getTemperatureK() + ", " +
                    dataPoint.getDepth());
        }
    }
}
