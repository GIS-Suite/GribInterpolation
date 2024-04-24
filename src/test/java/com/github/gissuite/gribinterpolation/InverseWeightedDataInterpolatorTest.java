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

        int x = 5;
        int y = 5;
        int z = 5;

        ArrayList<ArrayList<ArrayList<DataPoint>>> dataPoints = new ArrayList<>();

        for (int i = 0; i < x; i++) {
            ArrayList<ArrayList<DataPoint>> innerList1 = new ArrayList<>();
            for (int j = 0; j < y; j++) {
                ArrayList<DataPoint> innerList2 = new ArrayList<>();
                for (int k = 0; k < z; k++) {
                    innerList2.add(null); // Add default value, or leave empty
                    if (k == 0 || k == 2 || k == 4){
                        innerList2.add(new DataPoint(i, j, (float)Math.random(), k));
                    }
                    else{
                        innerList2.add(new DataPoint(i, j, NaN, k));
                    }
                }
                innerList1.add(innerList2);
            }
            dataPoints.add(innerList1);
        }

        for (ArrayList<ArrayList<DataPoint>> dataPoint : dataPoints) {
            for (ArrayList<DataPoint> point : dataPoint) {
            Assertions.assertNotEquals(point.getTemperatureK(), NaN);
        }}

        for (ArrayList<ArrayList<DataPoint>> point : dataPoints) {
            for (ArrayList<DataPoint> points : point) {
                for (int k = 0; k < points.size(); k++) {
                    System.out.print(points.get(k) + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }


        //check that InverseWeightedDataInterpolator returns the correct data points
        //InverseWeightedDataInterpolator.DataInterpolator(dataPoints);

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


    }

