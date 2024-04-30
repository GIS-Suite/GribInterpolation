package com.github.gissuite.gribinterpolation;
import com.github.gissuite.gribinterpolation.data.GridFileReader;
import org.junit.jupiter.api.Test;
import ucar.nc2.dt.grid.GridDataset;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearInterpolationIntegrationTests {
    @Test
    public void linearInterpolation_Should_Return_Arraylist() {
        GridFileReader gridFileReader = new GridFileReader();

        //pass filepath of folder holding the sample data to get dataset
        //GridDataset testDataset = gridFileReader.generateDataset();

        //pass dataset into DataPointBuilder to get all data points as ArrayList

        //Pass ArrayList of data points to datasetLinearInterpolation to get interpolated ArrayList


    }
}
