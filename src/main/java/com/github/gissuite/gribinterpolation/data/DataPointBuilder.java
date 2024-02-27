package com.github.gissuite.gribinterpolation.data;


import ucar.nc2.Variable;
import ucar.nc2.dataset.CoordinateAxis;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;

import ucar.nc2.dt.grid.GridCoordSys;
import ucar.nc2.dt.grid.GridDataset;


import java.util.List;

public class DataPointBuilder {
    static GridDataset dataset;
    static GridCoordSystem coordSystem;
    double[] latitudeCoords;
    double[] longitudeCoords;
    double[] times;
    double[] surfaceDepths;
    double[] temperatures;

    DataPointBuilder(GridDataset dataset) {
        this.dataset = dataset;
    }

    public static void main() {
        //create GridCoordSystem
        coordSystem = createGridCoordSystem();

        //
    }

    public static GridCoordSystem createGridCoordSystem() {
        List<GridDatatype> grids = dataset.getGrids();
        GridDatatype grid = grids.get(0);
        GridCoordSystem system = grid.getCoordinateSystem();
        return system;
    }

    public double getLatitudeCoords(GridCoordSystem system) {
        CoordinateAxis lat = system.getYHorizAxis();
        //code to get latitude value from CoordinateAxis

        return;
    }
    public double getlongitudeCoords(GridCoordSystem system) {
        CoordinateAxis lon = system.getXHorizAxis();
        //code to get longitude value from CoordinateAxis

        return;
    }

    public double getTimes(GridCoordSystem system) {
        CoordinateAxis time = system.getTimeAxis();
        //code to get time value from CoordinateAxis

        return;
    }

    public double getSurfaceDepths() {

    }

    public double getTemperatures() {

    }

}
