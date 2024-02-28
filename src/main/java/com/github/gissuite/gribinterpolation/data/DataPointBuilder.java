package com.github.gissuite.gribinterpolation.data;

import ucar.ma2.Array;
import ucar.nc2.dataset.CoordinateAxis;
import ucar.nc2.dataset.CoordinateAxis1DTime;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import java.io.IOException;
import java.util.List;

public class DataPointBuilder {
    static GridDataset dataset;
    static GridCoordSystem coordSystem;
    static Array latitudeCoords;
    static Array longitudeCoords;
    static Array surfaceDepths;
    static Array temperatureValues;

    DataPointBuilder(GridDataset dataset) {
        this.dataset = dataset;
    }

    public static void main() {
        //create GridCoordSystem
        coordSystem = createGridCoordSystem();

        //get array of Latitude coordinates
        latitudeCoords = getLatitudeCoords(coordSystem);

        //get array of longitude coordinates
        longitudeCoords = getlongitudeCoords(coordSystem);

        //call appropriate Time getter method
        if (coordSystem.hasTimeAxis1D()) {
            CoordinateAxis1DTime timeAxis1D = coordSystem.getTimeAxis1D();
        }
        else if (coordSystem.hasTimeAxis()) {
            CoordinateAxis timeAxis = coordSystem.getTimeAxis();
        }

        //get array of surface depths
        surfaceDepths = getSurfaceDepths(coordSystem);

        //get array of temperatures

    }

    public static GridCoordSystem createGridCoordSystem() {
        List<GridDatatype> grids = dataset.getGrids();
        GridDatatype grid = grids.get(0);
        GridCoordSystem system = grid.getCoordinateSystem();
        return system;
    }

    public static Array getLatitudeCoords(GridCoordSystem system) {
        Array latValues;
        CoordinateAxis lat = system.getYHorizAxis();
        try {
            latValues = lat.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return latValues;
    }

    public static Array getlongitudeCoords(GridCoordSystem system) {
        Array lonValues;
        CoordinateAxis lon = system.getXHorizAxis();
            try {
                lonValues = lon.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return lonValues;
    }

    public static Array getSurfaceDepths(GridCoordSystem system) {
        Array srfDpthValues;
        CoordinateAxis srfDpth = system.getVerticalAxis();
            try {
                srfDpthValues = srfDpth.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return srfDpthValues;
    }

//    public static Array getTemperatures(GridCoordSystem system) {
//        Array tempValues;
//    }

}
