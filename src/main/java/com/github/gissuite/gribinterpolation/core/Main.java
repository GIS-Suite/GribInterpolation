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

public class Main {
    public static void main(String[] args) {

        DataPoint dataPoint1 = new DataPoint(100, 100, 100, 0);
        DataPoint dataPoint2 = new DataPoint(102, 100, 105, 0);
        DataPoint dataPoint3 = new DataPoint(100, 100, 50, 4);
        DataPoint dataPoint4 = new DataPoint(102, 100, 49, 4);
        DataPoint dataPointMT = new DataPoint(101, 100, 212, 0);

        BilinearInterpolator terping = new BilinearInterpolator(dataPointMT, dataPoint1, dataPoint2, dataPoint3, dataPoint4);
        DataPoint[] LonterpedDataPointArray = terping.Interpolate();
        System.out.println("Upper Depth temperature(@DataPointMT): " + terping.upperDepthTemp);
        System.out.println("Lower Depth temperature(@DataPointMT): " + terping.lowerDepthTemp);
        System.out.println("\nData Points at the missing depths: ");
        for(int dataPointIndex = 0; dataPointIndex < LonterpedDataPointArray.length; dataPointIndex++){
                System.out.print(LonterpedDataPointArray[dataPointIndex].getTemperatureK() + ", ");
        }
        System.out.println();
    }
}
