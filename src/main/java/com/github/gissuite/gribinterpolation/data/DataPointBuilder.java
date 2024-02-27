package com.github.gissuite.gribinterpolation.data;

import ucar.nc2.dataset.CoordinateAxis;
import ucar.nc2.dataset.CoordinateAxis1DTime;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import ucar.nc2.time.CalendarDate;

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
        double latValue = lat.getMaxValue();    //test latValue, no getValue() method.
        return latValue;
    }

    public double getlongitudeCoords(GridCoordSystem system) {
        CoordinateAxis lon = system.getXHorizAxis();

        //code to get longitude value from CoordinateAxis

        return;
    }

    public double getTimes(GridCoordSystem system) {
        if (system.hasTimeAxis1D()) {
            CoordinateAxis1DTime timeAxis1D = system.getTimeAxis1D();
            List<CalendarDate> dates = timeAxis1D.getCalendarDates();
        }
        else if (system.hasTimeAxis()) {
            CoordinateAxis timeAxis = system.getTimeAxis();
        }

        //code to get time value from CoordinateAxis

        return;
    }

//    public double getSurfaceDepths() {
//
//    }

//    public double getTemperatures() {
//
//    }

}
