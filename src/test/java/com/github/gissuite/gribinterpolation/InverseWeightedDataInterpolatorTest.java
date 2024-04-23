package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.InverseWeightedDataInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.InverseWeighted.inverseWeighted;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InverseWeightedDataInterpolatorTest {
    @Test
    public void InverseWeightedDataInterpolator_Should_Return_ArrayList1(){
//        //sample data points
//        ArrayList<DataPoint> dataPoints = new ArrayList<>();
//        dataPoints.add(new DataPoint((float)34, (float)-118, 72));
//        dataPoints.add(new DataPoint((float)40, (float)-74, 65));
//        dataPoints.add(new DataPoint((float)51, (float)-0.1, 55));
//        dataPoints.add(new DataPoint((float)-33, (float)151, 78));
//        dataPoints.add(new DataPoint((float)19, (float)70, NaN));
//        dataPoints.add(new DataPoint((float)-30, (float)150, NaN));
//        dataPoints.add(new DataPoint((float)42, (float)-70, NaN));
//
//        //check that InverseWeightedDataInterpolator returns the correct data points
//        InverseWeightedDataInterpolator.DataInterpolator(dataPoints);
//        for (DataPoint dataPoint : dataPoints){
//            System.out.println(dataPoint.getTemperatureK());
//        }
//
//        float expected1;
//        float expected2;
//        float expected3;
//        DataPoint result1 = dataPoints.get(4);
//        DataPoint result2 = dataPoints.get(5);
//        DataPoint result3 = dataPoints.get(6);
//        System.out.println("Expected temp of 1st: " + expected);
//        System.out.println("Actual Inverse Weighted Average: " + result.getTemperatureK());

        //test
//        assertEquals(expected1, result1.getTemperatureK());
//        assertEquals(expected2, result2.getTemperatureK());
//        assertEquals(expected3, result3.getTemperatureK());

    }
}
//        dataPoints.add(new DataPoint((float)-37.8136, (float)144.9631, 82));
//        dataPoints.add(new DataPoint((float)35.6895, (float)139.6917, 70));
//        dataPoints.add(new DataPoint((float)19.0760, (float)72.8777, 80));
//        dataPoints.add(new DataPoint((float)55.7558, (float)37.6176, 45));
//        dataPoints.add(new DataPoint((float)41.9028, (float)12.4964, 68));