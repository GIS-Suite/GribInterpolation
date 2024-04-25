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
    public void InverseWeightedDataInterpolator_Should_Return_ArrayList1() {

        int x = 5;
        int y = 5;
        int z = 5;

        ArrayList<ArrayList<ArrayList<DataPoint>>> dataPoints = new ArrayList<>();

        ArrayList<ArrayList<DataPoint>> innerList1 = null;
        ArrayList<DataPoint> innerList2 = null;
        for (int i = 0; i < x; i++) {
            innerList1 = new ArrayList<>();
            for (int j = 0; j < y; j++) {
                innerList2 = new ArrayList<>();
                for (int k = 0; k < z; k++) {
                    if (k == 0 || k == 2 || k == 4) {
                        innerList2.add(new DataPoint(i, j, (float) Math.random(), k));
                    } else {
                        innerList2.add(new DataPoint(i, j, NaN, k));
                    }
                }
                innerList1.add(innerList2);
            }
            dataPoints.add(innerList1);
        }
        InverseWeightedDataInterpolator.DataInterpolator(dataPoints);


        for (ArrayList<ArrayList<DataPoint>> dataPoints1 : dataPoints){
            for (ArrayList<DataPoint> points : dataPoints1){
                for (DataPoint point : points ){
                    for (int k = 0; k < innerList1.size(); k++) {
                        System.out.print("(" + point.getLatitude() + ", " + point.getLongitude() + ", " + point.getTemperatureK() + ", " + point.getDepth() + ") ");
                    }
                    System.out.println();

                }
            }
        }
        System.out.println();



        for (DataPoint dataPoint : innerList2) {
                Assertions.assertNotEquals(dataPoint.getTemperatureK(), NaN);
        }




        //check that InverseWeightedDataInterpolator returns the correct data points


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


    }}

