package com.github.gissuite.gribinterpolation.data;

import ucar.ma2.Array;
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

    public void buildDataPoint() {
        GribFileReader reader = new GribFileReader();
        dataset = reader.generateDatasetFromGribFile("C:\\Users\\zhink\\Desktop\\Southeastern Louisiana University\\Spring 2024\\CMPS 491 - Special Topics (Water Bodies Temperature Project)\\GribInterpolation\\src\\main\\resources\\dev-dataset.HYCOM\\2023-07-30T12_00_00Z\\HYCOM__2023073012__hycom-glbu-a1__sea_temp__dpth_sfc__00000000__00000000__fcst_ops__0360.grb");

        DataPointBuilder builder = new DataPointBuilder(dataset, "sea_temp_dpth_sfc");
        coordinateSystem = builder.createCoordinateSystem();

    }

    public CoordinateSystem createCoordinateSystem() {
        List<GridDatatype> grids = dataset.getGrids();
        VariableDS v = grids.get(0).getVariable();
        CoordinateSystem system = v.getCoordinateSystems().get(0);
        return system;
    }

    public double[] getLatitudeCoordinates(CoordinateSystem system) {
        lat = system.getLatAxis();
        return
    }

    public double[] getLongitudeCoordinates(CoordinateSystem system) {

        return
    }

    public double[] getSurfaceDepths(CoordinateSystem system) {

        return
    }

    public double[] getTime(CoordinateSystem system) {

        return
    }

    public double getTemperatureValue(GridDataset dataset, double lat, double lon) {
        GridDatatype grid = dataset.findGridDatatype(varNameForTemperatureValues);
        GridCoordSystem gridCoordSystem = grid.getCoordinateSystem();

        int[] xy = gridCoordSystem.findXYindexFromLatLon(lat,lon, null);

        Array data;
        try {
            data = grid.readDataSlice(0,0,xy[1],xy[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        double value = data.getDouble(0);
        return value;
    }
}
