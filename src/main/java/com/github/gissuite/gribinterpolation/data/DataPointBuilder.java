package com.github.gissuite.gribinterpolation.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.ma2.ArrayFloat;
import ucar.nc2.Variable;
import ucar.nc2.dt.grid.GridDataset;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

public class DataPointBuilder {
    private final Logger logger = LoggerFactory.getLogger(DataPointBuilder.class);

    public Set<DataPoint> buildDataPoints(GridDataset dataset) throws IOException {
        Set<DataPoint> dataPoints = new HashSet<>();
        try (dataset) {
            Variable temperatures = (Variable) dataset.getDataVariable("sea_temp_dpth_sfc");
            Variable latVar = (Variable) dataset.getDataVariable("lat");
            Variable lonVar = (Variable) dataset.getDataVariable("lon");
            ArrayFloat.D4 tempArr = (ArrayFloat.D4) temperatures.read();
            ArrayFloat.D1 latArr = (ArrayFloat.D1) latVar.read();
            ArrayFloat.D1 lonArr = (ArrayFloat.D1) lonVar.read();

            for (int latIndex = 0; latIndex < latVar.getSize(); latIndex++) {
                for (int lonIndex = 0; lonIndex < lonVar.getSize(); lonIndex++) {
                    float temp = tempArr.get(0, 0, latIndex, lonIndex);
                    float lat = latArr.get(latIndex);
                    float lon = lonArr.get(lonIndex);

                    DataPoint dp = new DataPoint(lat, lon, temp);
                    dataPoints.add(dp);
                }
            }
        } catch (Exception ex) {
            logger.error("An error occurred.", ex);
        }
        return dataPoints;
    }
}
