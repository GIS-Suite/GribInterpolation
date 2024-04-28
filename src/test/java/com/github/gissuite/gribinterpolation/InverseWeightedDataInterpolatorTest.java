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


        for (DataPoint dataPoint : innerList2) {
                Assertions.assertNotEquals(dataPoint.getTemperatureK(), NaN);
        }
    }
}

