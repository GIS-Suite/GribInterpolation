package com.github.gissuite.gribinterpolation;
import com.github.gissuite.gribinterpolation.core.BilinearInterpolator;
import com.github.gissuite.gribinterpolation.core.NearestNeighbor;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.NearestNeighbor.getNearestNeighbor;
import static com.github.gissuite.gribinterpolation.core.NearestNeighbor.knnInterpolation;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class NearestNeighborTest {
    @Test
    public void knn_Should_Return_DataPoint(){

        // testing data
        ArrayList<DataPoint> neighbors = new ArrayList<>();
        neighbors.add(new DataPoint(3, 3, 37, -4));
        neighbors.add(new DataPoint(3, 4, 43, -4));
        neighbors.add(new DataPoint(10, 9, 73, -5));
        neighbors.add(new DataPoint(2, 3, 90, -3));

        // Creating point that we are interpolating
        DataPoint interpolatedPoint = new DataPoint(2, 3, NaN, -4);

        try{
            // testing only ArrayList<DataPoint> getNearestNeighbor
            try{
                // Interpolation point coordinates
                float latitudeToInterpolate = 2;
                float longitudeToInterpolate = 3;
                // Number of nearest neighbors to find
                int k = 3;
                // Call the method to get nearest neighbors
                ArrayList<DataPoint> nearestNeighbors = NearestNeighbor.getNearestNeighbor(neighbors, longitudeToInterpolate, latitudeToInterpolate, k, neighbors.size());
                // Assert the result
                System.out.println("Nearest Neighbors:");
                for (DataPoint neighbor : nearestNeighbors) {
                    System.out.println("Longitude: " + neighbor.getLongitude() + ", Latitude: " + neighbor.getLatitude() + ", Temperature: " + neighbor.getTemperatureK() + ", Depth: " + neighbor.getDepth());
                }
            }
            catch(Exception e){
                System.out.println("Problem with getNearestNeighbor:" + e);
            }
            // testing knnInterpolation
            try{
                // Find k nearest neighbor
                int k = 3;
                ArrayList<DataPoint> nearestNeighbors = getNearestNeighbor(neighbors, interpolatedPoint.getLongitude(), interpolatedPoint.getLatitude(),  k, neighbors.size());

                // KNN interpolation for temperature
                double interpolatedTemp = knnInterpolation(nearestNeighbors, interpolatedPoint);

                // Setting the temperature
                interpolatedPoint.setTemperatureK((float) interpolatedTemp);

                // Showing the new temperature
                System.out.println("Interpolated Temp: " + interpolatedPoint.getTemperatureK());
            }
            catch (Exception e) {
                System.out.println("Problem with knnInterpolation:" + e);
            }
        }
        catch(Exception e){
            System.out.println("Error with the overall :( : " + e);
        }
    }
}
