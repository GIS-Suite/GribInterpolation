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

import com.github.gissuite.gribinterpolation.data.DataPointBuilder;
import com.github.gissuite.gribinterpolation.data.GridFileReader;
import ucar.nc2.dataset.CoordinateSystem;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.dt.grid.GridDataset;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        GridFileReader reader = new GridFileReader();
        String filePath = "C:\\Users\\zhink\\Desktop\\Southeastern Louisiana University\\Spring 2024\\CMPS 491 - Special Topics (Water Bodies Temperature Project)\\GribInterpolation\\src\\main\\resources\\dev-dataset.HYCOM\\2023-07-30T12_00_00Z\\HYCOM__2023073012__hycom-glbu-a1__sea_temp__dpth_sfc__00000000__00000000__fcst_ops__0360.grb";
        Set<NetcdfDataset.Enhance> enhancements = new HashSet<>();
        enhancements.add(NetcdfDataset.Enhance.CoordSystems);

        GridDataset dataset = reader.generateDatasetFromGridFile(filePath, enhancements);
        String fullVariableName = dataset.getDataVariables().get(0).getFullName();
        DataPointBuilder builder = new DataPointBuilder(dataset, fullVariableName);
        CoordinateSystem coordinateSystem = builder.createCoordinateSystem();
        double[] latitudeCoordinates = builder.getLatitudeCoordinates(coordinateSystem);
        double[] longitudeCoordinates = builder.getLongitudeCoordinates(coordinateSystem);
        double[] surfaceDepths = builder.getSurfaceDepths(coordinateSystem);
        double[] times = builder.getTimes(coordinateSystem);

        float tempValue = 0;
        for (double longitudeCoordinate : longitudeCoordinates) {
            for (double latitudeCoordinate : latitudeCoordinates) {
                tempValue = builder.getTemperatureValue(dataset, latitudeCoordinate, longitudeCoordinate);
                System.out.printf("%s %f || %s %f || %s %f\n", "lat: ", latitudeCoordinate, "lon: ", longitudeCoordinate, "Value: ", tempValue);
            }
        }

    }
}
