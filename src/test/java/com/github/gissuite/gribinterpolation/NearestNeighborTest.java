package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.NearestNeighbor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NearestNeighborTest {
    @Test
    public void distanceTest(){
        //anna pls give test data here
        double latitude1 = 1500;
        double longitude1 = 1600;
        double latitude2 = 1300;
        double longitude2 = 600;
        double expectedResult = 1500;

        double result = NearestNeighbor.haverSine(latitude1,longitude1,latitude2,longitude2);
        assertEquals(expectedResult, result);
    }
}


