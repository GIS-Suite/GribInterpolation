package com.github.gissuite.gribinterpolation.data;

import ucar.ma2.Array;
import ucar.nc2.Variable;
import ucar.nc2.dataset.CoordinateAxis;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DataPointBuilder {
    static GridDataset dataset;
    static GridCoordSystem coordSystem;
    static Array latitudeCoords;
    static Array longitudeCoords;
    static Array surfaceDepths;
    static Array temperatureValues;
    static String varNameForTemperatureValues;

    DataPointBuilder(GridDataset dataset, String varNameForTemperatureValues) {
        this.dataset = dataset;
        this.varNameForTemperatureValues = varNameForTemperatureValues;
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

    public static Array getTemperatures(GridDataset dataset) {
        Array tempValues;
        Variable v = Objects.requireNonNull(dataset.getNetcdfFile()).findVariable(varNameForTemperatureValues);
        try {
            tempValues = Objects.requireNonNull(v).read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempValues;
    }
}
