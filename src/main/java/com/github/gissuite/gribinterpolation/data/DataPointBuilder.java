package com.github.gissuite.gribinterpolation.data;

import ucar.ma2.Array;
import ucar.nc2.dataset.CoordinateAxis;
import ucar.nc2.dataset.CoordinateAxis1D;
import ucar.nc2.dataset.CoordinateSystem;
import ucar.nc2.dataset.VariableDS;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import java.io.IOException;
import java.util.List;

public class DataPointBuilder {
    GridDataset dataset;
    CoordinateSystem coordinateSystem;
    double[] latitudeCoordinates;
    double[] longitudeCoordinates;
    double[] surfaceDepths;
    double temperatureValue;
    String varNameForTemperatureValues;

    public DataPointBuilder(GridDataset dataset, String varNameForTemperatureValues) {
        this.dataset = dataset;
        this.varNameForTemperatureValues = varNameForTemperatureValues;
    }

    public CoordinateSystem createCoordinateSystem() {
        List<GridDatatype> grids = dataset.getGrids();
        VariableDS v = grids.get(0).getVariable();
        CoordinateSystem system = v.getCoordinateSystems().get(0);
        return system;
    }

    public double[] getLatitudeCoordinates(CoordinateSystem system) {
        CoordinateAxis lat = system.getLatAxis();
        double[] latCoordinates = ((CoordinateAxis1D)lat).getCoordValues();
        return latCoordinates;
    }

    public double[] getLongitudeCoordinates(CoordinateSystem system) {
        CoordinateAxis lon = system.getLonAxis();
        double[] lonCoordinates = ((CoordinateAxis1D)lon).getCoordValues();
        return lonCoordinates;
    }

    public double[] getSurfaceDepths(CoordinateSystem system) {
        CoordinateAxis depth = system.getZaxis();
        double[] srfDpths = ((CoordinateAxis1D)depth).getCoordValues();
        return srfDpths;
    }

    public double[] getTimes(CoordinateSystem system) {
        CoordinateAxis time = system.getTaxis();
        double[] times = ((CoordinateAxis1D)time).getCoordValues();
        return times;
    }

    public float getTemperatureValue(GridDataset dataset, double lat, double lon) {
        Array data;

        GridDatatype grid = dataset.findGridDatatype(varNameForTemperatureValues);
        GridCoordSystem gridCoordSystem = grid.getCoordinateSystem();
        int[] xy = gridCoordSystem.findXYindexFromLatLon(lat,lon, null);

        try {
            data = grid.readDataSlice(-1,-1,xy[1],xy[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data.getFloat(0);
    }
}
