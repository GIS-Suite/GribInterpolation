package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.InverseWeightedDataInterpolator;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.gissuite.gribinterpolation.core.InverseWeighted.inverseWeighted;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InverseWeightedDataInterpolatorTest {
    @Test
    public void InverseWeightedDataInterpolator_Should_Return_ArrayList1(){

        //sample data points
//        ArrayList<DataPoint> dataPoints = new ArrayList<>();
//        dataPoints.add(new DataPoint((float)-118, (float)34, 72));
//        dataPoints.add(new DataPoint((float)-74, (float)40, 65));
//        dataPoints.add(new DataPoint((float)-0.1, (float)51, 55));
//        dataPoints.add(new DataPoint((float)151, (float)-33, 78));
//        dataPoints.add(new DataPoint((float)-100, (float)40, NaN));
//        dataPoints.add(new DataPoint((float)150, (float)-30, NaN));
//        dataPoints.add(new DataPoint((float)-70, (float)42, NaN));
        int x = 5;
        int y = 5;
        int z = 10;

        ArrayList<ArrayList<ArrayList<DataPoint>>> dataPoints = new ArrayList<>();

        for (int i = 0; i < x; i++) {
            ArrayList<ArrayList<DataPoint>> innerList1 = new ArrayList<>();
            for (int j = 0; j < y; j++) {
                ArrayList<DataPoint> innerList2 = new ArrayList<>();
                for (int k = 0; k < z; k++) {
                    innerList2.add(null); // Add default value, or leave empty
                }
                innerList1.add(innerList2);
            }
            dataPoints.add(innerList1);
        }


        //check that InverseWeightedDataInterpolator returns the correct data points
        InverseWeightedDataInterpolator.DataInterpolator(dataPoints);

//        float expected1 = (float)67.913185;
//        float expected2 = (float)77.125404;
//        float expected3 = (float)65.18114;
//        DataPoint result1 = dataPoints.get(4);
//        DataPoint result2 = dataPoints.get(5);
//        DataPoint result3 = dataPoints.get(6);


        //test
//        assertEquals(expected1, result1.getTemperatureK());
//        assertEquals(expected2, result2.getTemperatureK());
//        assertEquals(expected3, result3.getTemperatureK());

        //check that all points have a temp value
        for (DataPoint dataPoint : dataPoints) {
            Assertions.assertNotEquals(dataPoint.getTemperatureK(), NaN);
        }

    }
}
