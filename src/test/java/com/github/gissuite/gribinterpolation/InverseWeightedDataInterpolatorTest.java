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
        //sample data points
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        dataPoints.add(new DataPoint((float)-118, (float)34, 72));
        dataPoints.add(new DataPoint((float)-74, (float)40, 65));
        dataPoints.add(new DataPoint((float)-0.1, (float)51, 55));
        dataPoints.add(new DataPoint((float)151, (float)-33, 78));
        dataPoints.add(new DataPoint((float)70, (float)19, NaN));
        dataPoints.add(new DataPoint((float)150, (float)-30, NaN));
        dataPoints.add(new DataPoint((float)-70, (float)42, NaN));

        //check that InverseWeightedDataInterpolator returns the correct data points
        InverseWeightedDataInterpolator.DataInterpolator(dataPoints);
        for (DataPoint dataPoint : dataPoints){
            System.out.println(dataPoint.getTemperatureK());
        }

        float expected1;
        float expected2;
        float expected3;
        DataPoint result1 = dataPoints.get(4);
        DataPoint result2 = dataPoints.get(5);
        DataPoint result3 = dataPoints.get(6);
//        System.out.println("Expected temp of 1st: " + expected);
//        System.out.println("Actual Inverse Weighted Average: " + result.getTemperatureK());

        //test
//        assertEquals(expected1, result1.getTemperatureK());
//        assertEquals(expected2, result2.getTemperatureK());
//        assertEquals(expected3, result3.getTemperatureK());

    }
}
