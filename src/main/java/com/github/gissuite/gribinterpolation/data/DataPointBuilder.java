package com.github.gissuite.gribinterpolation.data;


import ucar.nc2.Variable;
import ucar.nc2.dataset.CoordinateAxis;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.GridDatatype;

import ucar.nc2.dt.grid.GridCoordSys;
import ucar.nc2.dt.grid.GridDataset;


import java.util.List;

public class DataPointBuilder {
    GridDataset dataset;
    double[] latitudeCoords;
    double[] longitudeCoords;
    double[] times;
    double[] surfaceDepth;
    double[] temperatures;

    DataPointBuilder(GridDataset dataset) {
        this.dataset = dataset;
    }

    public double getlatCoords() {
        List<GridDatatype> grids = dataset.getGrids();
        GridCoordSystem system = grids.get(0).getCoordinateSystem();
        CoordinateAxis lat = system.getYHorizAxis();
        lat.get


        //List<CoordinateAxis> coordinateAxes = system.getCoordinateAxes();

        return
    }

}
