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
    GridDataset dataset;
    GridCoordSystem coordinateSystem;
    Array latitudeCoordinates;
    Array longitudeCoordinates;
    Array surfaceDepths;
    Array temperatureValues;
    String varNameForTemperatureValues;

    DataPointBuilder(GridDataset dataset, String varNameForTemperatureValues) {
        this.dataset = dataset;
        this.varNameForTemperatureValues = varNameForTemperatureValues;
    }

    public GridCoordSystem createGridCoordinateSystem() {
        List<GridDatatype> grids = dataset.getGrids();
        GridDatatype grid = grids.get(0);
        GridCoordSystem system = grid.getCoordinateSystem();
        return system;
    }

    public Array getLatitudeCoordinates(GridCoordSystem system) {
        Array latValues;
        CoordinateAxis lat = system.getYHorizAxis();
        try {
            latValues = lat.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return latValues;
    }

    public Array getLongitudeCoordinates(GridCoordSystem system) {
        Array lonValues;
        CoordinateAxis lon = system.getXHorizAxis();
            try {
                lonValues = lon.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return lonValues;
    }

    public Array getSurfaceDepths(GridCoordSystem system) {
        Array srfDepthValues;
        CoordinateAxis srfDepth = system.getVerticalAxis();
            try {
                srfDepthValues = srfDepth.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return srfDepthValues;
    }

    public Array getTemperatureValues(GridDataset dataset) {
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
