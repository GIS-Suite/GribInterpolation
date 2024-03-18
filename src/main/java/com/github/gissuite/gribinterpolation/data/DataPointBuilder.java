package com.github.gissuite.gribinterpolation.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.ma2.ArrayFloat;
import ucar.nc2.Variable;
import ucar.nc2.dt.grid.GridDataset;

import java.util.HashSet;
import java.util.Set;


/**
 * Builds a set of data points from a grid dataset.
 */
public class DataPointBuilder {

    private final String TEMPERATURE_VARIABLE_NAME = "sea_temp_dpth_sfc";
    private final String LATITUDE_VARIABLE_NAME = "lat";
    private final String LONGITUDE_VARIABLE_NAME = "lon";
    private final String DEPTH_VARIABLE_NAME = "dpth_sfc";

    private final Logger logger = LoggerFactory.getLogger(DataPointBuilder.class);

    /**
     * Builds a set of data points from the given NetCDF dataset.
     *
     * @param dataset the NetCDF dataset
     * @return a Set of data points (DataPoint)
     */
    public Set<DataPoint> buildDataPoints(GridDataset dataset) {
        Set<DataPoint> dataPoints = new HashSet<>();
        try (dataset) {
            Variable temperatures = (Variable) dataset.getDataVariable("sea_temp_dpth_sfc");
            Variable latVar = (Variable) dataset.getDataVariable("lat");
            Variable lonVar = (Variable) dataset.getDataVariable("lon");
            Variable depthVar = (Variable) dataset.getDataVariable("dpth_sfc");

            ArrayFloat.D4 tempArr = (ArrayFloat.D4) temperatures.read();
            ArrayFloat.D1 latArr = (ArrayFloat.D1) latVar.read();
            ArrayFloat.D1 lonArr = (ArrayFloat.D1) lonVar.read();
            ArrayFloat.D1 depthArr = (ArrayFloat.D1) depthVar.read();

            float depth = depthArr.get(0);
            for (int latIndex = 0; latIndex < latVar.getSize(); latIndex++) {
                for (int lonIndex = 0; lonIndex < lonVar.getSize(); lonIndex++) {
                    float temp = tempArr.get(0, 0, latIndex, lonIndex);
                    float lat = latArr.get(latIndex);
                    float lon = lonArr.get(lonIndex);
                    DataPoint dp = new DataPoint(lat, lon, temp, depth);
                    dataPoints.add(dp);
                }
            }
        } catch (Exception ex) {
            logger.error("An error occurred.", ex);
        }
        return dataPoints;
    }
}
